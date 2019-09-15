**定义两个类**

```py
class A:	
	x = 10

class B(A):
	pass
```

首先定义上述两个 class : A、以及继承 A 的 B。A 中定义类属性 x 。

 **查询实例属性**

```python
b = B()
print(b.x) # 10
```

这里 b 为 B 的实例。输出 `b.x`  能得到10。这里调用了 `object.__getattribute__(b, "x")` 方法，其内部查询机制 (在没有数据描述符 data descroiptor 的情况下)：

```python
b.__dict__ --> type(b).__dict__  (这里 type(b) 就为 B) --> A.__dict__ --> object.__dict__
```

 先检查实例 ` b` 的 `__dict__` ，如果没有就向上搜索 , 沿着  `type(b)` 也是 `b.__class__` 的 `__mro__` 继承链。

```python
print(type(b).__mro__) 
#(<class '__main__.B'>, <class '__main__.A'>, <class 'object'>)
```

**查询类属性**

如果直接返回类的属性 `B.x`

```
print(B.x) # 10
```

直接在类上查询类属性 x.。这种情况和直接在实例属性上查询的机制不同，并不是调用 `object.__getattribute__(B, "x")`。而是<u>调用了 `type.__getattribute__(B, "x")`</u>

可以通过重新定义一个元类证明：

```python
'''定义一个新的元类, 重写'''
class Meta(type):
    def __getattribute__(self, prop):
        print("Meta class __getattribute__")
        return type.__getattribute__(self, prop)
    	# 这里些 return super().__getattribute__(prop) 也可以

class SupClass(metaclass=Meta):
    x = 10

class SubClass(SupClass):
    pass

if __name__ == "__main__":
    printSubClass.x)

# Meta class __getattribute__
# 10
```

如果我们将上面的 `return type.__getattribute__(self, prop)` 改为 `return object.__getattribute__(self, prop)` 就会报错. 原因为是此时是按照以下机制寻找：

```python
SubClass.__dict__ --> type(SubClass) (也就是 type) --> object.__dict__
```

而`type.__getattribute__(B, "x")` 的内部机制是：

```python
SubClass.__dict__ --> SupClass.__dict__ --> object.__dict__
```



