package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem

class IncrementoDecremento(var inOde: Token): Sentencia() {

    override fun toString(): String {
        return "IncrementoDecremento(inOde=$inOde)"
    }

    override fun getArbolVisual(): TreeItem<String>? {
        var raiz= TreeItem("Incremento o Decremento")

        if (inOde.lexema == "++"){

            return  TreeItem("Incremento : ${inOde.lexema}")
        }

        if (inOde.lexema == "--"){

            return  TreeItem("Decremento : ${inOde.lexema}")
        }

        return raiz
    }

}