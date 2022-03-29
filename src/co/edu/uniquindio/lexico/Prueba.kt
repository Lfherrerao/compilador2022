package co.edu.uniquindio.lexico

fun main ()  {

    val lexico=AnalizadorLexico("&armario90 6E10")
    lexico.analizar()
    print (lexico.listaTokens)
}