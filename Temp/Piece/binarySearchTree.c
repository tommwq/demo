
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Data {
  void *_buf;
  int _len;
};

int defaultCompareFunction(const void *left, const void *right){
  return memcmp(left, right, 1);
}

struct Data *createData(const void *buf, int len){
  struct Data *data;
  if ((data = (struct Data *)malloc(sizeof(*data))) == 0){
    return 0;
  }

  if ((data->_buf = malloc(len)) == 0){
    free(data);
    return 0;
  }
  data->_len = len;
  memcpy(data->_buf, buf, len);
  return data;
}

struct Data *dataCopy(struct Data *data){
  struct Data *newData;
  if ((newData = (struct Data *)malloc(sizeof(*newData))) == 0){
    return 0;
  }
  
  if ((newData->_buf = malloc(data->_len)) == 0){
    free(newData);
    return 0;
  }

  memcpy(newData->_buf, data->_buf, data->_len);
  newData->_len = data->_len;
  return newData;
}

int destroyData(struct Data *data){
  if (data == 0){
    return 0;
  }

  free(data->_buf);
  free(data);
  return 0;
}

struct Node {
  struct Node *_left, *_right;
  struct Data *_key, *_value;
};

struct Node *createNode(struct Data *key, struct Data *value){
  struct Node *node;
  struct Data *newKey, *newValue;

  if (key == 0){
    return 0;
  }

  if ((node = (struct Node *)malloc(sizeof(*node))) == 0){
    return 0;
  }

  if ((newKey = dataCopy(key)) == 0){
    free(node);
  }

  if (value == 0){
    if ((newValue = (struct Data *)malloc(sizeof(*newValue))) == 0){
      free(newKey);
      free(node);
      return 0;
    }
    newValue->_buf = 0;
    newValue->_len = 0;
  } else {
    if ((newValue = dataCopy(value)) == 0){
      free(newKey);
      free(node);
      return 0;
    }
  }
  
  node->_key = newKey;
  node->_value = newValue;
  node->_left = 0;
  node->_right = 0;
  return node;
}

struct BinarySearchTree {
  int (*_compare)(const void *, const void *);
  struct Node *_root;
};


struct BinarySearchTree *createBinarySearchTree(int (*compare)(const void *, const void *));
int binarySearchTreeInsert(struct BinarySearchTree *tree, struct Data *key, struct Data *value);
struct Data *binarySearchTreeFind(struct BinarySearchTree *tree, const struct Data *key);
int binarySearchTreeRemove(struct BinarySearchTree *tree, const struct Data *key);
int destroyBinarySearchTree(struct BinarySearchTree *tree);


struct BinarySearchTree *createBinarySearchTree(int (*compare)(const void *, const void *)){
  struct BinarySearchTree *tree = (struct BinarySearchTree *)malloc(sizeof(*tree));
  if (tree == 0){
    return 0;
  }

  tree->_root = 0;
  tree->_compare = compare;
  return tree;
}

int binarySearchTreeInsert(struct BinarySearchTree *tree, struct Data *key, struct Data *value){
  if (tree == 0 || key == 0){
    return -1;
  }

  if (tree->_root == 0){
    tree->_root = createNode(key, value);
  } else {
    int (*compare)(const void *, const void *);
    if (tree->_compare == 0){
      compare = defaultCompareFunction;
    } else {
      compare = tree->_compare;
    }
  }

  return 0;
}

struct Data *binarySearchTreeFind(struct BinarySearchTree *tree, const struct Data *key){
  return 0;
}

int binarySearchTreeRemove(struct BinarySearchTree *tree, const struct Data *key){
  return -1;
}
int destroyBinarySearchTree(struct BinarySearchTree *tree){
  return -1;
}


int main(){
  return 0;
}
