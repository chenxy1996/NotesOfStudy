# 直接执行当前文件, 测试用, 线上部署时候可以删除 else 前面的
if __name__ == "__main__" and __package__ is None:
    import sys
    import os
    sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

    from models import Device_user, Device
else:
    from .models import Device_user, Device


from django.views import View
from django.http import HttpResponse, JsonResponse
# from django.shortcuts import redirect
# from django.urls import reverse

from my_util.test_util import get_one_arbitary_data, get_arbitary_data_list
from my_util.util import Account, get_data_from_database
from configure import MAX_DATA_REQUEST_NUMBER, USER_NAME, PASSWORD, MONGODB_CONFIGURE


class Mimicking_Info_Sender(View):
    '''
    在 Monitor 类还没有写成时使用
    测试用：Mimicking_Info_Sender： 模拟从数据库中得到 http request 需要的数据并发送 json 数据对象
    '''
    def get(self, request, num):
        return_json_data = {}

        if num <= MAX_DATA_REQUEST_NUMBER:
            return_json_data["data"] = get_arbitary_data_list(num)
            return_json_data["dataNum"] = num
        else:
            return_json_data["data"] = []
            return_json_data["dataTotalNumber"] = 0

        return JsonResponse(return_json_data, safe=False)


class Monitor(View):
    '''监听从数据库中获得数据信息的请求'''
    def get(self, request, num):
        return_json_data = {
            "status": None,
            "msg": None,
            "data": []
        }

        # 检测请求的数据条数有没有超过预先设定的最大请求数量
        if num <= MAX_DATA_REQUEST_NUMBER:
            raw_data = get_data_from_database(MONGODB_CONFIGURE, {},\
                                    {"_id": 0, "name": 0, "guid": 0, "id": 0},\
                                         sort_query=[("_id", -1)], limit_num=num)
            for each in raw_data:
                id = each["id"]
                try:
                    filter = ["device_user_id", "device_name", "device_id",\
                                 "device_guid", "device_setted_latitude",\
                                            "device_setted_longitude", "device_address"]
                    target = Device.objects.filter(device_id = id).values(*filter)[0]
                    # 之后的想法：
                    # 只返回已经注册的设备信息，没有注册的设备信息不返回。除非注册：
                    # 注册url info/device/register
                    for key, value in target.items():
                        each[key] = value
                    return_json_data["data"].append(each)
                    return_json_data["msg"] = "success"
                    return_json_data["status"] = 1
                
                # 如果出现 IndexError 说明某个设备还没有注册，即在
                # 上面 Device.objects.filter(device_id = id) 这一步出错
                # 继续进行
                except IndexError:
                    # 之前的想法：
                    # 数据库中还有设备没有在 django 自带的 models 中注册, 
                    # 自动使用 redirect 方法重新向至 /info/device/register 页面注册
                    # 新设备
                    # return redirect(reverse("register"))
                    pass
                except Exception as e:
                    return_json_data["status"] = 0
                    return_json_data["msg"] = str(e)

        # 超过了最大请求数量
        else:
            return_json_data["status"] = 0
            return_json_data["msg"] = "queried data count should never be greater than %d"\
                                              % MAX_DATA_REQUEST_NUMBER
    
        return JsonResponse(return_json_data, safe=False)


class Register(View):
    '''注册新设备： 检测是否有新设备，如果有，将设备信息加入至数据库中'''
    def get(self, request):
        ret_info = {
            "count": 0,
            "registerred": []
        }

        user = Account(user_name = USER_NAME, password = PASSWORD)
        device_info = user.getDevicesInfo()

        for each_device in device_info:
            try:
                target = Device.objects.filter(device_id = each_device["id"])

                if not target:
                    inserted_item = Device(
                        device_name = each_device["name"],
                        device_id = each_device["id"],
                        device_guid = each_device["guid"],
                        device_setted_latitude = each_device["defaultLatitude"],
                        device_setted_longitude = each_device["defaultLongitude"],
                        device_address = each_device["address"],
                    )
                    
                    inserted_item.save()
                    ret_info["count"] += 1
                    ret_info["registerred"].append(each_device)

            except Exception:
                ret_info["msg"] = str(Exception)
            
        return JsonResponse(ret_info, safe=False)

def update():
    ret_info = {}

    user = Account(user_name = USER_NAME, password = PASSWORD)
    device_info = user.getDevicesInfo()
    for each_device in device_info:
        
        try:
            target = Device.objects.filter(device_id = each_device["id"])

            if target:
                target.update(
                device_setted_latitude = each_device["defaultLatitude"],
                device_setted_longitude = each_device["defaultLongitude"],    
                )
                ret_info="已经更新"
            
            else:
                inserted_item = Device(
                    device_name = each_device["name"],
                    device_id = each_device["id"],
                    device_guid = each_device["guid"],
                    device_setted_latitude = each_device["defaultLatitude"],
                    device_setted_longitude = each_device["defaultLongitude"],
                    device_address = each_device["address"],
                )

                inserted_item.save()
                ret_info = "导入新设备信息成功"

        except Exception:
            ret_info = Exception
        
    return HttpResponse(ret_info)
    

if __name__ == "__main__":
    data = get_data_from_database(MONGODB_CONFIGURE, {}, {"_id": 0, "time": 1, "temperature": 1, "humidity": 1}, sort_query=[("_id", -1)], limit_num=5)
    print(data)
        
