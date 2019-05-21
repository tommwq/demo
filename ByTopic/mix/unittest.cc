#include "unittest.hh"

namespace unittest {

    void expect(int expected, int actural, char *file, int line, char *expected_expr, char *actural_expr) {
        if (expected == actural) {
            return;
        }

        std::cout << "TEST FAILED: " << file << "+" << line << std::endl
                  << "    expected: " << expected_expr << "(" << expected << ")" << std::endl
                  << "    actural:  " << actural_expr << "(" << actural << ")" << std::endl
                  << std::endl;
    }

    void expect_true(bool value, char *file, int line, char *expr) {
        if (value) {
            return;
        }

        std::cout << "TEST FAILED: " << file << "+" << line << std::endl
                  << "    expected: true" << std::endl
                  << "    actural:  " << value << std::endl
                  << std::endl;
    }

    void expect_false(bool value, char *file, int line, char *expr) {
        if (!value) {
            return;
        }

        std::cout << "TEST FAILED: " << file << "+" << line << std::endl
                  << "    expected: false" << std::endl
                  << "    actural:  " << value << std::endl
                  << std::endl;
    }

    void suit(const std::string& name) {
        std::cout << name << std::endl;
    }
}
