// pages/map/map.js
const app = getApp();
const get = require("../../utils/http.js").get;

Page({

  /**
   * 页面的初始数据
   */
  data: {
  },
  

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // app 静态方法: 在 pageObj 这个 Page 对象上显示各个设备的位置
    // 第一次从头加载程序没有 app.showDevicesLocation，注册 app 方法
    // 后面如果再次启动当前页面的 onLoad 方法就不需要再初始化函数
    if (!app.showDevicesLocation) {
      app.showDevicesLocation = (pageObj) => {
        const devicesInfo = app.globalData.devicesInfo;
        // devicesInfo 中是数组（说明 app.globalData.devicesInfo 有已经注册的设备信息）
        if (Object.prototype.toString.call(devicesInfo) === "[object Array]") {
          devicesInfo.forEach(eachDevice => {
            eachDevice.iconPath = app.globalData.mapMarkerPath
          });
        }
        this.setData({
          markers: devicesInfo
        })

        // 注册一个新的地图对象
        this.mapCtx = wx.createMapContext('myMap');
        this.includePoints();
      };
    }

    if (app.globalData.devicesInfo) {
      app.showDevicesLocation(this);
    } else {
      // 由于 app 初始化中 fetchDevicesInfoPromise 是网络请求，
      // 可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.devicesInfoReadyCallbak = data => {
        app.showDevicesLocation(this);
      }
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   * 首次加载 onReady 是在
   */
  onReady: function (e) {
  },


  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  },

  getCenterLocation: function () {
    this.mapCtx.getCenterLocation({
      success: function (res) {
        console.log(res.longitude);
        console.log(res.latitude);
      }
    })
  },

  moveToLocation: function () {
    this.mapCtx.moveToLocation()
  },

  translateMarker: function () {
    this.mapCtx.translateMarker({
      markerId: 1,
      autoRotate: true,
      duration: 1000,
      destination: {
        latitude: 23.10229,
        longitude: 113.3345211,
      },
      animationEnd() {
        console.log('animation end')
      }
    })
  },

  includePoints: function () {
    const devicesInfo = app.globalData.devicesInfo;
    // 如果 devicesInfo 是数组，说明是含有已经注册设备信息的数组。
    if (Object.prototype.toString.call(devicesInfo) === "[object Array]") {
      console.log(this.mapCtx);
      this.mapCtx.includePoints({
        padding: [30, 30, 30, 30],
        points: devicesInfo
      });
    } else {
      // 如果没有已经注册设备的信息就弹窗提示注册并更新设备然后显示当前定位点
      const that = this;
      wx.showModal({
        title: '提示',
        content: "当前没有已注册设备信息，请注册并更新设备信息", 
        showCancel: false,
        success(res) {
          // 地图移动到定位点
          that.moveToLocation();
        }
      });
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  // 检索有没有尚未注册的设备，如果有则注册
  registerNewDevices(e) {
    const that = this;
    const newGetPromise = get("https://www.chen1996.com/info/devices/register/");

    newGetPromise.then(data => {
      const that = this;
      const count = data.data.count;
      const devicesInfo = app.formatDevicesInfo(data.data.registerred);

      // 将新注册的设备信息添加到 app.globalData.devicesInfo 中
      // 先检查 app.globalData.devicesInfo 是不是数组
      // 因为有可能在刚开始启动小程序的时候，由于没有注册设备的信息，
      // 被初始化为一个对象 {"msg": "no devices"}
      if (Object.prototype.toString.call(app.globalData.devicesInfo) 
                                                        !== "[object Array]") {
        app.globalData.devicesInfo = [];
      }

      devicesInfo.forEach(eachDevice => app.globalData.devicesInfo.push(eachDevice));
      console.log(app.globalData.devicesInfo)
      // 弹窗显示注册信息
      wx.showModal({
        title: '注册信息',
        content: "注册数量: " + count,
        showCancel: false,
        success(res) {
          // 点击确认后，地图将所有的已经注册的设备全部显示
          that.includePoints();
        }
      });
    });
  },

  onHide: function () {
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})

// const get = require("../../utils/http.js").get;
// const newPromise = get("https://www.chen1996.com/info/devices/");

// newPromise.then((data) => console.info(data.data));
