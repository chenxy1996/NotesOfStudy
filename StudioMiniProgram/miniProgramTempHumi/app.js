//app.js
const get = require("/utils/http.js").get;

App({
  /*app 对象静态方法：格式化从远处服务器中获得的设备信息数据*/
  formatDevicesInfo(devicesRawInfo) {
    const propertyNames = Object.getOwnPropertyNames(devicesRawInfo[0]);
    const devicesInfo = [];

    devicesRawInfo.forEach(eachDevice => {
      const device = {};
      propertyNames.forEach(eachPropertyName => {
        const splitName = eachPropertyName.split('_');
        device[splitName[splitName.length - 1]] = eachDevice[eachPropertyName];
      });
      devicesInfo.push(device);
    });

    return devicesInfo;
  },

  /*app 对象静态方法：从远处服务器中获得设备信息数据，并格式化*/
  fetchDevicesInfo() {
    return new Promise((resolve) => {
      const fetchDataPromise = get("https://www.chen1996.com/info/devices/");

      fetchDataPromise.then(data => {
        const devicesData = data.data; // 得到未经格式化的原始数据;
        let devicesInfo;

        if (devicesData.status === 1) {
          // 如果有设备信息，非空列表
          // 格式化数据
          const devicesRawInfo = devicesData.data;
          devicesInfo = this.formatDevicesInfo(devicesRawInfo);
          // resolve(devicesInfo)
        } else {
          // 无设备信息，空列表
          // 则将 devicesInfo 改为对象, 附带一个 msg 字段消息
          devicesInfo = {"msg": "no devices"};
        }
        resolve(devicesInfo);
      });
      fetchDataPromise.catch((err) => console.log(err));
    })
  },

  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    });

    const fetchDevicesInfoPromise = this.fetchDevicesInfo();
    fetchDevicesInfoPromise.then(data => {
      this.globalData.devicesInfo = data;
      // 由于 fetchDevicesInfoPromise 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      if (this.devicesInfoReadyCallbak) {
        this.devicesInfoReadyCallbak(data);
      }
    })
  },

  globalData: {
    mapMarkerPath: "/static/image/location.png",
    userInfo: null,
    devicesInfo: null
  }
})