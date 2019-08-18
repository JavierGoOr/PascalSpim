program variables;
const
	c1 = -3;
	c2 = -3.2;
	c3 = '#';
	c4 = true;
var
	i, j: integer;
	vchar : char;
begin
	writereal(-c1 + c2 - (c1 / c1) + (c1 div c1) * (-c1) mod 4);
	writebooleanln(-c1 + c2 - (c1 / c1) + (c1 div c1) * (-c1) mod 4 >= c2 / c1 = true);
	i := 0;
	while -c1 + c2 - (c1 / c1) + (c1 div c1) * (-c1) mod 4 - i > 0 do
	begin
		i := i + 1;
	end;
	writeintln(i);
	vchar := c3;
	case vchar of
	'a':
		begin
			writechar('a');
		end;
	'#':
		writechar('#');
	else
		begin
			writechar('?');
		end;
	end;
end.