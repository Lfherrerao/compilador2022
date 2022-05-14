package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem

class Asignacion(var nombreAsignacion: Token,var  operador: Token, var expresion: Expresion) : Sentencia() {
    override fun toString(): String {
        return "Asignacion(nombreAsignacion=$nombreAsignacion, operador=$operador, expresion=$expresion)"
    }

     override fun getArbolVisual() :TreeItem<String>{

         var raiz = TreeItem("ASIGNACION")

         raiz.children.add(TreeItem("Nombre: ${nombreAsignacion.lexema}"))
         raiz.children.add(TreeItem("operador Asignacion: ${operador.lexema}"))
         var raizExpresion = TreeItem("EXPRESION")
         raizExpresion.children.add(expresion.getArbolVisual())
         raiz.children.add(raizExpresion)


         return raiz
    }

}