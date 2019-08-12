def create_person_class():
    count = 0
    info_dict = {} # 用来储存每个创建的 Person 实例的信息
    
    class Person:
        def __init__(self, name):
            nonlocal count
            
            info_dict[self] = {"id": count, "name": name}
            count += 1
        
        def get_name(self):
            return info_dict[self]["name"]
       	
        def get_id(self):
            return info_dict[self]["id"]
        
        def set_name(self, name):
            info_dict[self]["name"] = name
    
    return Person

Person = create_person_class()

if __name__ == "__main__":
    a_person = Person("chen")
    b_person = Person("lele")

    print(
        a_person.get_name(),
        a_person.get_id(),  
    )

    print(
        b_person.get_name(),
        b_person.get_id(),  
    )

    a_person.set_name("xiangyu")
    
    print(
        a_person.get_name(),
        a_person.get_id(),  
    )