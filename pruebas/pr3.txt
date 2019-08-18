program variables;
const
	c1 = -3;
	c2 = -3.2;
	c3 = '#';
	c4 = true;
var
	i, j: integer;
	a1: array[0..8, 1..2] of real;
{
proc1 stores -3.14 in a1[3,2]
}
procedure proc1(a1: array[0..8, 1..2] of real);
begin
	a1[3, 2] := -3.14;
end;
{
proc2 initiates all a1 values to -1
i, j local variables are not confused with global ones. 
}
procedure proc2(a1: array[0..8, 1..2] of real);
var
	i, j: integer;
begin
	i := 0;
	while i < 9 do
	begin
		j := 1;
		while (j < 3) do
		begin
			a1[i, j] := -1;
			j := j + 1;
		end;
		i := i + 1;
	end; 
end;
begin
	proc2(a1);
	writerealln(a1[3,2]); {this should print -1}
	proc1(a1);
	writerealln(a1[3,2]); {this should print -3.14}
end.