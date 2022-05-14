package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem

class ValorNumerico(var signo: Token?, var termino:Token) {

    override fun toString(): String {
        return "ValorNumerico(signo=$signo, termino=$termino)"
    }

    fun getArbolVisual (): TreeItem<String>?{
        var raiz= TreeItem("VALOR NUMERICO")

        if (signo?.lexema == "A+"){
            raiz.children.add(TreeItem("+${termino.lexema}"))
        }
        if (signo?.lexema == "A-"){
            raiz.children.add(TreeItem("-${termino.lexema}"))
        }
        if (signo?.lexema == null){
            raiz.children.add(TreeItem("${termino.lexema}"))
        }

        return raiz

    }

}