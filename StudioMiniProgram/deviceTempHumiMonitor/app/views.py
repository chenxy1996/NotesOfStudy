from django.views import View
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse

import sys
sys.path.append("..")

from my_util.test_util import *


class Mimicking_Info_Sender(View):
    def get(self, request, num):
        return_json_data = {}

        if num <= 300:
            return_json_data["data"] = get_arbitary_data_list(num)
            return_json_data["dataNum"] = num
        else:
            return_json_data["data"] = []
            return_json_data["dataTotalNumber"] = 0

        return JsonResponse(return_json_data, safe=False)
        
