import weakref

def person():
    number = 0
    name_map = weakref.WeakKeyDictionary() # 弱引用字典

    class Person:
        def __init__(self, name):
            nonlocal number
            number += 1

            name_map[self] = name

        def say_number(self):
            return number
        
        def say_name(self):
            return name_map[self]

        def say_name_map(self):
            return name_map

    return Person

if __name__ == "__main__":
    Person = person() # 利用函数创造一个闭包 closure 用来储存类实例的私人属性，返回这个类 

    person1 = Person("chen")
    print(person1.say_number())

    person2 = Person("lele")

    print(person1.say_number())
    print(person2.say_number())

    print(person1.say_name())
    print(person2.say_name())

    person3 = Person("xiaobai")

    print(person1.say_number())
    print(person2.say_number())
    print(person3.say_number())
    
    print(person3.say_name_map())

    person1 = None

    print(person3.say_name_map())


