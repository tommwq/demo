file_name = "a.txt"

lbm_no = ""

with open(file_name, "r", encoding="utf16", errors="ignore") as file:
    while True:
        line = file.readline()
        if len(line) == 0:
            break
        elif line.startswith("===="):
            lbm_no = line[5:12]
            print(lbm_no)
        elif line.lower().find('from') != -1:
            print(line)
        elif line.find('update') != -1:
            print(line)
            
