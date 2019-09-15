program switchCaseInt;
const
	c3 = 'j';
var
	v1, v2, v3, v4, v5, v6, v7, v8, v9, v10: integer;
	vchar : char;
begin
	vchar := c3;
	v1 := 1;
	v2 := 1;
	v3 := 1;
	v4 := 1;
	v5 := 1;
	v6 := 1;
	v7 := 1;
	v8 := 1;
	v9 := 1;
	v10 := 1;
	v1 := v2 + v3 * v4 div v5 + v6;
	case vchar of
	'a':
		begin
			v2 := v7 + v8 + v9 + v10;
			writechar('a');
		end;
	'#':
		writechar('#');
	else
		begin
			v10:= v10 + v2 + v3;
			writechar('?');
		end;
	end;
	writeintln(v2);
	writeintln(v10);
end.