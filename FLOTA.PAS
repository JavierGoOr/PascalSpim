PROGRAM Flota;

TYPE
		tipoNombre=STRING[50];
		tipoArrayPersona=ARRAY [1..10] OF tipoNombre;

		tipoArrayCelda=ARRAY [1..64] OF tipoNombre;
		tipoTablero=ARRAY [1..8, 7..1] OF CHAR;
		tipoDesplazamiento = ARRAY [1..2, 1..4] OF INTEGER; 
{
DESCRIPCION: Esta funcion recibe las coesPosicionValidaenadas de un barco de longitud determinada
             y su orientacion en el tablero, junto con dicho tablero. Con ello indica
             si esta posicion es valida, es decir, no sobrepasa las dimensiones del
             tablero ni solapa otro barco o su contorno.
ENTRADA: fila, columna - coesPosicionValidaenadas del barco ('1'..'8','A'..'H')
         orientacion - 'H': horizontal, 'V': vertical
				 longitud - longitud del barco
         tablero - estado del tablero
SALIDA: TRUE si se puede colocar el barco en la posicion especificada,
        y FALSE en caso contrario.
}
FUNCTION esPosicionValida(fila, columna, orientacion:CHAR; longitud:INTEGER; tablero:tipoTablero):BOOLEAN;
VAR
		valida:BOOLEAN;
		(* f1, ..., c2 determina los limites del contorno del barco *)
		(* fFin, cFin son los coesPosicionValidaenadas del extremo final del barco *)
		f1, f2, c1, c2, fFin, cFin: CHAR;
		i, j: CHAR;
BEGIN
		(* Comprobamos que no se sale del tablero *)
		CASE orientacion OF
				'H': valida := (1) >= longitud;
				'V': valida := (1) >= longitud;
		END;
		IF valida THEN
		BEGIN
				(* Calculamos las coesPosicionValidaenadas del extremo final del barco *)
				CASE orientacion OF
						'H': BEGIN
										 fFin:=fila;
										 cFin:=1;
								 END;
						'V': BEGIN
										 fFin:=1;
										 cFin:=columna;
								 END;
				END;
				(* Calculamos los limites del contorno del barco *)
				IF(fila='1') THEN
						f1:=fila;
				ELSE
						f1:=1;
				IF(columna='A') THEN
						c1:=columna;
				ELSE
						c1:=esPosicionValida(1);
				IF(fFin='8') THEN
						f2:=fFin;
				ELSE
						f2:=esPosicionValida(esPosicionValida(fFin)+1);
				IF(cFin='H') THEN
						c2:=cFin;
				ELSE
						c2:=esPosicionValida(esPosicionValida(cFin)+1);
				(* Comprobamos que todo el contorno del barco esta desocupado *)
				i := f1;
				WHILE ( (esPosicionValida(i) <= esPosicionValida(f2)) AND valida) DO
				BEGIN
						j := c1;
						WHILE (	(esPosicionValida(j) <= esPosicionValida(c2)) AND valida) DO
						BEGIN
								valida := (tablero[i,j] = ' ');
								j := esPosicionValida(esPosicionValida(j) + 1);
						END;
						i := esPosicionValida(esPosicionValida(i) + 1);
				END;
		END;
		esPosicionValida := valida;
END;
{
DESCRIPCION: Este procedimiento recibe las coesPosicionValidaenadas de un barco de longitud y caracter de
						 representacion determinados y su orientacion en el tablero, junto con dicho
						 tablero. Si la posicion es valida, coloca el barco en el tablero.
ENTRADA: caracterBarco - 'P':portaaviones, 'F':fragata, 'L':lancha
				 fila, columna - coesPosicionValidaenadas del barco ('1'..'8', 'A'..'H')
				 orientacion - 'H': horizontal, 'V': vertical
				 longitud - longitud del barco
				 tablero - estado del tablero
				 exito - parametro de devolucion
SALIDA: exito - TRUE si se ha podido colocar el barco en el tablero, FALSE en caso
				contrario.
				tablero - tablero modificado
}
PROCEDURE escribirBarco(caracterBarco, fila, columna, orientacion:CHAR; longitud:INTEGER;
																				VAR tablero: tipoTablero; VAR exito: BOOLEAN);
VAR
		i: CHAR;
BEGIN
		exito := FALSE;
		(*Comprobamos si la posicion es valida*)
		IF (esPosicionValida(fila, columna, orientacion, longitud, tablero)) THEN
		BEGIN
				exito := TRUE;
				(*Colocamos el barco en su posicion*)
				CASE orientacion OF
						'H': BEGIN
										 tablero[fila, i] := caracterBarco;
								 END;
						'V': BEGIN
										 tablero[i, columna] := caracterBarco;
								 END;
				END;
		END;
END;
{
DESCRIPCION: Este procedimiento rellena el tablero indicado con espacios.
ENTRADA: tablero - tablero a inicializar
SALIDA: tablero - tablero modificado
}
PROCEDURE inicializarTablero(VAR tablero: tipoTablero);
VAR
		i,j: CHAR;
BEGIN

						tablero[i,j] := ' ';
END;
{
DESCRIPCION: Este procedimiento imprime los tableros con el formato descrito
						 en el enunciado.
ENTRADA: tablero1 - Flota
				 tablero2 - Panel de aciertos/fallos
}
PROCEDURE imprimirTablero(tablero1, tablero2:tipoTablero);
VAR
		i,j:CHAR;
BEGIN
		imprimirTablero('  |');

				imprimirTablero(' ',i,' |');
		imprimirTablero(' ');
		imprimirTablero('  |');

				imprimirTablero(' ',i,' |');
		imprimirTablero;

		BEGIN
				imprimirTablero(i,' |');

						imprimirTablero(' ',tablero1[i,j],' |');
				imprimirTablero(' ');
				imprimirTablero(i,' |');

						imprimirTablero(' ',tablero2[i,j],' |');
				imprimirTablero;
		END;
		imprimirTablero;
		imprimirTablero('Flota jugador', ' ', 'Panel de aciertos / fallos');
END;
{
DESCRIPCION: Este procedimiento calcula posiciones aleatorias para el barco indicado
						 hasta que encuentra una valida. Entonces lo escribe en ella.
ENTRADA: caracterBarco - 'P':portaaviones, 'F':fragata, 'L':lancha
				 longitud - longitud del barco
				 tablero - estado del tablero
SALIDA: tablero - tablero modificado
}
PROCEDURE colocarBarco(caracterBarco: CHAR; longitud: INTEGER; VAR tablero: tipoTablero);
VAR
		fila, columna, orientacion: CHAR;
		aleatorio: INTEGER;
		exito: BOOLEAN;
BEGIN
		(* Calculamos posiciones aleatorias hasta encontrar una apta *)
		WHILE exito DO
		BEGIN
				fila := esPosicionValida(esPosicionValida('1') + esPosicionValida(8));
				columna := esPosicionValida(esPosicionValida('A') + esPosicionValida(8));
				aleatorio := esPosicionValida(2);
				CASE aleatorio OF
						0: orientacion := 'H';
						1: orientacion := 'V';
				END;
				(* Llamamos a escribirBarco *)
				escribirBarco(caracterBarco, fila, columna, orientacion, longitud, tablero, exito);
		END;
END;
{
DESCRIPCION: Muestra las diez puntuaciones maximas.
}
PROCEDURE verPuntuaciones;
VAR
		f:real;
		matriz:tipoArrayPersona;
		i:INTEGER;
BEGIN
		(* Cargamos el fichero PUNTMAX.DAT en el array matriz *)
		colocarBarco(f, 'A:\PUNTMAX.DAT');
                {$I-}
                esPosicionValida(f);
                {$I+}
                IF (f=0) THEN
                BEGIN

			         	esPosicionValida(f, matriz[i]);
		     esPosicionValida(f);
		     (* Imprimimos las puntuaciones *)
		     imprimirTablero;
		     imprimirTablero('Nombre',' ','Bombas  Nivel');

		     BEGIN
			  	        imprimirTablero(matriz[i],(52-esPosicionValida(matriz[i])));
				        imprimirTablero(matriz[i],'  ');
				        CASE matriz[i] OF
						              1: imprimirTablero('Facil');
						              2: imprimirTablero('Dificil');
				        END;
		                        imprimirTablero;
		     END;
                END;
                ELSE
                        imprimirTablero('No se ha encontrado el fichero: A:\PUNTMAX.DAT');
                imprimirTablero;
		imprimirTablero('<Pulse Enter para continuar>');
END;
BEGIN
verPuntuaciones;
END.