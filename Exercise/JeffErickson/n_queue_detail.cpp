#include <iostream>
#include <array>
#include <algorithm>
#include <iterator>

template<int N>
class Queue_problem {
public:
    void solve() {
        solve_next_row(0);
    }
private:
    void solve_next_row(int row) {
        if (is_solved(row)) {
            print();
            return;
        }

        for (int column = 0; column < N; column++) {
            if (is_valid_queue_position(row, column)) {
                queue[row] = column;
                solve_next_row(row + 1);
            }
        }
    }

    bool is_valid_queue_position(int row, int column) {
        for (int r = 0; r < row; r++) {
            if (queue[r] == column || queue[r] == column + row - r || queue[r] == column - row + r) {
                return false;
            }
        }
        
        return true;
    }

    bool is_solved(int row) {
    return row == N;
}
    
void print() {
    std::copy(queue.begin(), queue.end(), std::ostream_iterator<int>(std::cout, " "));
    std::cout << std::endl;
}
private:
std::array<int,N> queue;
};

int main() {
    Queue_problem<4> problem;
    problem.solve();
    
    return 0;
}
