class Meta(type):
    def __getattribute__(*args):
        print("Metaclass getattribute invoked")
        return type.__getattribute__(*args)

class C(object):
    def __len__(self):
        return 10
    
    def __getattribute__(*args):
        return object.__getattribute__(*args)

# c = C()
# print(C.__len__(c))

'''
C.__getattribute__:
via type.__getattribute__: C -> object
via object.__getattribute: C -> type(C) -> object
'''

class A:
    x = 10

class B(A, metaclass=Meta):
    def __getattribute__(self, name):
        print("__getattribute__")
        return super().__getattribute__(name)

print(B.x)
b = B()
print(b.x)

'''
B.x: 
via type.__getattribute__: B -> A -> object

b.x:
via object.__getattibute__: b -> type(b)/B -> A -> object

object.get__attribute__(B, "x"):
via object.__getattibute__: B -> type(B)/type -> object  ERROR!!!

type.get__attribute__(B, "x"):
via type.__getattibute__: B -> A -> object
'''