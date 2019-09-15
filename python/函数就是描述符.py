'''
Python’s object oriented features are built upon a function based environment. 
Using non-data descriptors, the two are merged seamlessly.

Class dictionaries store methods as functions. In a class definition, methods 
are written using def or lambda, the usual tools for creating functions. Methods 
only differ from regular functions in that the first argument is reserved for 
the object instance. By Python convention, the instance reference is called self 
but may be called this or any other variable name.

To support method calls, functions include the __get__() method for binding 
methods during attribute access. This means that all functions are non-data 
descriptors which return bound methods when they are invoked from an object. 
In pure Python, it works like this:

class Function(object):
    . . .
    def __get__(self, obj, objtype=None):
        "Simulate func_descr_get() in Objects/funcobject.c"
        if obj is None:
            return self
        return types.MethodType(self, obj)
'''


'''
>>> class D(object):
...     def f(self, x):
...         return x
...
>>> d = D()

# Access through the class dictionary does not invoke __get__.
# It just returns the underlying function object.
>>> D.__dict__['f']
<function D.f at 0x00C45070>

# Dotted access from a class calls __get__() which just returns
# the underlying function unchanged.
>>> D.f
<function D.f at 0x00C45070>

# The function has a __qualname__ attribute to support introspection
>>> D.f.__qualname__
'D.f'

# Dotted access from an instance calls __get__() which returns the
# function wrapped in a bound method object
>>> d.f
<bound method D.f of <__main__.D object at 0x00B18C90>>

# Internally, the bound method stores the underlying function,
# the bound instance, and the class of the bound instance.
>>> d.f.__func__
<function D.f at 0x1012e5ae8>
>>> d.f.__self__
<__main__.D object at 0x1012e1f98>
>>> d.f.__class__
<class 'method'>
'''


# 仿照函数描述符写一个新的函数描述符
class FunctionDescriptor:
    def __init__(self, func):
        self.func = func
    
    def __get__(self, instance=None, owner=None):
        this = instance  
        
        # 这里利用闭包
        def newFunc(*args):
            return self.func(this, *args)
        
        return newFunc

class Test:
    def __init__(self, number):
        self.number = number

    # function = FunctionDescriptor(func=lambda obj, a, b: obj.number + a + b)

if __name__ == "__main__":
    new_obj = Test(5)

    fun1 = lambda obj, a, b: obj.number + a + b
    new_function = FunctionDescriptor(fun1).__get__(new_obj)

    print(new_function(2, 3))
    # a = Test(3)
    # print(a.function(3, 3))