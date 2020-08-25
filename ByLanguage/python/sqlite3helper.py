#!/usr/bin/env python

'''
管理数据。
'''

'''
TODO tk菜单
TODO tk标题栏
TODO tk spinner
TODO tk datagrid
TODO tk edit


'''

import sqlite3
import sqlparse

class DatabaseHelper(object):
    '''返回['db1', 'db2']。'''
    def databases(self):
        raise NotImplementedError()

    def use(self, database):
        raise NotImplementedError()

    '''返回['tbl1', 'tbl2']。'''
    def tables(self, database=None):
        raise NotImplementedError()

    '''返回'create table ...'。'''
    def create_table_sql(self, table, database=None):
        return ''

    '''返回[('field1','int', ('field2','int')]。'''
    def columns(self, table, database=None):
        raise NotImplementedError()

class SQLite3Helper(DatabaseHelper):
    def __init__(self, connection):
        self._connection = connection

    def tables(self, database=None):
        cursor = self._connection.cursor()
        cursor.execute('select name from sqlite_master where type="table"')
        return [x[0] for x in cursor.fetchall()]

    def create_table_sql(self, table, database=None):
        cursor = self._connection.cursor()
        cursor.execute('select sql from sqlite_master where type="table" and name="{}"'.format(table))
        rows = cursor.fetchall()
        if len(rows) == 0:
            raise NameError(table)
        return rows[0][0]

    def columns(self, table, database=None):
        def filter(x):
            if isinstance(x, sqlparse.sql.IdentifierList):
                return True
            
            if x.ttype not in [sqlparse.tokens.Whitespace,
                               sqlparse.tokens.Punctuation,
                               sqlparse.tokens.Keyword,
                               sqlparse.tokens.Newline]:
                return True

            return False
        
        stmts = sqlparse.parse(self.create_table_sql(table, database))
        n = len(stmts[0].tokens)
        stmts = stmts[0].tokens[n-1]
        tokens = [x for x in stmts.tokens if filter(x)]
        flatten = []
    
        for x in tokens:
            if isinstance(x, sqlparse.sql.IdentifierList):
                flatten.extend([y for y in x.flatten() if filter(y)])
            else:
                flatten.append(x)

        result = []
        fname = None
        ftype = None
        x = 0
        while x < len(flatten):
            if not fname:
                fname = flatten[x].value
            elif not ftype:
                ftype = flatten[x].value
                if x + 1 < len(flatten) and flatten[x + 1].ttype == sqlparse.tokens.Number.Integer:
                    x += 1
                    ftype = '{}({})'.format(ftype, flatten[x].value)

            if fname and ftype:
                result.append((fname, ftype))
                fname = None
                ftype = None

            x += 1
        return result

class DataManagerApp(object):
    def __init__(self):
        self.test()

    def test(self):
        filename = 'data.db'
        with sqlite3.connect(filename) as db:
            helper = SQLite3Helper(db)
            print(helper.tables())
            print(helper.columns('UnitTranslation'))

if __name__ == '__main__':
    DataManagerApp()
