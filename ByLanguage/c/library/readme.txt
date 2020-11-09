基础库

包括：

字符串
链表
数组
树
堆
优先队列
图
散列表


#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

const int SUCCESS = 0;
const int FAILURE = 1;

struct stack {
    void **data;
    int capacity;
    int size;
};

struct stack* stack_init(struct stack *stack) {
    stack->data = malloc(sizeof(void*) * 1024);
    if (stack->data == NULL) {
        return NULL;
    }
    
    stack->capacity = 1024;
    stack->size = 0;
    return stack;
}

void stack_release(struct stack *stack) {
    free(stack->data);
    stack->data = NULL;
    stack->size = 0;
    stack->capacity = 0;
}

int stack_is_empty(const struct stack *stack) {
    return stack->size == 0;
}

int stack_is_full(const struct stack *stack) {
    return stack->size == stack->capacity;
}

int stack_push(struct stack *stack, void *element) {
    if (stack_is_full(stack)) {
        return FAILURE;
    }

    stack->data[stack->size++] = element;
    return SUCCESS;
}

int stack_top(const struct stack *stack, void **result) {
    if (stack_is_empty(stack)) {
        return FAILURE;
    }

    *result = stack->data[stack->size - 1];
    return SUCCESS;
}

int stack_pop(struct stack *stack) {
    if (stack_is_empty(stack)) {
        return FAILURE;
    }

    stack->size--;
    return SUCCESS;
}


int main() {
    const char *infix = "(5*(((9+8)*(4*6))+7))";
    const char *postfix = "5 9 8+4 6**7+*";
    
    struct stack stack;
    stack_init(&stack);

    for (const char *p = postfix; *p != '\0'; p++) {
        char ch = *p;
        if (isspace(ch)) {
            continue;
        }
        
        if (isdigit(ch)) {
            int *value = (int*) malloc(sizeof(int));
            *value = ch - '0';
            stack_push(&stack, value);
            continue;
        }

        char operate = ch;
        int *op1;
        int *op2;
        int *result = (int*) malloc(sizeof(int));
        
        stack_top(&stack, &op1);
        stack_pop(&stack);
        stack_top(&stack, &op2);
        stack_pop(&stack);

        switch (operate) {
        case '+':
            *result = *op1 + *op2;
            break;
        case '-':
            *result = *op1 - *op2;
            break;
        case '*':
            *result = *op1 * *op2;
            break;
        case '/':
            *result = *op1 / *op2;
            break;
        }

        stack_push(&stack, result);
        free(op1);
        free(op2);
    }

    int *result;
    stack_top(&stack, &result);
    stack_pop(&stack);
    printf("%d\n", *result);
    free(result);

    stack_release(&stack);

    return 0;
}

struct linked_list {
    struct linked_list *next;
    void *data;
};

struct linked_list* linked_list_insert(struct linked_list *list, const void *data) {
    struct linked_list *node = (struct linked_list*) malloc(sizeof(struct linked_list));
    if (node == NULL) {
        return NULL;
    }

    node->data = data;
    node->next = NULL;

    if (list == NULL) {
        return node;
    }

    node->next = list->next;
    list->next = node;
    return node;
}

int linked_list_remove(struct linked_list *list, struct linked_list *to_remove) {
    struct linked_list *prev = NULL;
    struct linked_list *next = NULL;
    
    for (prev = list; prev != NULL && prev->next != to_remove; prev = prev->next) {
        NULL;
    }

    if (prev == NULL) {
        return -1;
    }

    next = to_remove->next;
    prev->next = next;
    free(to_remove);
    return 0;
}

struct double_linked_list;
struct array_list;
struct queue;
struct deque;
struct hash_table;


struct binary_tree {
    struct binary_tree *parent;
    struct binary_tree *left;
    struct binary_tree *right;
    void *data;
};

// 三叉树
struct ternary_tree {
    struct ternary_tree *parent;
    struct ternary_tree *left;
    struct ternary_tree *middle;
    struct ternary_tree *right;
    void *data;
};

// n叉树
struct nary_tree {
    struct ternary_tree *parent;
    struct ternary_tree **children;
    void *data;
};

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

struct binary_tree {
    struct binary_tree *parent;
    struct binary_tree *left_child;
    struct binary_tree *right_child;
    void *data;
};

int binary_tree_insert_left_child(struct binary_tree *tree, struct binary_tree *node) {
    if (tree == NULL) {
        return -1;
    }

    if (tree->left_child != NULL || node->parent != NULL) {
        return -1;
    }

    tree->left_child = node;
    return 1;
}

int binary_tree_insert_right_child(struct binary_tree *parent, struct binary_tree *node) {
    if (parent == NULL) {
        return -1;
    }

    if (parent->right_child != NULL || node->parent != NULL) {
        return -1;
    }

    parent->right_child = node;
    node->parent = parent;
    return 1;    
}

int binary_tree_init(struct binary_tree *tree) {
    if (tree == NULL) {
        return -1;
    }

    tree->parent = NULL;
    tree->left_child = NULL;
    tree->right_child = NULL;
    tree->data = NULL;
    return 0;
}

typedef void (*binary_tree_visitor)(struct binary_tree *tree, void *parameter);


enum traverse_mode {
    preorder,
    inorder,
    postorder
};

void binary_tree_visit(struct binary_tree *tree, binary_tree_visitor visitor, void *parameter, enum traverse_mode mode) {
    struct binary_tree *nodes[3] = { NULL, NULL, NULL };
    
    switch (mode) {
    case preorder:
        nodes[0] = tree;
        nodes[1] = tree->left_child;
        nodes[2] = tree->right_child;
        break;
    case inorder:
        nodes[0] = tree->left_child;
        nodes[1] = tree;
        nodes[2] = tree->right_child;
        break;
    case postorder:
        nodes[0] = tree->left_child;
        nodes[1] = tree->right_child;
        nodes[2] = tree;
        break;
    default:
        return;
    }

    for (int i = 0; i < 3; i++) {
        if (nodes[i] != NULL) {
            visitor(nodes[i], parameter);
        }
    }
}

void print_int(struct binary_tree *tree, void *parameter) {
    int value = *(int*) tree->data;
    printf("%d ", value);
}

int main() {
    struct binary_tree root, l, r;
    binary_tree_init(&root);
    binary_tree_init(&l);
    binary_tree_init(&r);

    int a = 1;
    int b = 2;
    int c = 3;
    root.data = &b;
    l.data = &a;
    r.data = &c;

    binary_tree_insert_left_child(&root, &l);
    binary_tree_insert_right_child(&root, &r);

    binary_tree_visit(&root, print_int, NULL, preorder);
    printf("\n");
    binary_tree_visit(&root, print_int, NULL, inorder);
    printf("\n");
    binary_tree_visit(&root, print_int, NULL, postorder);
    printf("\n");
    
    return 0;
}


void binary_tree_visit_iter_preorder(struct binary_tree *tree, binary_tree_visitor visitor, void *parameter) {

    struct binary_tree *node;            

    struct stack stack;
    stack_init(&stack);

    stack_push(&stack, tree);
    while (!stack_is_empty(&stack)) {

        stack_top(&stack, &node);
        stack_pop(&stack);
        visitor(node, parameter);
        if (node->right_child != NULL) {
            stack_push(&stack, node->right_child);
        }
        if (node->left_child != NULL) {
            stack_push(&stack, node->left_child);
        }
    }
    
    stack_release(&stack);
}

void binary_tree_visit_iter_inorder(struct binary_tree *tree, binary_tree_visitor visitor, void *parameter) {
    struct stack stack;
    stack_init(&stack);
    
    struct binary_tree *node;
    struct binary_tree *last_pop = NULL;

    stack_push(&stack, tree);
    while (!stack_is_empty(&stack)) {
        stack_top(&stack, &node);
        while (node != NULL && node->left_child != last_pop && node->left_child != NULL) {
            stack_push(&stack, node->left_child);
            node = node->left_child;
        }

        stack_top(&stack, &node);
        stack_pop(&stack);
        last_pop = node;
        visitor(node, parameter);
        if (node->right_child != NULL) {
            stack_push(&stack, node->right_child);
        }
    }

    stack_release(&stack);
}

void binary_tree_visit_iter_postorder(struct binary_tree *tree, binary_tree_visitor visitor, void *parameter) {
    struct stack stack;
    stack_init(&stack);
    
    struct binary_tree *node;
    struct binary_tree *last_pop = NULL;
    
    stack_push(&stack, tree);
    while (!stack_is_empty(&stack)) {
        stack_top(&stack, &node);

        if (last_pop == node->left_child && node->right_child != NULL) {
            stack_push(&stack, node->right_child);
            continue;
        }

        if ((last_pop == node->left_child && node->right_child == NULL) || last_pop == node->right_child) {
            stack_top(&stack, &node);
            stack_pop(&stack);
            last_pop = node;
            visitor(node, parameter);
            continue;
        }

        while (node != NULL && node->left_child != NULL) {
            stack_push(&stack, node->left_child);
            node = node->left_child;
        }

        stack_top(&stack, &node);
        if (node->right_child != NULL) {
            stack_push(&stack, node->right_child);
            continue;
        }

        stack_top(&stack, &node);
        stack_pop(&stack);
        last_pop = node;
        visitor(node, parameter);
    }

    stack_release(&stack);
}

void binary_tree_visit_iter(struct binary_tree *tree, binary_tree_visitor visitor, void *parameter, enum traverse_mode mode) {
    switch (mode) {
    case preorder:
        binary_tree_visit_iter_preorder(tree, visitor, parameter);
        break;
    case inorder:
        binary_tree_visit_iter_inorder(tree, visitor, parameter);
        break;
    case postorder:
        binary_tree_visit_iter_postorder(tree, visitor, parameter);
        break;
    default:
        break;
    }
}


struct queue {
    void **data;
    int capacity;
    int size;
};

struct queue* queue_init(struct queue *queue) {
    queue->data = malloc(sizeof(void*) * 1024);
    if (queue->data == NULL) {
        return NULL;
    }
    
    queue->capacity = 1024;
    queue->size = 0;
    return queue;
}

void queue_release(struct queue *queue) {
    free(queue->data);
    queue->data = NULL;
    queue->size = 0;
    queue->capacity = 0;
}

int queue_is_empty(const struct queue *queue) {
    return queue->size == 0;
}

int queue_is_full(const struct queue *queue) {
    return queue->size == queue->capacity;
}

int queue_enqueue(struct queue *queue, void *element) {
    if (queue_is_full(queue)) {
        return FAILURE;
    }

    queue->data[queue->size++] = element;
    return SUCCESS;
}

int queue_get(const struct queue *queue, void **result) {
    if (queue_is_empty(queue)) {
        return FAILURE;
    }

    *result = queue->data[0];
    return SUCCESS;
}

int queue_dequeue(struct queue *queue) {
    if (queue_is_empty(queue)) {
        return FAILURE;
    }

    for (int i = 0; i < queue->size - 1; i++) {
        queue->data[i] = queue->data[i+1];
    }

    queue->size--;
    return SUCCESS;
}

void binary_tree_visit_by_level(struct binary_tree *tree, binary_tree_visitor visitor, void *parameter) {
    struct queue queue;
    queue_init(&queue);
    queue_enqueue(&queue, tree);
    
    struct binary_tree *node;
    while (!queue_is_empty(&queue)) {
        queue_get(&queue, &node);
        queue_dequeue(&queue);
        visitor(node, parameter);
        if (node->left_child != NULL) {
            queue_enqueue(&queue, node->left_child);
        }
        if (node->right_child != NULL) {
            queue_enqueue(&queue, node->right_child);
        }
    }
    queue_release(&queue);
}
