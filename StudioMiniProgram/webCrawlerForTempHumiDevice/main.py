import os
from configure import * 
from util import Account, insert_to_database
import time
import json


def main():
    # log_path 为程序运行日志的路径
    log_path = os.path.join(os.path.dirname(os.path.abspath(__file__)), "execute.log")
    while True:
        user = Account(USER_NAME, PASSWORD)
        data_list = user.getDevicesInfo()
        with open(log_path, "a+", encoding="utf-8") as f:
            ret_info = json.dumps(insert_to_database(MONGODB_CONFIGURE, data_list)) + '\n'
            f.write(ret_info)

        time.sleep(INTERVAL)


if __name__ == "__main__":
    main()