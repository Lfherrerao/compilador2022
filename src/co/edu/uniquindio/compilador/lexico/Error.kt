package co.edu.uniquindio.compilador.lexico

class Error(var mensaje:String, var fila:Int,var columna:Int) {
    override fun toString(): String {
        return "Error(mensaje='$mensaje', fila=$fila, columna=$columna)"
    }
}