import csvdb

class DealService(object):
    
    def deal(self, buyer_id, seller_id, security, price, count):
        pass
        
class Customer(object):
    def __init__(self, entity, account_system):
        self.__dict__ = entity.__dict__
        self._account_system = account_system

    def buy(self, peer_customer, security, price, count):
        self._account_system.deal_service().deal(self.id, peer_customer.id, security, price, count)

    def print(self):
        print('customer\t{}'.format(self.name))
        money_account = self._account_system.find_money_account(self.id)
        money_account.print()
        security_account = self._account_system.find_security_account(self.id)
        security_account.print()
        print('------------------------------')

class MoneyAccount(object):
    def __init__(self, entity, account_system):
        self.__dict__ = entity.__dict__
        self._account_system = account_system

    def add(self, value):
        self.balance = str(float(self.balance) + value)
        self._account_system.save_money_account(self)
        
    def substract(self, value):
        self.balance = str(float(self.balance) - value)
        self._account_system.save_money_account(self)

    def print(self):
        print('money account')
        print('\tbalance {}\tlock {}'.format(self.balance, self.lock))

class SecurityAccountItem(object):
    def __init__(self, accountid, securityid, count):
        self.accountid = accountid
        self.securityid = securityid
        self.count = count

class SecurityAccount(object):
    def __init__(self, entity, account_system):
        self.__dict__ = entity.__dict__
        self._account_system = account_system
        self._items = [SecurityAccountItem(self.id, entity.securityid, entity.count) for entity in account_system.query_security_account_detail(self.id)]

    def add(self, security, count):
        added = False
        for x in self._items:
            if x.security == security:
                x.count = x.count + count
                item = x
                added = True

        if not added:
            item = SecurityAccountItem(self.id, security, count)
            self._items.append(item)

        self._account_system.save_security_account_detail(item)
        
    def substract(self, security, count):
        for x in self._items:
            if x.securityid == security:
                x.count = str(int(x.count) - count)
                item = x

        self._account_system.save_security_account_detail(item)
        
    def print(self):
        print('security account\tlock {}'.format(self.lock))
        for item in self._items:
            print('\t{}\t{}'.format(item.securityid, item.count))

class AccountSystem(object):
    def __init__(self, db_path, deal_service):
        self._deal_service = deal_service
        db = csvdb.Database(db_path)
        self._customer = csvdb.Repository(db.table('customer'))
        self._moneyaccount = csvdb.Repository(db.table('money_account'))
        self._moneytransferhistory = csvdb.Repository(db.table('money_transfer_history'))
        self._operationhistory = csvdb.Repository(db.table('operation_history'))
        self._securityaccount = csvdb.Repository(db.table('security_account'))
        self._securityaccountdetail = csvdb.Repository(db.table('security_account_detail'))
        self._securitytransferhistory = csvdb.Repository(db.table('security_transfer_history'))

    def deal_service(self):
        return self._deal_service

    def customer(self, customer_id):
        entity = self._customer.select_by_id(customer_id)
        if not entity:
            raise Error("invalid account id")
        
        return Customer(entity, self)

    def find_money_account(self, customer_id):
        return MoneyAccount(self._moneyaccount.select_one("customerid",customer_id), self)
        
    def find_security_account(self, customer_id):
        return SecurityAccount(self._securityaccount.select_one("customerid",customer_id), self)
        
    def query_security_account_detail(self, security_account_id):
        return self._securityaccountdetail.select_many("accountid", security_account_id)

    def save_money_account(self, money_account):
        self._moneyaccount.update_by_id(money_account)
        
    def save_security_account_detail(self, security_account_detail):
        self._securityaccountdetail.update([("accountid","=",security_account_detail.accountid),
                                            ("securityid","=",security_account_detail.securityid)],
                                           security_account_detail.__dict__)
