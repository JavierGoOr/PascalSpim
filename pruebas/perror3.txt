program fact;
type
	t0 : real;
	t1 = integer;
	t2 = array[0..4, 0..4] of array[1..1] of char;
var
	a1: integer;
	{This is a comment}
	(* Another comment *)
	a2: t2;
procedure p1;
begin
end;
function f1: a1;
begin
end;
function factorial(num:integer):integer;
var
	result : integer;
begin
	if num = 1 then
		result := 1;
	else
		result := factorial(num - 1) * num;
	factorial := result;
end;
begin
	a1 := p1;
	a1 := t1;
	a1 := p1(1, 2, 3, true);
	a1(3, 4, 5);
	p1[1,2,3] := 'd';
	a1[1,2,3] := 'd';
	factorial(6);
	a2[1][1,1][2] := '4';
	a2[1,1][1,1][2] := '4';
	a2[1,1][1][2] := '4';
end.