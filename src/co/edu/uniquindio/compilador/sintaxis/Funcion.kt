package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem

//nombreFuncion,tipoDato,listaParametro,listaSentencias
class Funcion (var nombreFuncion: Token, var tipoRetorno:Token?, var listaParametros: ArrayList<Parametro>?, var listaSeentencias: ArrayList<Sentencia>?) : Elemento() {
    override fun toString(): String {
        return "Funcion(nombreFuncion=$nombreFuncion, tipoDato=$tipoRetorno, listaParametros=$listaParametros, listaSeentencias=$listaSeentencias)"
    }

     override fun getArbolVisual(): TreeItem<String>?{

         var raiz =  TreeItem("Funcion")

         raiz.children.add(TreeItem("nombre : ${nombreFuncion.lexema}"))
         raiz.children.add(TreeItem("TipoRetorno : ${tipoRetorno?.lexema}"))

         var raizParametro = TreeItem("Lista parametros")

         for (p in listaParametros!!){
             raizParametro.children.add(p.getArbolVisual())

         }
        raiz.children.add((raizParametro))

         var listaSentencias = TreeItem("Lista sentencias")
         for (p in listaSeentencias!!){
             listaSentencias.children.add(p.getArbolVisual())

         }
         raiz.children.add((listaSentencias))

        return raiz

    }
}