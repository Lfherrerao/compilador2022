package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Categoria
import co.edu.uniquindio.compilador.lexico.Token
import co.edu.uniquindio.compilador.lexico.Error


class AnalizadorSintactico(var listaTokens: ArrayList<Token>) {

    var posicionActual = 0
    var tokenActual = listaTokens[0]
    val listaDeErrores = ArrayList<Error>()


    /**
     * Obtiene el siguiente token de la tabla de tokens
     */
    fun obtenerSiguienteToken() {
        posicionActual++
        if (posicionActual < listaTokens.size) {
            tokenActual = listaTokens[posicionActual]
        }
    }

    /**
     * funcion encargada de guardar los errores ocurridos en el analizador sintactico
     */
    fun reportarError(mensaje: String) {
        listaDeErrores.add(Error(mensaje, tokenActual.fila, tokenActual.columna))
    }

    /**
     * <unidadDeCompilacion>:: _inicio [<ListaElemento>]  _fin
     */
    fun esUnidadDeCompilacion(): UnidadDeCompilacion? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_inicio") {
            obtenerSiguienteToken()

            var listaElementos: ArrayList<Elemento> = esListaElemento()


                if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_fin") {
                    obtenerSiguienteToken()
                    return UnidadDeCompilacion(listaElementos)
                }

        }
        return  null
    }

    /**
     * <ListaElementos>:: <Elementos>[<ListaElementos>]
     */
    fun esListaElemento(): ArrayList<Elemento> {

        var listaElementos = ArrayList<Elemento>()
        var elemento = esElemento()

        while (elemento != null) {
            listaElementos.add(elemento)
            elemento = esElemento()

        }
        return listaElementos
    }

    /**
     * <Elemento>:: <Funcion> | <DeclaracionVariable>
     */
    fun esElemento(): Elemento? {
        var elementoF = esFuncion()

        if(elementoF != null){
            return elementoF
        }

        var elementoV = esDeclaracioVariable()
        return elementoV
    }

    /**
     * <listaFunciones>:: funcion [<ListaFunciones>]
     */
    fun esListaFunciones(): ArrayList<Funcion> {
        var listaFunciones = ArrayList<Funcion>()
        var funcion = esFuncion()

        while (funcion!= null) {
            listaFunciones.add(funcion)
            funcion = esFuncion()
        }
        return listaFunciones
    }

    /**
     * <Funcion> :: _fun identificador ":" [<TipoDato>] "("  <ListaParametros> ")" "{" <ListaSentencias> "}"
     */
    fun esFuncion(): Funcion? {
        return null

    }

    /**
     * <DeclaracionVariable>:: <variable> | <Constante>
     */
    fun esDeclaracioVariable(): DeclaracionVariable?{
        return null
    }
}