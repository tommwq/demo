#include "Instrument_sub.hh"
#include "Machine.hh"

#include "unittest.hh"


int main() {
    
    Suit("SUB");

    mix::Machine machine;
    mix::Word value = {0, 31, 16, 2, 22, 0};
    std::uint32_t address = 1000;
    machine.write_memory(address, value);
    mix::Word ra_value = {0, 19, 18, 0, 0, 9};
    machine.get_ra() = ra_value;
        
    struct {
        mix::Instrument_sub instrument;
        mix::Word expected;
    } test_cases[] = {{{1, 15, 40, 0, 5, 2}, {1, 11, 62, 2, 21, 55}},
    };
    
    for (auto& test: test_cases) {
        test.instrument.execute(machine);
        const mix::Word& ra = machine.get_ra();
        
        if (!ExpectTrue(ra == test.expected)) {
            std::cout << ra << " " << test.expected << std::endl;
        }
    }
}
