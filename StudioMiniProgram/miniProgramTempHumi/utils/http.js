const get = (url, { params = {}, header = {} } = {}) => new Promise(
  (resolve, reject) => {
    wx.request({
      url: url,
      data: params,
      header: header,
      method: "GET",
      dataType: "json",
      responseType: "text",
      success(data) {
        resolve(data);
      },
      fail(err) {
        // 打印 error
        console.info(err);
        reject(err);
      }
    });
  }
);

module.exports = {
  get: get
}