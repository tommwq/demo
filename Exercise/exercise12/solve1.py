prime = [1]*100
for i in range(0, 100):
    n = i + 101
    p = 2
    while p < 100:
        if n % p == 0:
            prime[i] = 0
        if p == 2:
            p = p + 1
        else:
            p = p + 2


for i in range(0, 100):
    if prime[i]:
        print(i + 101)
