import os
from time import time
import re


class Transfer:
    def __init__(self):
        self.COPIED_ROOT = None
        self.ROOT = None
        self.stack1 = None
        self.stack2 = None
        self.stack3 = None


    # Traverse the target path to find markdown files. 
    # Create a new directory (or a new file if the objective is a file)in the 
    # same position, which has the same documentary structure as the objective 
    # directory. Meanwhile process markdown files.
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
                file_string = self.youdToTypo(path)
                f.write(file_string)


    def generateMetaString(self):
        current_time = str(time())
        index = current_time.index('.')
        meta_string = current_time[(index + 1):]
        return meta_string
    

    def youdToTypo(self, file_path): # Convert the format of md in YoudaoYun to that in Typora.
        self.stack1 = []
        self.stack2 = []
        self.stack3 = []

        with open(file_path, encoding = "utf-8") as f:
            file_string = f.read()

            # add js name to js code block:
            file_string = re.sub(r"(`{1,})(\n{0,1})", self.addJsNameToCodeBlock, \
                                                                file_string, flags = re.S)
            # transfer underline format
            file_string = re.sub(r"</{0,1}font.*?>|`+|\+\+", self.underlineTrans, \
                                                                file_string, flags = re.S)

            return file_string
            
            
    def typoToYoud(self, file_name): # Convert the format of md in Typora to that in YoudaoYun.
        pass


    # add js name to js code block:
    # ```                           ```js
    # function fun() {}    =>       function fun() {}
    # ```                           ```
    def addJsNameToCodeBlock(self, m):
        first_str = m.group(1)
        second_str = m.group(2)

        if first_str not in self.stack3:
            self.stack3.append(first_str)

            if second_str and len(first_str) >= 3 and len(self.stack3) == 1:
                return first_str + "java" + second_str

        else:
            self.stack3 = self.stack3[0:(self.stack3.index(first_str))]

        return m.group(0)
    

    # transfer Youdao md underline format to that of Typora
    # ++abcd++ => <u>abcd</u>
    def underlineTrans(self, m):
        current_string = m.group()

        if "font" in current_string:
            return

        elif current_string == "++":
            if len(self.stack1) != 0:
                return current_string

            else:
                if len(self.stack2) == 0:
                    self.stack2.append(current_string)
                    return "<u>"

                else:
                    self.stack2.pop()
                    return "</u>"

        else:
            if len(self.stack1) == 0 or current_string not in self.stack1:
                self.stack1.append(current_string)

            else:
                self.stack1 = self.stack1[0:(self.stack1.index(current_string))]

            return current_string


if __name__ == "__main__":
    transfer = Transfer()
    transfer.traverse(r"C:\Users\陈翔宇\Desktop\Java_程序设计")
        
