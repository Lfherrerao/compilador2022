package co.edu.uniquindio.compilador.sintaxis

import javafx.scene.control.TreeItem

open class DeclaracionVariable(): Elemento() {
    override fun toString(): String {
        return "DeclaracionVariable()"
    }

   open  override fun getArbolVisual(): TreeItem<String>? {
        return null

    }
}