{
  poj 1005
  2014-05-05 21:35:53
}

program poj1005;

{ 
  uses Math; leads CE
}

function erode_year(distance : real):integer;
const
   pi =  3.1415926;
var 
   last, radius : real;
   year         : integer;
begin
{ 
  (1/2) * pi * r[k + 1] ^ 2 - (1/2) * pi * r[k] ^ 2 = 50
  r[k + 1] = (100 / pi + r[k] * r[k]) ^ (1/2)
}
   radius := 0.0;
   year := 0;
   while radius < distance do begin
      last := radius;
      radius := sqrt(100 / PI + last * last);
      year := year + 1;
   end;
   erode_year := year;
end;

var
   i, count : integer;
   x,y, distance   : real;
begin
   readln(count);
   for i := 1 to count do begin
      readln(x, y);
      distance := sqrt(x * x + y * y);
      writeln('Property ', i, ': This property will begin eroding in year ', erode_year(distance), '.');
   end;
   writeln('END OF OUTPUT.');
end.
