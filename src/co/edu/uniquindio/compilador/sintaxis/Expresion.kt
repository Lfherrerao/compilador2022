package co.edu.uniquindio.compilador.sintaxis

import javafx.scene.control.TreeItem

open class Expresion {
    open fun getArbolVisual(): TreeItem<String> {
return TreeItem("EXPRESION")
    }

}