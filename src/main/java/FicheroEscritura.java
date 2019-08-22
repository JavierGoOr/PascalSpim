import java.io.*;
/**
 *Esta clase controla la escritura de ficheros.
 *@author Daniel Fernández Caballero
 *@author Francisco Javier González Ortega
 *@version 7-1-2004
*/
public class FicheroEscritura{
    /**
     *Flujo de escritura hacia el fichero.
    */
    private PrintWriter _fichero;
    /**
     *Dirección del fichero.
    */
    private String _nombreFichero;
    /**
     *Constructor de la clase.
     *@param nombreFichero Dirección del fichero.
    */
    public FicheroEscritura(String nombreFichero){
        _nombreFichero = nombreFichero;
    }
    /**
     *Abre el flujo de escritura hacia el fichero.
     *@return True si se ha conseguido abrir el flujo de escritura, false en caso contrario. 
    */
    public boolean abrir(){
        boolean realizado = true;
        try{
            _fichero = new PrintWriter(new BufferedWriter(new FileWriter(_nombreFichero)));
        }catch(IOException e){
            //System.out.println("Error en la escritura de ficheros: " + _nombreFichero);
            realizado = false;
        }
        return realizado;
    }
    /**
     *Escribe un carácter en el fichero.
     *@param c Carácter a escribir.
     *@return True si ha conseguido escribir en el fichero, false en caso contrario.
    */
    public boolean escribir(char c){
        boolean realizado = true;
        try{
            _fichero.print(c);
        }catch(Exception e){
            //System.out.println("Error en la escritura de ficheros: " + _nombreFichero);
            realizado = false;
        }
        return realizado;
    }
    /**
     *Escribe un dato de tipo boolean en el fichero.
     *@param c Dato de tipo boolean a escribir.
     *@return True si ha conseguido escribir en el fichero, false en caso contrario.
    */
    public boolean escribir(boolean c){
        boolean realizado = true;
        try{
            _fichero.print(c);
        }catch(Exception e){
            //System.out.println("Error en la escritura de ficheros: " + _nombreFichero);
            realizado = false;
        }
        return realizado;
    }
    /**
     *Escribe un dato de tipo long en el fichero.
     *@param c Dato de tipo long a escribir.
     *@return True si ha conseguido escribir en el fichero, false en caso contrario.
    */
    public boolean escribir(long c){
        boolean realizado = true;
        try{
            _fichero.print(c);
        }catch(Exception e){
            //System.out.println("Error en la escritura de ficheros: " + _nombreFichero);
            realizado = false;
        }
        return realizado;
    }
    /**
     *Escribe un dato de tipo double en el fichero.
     *@param c Dato de tipo double a escribir.
     *@return True si ha conseguido escribir en el fichero, false en caso contrario.
    */
    public boolean escribir(double c){
        boolean realizado = true;
        try{
            _fichero.print(c);
        }catch(Exception e){
            //System.out.println("Error en la escritura de ficheros: " + _nombreFichero);
            realizado = false;
        }
        return realizado;
    }
    /**
     *Escribe una cadena de caracteres en el fichero.
     *@param cadena Cadena a escribir.
     *@return True si ha conseguido escribir en el fichero, false en caso contrario.
    */
    public boolean escribir(String cadena){
        boolean realizado = true;
        try{
            _fichero.print(cadena);
        }catch(Exception e){
            //System.out.println("Error en la escritura de ficheros: " + _nombreFichero);
            realizado = false;
        }
        return realizado;
    }
    /**
     *Escribe un carácter en el fichero, y salta a la línea siguiente.
     *@param c Carácter a escribir.
     *@return True si ha conseguido escribir en el fichero, false en caso contrario.
    */
    public boolean escribirLinea(char c){
        boolean realizado = true;
        try{
            _fichero.println(c);
        }catch(Exception e){
            //System.out.println("Error en la escritura de ficheros: " + _nombreFichero);
            realizado = false;
        }
        return realizado;
    }
    /**
     *Escribe un dato de tipo boolean en el fichero, y salta a la línea siguiente.
     *@param c Dato de tipo boolean a escribir.
     *@return True si ha conseguido escribir en el fichero, false en caso contrario.
    */
    public boolean escribirLinea(boolean c){
        boolean realizado = true;
        try{
            _fichero.println(c);
        }catch(Exception e){
            //System.out.println("Error en la escritura de ficheros: " + _nombreFichero);
            realizado = false;
        }
        return realizado;
    }
    /**
     *Escribe un dato de tipo long en el fichero, y salta a la línea siguiente.
     *@param c Dato de tipo long a escribir.
     *@return True si ha conseguido escribir en el fichero, false en caso contrario.
    */
    public boolean escribirLinea(long c){
        boolean realizado = true;
        try{
            _fichero.println(c);
        }catch(Exception e){
            //System.out.println("Error en la escritura de ficheros: " + _nombreFichero);
            realizado = false;
        }
        return realizado;
    }
    /**
     *Escribe un dato de tipo double en el fichero, y salta a la línea siguiente.
     *@param c Dato de tipo double a escribir.
     *@return True si ha conseguido escribir en el fichero, false en caso contrario.
    */
    public boolean escribirLinea(double c){
        boolean realizado = true;
        try{
            _fichero.println(c);
        }catch(Exception e){
            //System.out.println("Error en la escritura de ficheros: " + _nombreFichero);
            realizado = false;
        }
        return realizado;
    }
    /**
     *Escribe una cadena de caracteres en el fichero, y salta a la línea siguiente.
     *@param c Cadena a escribir.
     *@return True si ha conseguido escribir en el fichero, false en caso contrario.
    */
    public boolean escribirLinea(String cadena){
        boolean realizado = true;
        try{
            _fichero.println(cadena);
        }catch(Exception e){
            //System.out.println("Error en la escritura de ficheros: " + _nombreFichero);
            realizado = false;
        }
        return realizado;
    }
    /**
     *Introduce un salto de línea en el fichero.
     *@return True si ha conseguido escribir en el fichero, false en caso contrario.
    */
    public boolean nuevaLinea(){
        boolean realizado = true;
        try{
            _fichero.println();
        }catch(Exception e){
            //System.out.println("Error en la escritura de ficheros: " + _nombreFichero);
            realizado = false;
        }
        return realizado;
    }
    /**
     *Cierra el flujo de escritura.
     *@return True si se ha conseguido cerrar el flujo, false en caso contrario.
    */
    public boolean cerrar(){
        boolean realizado = true;
        try{
            _fichero.close();
        }catch(Exception e){
            //System.out.println("Error en la escritura de ficheros: " + _nombreFichero);
            realizado = false;
        }
        return realizado;
    }
}
