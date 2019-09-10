// pages/map/map.js
const app = getApp();
const get = require("../../utils/http.js").get;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    hasMarkers: false
  },
  

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    app.showDevicesLocation(this);
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

   /**
    * 更新设备信息
    */
  updateDevicesInfo: function () {
    app.showDevicesLocation(this);
  },


  /**
   * 地图范围内显示所有设备信息, 不重新从网络上请求设备信息数据
   */
  includePoints: function () {
    // 判断之前设置的 this.data.markers 数组长度是不是大于1
    // 如果只有1个，那么就不设置 padding
    // 大于1个，就设置padding
    this.mapCtx.includePoints(
      this.data.markers.length > 1 ? Object.assign(
        { points: this.data.markers}, {padding: [60, 20, 20, 20]}) : {
          points: this.data.markers}
    );
  },


  /**
   * 检索有没有尚未注册的设备，如果有则注册
   */
  registerNewDevices(e) {
    const that = this;
    // 注册设备
    const newGetPromise = get("https://www.chen1996.com/info/devices/register/");

    newGetPromise.then(data => {
      const that = this;
      const count = data.data.count;
      if (count > 0) {
        // 获取注册的设备信息
        const getDevicesInfoPromise = app.fetchDevicesInfo();

        getDevicesInfoPromise.then(data => {
          app.showDevicesLocation(that);
        });
      }
      // 弹窗显示注册信息
      wx.showModal({
        title: '注册信息',
        content: "注册数量: " + count,
        showCancel: false
      });
    });
  },

  /**
   * 跳转至设备详情页面
   */
  navigateToDevicesInfo(e) {
    // console.log(id);
    wx.navigateTo({
      url: '/pages/devicesInfo/devicesInfo'
    })
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
