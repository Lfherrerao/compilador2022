package co.edu.uniquindio.compilador.sintaxis

import javafx.scene.control.TreeItem

//,
class Ciclo (var expresionLogica : ExpresionLogica, var listaSentencias: ArrayList<Sentencia>?):Sentencia(){
    override fun toString(): String {
        return "Ciclo(expresionLogica=$expresionLogica, listaSentencias=$listaSentencias)"
    }

    override fun getArbolVisual(): TreeItem<String>{
        var raiz = TreeItem("CICLO")


        raiz.children.add(expresionLogica.getArbolVisual())

        var raiZSentencias = TreeItem("LISTA SENTENCIAS")

        for (s in listaSentencias!!){
            raiZSentencias.children.add(s.getArbolVisual())
        }
        raiz.children.add(raiZSentencias)
        return raiz

    }

}