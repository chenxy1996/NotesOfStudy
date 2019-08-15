'''模拟 Iterator'''
class Iterator:
    def __init__(self, *items):
        self.i = 0
        self.items = items
        self.length = len(self.items) 
        
    def next(self):
        ret = {
            "value": self.items[self.i] if self.i < self.length else None,
            "done": self.i >= self.length - 1
        }

        self.i += 1

        return ret


if __name__ == "__main__":
    new_iterator = Iterator(1, 2, 3, 4, 5)
    current_item = new_iterator.next()

    while (current_item["value"] != None):
        print(current_item)
        current_item = new_iterator.next()
    
    print(new_iterator.next())



    
