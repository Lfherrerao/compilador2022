package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem

class InicializarArreglo ( var identificador: Token,  var operador: Token, var tipD: Token, var filas: ValorNumerico): Sentencia() {

    override fun toString(): String {
        return "InicializarArreglo(identificador=$identificador, operador=$operador, tipD=$tipD, filas=$filas)"
    }

    override fun getArbolVisual(): TreeItem<String>? {

        var raiz = TreeItem(("INICIALIZAR ARREGLO"))

        raiz.children.add(TreeItem("nombre arreglo : ${identificador.lexema}"))
        raiz.children.add(TreeItem("operador Asignacion : ${operador.lexema}"))
        raiz.children.add(TreeItem("tipo dato: ${tipD.lexema}"))
        raiz.children.add(  filas.getArbolVisual())
        return raiz
    }
}