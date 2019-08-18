program fib;
function fibonacci(num:integer):integer;
var
	result : integer;
begin
	if ((num = 0) or (num = 1)) and (not (num = 2)) then
	begin
		result := 1;
	end;
	else
		result := fibonacci(num - 1) + fibonacci(num - 2);
	fibonacci := result;
end;
begin
	writeintln(fibonacci(0));
	writeintln(fibonacci(1));
	writeintln(fibonacci(2));
	writeintln(fibonacci(3));
	writeintln(fibonacci(4));
	writeintln(fibonacci(5));
	writeintln(fibonacci(6));
	writeintln(fibonacci(7));
	writeintln(fibonacci(8));
end.