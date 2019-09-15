class Score:
    def __init__(self, subject):
        self.subject = subject

    def __get__(self, instance, owner):
        print("TestDesc __get__")
        if instance:
            return instance.__dict__[self.subject]
        else:
            return 10
    
    def __set__(self, instance, value):
        print("TestDesc __set__")
        if not 0 <= value <= 100:
            raise AttributeError("Invalid Score")
        else:
            instance.__dict__[self.subject] = value
        
class Student:
    math = Score("math")
    english = Score("math")
    chinese = Score("chinese")

    def __init__(self, name, math, english, chinese):
        self.name = name
        self.english = english
        self.math = math
        self.chinese = chinese


if __name__ == "__main__":
    a_student = Student("chenxiangyu", 100, 100, 100)
    print(a_student.math, a_student.english, a_student.chinese)

    b_student = Student("lele", 99, 99, 99)
    print(b_student.math, b_student.english, b_student.chinese)

    print(Student.math)
        