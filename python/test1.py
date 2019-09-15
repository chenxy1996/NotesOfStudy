class MyDescriptor:
    def __init__(self, fget=None, fset=None):
        self.fget = fget
        self.fset = fset
    
    def __get__(self, instance, owner):
        print("MyDescriptor __get__")
        return self.fget(instance)
    
    def __set__(self, instance, value):
        print("MyDescriptor __set__")
        self.fset(instance, value)
        


    