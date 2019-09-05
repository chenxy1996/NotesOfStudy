'''
操作数据库所用的 utils
'''

from pymongo import MongoClient


def get_data_from_database(mongodb_config, query={}, projection={},\
                                    col_list=None, sort_query=None, limit_num=None):
    '''
    query: 字典格式的在 collection 中的查询条件；col_list: 当前 db 下需要查询的
    所有 collection 的名称， 封装在列表中；sort: 列表[(<str:filed1>, 1)...]，返回数据
    的顺序; limit: 整型, 指定在每个 collection 查询返回的 document 个数。
    '''

    ret = [] # 返回值初始化
    
    db_url = "mongodb://%s:%s@%s:%s/%s" % (
        mongodb_config["user"],
        mongodb_config["pwd"],
        mongodb_config["ip"],
        mongodb_config["port"],
        mongodb_config["db"]
    )

    client = MongoClient(db_url)
    db = client["monitor"]
    
    # 如果没有指定要查询的 collection, 就是要查询所有 collection
    if not col_list:
        # 得到当前 db 下所有的 colletion 名称，储存在列表中
        col_list = db.list_collection_names(session=None)

    for each_col in col_list:
        current_col = db.get_collection(each_col)

        # 判断有没有设置有效的 limit_num
        if isinstance(limit_num, int) and limit_num >= 0:
            data = current_col.find(query, projection=projection, sort=sort_query,\
                                 limit=limit_num)
        else:
             data = current_col.find(query, projection=projection, sort=sort_query)
             
        ret.append({
            "id": each_col,
            "data": list(data)
        })

    client.close()
    client = None

    return ret