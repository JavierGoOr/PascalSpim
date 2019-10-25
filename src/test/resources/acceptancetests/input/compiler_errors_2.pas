program fact;
var
	a1: integer;
function f1():integer;
begin
	f1 := 2;
end;
function factorial(num:integer):integer;
var
	result : integer;
begin
	if num = 1 then
	begin
		factorial := 1;
	end;
	else
		result := factorial(num - 1) * num;
	f1 := result;
end;
begin
	writeintln(factorial(5));
	a1 := 2;
	case a1 of
		'a' :
			begin
				a1 := 2;
			end;
		'a' :
			a1 := 2 / 2;
	end;
	factorial := 3;
	case 3.0 of
		5.5:
			a1 := 3;
		6.6:
			a1 := 3;
	end;
end.