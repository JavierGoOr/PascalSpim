program test;
const
	c1 = 'hello world';
	c2 = c1;
var
	a1, a2, a2: integer;
	v1, v2, v3: string;
FUNCTION isValid(f, o:CHAR; length:INTEGER; tablero: string):BOOLEAN;
VAR
		valid:BOOLEAN;
		f1, cFin: CHAR;
BEGIN
	CASE o OF
		'H': valid := (1) >= length;
		'V': valid := (1) >= length;
	END;
	isValid := valid;
END;
BEGIN
	a1 := 3 + 6 - c3;
	WHILE (a1 > 0) do
	BEGIN
		a1 := a1 - 1;
	END;
END.