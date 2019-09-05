'''
监听注册新设备的请求
'''

from .models import Device
from django.views import View
from django.http import JsonResponse

from myUtils.device_utils import Account
from configure import USER_NAME, PASSWORD


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
                # 如果当前设备没有被注册
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