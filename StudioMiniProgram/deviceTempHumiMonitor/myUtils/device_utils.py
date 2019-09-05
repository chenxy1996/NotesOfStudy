'''
直接从网页上获取取设备信息的 utils 
'''

from datetime import datetime
import requests
import random

# 登陆爬取网页的默认的用户名和密码
DEFAULT_USER_NAME = "15773122754"
DEFAULT_PASSWORD = "123456"


class Account(object):
    '''账号类：每个账户下的设备信息'''

    headers_list = [
        {"User-Agent" : "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.81 Safari/537.36"},
        {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134"},
    ]

    def __init__(self, user_name = DEFAULT_USER_NAME, password = DEFAULT_PASSWORD):
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
    
    # 得到每一个温湿度记录仪的当前信息，包含每个仪器最近的一条温度、湿度信息
    def getDevicesInfo(self):
        headers = self.selectHeaders()
        login_post = {
            "user.username": self.user_name,
            "user.password": self.password,
            "checkinput": self.checkinput
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
                "defaultLatitude": each["latitude"],
                "defaultLongitude": each["longitude"],
                "address": each["address"],
                "time": each["lastCodeTimeStr"],
                "temperature": each["sensor1"],
                "humidity": each["sensor2"]
            })
        return data


if __name__ == "__main__":
    my_account = Account()
    print(my_account.getDevicesInfo())