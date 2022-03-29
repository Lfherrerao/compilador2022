package co.edu.uniquindio.lexico


import java.util.*
import kotlin.collections.ArrayList

/**
 * @author leonardo fabio herrera o.
 * @author john Alexander Martinez
 * @author Diego Armando jimenez
 * Analizador Lexico, donde se encuentran todos los AFD programados.
 */
class AnalizadorLexico(var codigoFuente: String) {


    // variables globales que se usaran en los automatas.
    var posicionActual = 0
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var listaErrores = ArrayList<Error>()
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0
    var posicionInicial = posicionActual
    var palabrasReservadas = ArrayList<String>()


    /**
     * funcion con la que analizaremos los automatas
     */
    fun analizar() {
        while (caracterActual != finCodigo) {

            inicializarPalabraReservada()


            if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n') {
                obtenerSiguienteCaracter()
                continue
            }
            if (esEntero()) continue
            if (esDecimal()) continue
            if (esIdentificador()) continue
            /** if (esComa()) continue
            if (esLlaves()) continue
            if (esCorchete()) continue
            if (esParentesis()) continue
            if (esOperadorRelacional()) continue
            if (esFinSentencia()) continue
            if (esOperadorLogico()) continue
            if (esPunto()) continue
            if (esDosPuntos()) continue
            if (esOperadorDecremento()) continue
            if (esOperadorIncremento()) continue
            if (esOperadorAsignacion()) continue
            if (esOperadorArtimetico()) continue
            if (esCadena()) continue
            if (esComentarioBloque()) continue
            if (esComentarioLinea()) */


            almacenarToken("" + caracterActual, Categoria.DESCONOCIDO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
        }
    }


    fun inicializarPalabraReservada() {
        palabrasReservadas.add("_function")
        palabrasReservadas.add("_abstract")
        palabrasReservadas.add("_continue")
        palabrasReservadas.add("_for")
        palabrasReservadas.add("_new")
        palabrasReservadas.add("_switch")
        palabrasReservadas.add("_default")
        palabrasReservadas.add("_boolean")
        palabrasReservadas.add("_do")
        palabrasReservadas.add("_if")
        palabrasReservadas.add("_private")
        palabrasReservadas.add("_this")
        palabrasReservadas.add("_break")
        palabrasReservadas.add("_double")
        palabrasReservadas.add("_implements")
        palabrasReservadas.add("_protected")
        palabrasReservadas.add("_thow")
        palabrasReservadas.add("_byte")
        palabrasReservadas.add("_else")
        palabrasReservadas.add("_import")
        palabrasReservadas.add("_public")
        palabrasReservadas.add("_throws")
        palabrasReservadas.add("_case")
        palabrasReservadas.add("_enum")
        palabrasReservadas.add("_return")
        palabrasReservadas.add("_catch")
        palabrasReservadas.add("_extends")
        palabrasReservadas.add("_int")
        palabrasReservadas.add("_short")
        palabrasReservadas.add("_try")
        palabrasReservadas.add("_char")
        palabrasReservadas.add("_final")
        palabrasReservadas.add("_print")
        palabrasReservadas.add("_interface")
        palabrasReservadas.add("_static")
        palabrasReservadas.add("_void")
        palabrasReservadas.add("_string")
        palabrasReservadas.add("_class")
        palabrasReservadas.add("_long")
        palabrasReservadas.add("_float")
        palabrasReservadas.add("_while")
        palabrasReservadas.add("_true")
        palabrasReservadas.add("_false")
        palabrasReservadas.add("_make")
        palabrasReservadas.add("_case")
        palabrasReservadas.add("_call")
        palabrasReservadas.add("_var")
        palabrasReservadas.add("_read")
    }

    /**
     * Bactraking para cuando una funcion utiliza los caracteres que se usan en otro automata
     */
    fun hacerBT(pocicionInicial: Int, filaInicial: Int, columnaInicial: Int) {
        posicionActual = posicionInicial
        filaActual = filaInicial
        columnaActual = columnaInicial
        caracterActual = codigoFuente[posicionActual]
    }

    /**
     * funcion encargada de almacenar los tokens encontrados en un array
     */
    fun almacenarToken(lexema: String, categoria: Categoria, fila: Int, columna: Int) =
        listaTokens.add(Token(lexema, categoria, fila, columna))


    /**
     * funcion que reprecenta el automata para los numeros enteros.
     */
    fun esEntero(): Boolean {
        if (caracterActual == 'E') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual

            obtenerSiguienteCaracter()
            if (caracterActual.isDigit()) {

                lexema += caracterActual
                obtenerSiguienteCaracter()

                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
                return true

            }
            // reportar error.
        }
        return false  //retorno inmediato
    }

    /**
     * funcion que que reprecenta el automata  de los  numeros decimales.
     */
    fun esDecimal(): Boolean {

        if (caracterActual == 'D') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual

            obtenerSiguienteCaracter()

            if (caracterActual == '.' || caracterActual.isDigit()) {

                if (caracterActual == '.') {

                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if (caracterActual.isDigit()) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        while (caracterActual.isDigit()) {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }
                        almacenarToken(lexema, Categoria.DECIMAL, filaInicial, columnaInicial)
                        return true


                    }
                    // reportar error, "despues de D. debe de seguir almenos un digito"


                } else {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    while (caracterActual.isDigit()) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }

                    if (caracterActual == '.') {

                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        while (caracterActual.isDigit()) {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }
                        almacenarToken(lexema, Categoria.DECIMAL, filaInicial, columnaInicial)
                        return true


                    }

                    // reportar error, "despues de D D debe de seguir un punto, si no es un entero y estos comienzan con E"

                }
            }
            // retornar error  "Despues de el caracter D, debe de ingresar un dijito o un punto."
        }
        return false // retorno inmediato
    }

    /**
     *funcion que que reprecenta el automata  que define los identificadores
     */

    fun esIdentificador(): Boolean {
        if (caracterActual == '&') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual.isDigit() || caracterActual.isLetter()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual.isDigit() || caracterActual.isLetter()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual.isDigit() || caracterActual.isLetter()) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual.isDigit() || caracterActual.isLetter()) {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual.isDigit() || caracterActual.isLetter()) {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                if (caracterActual.isDigit() || caracterActual.isLetter()) {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()
                                    if (caracterActual.isDigit() || caracterActual.isLetter()) {
                                        lexema += caracterActual
                                        obtenerSiguienteCaracter()
                                        if (caracterActual.isDigit() || caracterActual.isLetter()) {
                                            lexema += caracterActual
                                            obtenerSiguienteCaracter()
                                            if (caracterActual.isDigit() || caracterActual.isLetter()) {
                                                lexema += caracterActual
                                                obtenerSiguienteCaracter()
                                                if (caracterActual.isDigit() || caracterActual.isLetter()) {
                                                    print("Error unidentificador solo acepta hasta 10 caractres.")
                                                    return false
                                                }


                                                almacenarToken(
                                                    lexema,
                                                    Categoria.IDENTIFICADOR,
                                                    filaInicial,
                                                    columnaInicial
                                                )
                                                return true
                                            }
                                            almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
                                            return true
                                        }
                                        almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
                                        return true
                                    }
                                    almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
                                    return true
                                }
                                almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
                                return true
                            }
                            almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
                            return true
                        }
                        almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
                        return true
                    }
                    almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
                    return true
                }
                almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
                return true
            }
            //reporte de errorr "despues del caracter $, debe de ir porlo menos un numero o una letra para que el identificador sea valido "
        }
        return false   // rechazo inmediato
    }

    /**
     *funcion que que reprecenta el automata  que define los operadores relacionales
     */


    /**
     * Metodo que indica si el lexema es un fin de sentancia
     */


    /**
     *funcion que que reprecenta el automata  que define los operadores logicos
     */


    /**
     * funcion que que reprecenta el automata  que define los operadores de incremento
     */
    /**
     * funcion que que reprecenta el automata  que define los operadores de decremento.
     */


    /*
    * Metodo que verifica si el lexema es un punto
     */


    /*
    * Metodo que verifica si el lexema es un dos puntos
     */


    /**
     *Metodo que verifica si el caracter a analizar en un operador de asignacion
     */

    /**
     *Metodo que verifica si el lexema que se analiza es un operador aritmetrico
     */

    /*
    Metodo que indica si el lexema ingresado es una cadena
     */

    /*
	 * Metodo encargado de indicar si lo que se esta analizando es o no un
	 * comentario de bloque
     */

    /*
	 * Metodo encargado de indicar si lo que se esta analizando es o no un
	 * comentario de bloque
     */
    /**
     * funcion que que reprecenta el automata  para las llaves tanto izquierda como derecha.
     */


    /**
     * funcion que que reprecenta el automata para los corchetes derecho e izquierdo
     */


    /**
     *    Metodo que indica si el lexema es un parentesis derecho u izquierdo
     */


    /**
     * funcion encargada de obtener el siguiente caracter en otras funciones.
     */
    fun obtenerSiguienteCaracter() {
        if (posicionActual == codigoFuente.length - 1) {
            caracterActual = finCodigo
        } else {
            if (caracterActual == '\n') {
                filaActual++
                columnaActual = 0


            } else {
                columnaActual++
            }

            posicionActual++
            caracterActual = codigoFuente[posicionActual]
        }

    }
}

