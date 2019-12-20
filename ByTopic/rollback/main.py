import csvdb
from account import *

db_path = './db'

class DealService1(DealService):
    def set_account_system(self, account_system):
        self._account_system = account_system
        
    def deal(self, buyer_id, seller_id, security, price, count):
        money = price * count
        buyer_money_account = self._account_system.find_money_account(buyer_id)
        buyer_security_account = self._account_system.find_security_account(buyer_id)
        seller_money_account = self._account_system.find_money_account(seller_id)
        seller_security_account = self._account_system.find_security_account(seller_id)

        buyer_money_account.substract(money)
        seller_security_account.substract(security, count)
        seller_money_account.add(money)
        buyer_security_account.add(security, count)


deal_service = DealService1()
account_system = AccountSystem(db_path, deal_service)
deal_service.set_account_system(account_system)

alice = account_system.customer(1)
bob = account_system.customer(2)
alice.print()
bob.print()

# alice以每股1元的价格向bob购买100股abc股票。
alice.buy(bob, "abc", 1.00, 100)

alice.print()
bob.print()
