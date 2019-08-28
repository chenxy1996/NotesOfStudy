'''
前端测试所用接口
'''

from datetime import datetime
from random import randint, random

'''
返回任意一个随机字典对象数据
'''
def get_one_arbitary_data():
    return {
        "time": datetime.now().strftime("%b %d %Y %H:%M:%S"),
        "temperature": str((randint(10, 39) + random()))[0:5],
        "humidity": str((randint(10, 70) + random()))[0:4] + "%",
    }


'''
返回多个随机字典对象数据，封装在列表中
'''
def get_arbitary_data_list(nums=0):
    return_data = []
    i = 0

    while i < nums:
        return_data.append(get_one_arbitary_data())
        i += 1

    return return_data