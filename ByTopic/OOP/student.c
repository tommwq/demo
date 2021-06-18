#include <stdio.h>
#include <stdlib.h>
#include "student.h"

struct Student {
    struct StudentInterface *interface;
    char name[16];
    int score;
};

struct StudentInterface _student_interface;

void _student_print_name(struct Student *s) {
    printf("%s\n", s->name);
}

void _student_set_name(struct Student *s, const char *name) {
    strncpy(s->name, name, 16);
}

void _student_print_score(struct Student *s) {
    printf("%d\n", s->score);
}

void _student_set_score(struct Student *s, int score) {
    s->score = score;
}

int _student_compare_score(struct Student *lhs, struct Student *rhs) {
    int a = lhs->score;
    int b = rhs->score;
    if (a < b) {
        return -1;
    } else if (a == b) {
        return 0;
    } else {
        return 1;
    }
}

void _student_destroy(struct Student *student) {
    free(student);
}

struct Student* Student_create() {
    struct Student *s = (struct Student*) malloc(sizeof(struct Student));
    if (s == NULL) {
        return NULL;
    }
    s->interface = &_student_interface;
    return s;
}

void Student_class_init() {
    _student_interface.print_name = _student_print_name;
    _student_interface.print_score = _student_print_score;
    _student_interface.set_name = _student_set_name;
    _student_interface.set_score = _student_set_score;
    _student_interface.compare_score = _student_compare_score;
    _student_interface.destroy = _student_destroy;
}

struct CheatStudent {
    struct Student base;
};

struct StudentInterface _cheatstudent_interface;

struct CheatStudent* CheatStudent_create() {
    struct CheatStudent *s = (struct CheatStudent*) malloc(sizeof(struct CheatStudent));
    if (s == NULL) {
        return NULL;
    }
    s->base.interface = &_cheatstudent_interface;
    return s;
}

void _cheatstudent_print_score(struct Student *s) {
    printf("%d(cheat)\n", 100);
}

void CheatStudent_class_init() {
    _cheatstudent_interface.print_name = _student_print_name;
    _cheatstudent_interface.print_score = _cheatstudent_print_score;
    _cheatstudent_interface.set_name = _student_set_name;
    _cheatstudent_interface.set_score = _student_set_score;
    _cheatstudent_interface.compare_score = _student_compare_score;
    _cheatstudent_interface.destroy = _student_destroy;
}
