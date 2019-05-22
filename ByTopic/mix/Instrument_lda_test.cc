#include "Instrument_lda.hh"
#include "Machine.hh"

#include "unittest.hh"


int main() {
    
    Suit("LDA");

    mix::Machine machine;
    mix::Word value = {0, 1, 16, 3, 5, 4};
    std::uint32_t address = 2000;
    machine.write_memory(address, value);

    struct {
        mix::Instrument_lda instrument;
        mix::Word expected;
    } test_cases[] = {{{1, 31, 16, 0,  5, 8}, {0, 1, 16, 3,  5, 4}},
                      {{1, 31, 16, 0, 13, 8}, {1, 1, 16, 3,  5, 4}},
                      {{1, 31, 16, 0, 29, 8}, {1, 0,  0, 3,  5, 4}},
                      {{1, 31, 16, 0,  3, 8}, {0, 0,  0, 1, 16, 3}},
                      {{1, 31, 16, 0, 36, 8}, {1, 0,  0, 0,  0, 5}},
                      {{1, 31, 16, 0,  0, 8}, {0, 0,  0, 0,  0, 0}},
                      {{1, 31, 16, 0,  9, 8}, {1, 0,  0, 0,  0, 1}},
    };

    for (auto& test: test_cases) {
        test.instrument.execute(machine);
        const mix::Word& ra = machine.get_ra();
        if (!ExpectTrue(ra == test.expected)) {
            std::cout << ra << " " << test.expected << std::endl;
        }
    }
}
