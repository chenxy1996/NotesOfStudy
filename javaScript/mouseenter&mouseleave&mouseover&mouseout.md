[TOC]

# EVENT

## `mouseenter, mouseleave, mouseover, mouseout` 之间的区别

<u>**==NOTE==: 鼠标事件中，只有 `mouseenter, mouseleave` 不向上冒泡 **</u>



- `mouseenter`不向上冒泡， `mouseover` 向上冒泡。

- `mouseenter` 在 cursor 移动到后代元素上不触发； `mouseover` 在 cursor 从其他元素移动至目标元素时候触发（**穿越内部元素的边界**）， 这个**其他元素可能是目标元素的后代元素或者是外部元素**。

  

```html
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Events</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="outer" id="outer1">
        This is a outer box. #onmouseenter #onmouseleave
        <div class="inner1">
            This is an inner box.
        </div>
        <div class="inner2">
            This is another inner box.
        </div>
    </div>

    <div class="outer" id="outer2">
        This is a outer box. #onmouseover #onmouseout
        <div class="inner1">
            This is an inner box.
        </div>
        <div class="inner2">
            This is another inner box.
        </div>
    </div>
    

    <script>
        // get a randome value from the interval [lowerValue, upperValue].
        function selectFrom(lowerValue, upperValue) {
            var choices = upperValue - lowerValue + 1;
            return Math.floor(Math.random() * choices + lowerValue);
        }

        // 函数: 得到一个随机的颜色，以 'rgb(xxx, xxx, xxx)' 格式表示
        function getRandomColor() {
            return "rgb(" + selectFrom(0, 255) + "," + selectFrom(0, 255) + "," 
                                                        + selectFrom(0, 255) + ")";;
        }

        // 函数：事件发生，随机改变颜色
        function changeColor(e) {
            e.currentTarget.style.color = getRandomColor();
        }

        // 函数：事件发生，颜色返回默认
        function colorToDefault(e) {
            e.currentTarget.style.color = "";
        }

    </script>
    <script>
        function compare(idName, fun1Name, fun2Name) {
            var outerBox = document.getElementById(idName);
            var innerBox1 = outerBox.getElementsByClassName("inner1")[0];
            // var innerBox2 = outerBox.getElementsByClassName("inner2")[0];

            outerBox[fun1Name] = changeColor;
            outerBox[fun2Name] = colorToDefault;
        }
        
        compare("outer1", "onmouseenter", "onmouseleave");
        compare("outer2", "onmouseover", "onmouseout");
    </script>
</body>
</html>
```

