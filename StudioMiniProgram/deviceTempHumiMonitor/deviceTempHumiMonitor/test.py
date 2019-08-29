import sys
sys.path.append("../my_util")

from my_util import test_util


data = test_util.get_one_arbitary_data()
print(data)