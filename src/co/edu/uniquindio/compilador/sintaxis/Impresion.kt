package co.edu.uniquindio.compilador.sintaxis

import javafx.scene.control.TreeItem

class Impresion(var expresion: Expresion) : Sentencia() {

    override fun toString(): String {
        return "Impresion(expresion=$expresion)"
    }

    override fun getArbolVisual(): TreeItem<String>? {

        var raiz = TreeItem("IMPRESION")
        raiz.children.add(expresion.getArbolVisual())
        return raiz
    }
}