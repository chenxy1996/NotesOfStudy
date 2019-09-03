from django.db import models

# Create your models here.
class Device_user(models.Model):
    user_atlas = models.CharField(max_length = 30)
    user_name = models.CharField(max_length = 30)
    user_password = models.CharField(max_length = 30)

    def __str__(self):
        return "User_name: " + self.user_name

class Device(models.Model):
    device_user = models.ForeignKey(Device_user, on_delete=models.CASCADE, null=True)
    device_name = models.CharField(max_length = 60, null=True)
    device_id = models.CharField(max_length = 60, null=True)
    device_guid = models.CharField(max_length = 60, null=True)
    device_setted_latitude = models.CharField(max_length = 60, null=True)
    device_setted_longitude = models.CharField(max_length = 60, null=True)
    device_address = models.CharField(max_length = 60, null=True)

    def __str__(self):
        return  str(self.device_id) + ':' + (self.device_name if self.device_name else "")