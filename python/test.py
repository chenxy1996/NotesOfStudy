class MyDecorator:
    def __init__(self, get_func=None):
        self.get = get_func
    
    def __get__(self, instance, owner):
        def return_function(*args):
            print("MyDecorator")
            return self.get(owner, *args)
        return return_function

class TestClass:
    
    @MyDecorator
    def add(self, a, b):
        return a + b
    
    @MyDecorator
    def sub(self, a, b):
        return a - b

if __name__ == "__main__":
    instance1 = TestClass()
    print(TestClass.__dict__)
    # print(instance1.sub(8, 5))
    # print(instance1.add(8, 5))

            
        
        