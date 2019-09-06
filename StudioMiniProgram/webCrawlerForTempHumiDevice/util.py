import requests
import json
import random
from datetime import datetime
from configure import USER_NAME, PASSWORD



class Account(object):
    '''账号类：每个账户下的设备信息'''

    headers_list = [
        {"User-Agent" : "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.81 Safari/537.36"},
        {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134"},
    ]

    def __init__(self, user_name = USER_NAME, password = PASSWORD):
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
                "defalutLatitude": each["latitude"],
                "defalutLongitude": each["longitude"],
                "address": each["address"],
                "time": each["lastCodeTimeStr"],
                "temperature": each["sensor1"],
                "humidity": each["sensor2"]
            })
        return data



class DataOfTH(Account): # 温湿度记录仪
    '''class : Data of temperature and humidity'''

    def __init__(self, id = 159147, source = "http://www.e-elitech.cn/deviceDataAction.do?method=getGridData"):
        # The id of target device
        self.device_id = id
        self.source = source
        self.post_data = {
            "device.id": self.device_id,
            "rows" : 1000,
            # "startTime": "2019-02-19 00:00:00",
            # "endTime": "2019-02-19 23:59:59",
            # "page" : 1,
            #rows of each day : 96,
        }
        self.default_start_time = None

    def getJson(self):
        headers = self.selectHeaders()
        response = requests.post(self.source, data = self.post_data, headers = headers)
        return response.json()["rows"]
    
    def getNum(self, num):
        now = self.currentTime()
        self.post_data["startTime"] = now.strftime("%Y-%m-%d") + " 00:00:00"
        self.post_data["endTime"] = now.strftime("%Y-%m-%d") + " 23:59:59"
        self.post_data["rows"] = num

        ret = self.getJson()
        self.post_data["rows"] = 1000
        return ret
    
    # 默认是没有的
    def getPeriod(self, start_time = None, end_time = None):
        now = self.currentTime()
        if not start_time:
            start_time = now.strftime("%Y-%m-%d") + " 00:00:00"
        if not end_time:
            end_time = now.strftime("%Y-%m-%d") + " 23:59:59"
        self.post_data["startTime"] = start_time
        self.post_data["endTime"] = end_time

        ret = self.getJson()
        return ret
    
    # 得到一个生成器
    def parseJsonData(self, json):
        ret = []
        for row in json:
            raw_data_time = row["dataTime"]
            data_time = raw_data_time[0:4] + "/" + raw_data_time[4:6] + "/" + raw_data_time[6:8] + " " + raw_data_time[8:10] + ":" + raw_data_time[10:12] + ":" + raw_data_time[12:14]
            data_temper = row["sensor1"] + "℃" # temperature 
            data_relative_humidity = row["sensor2"] + "%RH" # relative humidity
            
            ret.append({
                "Time" : data_time,
                "Temperature" : data_temper,
                "Relative humidity" : data_relative_humidity,
            })
        return ret


class WeatherStation(object):
    '''气象台'''

    headers_list = [
        {"User-Agent" : "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.81 Safari/537.36"},
        {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134"},
    ]

    def __init__(self, id):
        self.id = str(id)
        self.source = "http://envbox.net:9090/surf/surf_chn_mul_hor?station=" + self.id
    
    def selectHeaders(self):
        return self.headers_list[random.randint(0, len(self.headers_list) - 1)]
    
    def getWeatherData(self):
        res_data = requests.get(self.source, headers = self.selectHeaders()).json()
        return res_data



def insert_to_database(mongodb_config, data_list=None):
    '''将数据存入 mongodb 数据库当中。 
    data_list 为列表, 形式为[{...}, {...}, ...]
    '''
    
    db_url = "mongodb://%s:%s@%s:%s/%s" % (
        mongodb_config["user"],
        mongodb_config["pwd"],
        mongodb_config["ip"],
        mongodb_config["port"],
        mongodb_config["db"]
    )

    ret = {
        "count": 0,
        "executing_time": None,
        "inserted": [],
    }

    # 如果没有引入 pymongo 中 MongoClient类
    try:
        client = MongoClient(db_url)
    except NameError:
        from pymongo import MongoClient
        client = MongoClient(db_url)

    db = client["monitor"]

    for each_data in data_list:
        col = db[str(each_data["id"])]
        
        # 如果当前 collectoion 中没有相应的 document, 则存入此 collection
        if not col.find_one({"time": each_data["time"]}):
            ret["count"] += 1
            col.insert_one(each_data)
            ret["inserted"].append({
                "name": each_data["name"],
                "id": each_data["id"],
                "time": each_data["time"]
            })
        ret["executing_time"] = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    client.close()
    client = None

    return ret

if __name__ == "__main__":
    from configure import MONGODB_CONFIGURE
 
    user = Account(USER_NAME, PASSWORD)
    data_list = user.getDevicesInfo()
    print(data_list)
    # print(insert_to_database(MONGODB_CONFIGURE, data_list))


        



        
