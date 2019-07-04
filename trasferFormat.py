import os
from time import time


class Transfer:
    def __init__(self):
        self.COPIED_ROOT = None
        self.ROOT = None
    
    def traverse(self, path, in_root = True, reverse = False): # Traverse the target path to find markdown files.
        if in_root:
            self.ROOT = path
            meta_string = self.generateMetaString()
            if os.path.isdir(path):
                self.COPIED_ROOT = path + meta_string
            else:
                self.COPIED_ROOT = os.path.splitext(path)[0] + meta_string + os.path.splitext(path)[1]
            current_copy_path = self.COPIED_ROOT
        else:
            current_copy_path = self.COPIED_ROOT + path.split(self.ROOT)[1]
        
        if os.path.isdir(path):
            os.mkdir(current_copy_path)
            for each_item in os.listdir(path):
                current_path = os.path.join(path, each_item)
                self.traverse(current_path, False, reverse)
        else:
            with open(current_copy_path, "w", encoding = "utf-8") as f:
                f.write("")
    
    def generateMetaString(self):
        current_time = str(time())
        index = current_time.index('.')
        meta_string = current_time[(index + 1):]
        return meta_string
    
    def youdToTypo(self, file_name): # Convert the format of md in YoudaoYun to that in Typora.
        pass
    
    def typoToYoud(self, file_name): # Convert the format of md in Typora to that in YoudaoYun.
        pass

if __name__ == "__main__":
    transfer = Transfer()
    transfer.traverse(r"C:\Users\陈翔宇\Desktop\dir_root")
        