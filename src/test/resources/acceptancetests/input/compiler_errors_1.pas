program errors;
const
	c1 = -3;
	c2 = -3.2;
	c2 = '#';
	c3 = anotherconst;
	c4 = true;
	c5 = 23453245672345623456734567;
type
	t1 = integer;
var
	i, j, j: integer;
	a1: array[0..8, 1..2] of real;
	a2: array[0..8, 1..2] of array[1..-2] of real;
	a3: array[0..8, 1..2] of array[1..2] of real;
	a4: array[1..2] of real;
procedure proc1(a1: array[0..8, 1..2] of real);
begin
	a1[3, 2] := -3.14;
	proc1 := 3;
end;
procedure proc2(a1: array[0..8, 1..2] of real);
var
	i, j: integer;
begin
	i := 3.5;
	while 9 do
	begin
		j := 1 + 5.3;
		while (j < 3) do
		begin
			a1[i, j] := -1;
			j := j + 1;
		end;
		i := i + 1;
	end; 
end;
begin
	a1 := 4;
	a3[0, 0] := a4;
	proc2;
	writerealln(true);
	proc1(a1);
	writerealln(a1[3,2]);
end.