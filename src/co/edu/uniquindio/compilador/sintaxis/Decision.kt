package co.edu.uniquindio.compilador.sintaxis

import javafx.scene.control.TreeItem

class Decision(
    var expresion: ExpresionLogica,
    var sentencias: ArrayList<Sentencia>?,
    var sentenciasDemas: ArrayList<Sentencia>?
) : Sentencia() {
    override fun toString(): String {
        return "Decision(expresion=$expresion, sentencias=$sentencias, sentenciasDemas=$sentenciasDemas)"
    }

    override fun getArbolVisual(): TreeItem<String>? {

        var raiz = TreeItem("DECISION")

        raiz.children.add(expresion.getArbolVisual())

        var sentenciasSi = TreeItem("Sentencias _si")
        for (ss in sentencias!!) {
            sentenciasSi.children.add(ss.getArbolVisual())
        }
        raiz.children.add(sentenciasSi)

        if (sentenciasDemas != null) {
            var raizSd = TreeItem("Sentencias _demas")

            for (sd in sentenciasDemas!!) {
                raizSd.children.add(sd.getArbolVisual())
            }
            raiz.children.add(raizSd)
        }

        return raiz
    }
}