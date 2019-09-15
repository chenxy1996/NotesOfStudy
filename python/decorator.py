def method_call_times(func):

    def return_func(*args):
        instance = args[0]
        name = func.__name__ + "_times"
        try:
            instance.__dict__[name] += 1
        except KeyError:
            instance.__dict__[name] = 1
        
        return func(*args)

    return return_func

class Time:
    
    @method_call_times
    def add(self, a, b):
        return a + b
    
    @method_call_times
    def muit(self, a, b):
        return a * b

if __name__ == "__main__":
    a = Time()

    a.add(1, 2)
    a.add(5, 7)
    a.add(3, 9)
    
    a.muit(3, 4)
    a.muit(9, 9)
    print(a.__dict__)