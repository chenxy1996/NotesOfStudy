# 直接执行当前文件, 测试用, 线上部署时候可以删除
if __name__ == "__main__" and __package__ is None:
    import sys
    import os
    sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

    from models import Device_user, Device
else:
    from .models import Device_user, Device


from django.views import View
from django.http import HttpResponse, JsonResponse

from my_util.test_util import get_one_arbitary_data, get_arbitary_data_list
from my_util.util import Account
from configure import MAX_DATA_REQUEST_NUMBER, USER_NAME, PASSWORD



'''
Mimicking_Info_Sender： 模拟从数据库中得到 http request 需要的数据并发送 json 数据对象
'''
class Mimicking_Info_Sender(View):
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
    # 刷新数据库中 Device 的信息，全部设置为从目标网页爬取的原始默认信息
    def get(self, request):
        ret_info = {}

        user = Account(user_name = USER_NAME, password = PASSWORD)
        device_info = user.getDevicesInfo()
        for each_device in device_info:
            
            try:
                target_list = Device.objects.filter(device_id = each_device["id"])

                if target_list:
                    target_list.update(
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



        
