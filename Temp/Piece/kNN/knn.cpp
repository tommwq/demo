/*
  knn算法例子
  @todo
  文件格式：
  label
  a b c d
  data
  # id label value1, value2, ...
  1 1 v1 v2 v3 
  2 1 v1 v2 v3
*/



#include <cmath>
#include <utility>
#include <iostream>
#include <algorithm>
#include <vector>
#include <string>
#include <map>
#include <numeric>
#include <iterator>

template<size_t N>
class Point {
public:
    Point(const Point &);
    Point();
    Point(std::initializer_list<double> list);
    double operator*(const Point &point);
    double Distance(const Point &point);
    size_t Size() const;
    void Label(int label);
    int Label() const;
    void Set(size_t index, double value);
    double Get(size_t index) const; // nan on bad index
private:
    double  mComponent[N];
    int mLabel;
};

template<size_t N>
double Point<N>::Get(size_t index) const {
    if (index >= N){
        return std::nan("");
    }
    return mComponent[index];
}

template<size_t N>
void Point<N>::Set(size_t index, double value){
    if (index >= N){
        return;
    }
    mComponent[index] = value;
}

template<size_t N>
void Point<N>::Label(int label){
    mLabel = label;
}

template<size_t N>
int Point<N>::Label() const {
    return mLabel;
}

template<size_t N>
size_t Point<N>::Size() const {
    return N;
}

template<size_t N>
double Point<N>::Distance(const Point &p){
    double middle[N];
    std::transform(mComponent, mComponent + N, p.mComponent, middle, [](double x, double y){
            return (x - y) * (x - y);
        });
    return std::sqrt(std::accumulate(middle, middle + N, 0.0));
}

template<size_t N>
double Point<N>::operator*(const Point &p){
    return std::inner_product(mComponent, mComponent + N, p.mComponent, 0.0);
}

template<size_t N>
Point<N>::Point(const Point &p){
    std::copy(p.mComponent, p.mComponent + N, mComponent);
    mLabel = p.mLabel;
}

template<size_t N>
Point<N>::Point(std::initializer_list<double> list){
    std::fill(mComponent, mComponent + N, 0.0);
    std::copy_n(list.begin(), N, mComponent);
    if (list.size() > N){
        int label = static_cast<int>(*(list.begin() + N));
        Label(label);
    }
}

template<size_t N>
Point<N>::Point(){
    std::fill(mComponent, mComponent + N, 0.0);
    mLabel = 0;
}

template<size_t N>
int Knn(Point<N> &point, const std::vector<Point<N>> &base, size_t radius){
    std::vector<std::pair<Point<N>, double>> distances;
    for (auto x : base){
        distances.push_back(std::make_pair(x, point.Distance(x)));
    }
    std::sort(distances.begin(), distances.end(), [&](std::pair<Point<N>,double> x, std::pair<Point<N>,double> y){
            return x.second - y.second;
        });
    distances.resize(radius);
    std::map<int,int> candidates; // label, times
    int nearest{-1};
    for (auto x : distances){
        int times = ++candidates[x.first.Label()];
        if (nearest == -1 || times > candidates[nearest]){
            nearest = x.first.Label();
        }
    }
    if (nearest == -1){
        return -1;
    }
    return nearest;
}

std::string classify(const std::vector<double> &point, 
                     const std::vector<std::vector<double>> &input, 
                     const std::vector<std::string> &label,
                     size_t radius){
    // 计算point和现有点的距离
    std::vector<std::pair<int, double>> distances;
    for (int i = 0; i < input.size(); ++i){
        auto y = input[i];
        double d1{y[0] - point[0]};
        double d2{y[1] - point[0]};
        double d = std::sqrt(d1 * d1 + d2 * d2);
        distances.push_back(std::make_pair(i, d));
    }
    // 排序，取前radius个点
    std::sort(distances.begin(), distances.end(), [&](std::pair<int, double> x, std::pair<int, double> y){
            return x.second - y.second;
        });
    distances.resize(radius);
    std::map<std::string, int> candidate;
    for (auto x : distances){
        candidate[label[x.first]]++;
    }
    auto result = candidate.begin();
    if (result == candidate.end()){
        return "ERROR";
    }
    for (auto x = candidate.begin(); x != candidate.end(); ++x){
        if (x->second > result->second){
            result = x;
        }
    }
    // 取其中label最多的label，作为结果。
    return result->first;
}

int main(int argc, char **argv){
    /*
      std::vector<std::vector<double>> input{{ 1.0, 1.1 }, { 1.0, 1.0 }, { 0, 0 }, { 0, 0.1 }};
      std::vector<std::string> label{"A", "A", "B", "B"};
      std::vector<double> point{0, 0};
      size_t depth{3};
      std::cout << classify(point, input, label, depth) << std::endl;
    */

    Point<2> p{0, 0};
    std::vector<Point<2>> base{
        { 1.0, 1.1, 1 }, { 1.0, 1.0, 1 }, { 0, 0, 2 }, { 0, 0.1, 2 }
    };
    size_t radius{3};
    std::cout << Knn(p, base, radius) << std::endl;
    return 0;
}
