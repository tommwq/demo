/**
 * 展示std::vector的容量的变化。
 */

#include <vector>
#include <iostream>

template<class T>
void print_vector_size(const std::vector<T> &vec){
  std::cout << "size: " << vec.size() << " capacity: " << vec.capacity() << std::endl;
}

int main(void){

  std::vector<int> vec;
  print_vector_size(vec);

  size_t capacity(vec.capacity());

  for (int i = 0; i < 1000; ++i){
    vec.push_back(i);
    if (vec.capacity() != capacity){
      capacity = vec.capacity();
      print_vector_size(vec);
    }
  }
  print_vector_size(vec);

  for (int i = 0;  i < 100; ++i){
    vec.erase(vec.begin());
  }
  print_vector_size(vec);

  vec.clear();
  print_vector_size(vec);
  
  vec.reserve(10);
  print_vector_size(vec);

  vec.resize(0);
  print_vector_size(vec);
  
  while (!vec.empty()){
    vec.erase(vec.begin());
  }
  print_vector_size(vec);

  std::vector<int> trash;
  vec.swap(trash);
  print_vector_size(vec);

  return 0;
}
