#include <stdio.h>
#include <stdlib.h>

struct list_entry_t;
typedef struct list_entry_t list_entry_t;
typedef list_entry_t list_t;

typedef int (*compare_t)(void*,void*);
typedef void (*list_visitor_t)(void *data, void *param);

struct list_entry_t {
        list_entry_t *prev, *next;
        void *data;
};

struct pair_t {
        void *first;
        void *second;
};

typedef struct pair_t pair_t;

list_t* new_list();
list_t* list_insert(list_t *list, void *data);
void delete_list(list_t *list);

pair_t* new_pair(void *first, void *second);
void delete_pair(pair_t *pair);

int main() {
        int bonus_table[][2] = {
                {0, 100},
                {100000, 75},
                {200000, 50},
                {400000, 30},
                {600000, 15},
                {1000000, 10},
        };
        
        list_t *chain = NULL;
        for (int i = 0; i < sizeof(bonus_table) / sizeof(bonus_table[0]); i++) {
                chain = list_insert(chain, new_pair((void*)bonus_table[i][0], (void*)bonus_table[i][1]));
        }

        double sales = 0.0;
        double bonus = 0.0;
        
        printf("input sales: ");
        scanf("%lf", &sales);
        printf("sales = %.2f\n", sales);
        
        // printf("%.2f %.2f\n", sales, bonus);
        for (list_entry_t *node = (list_entry_t*) chain; node != NULL; node = node->next) {
                pair_t *pair = node->data;
                int limit = (int) pair->first;
                double percentage = ((int) pair->second) / 1000.0;
                
                if (sales > limit) {
                        bonus += percentage * (sales - limit);
                        sales = limit;
                }
                // printf("%d %.2f %.2f\n", limit, sales, bonus);
        }
        
        printf("bonus = %.2f\n", bonus);
        
        for (list_entry_t *node = (list_entry_t*) chain; node != NULL; node = node->next) {
                pair_t *pair = node->data;
                delete_pair(pair);
        }
        delete_list(chain);
        
        return 0;
}

list_t* new_list(void *data) {
        list_entry_t *node = (list_entry_t*) malloc(sizeof(list_entry_t));
        if (node == NULL) {
                abort();
        }

        node->prev = NULL;
        node->next = NULL;
        node->data = data;
        return (list_t*) node;
}

list_t* list_insert(list_t *list, void *data) {
        list_entry_t *node = (list_entry_t*) malloc(sizeof(list_entry_t));
        if (node == NULL) {
                abort();
        }

        list_entry_t *head = (list_entry_t*) list;
        node->next = head;
        node->prev = NULL;
        node->data = data;

        if (head != NULL) {
                head->prev = node;
        }
        
        return (list_t*) node;
}

void delete_list(list_t *list) {
        list_entry_t* node = (list_entry_t*) list;
        while (node != NULL) {
                node = node->next;
                free(node);
        }
}

pair_t* new_pair(void *first, void *second) {
        pair_t *pair = (pair_t*) malloc(sizeof(pair_t));
        if (pair == NULL) {
                abort();
        }

        pair->first = first;
        pair->second = second;
        return pair;
}

void delete_pair(pair_t *pair) {
        free(pair);
}
