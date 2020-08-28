#include "Instrument_mul.hh"
#include "Machine.hh"

#include "unittest.hh"


int main() {
    
    Suit("MUL");

    mix::Machine machine;
    mix::Word value = {0, 2, 0, 0, 0, 0};
    std::uint32_t address = 1000;
    machine.write_memory(address, value);
        
    struct {
        mix::Word origin_ra;
        mix::Instrument_mul instrument;
        mix::Word expect_ra;
        mix::Word expect_rx;
    } test_cases[] = {
                      {{0,  0,  0, 0, 1, 48},
                       {1, 15, 40, 0, 9,  3},
                       {0,  0,  0, 0, 0,  0},
                       {0,  0,  0, 0, 3, 32}},
                      {{0, 50,  0, 1, 48,  4},
                       {1, 15, 40, 0,  5,  3},
                       {1,  1, 36, 0,  3, 32},
                       {1,  8,  0, 0,  0,  0}},
    };
    
    for (auto& test: test_cases) {
        machine.get_ra() = test.origin_ra;
        test.instrument.execute(machine);
        const mix::Word& ra = machine.get_ra();
        const mix::Word& rx = machine.get_rx();
        
        if (!ExpectTrue(ra == test.expect_ra)) {
            std::cout << ra << " " << test.expect_ra << std::endl;
        }

        if (!ExpectTrue(rx == test.expect_rx)) {
            std::cout << rx << " " << test.expect_rx << std::endl;
        }
    }
}
