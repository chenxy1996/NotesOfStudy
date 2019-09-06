from django.urls import path
from django.contrib import admin

from .views_devices import Devices_monitor
from .views_register import Register
from .views_temp_humi import get_device_temp_humi


urlpatterns = [
    # 路由：返回特定设备温湿度信息
    path('get/id=<int:id>/', get_device_temp_humi), 
    path('get/id=<int:id>/<int:num>/', get_device_temp_humi, name="get_device_temp_humi"),
    # 路由：注册新设备 
    path("devices/register/", Register.as_view(), name="register"),
    # 路由：返回所有已注册设备的信息
    path("devices/", Devices_monitor.as_view(), name="get_devices_info"),  
]