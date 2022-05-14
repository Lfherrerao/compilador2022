package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem

class Lectura (var identificador : Token):Sentencia(){
    override fun toString(): String {
        return "Lectura(identificador=$identificador)"
    }

    override fun getArbolVisual(): TreeItem<String>? {
        var raiz = TreeItem("LECTURA")
        raiz.children.add(TreeItem("identificador ${identificador.lexema}"))
        return raiz
    }
}