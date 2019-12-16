function closureTest() {
    /**
     * 首先来看当 identifier 是 javaScript 最开始就有的 var时
     */
    // 定义个数组用来储存指向函数的指针（理解成引用也可）
    var functionList =[];

    // 开始给上面的数组中添加元素
    for (var i = 0; i < 5; i++) {
        functionList.push(() => {
            console.log(i);
        })
    }

    // 依次执行上面数组中所储存的函数
    functionList.forEach((elem) => elem());
}

closureTest();