#include "Instrument_st_.hh"
#include "Machine.hh"

#include "unittest.hh"


int main() {
    
    Suit("STA");

    mix::Machine machine;
    mix::Word value = {0, 1, 2, 3, 4, 5};
    std::uint32_t address = 2000;
    machine.write_memory(address, value);
    mix::Word ra_value = {1, 6, 7, 8, 9, 0};
    machine.get_ra() = ra_value;
        
    struct {
        mix::Instrument_sta instrument;
        mix::Word expected;
    } test_cases[] = {{{1, 31, 16, 0,  5, 31}, {1, 6, 7, 8, 9, 0}},
                      {{1, 31, 16, 0, 13, 31}, {0, 6, 7, 8, 9, 0}},
                      {{1, 31, 16, 0, 45, 31}, {0, 1, 2, 3, 4, 0}},
                      {{1, 31, 16, 0, 18, 31}, {0, 1, 0, 3, 4, 5}},
                      {{1, 31, 16, 0, 19, 31}, {0, 1, 9, 0, 4, 5}},
                      {{1, 31, 16, 0,  1, 31}, {1, 0, 2, 3, 4, 5}},
    };
    
    for (auto& test: test_cases) {
        machine.write_memory(address, value);
        test.instrument.execute(machine);
        const mix::Word& memory = machine.read_memory(address);
        
        if (!ExpectTrue(memory == test.expected)) {
            std::cout << memory << " " << test.expected << std::endl;
        }
    }
}
