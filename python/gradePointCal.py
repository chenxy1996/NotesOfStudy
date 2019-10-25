import os

def name_input(file):
    grade_info = []
    with open(file, encoding="utf-8") as f:
        for line in f:
            splited_line = line.split(',')
            splited_line[-1] = splited_line[-1].replace('\n', '')
            grade_info.append(splited_line)
    return grade_info


def output(grade_info):
    ret = ""
    ret += concat("course", "grade", "point", "grade * point") + '\n' + '*' * 80 + '\n'
    sum_points = 0
    sum_points_by_grade = 0
    for each_grade in grade_info:
        ret += concat(each_grade[0], each_grade[1], each_grade[2], str(int(each_grade[1]) * int(each_grade[2]))) + '\n'
        sum_points += int(each_grade[2])
        sum_points_by_grade += int(each_grade[1]) * int(each_grade[2])

    ret += '*' * 80 + '\n' + concat("SUM", "--", str(sum_points), str(sum_points_by_grade)) + '\n' +\
        '*' * 80 + '\n' +concat("AVG", "--", "--", str(sum_points_by_grade / sum_points ))

    print(ret)
    return ret

def concat(*string):
    return string[1].center(10, ' ') + \
            string[2].center(10, ' ') + string[3].center(20, ' ') + string[0].ljust(50, ' ')

def save(name, string):
    with open(name, "a+", encoding="utf-8") as f:
        f.write(string)


if __name__ == "__main__":
    name = input("grade file: ")
    grade_info = name_input(name)
    res = output(grade_info)
    prompt = input("save? (y/n): ")
    if prompt == "y":
        saved_name = input("name: ")
        dir_name = os.path.dirname(__file__)
        path = os.path.join(dir_name, saved_name)
        save(path, res)
    
