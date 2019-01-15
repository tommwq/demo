
tracetest.exe: Main.cpp AddOne.cpp Trace.cpp Trace2.cpp Trace3.cpp
	$(CXX) /EHsc /std:c++17 /Fe:$@ $**

run: tracetest.exe
	tracetest.exe 2>nul

clean:
	$(RM) *.obj *.exe
