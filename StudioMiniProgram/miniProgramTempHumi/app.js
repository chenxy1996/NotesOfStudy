//app.js
const get = require("/utils/http.js").get;

App({
  /**
   * app 对象静态方法：格式化从远处服务器中获得的设备信息数据
   */
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

  /**
   * app 对象静态方法：从远处服务器中获得设备信息数据，并格式化
   */
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
          devicesInfo = [];
        }
        resolve(devicesInfo);
      });
      fetchDataPromise.catch((err) => console.log(err));
    });
  },

  /**
   * app 静态方法: 远程请求 各个设备信息
   * 在 pageObj 这个 Page 对象上显示各个设备的位置 marker
   */
  showDevicesLocation(pageObj) {
    this.fetchDevicesInfo().then(devicesInfo => {
      // 如果当前页面没有 mapCtx
      // 注册一个新的地图对象
      if (!pageObj.mapCtx) {
        pageObj.mapCtx = wx.createMapContext('myMap');
      }
      pageObj.setData({
        hasMarkers: true
      });
      // 说明有已经注册的设备信息
      if (devicesInfo.length > 0) {
        // 给每个设备信息添加 iconPath 位置
        devicesInfo.forEach(eachDevice => {
          eachDevice.iconPath = this.globalData.mapMarkerPath
        });
        pageObj.setData({
          markers: devicesInfo
        });
        
        // 调用页面对象的 includePoints 方法
        pageObj.includePoints();
      } else {
        // 如果返回的设备信息数组长度为0，说明没有已经注册的设备
        // 弹窗显示没有已经注册的设备信息，请先注册.
        // 然后地图中心移动到定位点
        wx.showModal({
          title: '提示',
          content: "当前没有已注册设备信息，移动至定位点，请注册并更新设备信息", 
          showCancel: false,
          success(res) {
            pageObj.setData({
              // 此时 devicesInfo 为 [], 相当于清空地图上之前的 markers 标记
              markers: devicesInfo 
            });
            // 地图移动到定位点
            pageObj.mapCtx.moveToLocation();
          }
        });
      }
    });
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
          });
        }
      }
    });
  },

  globalData: {
    // 展示设备 marker 所用的图片的路径 
    mapMarkerPath: "/static/image/location.png",
    userInfo: null
  },

  onHide() {
  }
})