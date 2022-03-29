package co.edu.uniquindio.lexico

fun main ()  {

    val lexico=AnalizadorLexico("D123.2E123 ")
    lexico.analizar()
    print (lexico.listaTokens)



}