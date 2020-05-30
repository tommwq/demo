#include <iostream>
#include <deque>

using namespace std;

bool is_palindrome(int number);

int main() {
        int number;
        cin >> number;

        cout << is_palindrome(number);
        return 0;
}

bool is_palindrome(int number) {
        deque<int> deque;

        while (number > 0) {
                deque.push_front(number % 10);
                number /= 10;
        }

        while (!deque.empty()) {
                if (deque.size() == 1) {
                        break;
                }

                int a = deque.front();
                deque.pop_front();
                int b = deque.back();
                deque.pop_back();
                
                if (a != b) {
                        return false;
                }
        }

        return true;
}
