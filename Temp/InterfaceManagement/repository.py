#--coding:utf-8--

import sqlite3
import util

class InterfaceType(object):
    def __init__(self, id, description, table_name):
        self.id = id
        self.description = description
        self.table_name = table_name

class InterfaceMain(object):
    def __init__(self, id, type):
        self.id = id
        self.type = type

class InterfaceLBM(object):
    def __init__(self, id, label, description, function_name):
        self.id = id
        self.label = label
        self.description = description
        self.function_name = function_name

class InterfaceBusiproc(object):
    def __init__(self, id, url, description, class_name, method_name):
        self.id = id
        self.url = url
        self.description = description
        self.class_name = class_name
        self.method_name = method_name

class InterfaceTC(object):
    def __init__(self, id, description, service, mfunc, sfunc):
        self.id  = id 
        self.service = service
        self.mfunc = mfunc
        self.sfunc = sfunc
        self.description = description


class InterfaceCallRelation(object):
    def __init__(self, caller, callee):
        self.caller = caller
        self.callee = callee

class InterfaceComment(object):
    def __init__(self, interface, comment, time, user):
        self.interface = interface
        self.comment = comment
        self.time = time
        self.user = user

class InterfaceParameter(object):
    def __init__(self, id, interface, name, code, data_type, is_required, is_input):
        self.id = id
        self.interface = interface
        self.name = name
        self.code = code
        self.data_type = data_type
        self.is_required = is_required
        self.is_input  = is_input 

class DataDict(object):
    def __init__(self, parameter, value, description):
        self.parameter = parameter
        self.value = value
        self.description = description

class Repository(object):
    def __init__(self, database_file_name):
        self._db = sqlite3.connect(database_file_name)

    def __del__(self):
        self._db.close()

    def list_interface_type(self):
        cursor = self._db.cursor()
        cursor.execute('select * from interface_type')
        rows = cursor.fetchall()
        cursor.close()
        return [InterfaceType(*row) for row in rows]

    def get_interface_type(self, id):
        '''如果查询不到，返回None。如果查询到多条记录，返回第一条记录。'''
        cursor = self._db.cursor()
        cursor.execute('select * from interface_type where id=(?)', (id,))
        rows = cursor.fetchall()
        cursor.close()
        if len(rows) == 0:
            return None
        return InterfaceType(*rows[0])

    def insert_interface_main(self, interface_type_id):
        cursor = self._db.cursor()
        print(interface_type_id)
        cursor.execute('insert into interface_main (type) values (?)', (interface_type_id,))
        self._db.commit()
        id = cursor.lastrowid
        cursor.close()
        return id

    def list_interface_main(self):
        cursor = self._db.cursor()
        cursor.execute('select * from interface_main')
        rows = cursor.fetchall()
        cursor.close()
        return [InterfaceMain(*row) for row in rows]

    def insert_interface_lbm(lbm):
        '''插入lbm接口。'''
        cursor = self._db.cursor()
        cursor.execute('insert into interface_lbm (id,label,description,function_name) values (?,?,?,?)', lbm.__dict__)
        # (lbm.id, lbm.label, lbm.description, lbm.function_name))
        cursor.close()

    def list_interface_table(self):
        '''返回接口表名及对应的字段名。'''
        return [
            {'id': 1, 'fields': ['label', 'description', 'function_name']},
            {'id': 2, 'fields': ['url', 'description', 'class_name', 'method_name']},
            {'id': 3, 'fields': ['description', 'service', 'mfunc', 'sfunc']}
        ]

    def insert_interface_table(self, table_name, entry_dict):
        '''插入具体接口表。'''
        cursor = self._db.cursor()
        sql_param = ','.join(entry_dict.keys())
        sql_value = ','.join(map(lambda x: '?', entry_dict.keys()))
        sql_format = 'insert into {} ({}) values ({})'
        sql = sql_format.format(table_name, sql_param, sql_value)
        cursor.execute(sql, tuple(entry_dict.values()))
        self._db.commit()
        cursor.close()
