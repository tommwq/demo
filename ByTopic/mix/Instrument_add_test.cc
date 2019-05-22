#include "Instrument_add.hh"
#include "Machine.hh"

#include "unittest.hh"


int main() {
    
    Suit("ADD");

    mix::Machine machine;
    mix::Word value = {1, 1, 36, 5, 0, 50};
    std::uint32_t address = 1000;
    machine.write_memory(address, value);
    mix::Word ra_value = {1, 19, 18, 1, 2, 22};
    machine.get_ra() = ra_value;
        
    struct {
        mix::Instrument_add instrument;
        mix::Word expected;
    } test_cases[] = {{{1, 15, 40, 0, 5, 1}, {1, 20, 54, 6, 3, 8}},
    };
    
    for (auto& test: test_cases) {
        test.instrument.execute(machine);
        const mix::Word& ra = machine.get_ra();
        
        if (!ExpectTrue(ra == test.expected)) {
            std::cout << ra << " " << test.expected << std::endl;
        }
    }
}
