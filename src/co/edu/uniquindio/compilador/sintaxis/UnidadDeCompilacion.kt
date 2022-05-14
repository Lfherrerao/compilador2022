package co.edu.uniquindio.compilador.sintaxis

import javafx.scene.control.TreeItem

class UnidadDeCompilacion(var listaElementos: ArrayList<Elemento>) {
    override fun toString(): String {
        return "UnidadDeCompilacion(listaElementos=$listaElementos)"
    }

    fun getArbolVisual(): TreeItem<String>? {

        var raiz= TreeItem ("UNIDAD DE COMPILACION")

        for (e in listaElementos){
           raiz.children.add( e.getArbolVisual())
        }

        return raiz

    }
}