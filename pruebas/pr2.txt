program fact;
var
	global:integer;
function factorial(num:integer):integer;
var
	result : integer;
begin
	if num = 1 then
		result := 1;
	else
		result := factorial(num - 1) * num;
	global := global + 1;
	factorial := result;
end;
begin
	global := 1;
	writeintln(factorial(5));
	writeintln(global);
end.