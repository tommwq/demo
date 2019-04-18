
CXXFLAGS=/EHsc

Main.exe: Main.cc
	$(CXX) $(CXXFLAGS) /Fe$@ $?

clean:
	del *.obj *.exe

run: Main.exe
	.\Main.exe
