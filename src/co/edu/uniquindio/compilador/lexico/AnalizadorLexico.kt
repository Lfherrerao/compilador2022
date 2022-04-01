package co.edu.uniquindio.compilador.lexico


import javax.swing.JOptionPane
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
            if (esCorchete()) continue
            if (esParentesis()) continue
            if (esLlaves()) continue
            if (esPunto()) continue
            if (esDosPuntos()) continue
            if (esFinSentencia()) continue
            if (esComa()) continue
            /**



            if (esOperadorRelacional()) continue

            if (esOperadorLogico()) continue


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
            JOptionPane.showMessageDialog(
                null, "ERROR: Despues de 'E', " +
                        "debe de ir por lo menis un numero decimal, \n el lexema leido hasta el momnto " +
                        "no aparecera en la tabla. y se sigue analizando desde el proximo token !. "
            )// reportar error.
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
                    JOptionPane.showMessageDialog(
                        null, "ERROR: despues de un punto, debe de seguir por lo menos un numero. " +
                                "\n este token no se mostrara en la tabla y se procede a seguir analizando los siguientes tokens. "
                    )
                    // reportar error, "despues de D. debe de seguir almenos un digito"
                    return false;


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
                    JOptionPane.showMessageDialog(
                        null, "ERROR:  debe de seguir un punto, para que el AFD sea decimal. " +
                                "\n este token no se mostrara en la tabla y se procede a seguir analizando los siguientes tokens. "
                    )
                    // reportar error, "despues de D D debe de seguir un punto, si no es un entero y estos comienzan con E"
                    return false

                }
            }
            JOptionPane.showMessageDialog(
                null, "ERROR: despues de 'D', debe de seguir almenos un digito  o un punto " +
                        "\n este token no se mostrara en la tabla y se procede a seguir analizando los siguientes tokens. "
            )
            // retornar error  "Despues de el caracter D, debe de ingresar un dijito o un punto."
            return false
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
                                                    JOptionPane.showMessageDialog(
                                                        null,
                                                        "Error un identificador solo acepta hasta 10 caractres \n " +
                                                                " se procede analizar siguente token despues del decimo caracter del identificador."
                                                    )
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
    fun esFinSentencia():Boolean{
        if (caracterActual == ';') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual

            almacenarToken(lexema, Categoria . FIN_SENTENCIA , filaInicial, columnaInicial)
            obtenerSiguienteCaracter()
            return true
        }
        return false
    }


    /**
     *funcion que que reprecenta el automata  que define los operadores logicos
     */


    /**
     * funcion que que reprecenta el automata  que define los operadores de incremento
     */
    /**
     * funcion que que reprecenta el automata  que define los operadores de decremento.
     */


    /**
    * Metodo que verifica si el lexema es un punto
     */

    fun esPunto():Boolean{
        if (caracterActual == '.') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual

            almacenarToken(lexema, Categoria . PUNTO , filaInicial, columnaInicial)
            obtenerSiguienteCaracter()
            return true
        }
        return false
    }



    /**
    * Metodo que verifica si el lexema es un dos puntos
     */
    fun esDosPuntos():Boolean{
        if (caracterActual == ':') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual

            almacenarToken(lexema, Categoria . DOS_PUNTOS , filaInicial, columnaInicial)
            obtenerSiguienteCaracter()
            return true
        }
        return false
    }


    /**
     *Metodo que verifica si el caracter a analizar en un operador de asignacion
     */

    /**
     *Metodo que verifica si el caracter a analizar en un separador (es coma)
     */
    fun esComa():Boolean{
        if (caracterActual == ',') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual

            almacenarToken(lexema, Categoria . ES_COMA , filaInicial, columnaInicial)
            obtenerSiguienteCaracter()
            return true
        }
        return false
    }

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

    fun esLlaves(): Boolean {
        if (caracterActual == '{' || caracterActual == '}') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual


            if (caracterActual ==  '{' ) {

                almacenarToken(lexema, Categoria . LLAVE_IZQUIERDA , filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            } else {
                almacenarToken(lexema, Categoria . LLAVE_DERECHA , filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }

        }
        return false
    }



    /**
     * funcion que que reprecenta el automata para los corchetes derecho e izquierdo
     */
    fun esCorchete(): Boolean {
        if (caracterActual == '[' || caracterActual == ']') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual


            if (caracterActual ==  '[' ) {

                almacenarToken(lexema, Categoria . CORCHETE_IZQUIERDO , filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            } else {
                almacenarToken(lexema, Categoria . CORCHETE_DEREHO , filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }

        }
        return false
    }



    /**
     *    Metodo que indica si el lexema es un parentesis derecho u izquierdo
     */
    fun esParentesis():Boolean{
        if (caracterActual == '(' || caracterActual == ')') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual


            if (caracterActual ==  '(' ) {

                almacenarToken(lexema, Categoria . PARENTESIS_IZQUIERDO , filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            } else {
                almacenarToken(lexema, Categoria . PARENTESIS_DERECHO , filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }

        }
        return false


    }


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

