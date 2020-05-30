'''
导出数据库成json文件。

2019年11月15日
'''

import json
import codecs
import pymysql

class MySQLDumper(object):
    def __init__(self, host, port, user, password, db, charset):
        self._connection = pymysql.connect(host=host,
                                           port=port,
                                           user=user,
                                           password=password,
                                           db=db,
                                           charset=charset)

    def __del__(self):
        self._connection.close()
        
    def show_tables(self):
        sql = 'show tables'
        cursor = self._connection.cursor()
        cursor.execute(sql)
        return [row[0] for row in cursor.fetchall()]

    def query_table_field(self, table_name):
        sql = 'DESCRIBE {}'.format(table_name)
        cursor = self._connection.cursor()
        cursor.execute(sql)
        return [row[0] for row in cursor.fetchall()]

    def query_table_record(self, table_name):
        sql = 'SELECT * FROM {}'.format(table_name)
        cursor = self._connection.cursor()
        cursor.execute(sql)
        return cursor.fetchall()

    def dump_table(self, table_name):
        fields = self.query_table_field(table_name)
        records = self.query_table_record(table_name)
        items = [self.make_dict(fields, record) for record in records]
        file_name = '{}.json'.format(table_name)
        file_content = json.dumps(items)
        with codecs.open(file_name, "w", "utf-8") as output_file:
            output_file.write(file_content)

    def make_dict(self, fields, record):
        d = {}
        for i in range(0, len(fields)):
            d[fields[i]] = record[i]

        return d

    def dump(self):
        for table in self.show_tables():
            self.dump_table(table)

    
host = 'localhost'
port = 3306
database = 'test'
username = 'root'
password = '123456'
charset = 'utf8'

dumper = MySQLDumper(host, port, username, password, database, charset)
dumper.dump()
    
