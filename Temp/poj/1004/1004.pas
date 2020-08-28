{
  poj 1004 
  2014-05-05 20:07:51
}

program poj1004;

const
   month =  12;
var
   balance, sum, mean: real;
   i, frac: integer;
   padding: string;
begin
   sum := 0;
   for i := 1 to month do begin
      readln(balance);
      sum := sum + balance;
   end;
   mean := sum / 12;
   i := trunc(mean);
   mean := (mean - trunc(mean)) * 100;
   frac := round(mean);
   if frac < 10 then begin
      padding := '0';
   end;
   writeln('$', i, '.', padding, frac);
end.
