const util = require('../utils/util.js');

/**
 * 动态更新显示的时间 lexical 用于传递改哪个页面对象即 this.setData() 的 this 
 * timePropertyString为字符串 用来指定页面对象的 data 属性中要修改的属性即 
 * this.setData({[timePropertyString]: ""})
 */
const updateCurrentTime = function(timePropertyString, lexical) {
  (function update(timePropertyString) {
    setTimeout(() => {
      this.setData({
        [timePropertyString]: util.formatTime(new Date()) // util.formatTime 是微信小程序自带的包
      });
      return update.bind(this, timePropertyString)(); // 这里加上 return 是利用 ES6 对 Tail Call 的优化
    }, 1000);
  }).bind(lexical, timePropertyString)();
}


module.exports = {
  updateCurrentTime: updateCurrentTime
}