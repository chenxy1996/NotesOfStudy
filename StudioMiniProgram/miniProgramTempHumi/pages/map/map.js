// pages/map/map.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    latitude: 30.54155000,
    longitude: 114.37122300,
  },
  

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // app 静态方法: 在 pageObj 这个 Page 对象上显示各个设备的位置 
    app.showDevicesLocation = (pageObj) => {
      const devicesInfo = app.globalData.devicesInfo;
      devicesInfo.forEach(eachDevice => {
        eachDevice.iconPath = app.globalData.mapMarkerPath
      });
      pageObj.setData({
        markers: devicesInfo
      });
    };

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
   */
  onReady: function (e) {
    this.mapCtx = wx.createMapContext('myMap');
  },


  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  },

  getCenterLocation: function () {
    this.mapCtx.getCenterLocation({
      success: function (res) {
        console.log(res.longitude)
        console.log(res.latitude)
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
    this.mapCtx.includePoints({
      padding: [30, 30, 30, 30],
      points: this.data.markers
    })
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
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
