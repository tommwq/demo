#include "Instrument_sla.hh"
#include "Machine.hh"

#include "unittest.hh"


int main() {
    
    Suit("SLA");

    mix::Machine machine;
    machine.get_ra() = {1, 0, 1, 2, 3, 4};
    mix::Word expect = {1, 2, 3, 4, 0, 0};
        
    mix::Instrument_sla sla{1, 0, 2, 0, 0, 6};
    sla.execute(machine);
    const mix::Word& ra = machine.get_ra();
    if (!ExpectTrue(ra == expect)) {
        std::cout << ra << " " << expect << std::endl;
    }
}
