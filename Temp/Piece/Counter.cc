
class Counter {
public:
  Counter()
    :_value(0){}
  virtual ~Counter(){}
  void inc(){ ++_value; }
  int value() const { return _value; }
private:
  int _value;
};
