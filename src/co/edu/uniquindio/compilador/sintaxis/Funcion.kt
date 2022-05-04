package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Token

//nombreFuncion,tipoDato,listaParametro,listaSentencias
class Funcion (var nombreFuncion: Token, var tipoDato:Token?, var listaParametros: ArrayList<Parametro>?, var listaSeentencias: ArrayList<Sentencia>?) : Elemento() {
}