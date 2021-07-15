struct Student;

typedef void (*Student_print_name)(struct Student *);
typedef void (*Student_set_name)(struct Student *, const char *);
typedef void (*Student_print_score)(struct Student *s);
typedef void (*Student_set_score)(struct Student *s, int score);
typedef int (*Student_get_score)(struct Student *s);
typedef int (*Student_compare_score)(struct Student *lhs, struct Student *rhs);
typedef void (*Student_destroy)(struct Student *s);

struct StudentInterface {
    Student_print_name print_name;
    Student_set_name set_name;
    Student_print_score print_score;
    Student_set_score set_score;
    Student_get_score get_score;
    Student_compare_score compare_score;
    Student_destroy destroy;
};

typedef void (*CheatStudent_cheat)(struct CheatStudent *cs);

struct CheatStudentInterface {
    struct StudentInterface base;
    CheatStudent_cheat cheat;
};


void Student_class_init();
struct Student* Student_create();

struct CheatStudent;
void CheatStudent_class_init();
struct CheatStudent* CheatStudent_create();

#define AS_INTERFACE(INTERFACE, POINTER_TO_INSTANCE) ((INTERFACE *) *((void**)POINTER_TO_INSTANCE))
#define CREATE_DERIVED_INSTANCE(BASE_TYPE, CREATOR) (BASE_TYPE) (CREATOR())

