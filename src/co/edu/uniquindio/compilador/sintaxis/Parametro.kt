package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem

class Parametro (var tipoDato : Token,  var nombreParametro: Token ) {
    override fun toString(): String {
        return "Parametro(tipoDato=$tipoDato, nombreParametro=$nombreParametro)"
    }

    fun getArbolVisual(): TreeItem<String>? {

        var raiz = TreeItem("Tipo dato")

        return TreeItem( "${tipoDato.lexema} : ${nombreParametro.lexema}")

    }
}