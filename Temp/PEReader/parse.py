file_name = "a.txt"

lbm_no = ""


lbm_table = {}

def add_table(lbm, table):
    if not lbm in lbm_table:
        lbm_table[lbm] = {}

    lbm_table[lbm][table] = table



with open(file_name, "r", encoding="utf16", errors="ignore") as file:
    while True:
        line = file.readline()
        if len(line) == 0:
            break
        elif line.startswith("===="):
            lbm_no = line[5:12]
        elif line.lower().find('from') != -1:
            pos1 = line.lower().find('from')
            if pos1 != -1:
                line = line[pos1+5:].strip()
                pos2 = line.find(' ')
                if pos2 == -1:
                    table_name = line
                else:
                    table_name = line[:pos2]
                add_table(lbm_no, table_name)
        elif line.lower().find('update') != -1:
            pos1 = line.lower().find('update')
            if pos1 != -1:
                line = line[pos1+7:].strip()
                pos2 = line.find(' ')
                if pos2 == -1:
                    table_name = line
                else:
                    table_name = line[:pos2]
                add_table(lbm_no, table_name)

            
for lbm, table_set in lbm_table.items():
    for table in table_set.keys():
        print(lbm, table)
            print(lbm_no)
        elif line.lower().find('from') != -1:
            print(line)
        elif line.find('update') != -1:
            print(line)
            

