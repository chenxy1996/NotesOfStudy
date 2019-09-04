// pages/details/details.js
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
    const that = this;
    const id = options.id;
    // 第一次跳转至改页面，默认显示条温湿度数据
    const url = "https://www.chen1996.com/info/get/id=" + id + "/5/";

    /*更新仪器设备数据；onlyTempHumi：是否只更新页面设备的温湿度数据，其他保持原状*/
    const updateDeviceInfo = (url, onlyTempHumi=false) => {
      
      wx.request({
        url: url,

        success(data) {
          // console.log(data)
          const device = data.data.data

          if (!onlyTempHumi) {
            that.setData({
              device: device
            });
          } else {
            that.setData({
              "device.data.data": device.data.data
            });
          }
          
        },

        fail(err) {
          // console.log(err);
        }
      });
    };

    updateDeviceInfo(url);

    // 提交表单查询数据
    this.formSubmit = (e) => {
      const num = e.detail.value.num;
      const url = "https://www.chen1996.com/info/get/id=" + id + '/' + num + '/';
      updateDeviceInfo(url, true);
      this.setData({
        numInput: ""
      });
    }
  },

  
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

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