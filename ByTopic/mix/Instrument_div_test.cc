#include "Instrument_div.hh"
#include "Machine.hh"

#include "unittest.hh"


int main() {
    
    Suit("DIV");

    mix::Machine machine;
    std::uint32_t address = 1000;
        
    struct {
        mix::Word memory;
        mix::Word origin_ra;
        mix::Word origin_rx;
        mix::Instrument_div instrument;
        mix::Word expect_ra;
        mix::Word expect_rx;
    } test_cases[] = {
                      {{1,  0,  0, 0, 0,  3},
                       {1,  0,  0, 0, 0,  0},
                       {1,  0,  0, 0, 0, 17},
                       {1, 15, 40, 0, 5,  4},
                       {1,  0,  0, 0, 0,  5},
                       {1,  0,  0, 0, 0,  2}},
    };
    
    for (auto& test: test_cases) {
        machine.write_memory(address, test.memory);
        machine.get_ra() = test.origin_ra;
        machine.get_rx() = test.origin_rx;
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
