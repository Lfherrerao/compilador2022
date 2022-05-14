package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem

class Constante (var tipoDato: Token, var identificador: Token, var operadorAsignacion: Token, var expresion: Expresion ): DeclaracionVariable(){
   override fun toString(): String {
      return "Constante(tipoDato=$tipoDato, identificador=$identificador, operadorAsignacion=$operadorAsignacion, expresion=$expresion)"
   }


   override fun getArbolVisual(): TreeItem<String>? {

      var raiz = TreeItem("CONSTANTE")

      raiz.children.add(TreeItem("Tipo: ${tipoDato.lexema}"))
      raiz.children.add(TreeItem("Nombre: ${identificador.lexema}"))
      raiz.children.add(TreeItem("Operador de asignacion: ${operadorAsignacion?.lexema}"))
      raiz.children.add(TreeItem("Expresion: ${expresion.getArbolVisual()}"))

      return raiz

   }
}

