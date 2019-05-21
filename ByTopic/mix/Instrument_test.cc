#include "Instrument_lda.hh"
#include "Machine.hh"

#include "unittest.hh"


int main() {
    
    Suit("LDA");

    mix::Machine machine;
    mix::Word value = {0, 1, 16, 3, 5, 4};
    std::uint8_t address = 2000;
    machine.write_memory(address, value);
    
    mix::Instrument_lda i1 = {1, 31, 16, 0, 5, 8};
    i1.execute(machine);

    mix::Word ra = machine.get_ra();
    mix::Word expected = {1, 31, 16, 0, 5, 8};
    ExpectTrue(ra == expected);
}
