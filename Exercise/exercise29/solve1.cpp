#include <deque>
#include <iostream>
#include <cstdio>

using namespace std;

int main() {

        int number;
        scanf("%d", &number);

        deque<int> queue;

        while (number > 0) {
                queue.push_back(number % 10);
                number /= 10;
        }

        cout << queue.size() << endl;

        while (!queue.empty()) {
                cout << queue.front();
                queue.pop_front();
        }
        
        return 0;
}
