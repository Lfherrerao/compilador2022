package co.edu.uniquindio.lexico

fun main ()  {

    val lexico=AnalizadorLexico("aE123")
    lexico.analizar()
    print (lexico.listaTokens)



}