'''
监听返回或修改设备信息的请求
'''

from django.views import View
from .models import Device
from django.http import JsonResponse


class Devices_monitor(View):
    '''
    返回或修改已经注册设备信息的请求监听器
    '''
    def get(self, request):
        '''返回已经注册的设备的信息'''
        return_json_data = {
            "status": None,
            "msg": None,
            "data": {
                "data": None
            }
        }
        filter = ["device_user_id", "device_name", "device_id",\
                                "device_guid", "device_setted_latitude",\
                                        "device_setted_longitude", "device_address"]
        devices_list = Device.objects.filter().values(*filter)

        # 如果有设备已经注册
        if devices_list:
            return_json_data["data"] = list(devices_list)
            return_json_data["msg"] = "success"
            return_json_data["status"] = 1
        # 没有设备注册
        else:
            return_json_data["msg"] = "no devices registerred"
            return_json_data["status"] = 0

        return JsonResponse(return_json_data, safe=False)
    
    
    def post(request):
        '''修改已经注册设备的有关信息'''
        pass