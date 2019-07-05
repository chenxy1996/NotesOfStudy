import os
from time import time
import re


class Transfer:
    def __init__(self):
        self.COPIED_ROOT = None
        self.ROOT = None

    # Traverse the target path to find markdown files. 
    # Create a new directory (or a new file if the objective
    # is a file)in the same postition, which has the same 
    # documentary structure as the objective directory.
    # Meanwhile process markdown files.
    def traverse(self, path, in_root = True, reverse = False):
        if in_root:
            self.ROOT = path
            meta_string = self.generateMetaString()
            if os.path.isdir(path):
                self.COPIED_ROOT = path + "_" + meta_string
            else:
                self.COPIED_ROOT = os.path.splitext(path)[0] + "_" + meta_string + os.path.splitext(path)[1]
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
                with open(path, encoding = "utf-8") as objectiveMarkdownFile:
                    f.write(objectiveMarkdownFile.read())

    def generateMetaString(self):
        current_time = str(time())
        index = current_time.index('.')
        meta_string = current_time[(index + 1):]
        return meta_string
    
    def youdToTypo(self, file_name): # Convert the format of md in YoudaoYun to that in Typora.
        with open(file_name, encoding = "utf-8") as f:
            file_string = f.read()

            '''
            add js name to js code block:

            ```                         ```js
            function fun() {}    =>     function fun() {}
            ```                         ```

            '''
            file_string = re.sub(r"(```)(\n)((?!```).+?```\n{0,1})", \
                                                r"\g<1>js\g<2>\g<3>", file_string, flags=re.S)
    
    def typoToYoud(self, file_name): # Convert the format of md in Typora to that in YoudaoYun.
        pass

if __name__ == "__main__":
    transfer = Transfer()
    transfer.traverse(r"C:\Users\陈翔宇\Desktop\dir_root")
    print(transfer.ROOT)
    print(transfer.COPIED_ROOT)
        