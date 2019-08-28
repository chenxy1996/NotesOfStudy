import requests
import json
import random
from datetime import datetime

# User object to trace the all devices
class Account(object):

    headers_list = [
        {"User-Agent" : "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.81 Safari/537.36"},
        {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134"},
    ]

    def __init__(self, user_name = "15773122754", password = "123456"):
        self.user_name = user_name
        self.password = password
        self.checkinput = '6b9s'
        self.login_source = "http://www.e-elitech.cn/loginAction2.do?method=login"
        self.device_id_source = "http://www.e-elitech.cn/deviceAction2.do?method=getGridData"

    def currentTime(self):
        now = datetime.now()
        # return now.strftime("%Y-%m-%d %H:%M:%S")
        return now
    
    def selectHeaders(self):
        return self.headers_list[random.randint(0, len(self.headers_list) - 1)]
    
    def getDevices(self):
        headers = self.selectHeaders()
        login_post = {
            "user.username": self.user_name,
            "user.password": self.password,
            "checkinput": self.checkinput,
        }
        data = []

        # 会话窗口，用于距离cookies
        s = requests.Session()
        s.post(self.login_source, headers = headers, data = login_post)
        info = s.get(self.device_id_source, headers = headers).json()["rows"]
        for each in info:
            data.append({
                "name": each["name"],
                "id": each["id"],
                "guid": each["guid"],
                "latitude": each["latitude"],
                "longitude": each["longitude"],
                "address": each["address"],
            })
        return data



'''class : Data of temperature and humidity'''
class DataOfTH(Account): # 温湿度记录仪

    def __init__(self, id = 159147, source = "http://www.e-elitech.cn/deviceDataAction.do?method=getGridData"):
        # The id of target device
        self.device_id = id
        self.source = source
        self.post_data = {
            "device.id": self.device_id,
            "rows" : 1000,
            # "startTime": "2019-02-19 00:00:00",
            # "endTime": "2019-02-19 23:59:59",
            # "page" : 1,
            #rows of each day : 96,
        }
        self.default_start_time = None


    def getJson(self):
        headers = self.selectHeaders()
        response = requests.post(self.source, data = self.post_data, headers = headers)
        return response.json()["rows"]
    
    def getNum(self, num):
        now = self.currentTime()
        self.post_data["startTime"] = now.strftime("%Y-%m-%d") + " 00:00:00"
        self.post_data["endTime"] = now.strftime("%Y-%m-%d") + " 23:59:59"
        self.post_data["rows"] = num

        ret = self.getJson()
        self.post_data["rows"] = 1000
        return ret
    
    # 默认是没有的
    def getPeriod(self, start_time = None, end_time = None):
        now = self.currentTime()
        if not start_time:
            start_time = now.strftime("%Y-%m-%d") + " 00:00:00"
        if not end_time:
            end_time = now.strftime("%Y-%m-%d") + " 23:59:59"
        self.post_data["startTime"] = start_time
        self.post_data["endTime"] = end_time

        ret = self.getJson()
        return ret
    
    # 得到一个生成器
    def parseJsonData(self, json):
        ret = []
        for row in json:
            raw_data_time = row["dataTime"]
            data_time = raw_data_time[0:4] + "/" + raw_data_time[4:6] + "/" + raw_data_time[6:8] + " " + raw_data_time[8:10] + ":" + raw_data_time[10:12] + ":" + raw_data_time[12:14]
            #temperature
            data_temper = row["sensor1"] + "℃"

            #relative humidity
            data_relative_humidity = row["sensor2"] + "%RH"
            
            ret.append({
                "Time" : data_time,
                "Temperature" : data_temper,
                "Relative humidity" : data_relative_humidity,
            })
        return ret

class WeatherStation(object):

    headers_list = [
        {"User-Agent" : "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.81 Safari/537.36"},
        {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134"},
    ]

    def __init__(self, id):
        self.id = str(id)
        self.source = "http://envbox.net:9090/surf/surf_chn_mul_hor?station=" + self.id
    
    def selectHeaders(self):
        return self.headers_list[random.randint(0, len(self.headers_list) - 1)]
    
    def getWeatherData(self):
        res_data = requests.get(self.source, headers = self.selectHeaders()).json()
        return res_data

# test = WeatherStation(57494)
# print(test.getWeatherData())
# def weatherProcess(id = 57494):
#     station = WeatherStation(id)
#     sep = "*" * 22
#     try:
#         data = station.getWeatherData()
#         ret = "站台%s " % id + str(data[0]["year"]) + '/' + str(data[0]["mon"]) + '/' + str(data[0]["day"]) + '\n'
#         data = data[::-1]
#         for each in data:
#             ret += sep
#             ret += "时次: " + str(each["hour"]) + "℃" + '\n'
#             ret += "Temp: " + str(each["tem"]) + "℃" + '\n'
#             ret += "RHum: " + str(each["rhu"]) + "%" + '\n'
#             ret += "气压: " + str(each["prs"]) + '\n'
#             ret += "2分钟平均风向(角度): " + str(each["winDAvg2mi"]) + '\n'
#             ret += "2分钟平均风速: " + str(each["winSAvg2mi"]) + '\n'
#             ret += "水汽压: " + str(each["vap"]) + '\n'
#             ret += "降水量: " + str(each["pre1h"]) + '\n'  
#     except:
#         ret = "Wrong Station Id"
    
#     return ret

# def foo(x):
#     if x[0:7] == "weather":
#         station_id = x[7:]
#         ret = weatherProcess(station_id)
#     print(ret)

# foo("weather000")
        



        
