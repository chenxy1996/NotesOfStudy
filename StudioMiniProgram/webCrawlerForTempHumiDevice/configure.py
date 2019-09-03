'''Global configure 全局设置'''

# 爬取网页用户名和密码
USER_NAME = "15773122754"
PASSWORD = "123456"

# mongodb 数据库连接设置
MONGODB_CONFIGURE = {
    "user": "chen",
    "pwd": "chen",
    "ip": "101.132.47.221",
    # "ip": "127.0.0.1", # 部署到服务器端
    "port": "27017",
    "db": "monitor",
}

# 程序运行设置
INTERVAL = 10 * 60 # 每隔 INTERVAL 启动爬虫主程序。 单位：秒
