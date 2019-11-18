#include <iostream>
#include <vector>
#include <string>
#include <sstream>

using namespace std;

// 手动计算结果为(a,z)(b,x)(c,y)。
// 定义规则：A=(a,b,c)，B=(x,y,z)，BOOL=(t,f)，R=A*B*BOOL。
// 已知：(a,x,f)，(c,x,f)，(c,z,f)。求R中(?,?,t)的全部元素。

class Element {
public:
        Element(int a, int b, bool t)
                :a(a), b(b), t(t) {}
        bool is_compatible_with(Element aElement) {
                if (a == aElement.a && b == aElement.b && t != aElement.t) {
                        return false;
                }
                
                if (a == aElement.a && b != aElement.b && t && aElement.t) {
                        return false;
                }
                
                if (a != aElement.a && b == aElement.b && t && aElement.t) {
                        return false;
                }

                return true;
        }

        string to_string() {
                stringstream stream;
                stream << "(" << a << "," << b << "," << t << ")";
                return stream.str();
        }
        
        int a; // a_set element sequence
        int b; // b_set element sequence
        bool t; // t or f
};

class Solve {
public:
        Solve(int a_set, int b_set, vector<Element> knowledge)
                :a_set(a_set), b_set(b_set), knowledge(knowledge){}

        void solve() {
                result.clear();
                for (int i = 0; i < a_set; i++) {
                        for (int j = 0; j < b_set; j++) {
                                Element element(i, j, true);
                                result.push_back(element);
                        }
                }
                
                for (Element element: knowledge) {
                        if (element.t) {
                                result.push_back(element);
                        } else {
                                remove_uncompatible_result(element);
                        }
                }

                remove_uncompatible_result();
        }

        bool is_a_unique(int a) {
                vector<int> count(a_set, 0);
                
                for (auto it = result.begin(); it != result.end(); it++) {
                        count[it->a]++;
                }

                for (int c: count) {
                        if (c > 1) {
                                return false;
                        }
                }
                return true;
        }

        bool is_b_unique(int b) {
                vector<int> count(b_set, 0);
                
                for (auto it = result.begin(); it != result.end(); it++) {
                        count[it->b]++;
                }

                for (int c: count) {
                        if (c > 1) {
                                return false;
                        }
                }
                return true;
        }

        bool is_finish() {
                for (int i = 0; i < a_set; i++) {
                        if (!is_a_unique(i)) {
                                return false;
                        }
                }
                for (int i = 0; i < b_set; i++) {
                        if (!is_b_unique(i)) {
                                return false;
                        }
                }

                return true;
        }

        vector<Element> find_a_unique() {
                vector<Element> unique;
                vector<int> count(a_set, 0);
                
                for (auto it = result.begin(); it != result.end(); it++) {
                        count[it->a]++;
                }

                for (int i = 0; i < a_set; i++) {
                        int c = count[i];
                        if (c > 1) {
                                continue;
                        }

                        for (Element element: result) {
                                if (element.a == i) {
                                        unique.push_back(element);
                                }
                        }
                }
                return unique;
        }

        vector<Element> find_b_unique() {
                vector<Element> unique;
                vector<int> count(b_set, 0);
                
                for (auto it = result.begin(); it != result.end(); it++) {
                        count[it->b]++;
                }

                for (int i = 0; i < b_set; i++) {
                        int c = count[i];
                        if (c > 1) {
                                continue;
                        }

                        for (Element element: result) {
                                if (element.b == i) {
                                        unique.push_back(element);
                                }
                        }
                }
                return unique;
        }

        void remove_uncompatible_result() {
                while (!is_finish()) {
                        for (Element element: find_a_unique()) {
                                remove_uncompatible_result(element);
                        }
                        for (Element element: find_b_unique()) {
                                remove_uncompatible_result(element);
                        }
                }
        }

        void remove_uncompatible_result(Element element) {
                for (auto it = result.begin(); it != result.end(); ) {
                        if (!it->is_compatible_with(element)) {
                                it = result.erase(it);
                        } else {
                                it++;
                        }
                }
                cout << element.to_string() << " " << result.size() << endl;
        }

        vector<Element> get_result() {
                return result;
        }
private:
        int a_set;
        int b_set;
        vector<Element> knowledge;
        vector<Element> result;
};


int main() {
        Solve solve(3, 3, vector<Element> {
                        Element(0, 0, false),
                                Element(2, 0, false),
                                Element(2, 2, false)
                                });
        solve.solve();
        for (Element element: solve.get_result()) {
                cout << element.a << " " << element.b << endl;
        }
        
        return 0;
}
