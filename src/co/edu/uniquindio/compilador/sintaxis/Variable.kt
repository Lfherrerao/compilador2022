package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem


class Variable (var tipoDato: Token, var identificador:Token, var operadorAsignacion: Token?, var expresion: Expresion? ) : DeclaracionVariable(){
    override fun toString(): String {
        return "Variable(tipoDato=$tipoDato, identificador=$identificador, operadorAsignacion=$operadorAsignacion, expresion=$expresion)"
    }

    override fun getArbolVisual(): TreeItem<String>? {

        var raiz = TreeItem("VARIABLE")

        raiz.children.add(TreeItem("Tipo: ${tipoDato.lexema}"))
        raiz.children.add(TreeItem("Nombre: ${identificador.lexema}"))
        raiz.children.add(TreeItem("Operador de asignacion: ${operadorAsignacion?.lexema}"))



        var e = TreeItem("EXPRESION")
        if (expresion != null){

            e.children.add(expresion!!.getArbolVisual())
        }
        raiz.children.add(e)

        return raiz

    }

}