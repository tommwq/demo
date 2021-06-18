// 有3个塔，编号分别为0、1和2。初始时，塔0上有n个盘子，从小到大叠放。需要寻找一个方法，将着n个盘子移动到塔2上。移动的规则是：一、每次只能移动一个盘子。二、每次移动时，只能将小盘子移动到大盘子上。

// 迭代算法
// 如果n是偶数，交换塔1和塔2的位置。第i（i从1开始计算）次迭代时，忽略塔(i%3)。在剩余两个塔中，执行符合规则的盘子移动。如果按照规则没有盘子可以移动，算法结束。


#include <iostream>
#include <stack>
#include <array>

class Peg: public std::stack<int> {
public:
    void set_number(int value) {
        number = value;
    }
    int get_number() {
        return number;
    }
private:
    int number;
};

template<int number_of_disk>
class Hanoi {
public:
    Hanoi() {
        for (int i = 0; i < pegs.size(); i++) {
            pegs[i].set_number(i);
        }
        
        for (int i = number_of_disk; i > 0; i--) {
            pegs[0].push(i);
        }
    }
    
    void solve() {
        int from_index;
        int to_index;
        int tmp;
        
        int valid_peg_pair[3][2] = {
            {1, 2},
            {0, 2},
            {0, 1},
        };

        int dest_index = 2;
        if (number_of_disk % 2 == 0) {
            // pegs.swap_peg_1_2();
            pegs[1].set_number(2);
            pegs[2].set_number(1);
            dest_index = 1;
        }

        Peg &dest(pegs[dest_index]);
            
        // 迭代次数从1开始
        for (int i = 1; dest.size() != number_of_disk; i++) {
            int *pair = valid_peg_pair[i % 3];
            from_index = pair[0];
            to_index = pair[1];
            Peg &to(pegs[to_index]);
            Peg &from(pegs[from_index]);
            
            if (from.empty() || (!to.empty() && to.top() < from.top())) {
                tmp = from_index;
                from_index = to_index;
                to_index = tmp;
            }
            move_disk(from_index, to_index);
        }
    }

    void move_disk(int from_index, int to_index) {
        Peg &from_peg = pegs[from_index];
        Peg &to_peg = pegs[to_index];
        to_peg.push(from_peg.top());
        from_peg.pop();
        std::cout << from_peg.get_number() << " => " << to_peg.get_number() << std::endl;
    }
private:
    std::array<Peg,3> pegs;
};

int main() {
    Hanoi<3> hanoi;
    hanoi.solve();

    Hanoi<2> hanoi2;
    hanoi2.solve();
    return 0;
}
