class TestDescriptor:
    def __init__(self, name):
        self.name = name

    def __get__(self, instance, owner=None):
        print("Descriptor __get__ method")
        return instance.__dict__[self.name]
    

class Person:
    name = TestDescriptor("name")

    def __init__(self, person_name):
        self.name = person_name

if __name__ == "__main__":
    class Timer:
        def __init__(self, initial_number):
            self.number = initial_number
        
        @property
        def times(self):
            '''return the times of current instance'''
            ret = self.__dict__["number"]
            self.__dict__["number"] += 1
            return ret
        
        def say_times(self):
            return self.times
        
        def __getattribute__(self, name):
            print("----------------")
            return super().__getattribute__(name)
        
    new_timer = Timer(0)
    print(new_timer.times)
    print(new_timer.times)
    
    print(new_timer.__dict__)

    print(new_timer.times)
