package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem

class Invocacion(var identificador: Token, var listaParameetros: ArrayList<Parametro>?): Sentencia() {

    override fun toString(): String {
        return "Invocacion(identificador=$identificador, listaParameetros=$listaParameetros)"
    }

    override fun getArbolVisual(): TreeItem<String>? {
        var raiz = TreeItem("INVOCACION FUNCION")
            raiz.children.add(TreeItem("Nombre: ${identificador.lexema}"))

        var raizListaParametro = TreeItem("PARAMETROS")

        for (p in listaParameetros!! ){
            raizListaParametro.children.add(p.getArbolVisual())
        }
        raiz.children.add( raizListaParametro)

        return raiz
    }
}