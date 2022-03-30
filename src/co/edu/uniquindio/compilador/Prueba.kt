package co.edu.uniquindio.compilador

import co.edu.uniquindio.compilador.lexico.AnalizadorLexico

fun main ()  {

    val lexico= AnalizadorLexico("&armario90 6E10")
    lexico.analizar()
    print (lexico.listaTokens)
}