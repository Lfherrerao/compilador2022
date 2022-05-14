package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem

class DeclaracionArreglo(var tipoDato: Token, var identificador: Token): Sentencia() {

    override fun toString(): String {
        return "DeclaracionArreglo()"
    }

    override fun getArbolVisual(): TreeItem<String>? {
        var raiz = TreeItem("DECLARACION DE ARREGLO")
            raiz.children.add(TreeItem("Tipo de dato: ${tipoDato.lexema}"))
        raiz.children.add(TreeItem("nombre arreglo : ${identificador.lexema}"))

        return raiz

    }
}