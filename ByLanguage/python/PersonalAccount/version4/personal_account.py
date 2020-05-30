import tkinter

class Item(object):
    def __init__(self, is_debit, label, amount):
        self.is_debit = is_debit
        self.label = label
        self.amount = amount

class Account(object):
    def __init__(self):
        self.items = []
        self.expense = 0.0
        self.income = 0.0
        self.balance = 0.0

    def add_item(self, is_debit, label, amount):
        self.items.append(Item(is_debit, label, amount))

    def calculate(self):
        self.expense = sum([item.amount for item in self.items if item.is_debit])
        self.income = sum([item.amount for item in self.items if not item.is_debit])
        self.balance = self.income - self.expense

class ViewModel(object):
    def __init__(self):
        self.debit_or_credit = tkinter.StringVar()
        self.item = tkinter.StringVar()
        self.price = tkinter.StringVar()
        self.income = tkinter.StringVar()
        self.expense = tkinter.StringVar()
        self.balance = tkinter.StringVar()
        self.message = tkinter.StringVar()
        self.text = tkinter.StringVar()
        self.income.set('0.0')
        self.expense.set('0.0')
        self.balance.set('0.0')

    def add_item(self):
        self.text.set('{}\n{} {} {}'.format(self.text.get(),
                                            self.debit_or_credit.get(),
                                            self.item.get(),
                                            self.price.get()))

    def is_valid(self):
        if not self.debit_or_credit.get() in ['DEBIT', 'CREDIT']:
            return False
        
        if len(self.item.get()) == 0:
            return False

        try:
            float(self.price.get())
            return True
        except:
            return False

    def to_entity(self):
        return (self.debit_or_credit.get() == 'DEBIT', self.item.get(), float(self.price.get()))

class App(object):
    def __init__(self):
        self.main = tkinter.Tk()
        self.viewmodel = ViewModel()
        self.account = Account()
        main = self.main        
        viewmodel = self.viewmodel
        
        main.title('PERSONAL ACCOUNT')
        main['width'] = 400
        main['height'] = 300
        
        tkinter.Label(main, text="ITEM").place(x=100, y=0, width=100, height=20)
        tkinter.Label(main, text="PRICE").place(x=200, y=0, width=100, height=20)
        
        tkinter.Spinbox(main, values=('DEBIT','CREDIT'), textvariable=viewmodel.debit_or_credit).place(x=0, y=30, width=100, height=20)
        tkinter.Entry(main, textvariable=viewmodel.item).place(x=100, y=30, width=100, height=20)
        tkinter.Entry(main, textvariable=viewmodel.price).place(x=200, y=30, width=100, height=20)
        tkinter.Button(main, text='ENTER', command=self.enter_item).place(x=300, y=30, width=100, height=20)
        
        tkinter.Label(main, text='TOTAL INCOME').place(x=0, y=60, width=100, height=20)
        tkinter.Label(main, text='TOTAL EXPENSE').place(x=100, y=60, width=100, height=20)
        tkinter.Label(main, text='BALANCE').place(x=200, y=60, width=100, height=20)
        
        tkinter.Label(main, textvariable=viewmodel.income).place(x=0, y=90, width=100, height=20)
        tkinter.Label(main, textvariable=viewmodel.expense).place(x=100, y=90, width=100, height=20)
        tkinter.Label(main, textvariable=viewmodel.balance).place(x=200, y=90, width=100, height=20)
        
        tkinter.Label(main, textvariable=self.viewmodel.message, anchor='w').place(x=0, y=120, width=400, height=20)

        tkinter.Label(main, textvariable=self.viewmodel.text, anchor='nw').place(x=0, y=150, width=400, height=100)

        main.mainloop()

    def enter_item(self):
        self.show_message('')
        if not self.viewmodel.is_valid():
            self.show_message('invalid input')
        else:
            self.add_item()

    def add_item(self):
        self.account.add_item(*self.viewmodel.to_entity())
        self.account.calculate()
        self.viewmodel.income.set(str(self.account.income))
        self.viewmodel.expense.set(str(self.account.expense))
        self.viewmodel.balance.set(str(self.account.balance))
        self.viewmodel.add_item()

    def show_message(self, message):
        self.viewmodel.message.set(message)

App()
