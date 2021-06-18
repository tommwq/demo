#include <iostream>
#include <array>
#include <algorithm>
#include <iterator>

template<int N>
class Queue_problem {
public:
    void solve() {
        solve(0);
    }
private:
    void solve(int row) {
        if (row == N) {
            print();
            return;
        }

        for (int column = 0; column < N; column++) {
            bool legal = true;
            for (int r = 0; r < row; r++) {
                if (queue[r] == column || queue[r] == column + row - r || queue[r] == column - row + r) {
                    legal = false;
                }
            }

            if (legal) {
                queue[row] = column;
                solve(row + 1);
            }
        }
    }
    
    void print() {
        std::copy(queue.begin(), queue.end(), std::ostream_iterator<int>(std::cout, " "));
        std::cout << std::endl;
    }
private:
    std::array<int,N> queue;
};

int main() {
    Queue_problem<8> problem;
    problem.solve();
    
    return 0;
}
