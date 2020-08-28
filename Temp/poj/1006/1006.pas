{
  poj 1006
  2014-05-06 18:04:03
  WA
}

program poj1006;


{ after how many days, next peak will come? }
function next_peak(p, e, i, peak : integer):integer;
const
   p_period = 23;
   e_period = 28;
   i_period = 33;
   x        =  p_period * e_period * i_period;
var day : integer;
begin
   p := p mod p_period;
   e := e mod e_period;
   i := i mod i_period;
   day := peak + i_period - (peak mod i_period);
   while true do begin
      day := day + 5;
      if day < 0 then begin
         day := day + 21252;
      end;
      if ((peak + day - p) mod p_period) <> 0 then begin
         continue;
      end;
      if ((peak + day - e) mod e_period) <> 0 then begin
         continue;
      end;
      if ((peak + day - i) mod i_period) <> 0 then begin
         continue;
      end;
      next_peak := day;
      break;
   end;
end;

var
   p, e, i, peak : integer;
   count         : integer;
begin
   count := 1;
   readln(p, e, i, peak);
   while p <> -1 do begin
      writeln('Case ', count, ': the next triple peak occurs in ', next_peak(p, e, i, peak), ' days.');
      readln(p, e, i, peak);
      count := count + 1;
   end;
end.
