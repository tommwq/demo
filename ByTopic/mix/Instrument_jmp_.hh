#pragma once

#include "Instrument.hh"

namespace mix {
    class Jump_helper {
    public:
        static void jump(Machine& machine, const Word& address);
        static Word align_address(const Word& address);
    };

    class Machine_condition {
    public:
        virtual bool is_satisfy(const Machine& machine) const = 0;
    };

    class Machine_condition_true: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_overflow: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_not_overflow: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_less: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_greater: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_equal: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_greater_or_equal: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_not_equal: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_less_or_equal: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_ra_negative: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_ra_not_negative: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_ra_zero: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_ra_not_zero: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_ra_positive: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_ra_not_positive: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_rx_negative: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_rx_not_negative: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_rx_zero: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_rx_not_zero: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_rx_positive: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    class Machine_condition_rx_not_positive: public Machine_condition {
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    template<int Index>
    class Machine_condition_r__negative: public Machine_condition {
        static_assert(1 <= Index && Index <= 6, "address register index must be in [1, 6]");
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    template<int Index>
    class Machine_condition_r__not_negative: public Machine_condition {
        static_assert(1 <= Index && Index <= 6, "address register index must be in [1, 6]");
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    template<int Index>
    class Machine_condition_r__zero: public Machine_condition {
        static_assert(1 <= Index && Index <= 6, "address register index must be in [1, 6]");
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    template<int Index>
    class Machine_condition_r__not_zero: public Machine_condition {
        static_assert(1 <= Index && Index <= 6, "address register index must be in [1, 6]");
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    template<int Index>
    class Machine_condition_r__positive: public Machine_condition {
        static_assert(1 <= Index && Index <= 6, "address register index must be in [1, 6]");
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    template<int Index>
    class Machine_condition_r__not_positive: public Machine_condition {
        static_assert(1 <= Index && Index <= 6, "address register index must be in [1, 6]");
    public:
        bool is_satisfy(const Machine& machine) const override;
    };

    template<typename Condition>
    class Instrument_jump_: public Instrument {
    public:
        Instrument_jump_(const Word& word): Instrument(word){}
        Instrument_jump_(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    private:
        Condition condition;
    };

    class Instrument_jsj: public Instrument {
    public:
        Instrument_jsj(const Word& word): Instrument(word){}
        Instrument_jsj(std::initializer_list<int> initializers): Instrument(initializers){}
        void execute(Machine& machine) const override;
    };
}

#include "Instrument_jmp_.ih"
