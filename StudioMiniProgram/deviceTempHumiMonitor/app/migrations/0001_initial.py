# Generated by Django 2.1.7 on 2019-09-01 00:56

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Device',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('device_name', models.CharField(max_length=60, null=True)),
                ('device_id', models.CharField(max_length=60, null=True)),
                ('device_guid', models.CharField(max_length=60, null=True)),
                ('device_setted_latitude', models.CharField(max_length=60, null=True)),
                ('device_setted_longitude', models.CharField(max_length=60, null=True)),
                ('device_address', models.CharField(max_length=60, null=True)),
            ],
        ),
        migrations.CreateModel(
            name='Device_user',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('user_atlas', models.CharField(max_length=30)),
                ('user_name', models.CharField(max_length=30)),
                ('user_password', models.CharField(max_length=30)),
            ],
        ),
        migrations.AddField(
            model_name='device',
            name='device_user',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='app.Device_user'),
        ),
    ]