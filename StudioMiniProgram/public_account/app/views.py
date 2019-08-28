from django.shortcuts import render
from django.views import View
from django.http import HttpResponse, HttpResponseRedirect
from django.views.decorators.csrf import csrf_exempt
from django.utils.decorators import method_decorator
import hashlib
import xml.etree.ElementTree as ET
import time

# api
from .api import *
import requests
import json
import random
from datetime import datetime

# models
from .models import User, Device


# Create your views here.
class Wechat(View):
    token = 'chen'
    
    # 装饰符：如果没有这个post请求(发送信息到客户端)会是http/1.1 403
    @method_decorator(csrf_exempt)
    def dispatch(self, request, *args, **kwargs):
        return super().dispatch(request, *args, **kwargs)

    
    def get(self, request):
        # 是微信的验证请求还是网页请求？
        print("-"*50) # 控制台打印分隔线
        if request.GET:
            # 微信验证
            print("This quest is the varification from weixin.")
            request_body = request.GET
            signature = request_body.get('signature', None)
            timestamp = request_body.get('timestamp', None)
            nonce = request_body.get('nonce', None)
            echostr = request_body.get('echostr', None)
            hashlist = [self.token, timestamp, nonce]
            hashlist.sort()
            hashstr = ''.join(hashlist)
            hashcode = hashlib.sha1()
            hashcode.update(hashstr.encode())
            hashcode = hashcode.hexdigest()
            if hashcode == signature:
                return HttpResponse(echostr)
        else:
            # 网页请求
            print("This quest is from a website.")
            # a = User.objects.get()
            # return HttpResponse("lele is the most beautiful and beloved dog in the world")
            # return HttpResponse(updateDatabase())

            return HttpResponse(traceInfo())
            # return HttpResponse(a.user_name + a.user_password)
    
    
    def post(self, request):
        print("-"*50)
        default = "输入:(无需区别大小写)\n" + "-" * 22 + "-" * 24 + "\ndev\n\n———最新设备温湿度信息\n\n" +  "-" * 22 + "\nwea[气象台编号]\n\n———昨日对应气象台数据(若只输入wea则为wea57494得到武汉站数据)\n\n" + "-" * 22 + "\nupdate\n\n———更新设备信息\n\n"
        webData = request.body
        xmlData = ET.fromstring(webData)
        msg_type = xmlData.find('MsgType').text
        ToUserName = xmlData.find('ToUserName').text
        FromUserName = xmlData.find('FromUserName').text
        CreateTime = xmlData.find('CreateTime').text
        receive_content = xmlData.find('Content').text
 
        toUser = FromUserName
        fromUser = ToUserName
 
        if msg_type == "text":
            receive_content = receive_content.lower().replace(' ', '')
            reply_content = reply(receive_content)
            if not reply_content:
                reply_content = default            
        else:
            reply_content = default
        
        dict_ = {}
        dict_["toUser"] = toUser
        dict_["fromUser"] = fromUser
        dict_['nowtime'] = int(time.time())
        dict_["content"] = reply_content
        return render(request, 'app/mes.xml', dict_)



# 更新数据库(设备信息)
def updateDatabase():
    ret_info = "设备信息已是最新，无需更新."
    users = User.objects.all()

    for each_user in users:
        user = Account(user_name = each_user.user_name, password = each_user.user_password)
        device_info = user.getDevices()
        for each_device in device_info:
            try:
                device = Device.objects.get(device_id = each_device["id"])
            except Device.DoesNotExist:
                ret_info = "已经更新设备信息！"
                each_user.device_set.create(
                    device_name = each_device["name"],
                    device_id = each_device["id"],
                    device_guid = each_device["guid"],
                    device_latitude = each_device["latitude"],
                    device_longitude = each_device["longitude"],
                    device_address = each_device["address"],
                )
    
    return ret_info

def reply(receive_content):
    ret = ""

    if receive_content == "update":
        ret = updateDatabase()
    elif receive_content == "dev":
        ret = traceInfo(5)
    elif receive_content == "cxk":
        ret = '''链接：https://pan.baidu.com/s/11CmaTEO0Vz06Mv-_WA9CQw 提取码：z07z '''
    else:
        if receive_content[0:3] == "wea":
            station_id = receive_content[3:]
            if station_id:
                ret = weatherProcess(id = station_id)
            else:
                ret = weatherProcess()
    
    return ret
        
    

# 获取最新的所有设备的状况，并格式化输出
# num 为获得几个数据
def traceInfo(num = 2):
    sep = "*" * 22
    ret = "温湿度设备数量: "
    ret += str(Device.objects.count()) + '\n'
    device_list = Device.objects.all()

    for each_device in device_list:
        ret += '\n' + sep + '\n' + sep
        template = """
设备名称: {0}
设备ID: {1}
地址: {2}
"""
        template = template.format(each_device.device_name, each_device.device_id, each_device.device_address)
        
        device = DataOfTH(id = each_device.device_id)
        device_data = device.parseJsonData(device.getNum(num))
        template += '-' * 22
        for each in device_data:   
            template += '\n' + each["Time"] + '\n' + "Temp: " + each["Temperature"] + '\n' + "RHum: " + each["Relative humidity"] + '\n'
        
        ret += template
    
    return ret

def weatherProcess(id = 57494):
    station = WeatherStation(id)
    sep = "*" * 22
    try:
        data = station.getWeatherData()
        ret = str(data[0]["year"]) + '/' + str(data[0]["mon"]) + '/' + str(data[0]["day"]) +"\n站台%s " % id +  '\n'
        # count = 0
        for each in data:
            # count += 1
            # if count == 2:
            #     break
            ret += sep
            ret += "时次: " + str(each["hour"]) + '\n'
            ret += "Temp: " + str(each["tem"]) + "℃" + '\n'
            ret += "RHum: " + str(each["rhu"]) + "%" + '\n'
            # ret += "气压: " + str(each["prs"]) + '\n'
            # ret += "2分钟平均风向(角度): " + str(each["winDAvg2mi"]) + '\n'
            # ret += "2分钟平均风速: " + str(each["winSAvg2mi"]) + '\n'
            # ret += "水汽压: " + str(each["vap"]) + '\n'
            ret += "降水量: " + str(each["pre1h"]) + '\n'  
    except:
        ret = "Wrong Station ID!"
    
    return ret
            

# 微信的文本回复类
# class ReplyText():
#     def __init__(self, to_user, from_user, msg_type, content):
#         self.to_user = to_user
#         self.from_user = from_user
#         self.time = int(time.time())
#         self.msg_type = msg_type
#         self.content = content
#         self.raw_text = """<xml><ToUserName><![CDATA[{0}]]></ToUserName><FromUserName><![CDATA[{1}]]></FromUserName><CreateTime>{2}</CreateTime><MsgType><![CDATA[{3}]]></MsgType><Content><![CDATA[{4}]]></Content></xml>"""
    
#     def send(self):
#         return self.raw_text.format(self.to_user, self.from_user, self.time, self.msg_type, self.content)



