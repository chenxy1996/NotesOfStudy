const util = require('../utils/util.js');

/**
 * 常见用法：updateCurrentTime(this, timeProperty1, timeProperty2, timeProperty3)
 * 动态更新显示的时间: lexical 用于传递页面对象即 this.setData() 的 this, 
 * 之后的参数为当前页面所有需要动态变化的时间属性名称，用来指定页面对象的 data 属性
 * 中要修改的属性，即 this.setData({[timeProperty1]: "", [timeProperty2]: ""})
 * timePropertyStrings为这些属性组成的字符串数组.
 */
const updateCurrentTime = function (lexical, ...timePropertyStrings) {
  (function update(timePropertyStrings) {
    setTimeout(() => {
      const timePropertyStringsObj = {};
      const currentTimeString = util.formatTime(new Date());

      timePropertyStrings.forEach((item) => timePropertyStringsObj[item] = currentTimeString);
      this.setData(timePropertyStringsObj);

      // 从功能上说不需要加上 return. 此处加上 return 是利用 ES6 对 Tail Call 的优化
      return update.bind(this, timePropertyStrings)(); 
    }, 1000);
  }).bind(lexical, timePropertyStrings)();
}


module.exports = {
  updateCurrentTime: updateCurrentTime
}