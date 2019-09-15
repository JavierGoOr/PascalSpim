program types;
type
	btype = boolean;
	arraytype = array[0..2, -2..0] of array[-2..3] of real;
var
	vglobal : btype;
	aglobal : arraytype;
	vreal : real;
{
p1 writes @ in the screen
}
procedure p1;
begin
	writecharln('@');
end;
{
f1 initializes aglobal matrix
parameters are not used, they are only there to check that
  parameters of different types are correctly sent.
}
function f1(a1, a2: integer; a3, a4: real):real;
var
	i, j, k : integer;
begin
	vglobal := false; {sets vglobal to false}
	p1;               {starts p1}
	if true then
	begin
		i := 0;
		while i < 3 do
		begin
			j := -2;
			while j < 1 do
			begin
				k := -2;
				while k < 4 do
				begin
					if (i <> 1) and (j <> -1) and (k <> 2) then {complex condition}
						aglobal[i, j][k] := -1; {aglobal[1, -1][2] is not set to -1}
					k := k + 1;
				end;
				j := j + 1;
			end;
			i := i + 1;
		end;
	end;
	f1 := aglobal[1, -1][2]; {it returns its previous value}
end;
begin
	{global variables are initialized}
	vglobal := true; 
	vreal := 4;
	aglobal[1, -1][2] := 3.14;
	vreal := f1(1-1, 2+2, vreal, -vreal + 1 + 2); {it returns 3.14}
	writerealln(aglobal[0,0][0]); {this should print -1, becuase f1 has initialized the matrix to that value}
	writerealln(aglobal[1,-1][2]); {this prints 3.14}
	writerealln(vreal); {it prints 3.14}
	writeboolean(vglobal); {it prints false, f1 has changed it}
end.