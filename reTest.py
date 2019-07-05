import re


if __name__ == "__main__":
    path = r"C:\Users\陈翔宇\Desktop\test.md"
    with open(path, "r", encoding="utf-8") as f:
        article = f.read()
        # print(article)
        # result = re.findall(r"((```\n)[^`]+?```\n)", article, re.DOTALL)
        # result = re.findall(r"(```\n)(?!```\n).+?```\n", article, re.DOTALL)
        # print(result)
    # result = re.findall(r"(```\n)(?!```\n).+?```\n{0,1}", article, re.DOTALL)
    # print(result)
    # print(article)

    # result = re.findall(r"(``)(\n)(?!```\n).+?```\n{0,1}", article, re.DOTALL)
    # print(result)
    # print(type(article))
    # print("------------------------------")
    # x = re.sub(r"(```\n)(?!```\n).+?```\n{0,1}", r"hehehehee\1", article, re.DOTALL)
    # x = re.sub(r"(``)(\n)(?!```\n).+?```\n{0,1}", r"\g<1>js\g<2>", article, re.DOTALL)
    # x = re.sub(r"(f)un", r"\g<1>chenxiangyu", article, re.DOTALL)
    # print(x)

    result = re.findall(r"(```)(\n)(.+?```)", article, flags=re.S)
    print(result)
    x = re.sub(r"(```)(\n)((?!```).+?```\n{0,1})", r"\g<1>js\g<2>\g<3>", article, flags=re.S)
    print(x)