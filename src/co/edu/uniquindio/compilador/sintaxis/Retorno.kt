package co.edu.uniquindio.compilador.sintaxis

import javafx.scene.control.TreeItem

class Retorno(var expresion: Expresion): Sentencia() {
    override fun toString(): String {
        return "Retorno(expresion=$expresion)"
    }

    override fun getArbolVisual(): TreeItem<String>? {
        var raiz = TreeItem("RETORNO")
        raiz.children.add(expresion.getArbolVisual())
        return raiz
    }
}