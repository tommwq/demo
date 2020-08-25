#include "Instrument_sla.hh"
#include "Machine.hh"

#include "unittest.hh"


int main() {
    
    Suit("SRA");

    mix::Machine machine;
    machine.get_ra() = {1, 6, 7, 8, 9, 2};
    mix::Word expect = {1, 0, 0, 6, 7, 8};
        
    mix::Instrument_sla sla{1, 0, 2, 0, 1, 6};
    sla.execute(machine);
    const mix::Word& ra = machine.get_ra();
    if (!ExpectTrue(ra == expect)) {
        std::cout << ra << " " << expect << std::endl;
    }
}
