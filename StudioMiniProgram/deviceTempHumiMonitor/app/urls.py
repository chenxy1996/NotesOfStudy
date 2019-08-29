from django.urls import path
from .views import Mimicking_Info_Sender
from django.contrib import admin

urlpatterns = [
    # nums 为所请求的 json 列表所包含的信息数量
    path('get/<int:num>/', Mimicking_Info_Sender.as_view()), 
    path("update/default/", )
]