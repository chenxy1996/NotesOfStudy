from django.urls import path
from .views import Mimicking_Info_Sender, get_device_temp_humi, get_devices, Register
from django.contrib import admin

urlpatterns = [
    # nums 为所请求的 json 列表所包含的信息数量
    # path('get/<int:num>/', Mimicking_Info_Sender.as_view()),
    path('get/id=<int:id>/', get_device_temp_humi), 
    path('get/id=<int:id>/<int:num>/', get_device_temp_humi, name="get_device_temp_humi"), 
    path("devices/register/", Register.as_view(), name="register"),
    path("devices/", get_devices, name="get_devices_info"),  
]