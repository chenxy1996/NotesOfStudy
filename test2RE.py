import re
text = '''f
123456
f
789
f
123455666
f'''
# text = '''fb123456fb789fb晨晨fbfb123455666fb'''
# text = '''fb
# 123456
# fb
# 789
# fb
# 晨晨
# fb
# fb123455666
# fb'''

def fun(string, text, substring):
    
    find_result = re.findall(string, text, flags=re.S)
    print("-------------------")
    print(find_result)
    
    print(text)
    print("-------------------")
    x = re.sub(string, substring, text, flags=re.S)
    print(x)

if __name__ == "__main__":
    # fun(r"(f)(\n).+?f\n{0,1}", text, r"\g<0>js\g<1>")
    fun(r"(f)(\n)(.+?f\n{0,1})", text, r"\g<1>js\g<2>\g<3>")
    # fun(r"(fb)(\n)(.+?fb)", text, r"\g<1>得到\g<2>\g<3>")
    
    