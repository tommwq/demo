#pragma once

#include <iostream>
#include <string>

#define Expect(expected, actural) unittest::expect((expected),  \
                                                   (actural),   \
                                                   __FILE__,    \
                                                   __LINE__,    \
                                                   #expected,   \
                                                   #actural)

#define ExpectTrue(value) unittest::expect_true((value),    \
                                                __FILE__,   \
                                                __LINE__,   \
                                                #value)
#define ExpectFalse(value) unittest::expect_false((value),  \
                                                  __FILE__, \
                                                  __LINE__, \
                                                  #value)

#define Suit(title) unittest::suit(title)

namespace unittest {
    void expect(long expected,
                long actural,
                char *file,
                int line,
                char *expected_expr,
                char *actural_expr);

    bool expect_true(bool value,
                     char *file,
                     int line,
                     char *expr);

    bool expect_false(bool value,
                      char *file,
                      int line,
                      char *expr);

    void suit(const std::string& name);
}
