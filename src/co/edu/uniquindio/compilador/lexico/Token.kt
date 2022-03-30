package co.edu.uniquindio.compilador.lexico

/* *
* @autor leonardo Fabio Herrera o.
* @autor John Alexander Martínez N.
* @autor Diego .....
*
* Clase que representa un Token.
 */

class Token (var lexema:String, var categoria: Categoria, var fila:Int, var columna:Int) {
    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna)"
    }
}