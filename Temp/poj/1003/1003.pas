{ 
  solution for poj 1003. 
  2014-05-05 18:23:05
}

program poj1003;

function cards(distance : real):integer;
var
   k   : integer;
   sum : real;
begin
   { todo }
   sum := 0.0;
   k := 1;
   while sum < distance do begin
      sum := sum + 1 / (k + 1);
      if distance < sum then begin
         cards := k;
         break;
      end;
      k := k + 1;
   end;
end;

var 
   distance :  real;
begin
   repeat
      readln(distance);
      if distance = 0.0 then begin
         break;
      end;
      writeln(cards(distance), ' card(s)');
      until false;
end.
