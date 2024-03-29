package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionRelacional(): Expresion() {

    var expresionAritmetica1 :ExpresionAritmetica? = null
    var expresionAritmetica2 :ExpresionAritmetica? = null
    var operador: Token? = null
    var termino :Token?= null



    constructor (expresionAritmetica1: ExpresionAritmetica?, operador: Token?, expresionAritmetica2 :ExpresionAritmetica?):this(){
        this.expresionAritmetica1 = expresionAritmetica1
        this.operador=operador
        this.expresionAritmetica2 = expresionAritmetica2

    }


    // constructor para cuando es un true o false
    constructor(termino: Token?  ):this(){
        this.termino=termino
    }

    override fun toString(): String {
        return "ExpresionRelacional(expresionAritmetica1=$expresionAritmetica1, expresionAritmetica2=$expresionAritmetica2, operador=$operador, termino=$termino)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("EXPRESION RELACIONAL")

        if (termino != null){

            raiz.children.add(TreeItem("${termino!!.lexema}"))
            return raiz
        }
        if (expresionAritmetica1 != null && expresionAritmetica2!= null){
            raiz.children.add(expresionAritmetica1!!.getArbolVisual())
            raiz.children.add(TreeItem("${operador!!.lexema}"))
            raiz.children.add(expresionAritmetica2!!.getArbolVisual())
            return raiz
        }


        return raiz
    }


}