#include <iostream>
#include <algorithm>
#include <unordered_set>
#include <iterator>

class Subset_sum {
public:
    Subset_sum(std::unordered_set<int> set, int sum)
        :set(set), sum(sum), result(false) {
    }

    void solve() {
        if (sum == 0) {
            result = true;
            return;
        }

        if (sum < 0 || set.empty()) {
            return;
        }

        std::unordered_set<int> new_set(set);
        for (int x: set) {
            new_set.erase(x);
            Subset_sum sub_problem1(new_set, sum - x);
            sub_problem1.solve();
            if (sub_problem1.get_result()) {
                result = true;
                return;
            }

            Subset_sum sub_problem2(new_set, sum);
            sub_problem2.solve();
            if (sub_problem2.get_result()) {
                result = true;
                return;
            }
            new_set.insert(x);
        }

        result = false;
    }
    bool get_result() {
        return result;
    }
private:
    std::unordered_set<int> set;
    int sum;
    bool result;
};

int main() {
    std::unordered_set<int> set = {1, 2, 3, 4, 5};
    Subset_sum subset_sum(set, 100);
    subset_sum.solve();
    std::cout << std::boolalpha << subset_sum.get_result() << std::endl;
    return 0;
}
