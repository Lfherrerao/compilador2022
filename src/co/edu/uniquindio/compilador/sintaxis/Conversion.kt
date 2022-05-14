package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem

class Conversion(var identificador : Token, var tipoDato :Token ): Sentencia() {

    override fun toString(): String {
        return "Conversion(identificador=$identificador, tipoDato=$tipoDato)"
    }

    override fun getArbolVisual(): TreeItem<String>? {

        var raiz = TreeItem("CONVERSION")
        raiz.children.add(TreeItem("identificador: ${identificador.lexema}"))
        raiz.children.add(TreeItem("tipo dato a convertir: ${tipoDato.lexema}"))

        return raiz
    }
}