package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionCadena(var cadena : Token): Expresion() {


    override fun toString(): String {
        return "ExpresionCadena(cadena=$cadena)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("EXPRESION CADENA")
        raiz.children.add(TreeItem("Expresion:  ${cadena.lexema}"))
        return raiz
    }
}