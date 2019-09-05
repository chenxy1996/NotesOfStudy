'''
监听返回特定设备温湿度数据请求
'''

from .models import Device
from myUtils.db_utils import get_data_from_database
from configure import MAX_DATA_REQUEST_NUMBER, MONGODB_CONFIGURE
from django.http import JsonResponse


def get_device_temp_humi(request, id, num=5):
    '''返回特定 id 设备的近期 num 条数据'''
    return_json_data = {
        "status": None,
        "msg": None,
        "data": {
            "data": None
        }
    }

    # 检测请求的数据条数有没有超过预先设定的最大请求数量
    if num <= MAX_DATA_REQUEST_NUMBER:
        raw_data = get_data_from_database(MONGODB_CONFIGURE, query={},\
                col_list=[str(id)], projection={"_id": 0, "name": 0, "guid": 0, "id": 0},\
                                        sort_query=[("_id", -1)], limit_num=num)[0]
        try:
            filter = ["device_user_id", "device_name", "device_id",\
                            "device_guid", "device_setted_latitude",\
                                    "device_setted_longitude", "device_address"]
            target = Device.objects.filter(device_id = id).values(*filter)[0]
            # 之后的想法：
            # 只返回已经注册的设备信息，没有注册的设备信息不返回。除非注册：
            # 注册url info/device/register
            for key, value in target.items():
                return_json_data["data"][key] = value
            return_json_data["data"]["data"] = raw_data
            return_json_data["msg"] = "success"
            return_json_data["status"] = 1
        
        # 如果出现 IndexError 说明某个设备还没有注册，即在
        # 上面 Device.objects.filter(device_id = id) 这一步出错
        except IndexError:
            return_json_data["status"] = 0
            return_json_data["msg"] = "The device must be registerred before!"
        # 出现其他错误，将错误写入 return_json_data 的 msg 字段返回
        except Exception as e:
            return_json_data["status"] = 0
            return_json_data["msg"] = str(e)

    # 超过了最大请求数量
    else:
        return_json_data["status"] = 0
        return_json_data["msg"] = "queried data count should never be greater than %d"\
                                            % MAX_DATA_REQUEST_NUMBER

    return JsonResponse(return_json_data, safe=False)