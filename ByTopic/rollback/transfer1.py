import csvdb

class Account(object):
    def __init__(self, id, balance):
        self.id = id
        self.balance = balance

    def save(self, amount):
        self.balance = self.balance + amount

    def withdraw(self, amount):
        # wrong: not check balance
        self.balance = self.balance - amount

    def clear(self):
        self.balance = 0.0

class AccountSystem(object):
    def __init__(self):
        db  = csvdb.CsvDB(".")
        if not db.is_table_exist("account"):
            db.create_table("account", ["id","balance"])
    
        if not db.is_table_exist("transfer_history"):
            db.create_table("transfer_history", ["from","to","amount"])
            
        self._db = db
    
    def create_account(self):
        exist_ids= [0]
        exist_ids.extend([int(row["id"]) for row in self._db.table("account").select()])
        account_id = max(exist_ids) + 1
        balance = 0
        account = Account(account_id, balance)
        self._db.table("account").insert(account.__dict__)
        return account

    def save_account(self, account):
        self._db.table("account").update([("id","=",account.id)], {
            "balance": account.balance
        })

    def is_account_exist(self, id):
        return len(self._db.table("account").select([("id","=",id)])) > 0

    def get_account(self, id):
        if not self.is_account_exist(id):
            raise Error("no such account: " + id)
        
        record = self._db.table("account").select([("id","=",id)])[0]
        return Account(record["id"], record["balance"])

    
# init
system = AccountSystem()
if system.is_account_exist(1):
    account1 = system.get_account(1)
else:
    account1 = system.create_account()

account1.clear()
system.save_account(account1)
    
account1.save(100)
system.save_account(account1)

account1.withdraw(200)
system.save_account(account1)



