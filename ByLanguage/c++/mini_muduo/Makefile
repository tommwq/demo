demo.bin: main.o Channel.o Poller.o Event_loop.o Timestamp.o Timer_id.o Timer_queue.o Timer.o
	$(CXX) -o $@ $^

*.o: *.cc
	$(CXX) -c $@ $?

test: demo.bin
	./demo.bin

