'''
没有写正式接口时候，用于前端开发提供的临时接口
'''

from my_util.test_util import get_one_arbitary_data, get_arbitary_data_list


class Mimicking_Info_Sender(View):
    '''
    在其他接口还没有写成时使用
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