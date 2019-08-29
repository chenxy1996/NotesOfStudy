from django.contrib import admin
from .models import Device_user, Device

# Register your models here.
admin.site.register(Device_user)
admin.site.register(Device)

# admin.site.site_title="自定义"
admin.site.site_header="温湿度自记仪后台管理"