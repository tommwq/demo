
#include <ctime>
#include <cstdint>
#include <iostream>
#include <vector>
#include <map>

#include <process.h>
#include <windows.h>

class Event {
public:
  Event(){}
  explicit Event(uint32_t eventNumber)
    :_eventNumber(eventNumber){}
  virtual ~Event(){}
  uint32_t eventNumber() const {
    return _eventNumber;
  }
  Event& eventNumber(int eventNumber){
    _eventNumber = eventNumber;
    return *this;
  }
private:
  uint32_t _eventNumber;  // 0 means all events
};

class EventHandler {
public:
  EventHandler(){}
  virtual ~EventHandler(){}
  virtual void handle(const Event &event) = 0;
};

class EventManager {
public:
  void emit(Event &event){
    _events.push_back(event);
  }
  void addHandler(int eventNumber, EventHandler *handler){
    _handler[eventNumber].push_back(handler);
  }
  void removeHandler(int eventNumber, EventHandler *handler){
    // todo
    //  _handler[eventNumber].push_back(&handler);
  }
  void disable(int eventNumber){
    _disableEvents.push_back(eventNumber);
  }
  void enable(int eventNumber){
    // todo
  }
  void loop(){
    while (true){
      if (_events.empty()){
        continue;
      }

      Event &event(_events[0]);
      HANDLER_VECTOR &v = _handler[0];
      for (HANDLER_VECTOR::iterator it = v.begin(); it != v.end(); ++it){
        (*it)->handle(event);
      }
      {
        HANDLER_VECTOR &v = _handler[event.eventNumber()];
        for (HANDLER_VECTOR::iterator it = v.begin(); it != v.end(); ++it){
          (*it)->handle(event);
        }
      }
      _events.erase(_events.begin());
    }
  }
private:
  typedef std::vector<EventHandler *> HANDLER_VECTOR;
  std::vector<Event> _events;
  std::map<int, HANDLER_VECTOR> _handler;
  std::vector<int> _disableEvents;
};

void eventMaker(void *_manager){
  EventManager *manager((EventManager *)_manager);
  while (true){
    Event event(rand() + 1);
    manager->emit(event);
    Sleep(rand() % 1000);
  }
}

class EventPrinter: public EventHandler {
public:
  void handle(const Event &event){
    std::cout << "handle event " << event.eventNumber() << std::endl;
  }
};

int main(int argc, char **argv){
  srand(time(0));
  EventManager manager;
  EventPrinter printer;
  manager.addHandler(0, &printer);

  _beginthread(eventMaker, 0, (void *) &manager);

  manager.loop();
  
  return 0;
}
