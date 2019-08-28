from django.urls import path
from .views import Wechat
from django.contrib import admin

urlpatterns = [
    path('', Wechat.as_view()),
]