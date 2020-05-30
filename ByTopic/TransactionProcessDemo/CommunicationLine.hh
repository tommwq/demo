class Communication_line {
public:
    Communication_line(End end1, End end2);
    void send_to_end1(Signal signal);
    void send_to_end2(Signal signal);
};
