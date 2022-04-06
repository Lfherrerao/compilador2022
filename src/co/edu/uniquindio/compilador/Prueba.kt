package co.edu.uniquindio.compilador

import co.edu.uniquindio.compilador.lexico.AnalizadorLexico
import co.edu.uniquindio.compilador.sintaxis.AnalizadorSintactico

fun main ()  {


    var lexico= AnalizadorLexico("_inicio   _fin")
    lexico.analizar()
    var sintaxis=AnalizadorSintactico(lexico.listaTokens)
    print(sintaxis.esUnidadDeCompilacion())
}