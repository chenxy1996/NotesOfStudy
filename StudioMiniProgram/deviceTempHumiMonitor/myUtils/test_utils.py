'''
前端测试接口需要的 utils （还未写正式接口时拱前端开发所用）
'''

from datetime import datetime
from random import randint, random


def get_one_arbitary_data():
    '''
    返回任意一个随机字典对象数据
    '''
    return {
        "time": datetime.now().strftime("%b %d %Y %H:%M:%S"),
        "temperature": str((randint(10, 39) + random()))[0:5],
        "humidity": str((randint(10, 70) + random()))[0:4] + "%",
    }


def get_arbitary_data_list(nums=0):
    '''
    返回多个随机字典对象数据，封装在列表中
    '''
    return_data = []
    i = 0

    while i < nums:
        return_data.append(get_one_arbitary_data())
        i += 1

    return return_data