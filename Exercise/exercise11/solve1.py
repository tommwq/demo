def fib(n):
    a = 0
    b = 1
    for i in range(0, n):
        a, b = b, a
        b = a + b
        print(a)

fib(7)
