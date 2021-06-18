#include <stdio.h>
#include <stdlib.h>
#include "student.h"

int main() {
    Student_class_init();
    
    struct Student *student = Student_create();
    AS_INTERFACE(struct StudentInterface, student)->set_name(student, "Alice");
    AS_INTERFACE(struct StudentInterface, student)->print_name(student);
    AS_INTERFACE(struct StudentInterface, student)->set_score(student, 90);
    AS_INTERFACE(struct StudentInterface, student)->print_score(student);
    AS_INTERFACE(struct StudentInterface, student)->destroy(student);

    CheatStudent_class_init();
    struct Student *cheat_student = CREATE_DERIVED_INSTANCE(struct Student*, CheatStudent_create);
    AS_INTERFACE(struct StudentInterface, cheat_student)->set_name(cheat_student, "Bob");
    AS_INTERFACE(struct StudentInterface, cheat_student)->print_name(cheat_student);
    AS_INTERFACE(struct StudentInterface, cheat_student)->set_score(cheat_student, 90);
    AS_INTERFACE(struct StudentInterface, cheat_student)->print_score(cheat_student);
    AS_INTERFACE(struct StudentInterface, cheat_student)->destroy(cheat_student);
    
    return 0;
}
