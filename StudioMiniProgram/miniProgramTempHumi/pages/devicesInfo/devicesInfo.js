// pages/devicesInfo/devicesInfo.js
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
    // 保存页面对象
    const that = this;

    wx.request({
      url: 'https://www.chen1996.com/info/devices/',

      success(data) {
        const deviceArray = data.data.data
        // console.log(deviceArray);
        that.setData({
          deviceArray: deviceArray
        })
      },

      fail(err) {
        // console.log(err);
      }
    });
  },

  // 跳转至温湿度信息详情页面
  redirectToTempHumiDetails(e) {
    const id = e.target.id.slice(8)
    // console.log(id);
    wx.navigateTo({
      url: '/pages/details/details?id=' + id + "/"
    })
  },

  // 检索有没有尚未注册的设备，如果有则注册
  registerNewDevices(e) {
    const that = this;

    wx.request({
      url: 'https://www.chen1996.com/info/devices/register/',

      success(data) {
        const registerredDevices = data.data;
        
        // 弹窗显示注册信息
        wx.showModal({
          title: '注册信息',
          content: "注册数量: " + registerredDevices.count,
          showCancel: false,
          success(res) {
            if (res.confirm) {
              // 注册后弹窗显示注册信息，点击确定后更新页面的设备信息
              wx.request({
                url: 'https://www.chen1996.com/info/devices/',

                success(data) {
                  const deviceArray = data.data.data
                  // console.log(deviceArray);
                  that.setData({
                    deviceArray: deviceArray
                  })
                },

                fail(err) {
                  // console.log(err);
                }
              });
            }
          }
        });
      },
    
      fail(err) {
        // console.log(err)
      }
    });
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