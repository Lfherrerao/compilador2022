package co.edu.uniquindio.compilador.sintaxis

import javafx.scene.control.TreeItem

open class Elemento : Sentencia(){
    override fun getArbolVisual(): TreeItem<String>? {
        return  TreeItem ("Elemento")
    }
}