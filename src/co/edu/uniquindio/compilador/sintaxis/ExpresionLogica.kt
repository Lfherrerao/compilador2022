package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionLogica (): Expresion() {

    var expresionLogica1 :ExpresionLogica? = null
    var expresionLogica2 :ExpresionLogica? = null
    var expresionRelacional : ExpresionRelacional?=null
    var operador: Token? = null



    constructor(expresionLogica1: ExpresionLogica?, operador: Token?, expresionLogica2 :ExpresionLogica?):this(){
        this.expresionLogica1 = expresionLogica1
        this.operador=operador
        this.expresionLogica2 = expresionLogica2

    }

    constructor(expresionLogica1: ExpresionLogica?): this() {
        this.expresionLogica1= expresionLogica1
    }

    constructor(expresionRelacional : ExpresionRelacional?, operador: Token?,expresionLogica2 :ExpresionLogica? ):this(){
        this.expresionRelacional=expresionRelacional
        this.operador=operador
        this.expresionLogica2=expresionLogica2

    }

    constructor(expresionRelacional : ExpresionRelacional?): this() {
        this.expresionRelacional=expresionRelacional
    }

    override fun toString(): String {
        return "ExpresionLogica(expresionLogica1=$expresionLogica1, expresionLogica2=$expresionLogica2, expresionRelacional=$expresionRelacional, operador=$operador)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("EXPRESIONLOGICA")

        if (expresionLogica1 != null && expresionLogica2 != null){

            raiz.children.add(expresionLogica1!!.getArbolVisual())
            raiz.children.add(TreeItem("${operador!!.lexema}"))
            raiz.children.add(expresionLogica2!!.getArbolVisual())

            return raiz

        }
        if (expresionLogica1 != null && expresionRelacional != null){

            raiz.children.add(expresionLogica1!!.getArbolVisual())
            raiz.children.add(TreeItem("${operador!!.lexema}"))
            raiz.children.add(expresionRelacional!!.getArbolVisual())

            return raiz

        }
        if (expresionLogica2 != null && expresionRelacional  != null){

            raiz.children.add(expresionRelacional!!.getArbolVisual())
            raiz.children.add(TreeItem("${operador!!.lexema}"))
            raiz.children.add(expresionLogica2!!.getArbolVisual())

            return raiz

        }
        if (expresionLogica1 != null){
            raiz.children.add(expresionLogica1!!.getArbolVisual())
            return raiz
        }

        if (expresionRelacional != null){
            raiz.children.add(expresionRelacional!!.getArbolVisual())
            return raiz
        }

        return raiz
    }

}