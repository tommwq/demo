'''
用csv模拟数据库。

每个表是一个csv文件。

2019年12月10日
'''

import codecs
import csv
import os

class RecordUpdater(object):
    '''用于更新记录。'''

    def __init__(self, keyvalues):
        self._keyvalues = keyvalues

    def update(self, record):
        for k, v in self._keyvalues.items():
            record[k] = v

        return record

class Filter(object):
        '''filter rows by condition.

condition: field_name, op, value
'''
        def __init__(self, field_name, operator, value, next_filter=None):
            self._field = field_name
            self._op = operator
            self._value = str(value)
            self._next = next_filter

        def filter(self, record):
            if not self.do_filter(record):
                return False
            else:
                return self._do_next_filter(record)

        def _do_next_filter(self, record):
            if self._next:
                return self._next.filter(record)
            else:
                return True

        def do_filter(self, record):
            if not self._field in record:
                return False

            if self._op == "=":
                return record[self._field] == self._value
            elif self._op == "<":
                return record[self._field] < self._value
            elif self._op == ">":
                return record[self._field] > self._value
            elif self._op == "!=":
                return record[self._field] != self._value
            else:
                return False

class AlwaysTrueFilter(Filter):
    def __init__(self, next_filter=None):
        super(AlwaysTrueFilter, self).__init__("", "", "", next_filter)

    def do_filter(self, record):
        return True

class CsvTable(object):

    def __init__(self, csv_file_name):
        self._file_name = csv_file_name
        self._column_names = []
        self._rows = []
        self._load()

    def _load(self):
        with codecs.open(self._file_name, "rb", encoding = CsvDB.encoding) as csv_file:
            reader = csv.DictReader(csv_file)
            self._column_names = reader.fieldnames
            self._rows = [dict(x) for x in reader]
        
    def open():
        pass

    def close(self):
        pass
    
    def _sync_to_disk(self):
        with codecs.open(self._file_name, "wb", encoding = CsvDB.encoding) as csv_file:
            writer = csv.DictWriter(csv_file, fieldnames=self._column_names)
            writer.writeheader()
            writer.writerows(self._rows)

    def insert(self, row):
        record = {}
        if isinstance(row, list):
            for i in range(0, min(len(self._column_names), len(row))):
                record[self._column_names[i]] = str(row[i])
        elif isinstance(row, dict):
            for k, v in row.items():
                record[k] = str(v)
        else:
            raise Error("insert operation only support list and dict: " + row)

        self._rows.append(record)
        self._sync_to_disk()

    def update(self, conditions, keyvalues):
        '''
keyvalues: {key:value}
'''
        updater = RecordUpdater(keyvalues)
        tmp = [updater.update(row) for row in self._rows if self._make_filter(conditions).filter(row)]
        self.delete(conditions)
        for row in tmp:
            self.insert(row)

        self._sync_to_disk()


    def delete(self, conditions):
        self._rows = [row for row in self._rows if not self._make_filter(conditions).filter(row)]
        self._sync_to_disk()

    def truncate(self):
        self.get_db_file().truncate()

    def select(self, conditions=[]):
        '''conditions: [(field_name op value)]
op: > < = !=
'''
        return [row for row in self._rows if self._make_filter(conditions).filter(row)]

    def _make_filter(self, conditions):
        filter = AlwaysTrueFilter()
        for f, o, v in conditions:
            filter = Filter(f, o, v, filter)

        return filter

    def __del__(self):
        self.close()

    def dump(self):
        print(self._column_names)
        for row in self._rows.values():
            print(list(row))
        

class CsvDB(object):

    table_file_name_suffix = ".csvdb"
    encoding = "utf-8"

    def __init__(self, root_path):
        self._root = root_path
        self._tables = {}
        self._scan_table()

    def _scan_table(self):
        table_file_names = [entry for entry in os.listdir(self._root) if entry.endswith(self.table_file_name_suffix)]
        for file_name in table_file_names:
            table_name = file_name[:-len(self.table_file_name_suffix)]
            file_path = os.path.join(self._root, file_name)
            self._tables[table_name] = CsvTable(file_path)

    def list_table_name(self):
        return list(self._tables.keys())

    def create_table(self, table_name, column_names):
        if self.is_table_exist(table_name):
            raise Error("table name duplicated: " + table_name)

        table_file_name = self._table_file_name(table_name)
        with codecs.open(table_file_name, "w", encoding = self.encoding) as output_file:
            writer = csv.DictWriter(output_file, fieldnames = column_names)
            writer.writeheader()

        self._tables[table_name] = CsvTable(table_file_name)

    def drop_table(self, table_name):
        if not self.is_table_exist(table_name):
            return

        self._tables[table_name].close()
        del self._tables[table_name]
        os.remove(self._table_file_name(table_name))

    def table(self, table_name):
        if self.is_table_exist(table_name):
            return self._tables[table_name]
        else:
            raise Error("no such table: " + table_name)

    def is_table_exist(self, table_name):
        return table_name in self._tables

    def _table_file_name(self, table_name):
        return os.path.join(self._root, table_name + self.table_file_name_suffix)
