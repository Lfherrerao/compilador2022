package co.edu.uniquindio.compilador.lexico



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
    var filaInicial = 0
    var columnaInicial = 0


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
            if (esSeparador()) continue
            if (esOperadorAritmetico()) continue
            if (esCadena()) continue
            if (esComentarioBloque()) continue
            if (esOperadorAsignacion()) continue
            if (esOperadorDecremento()) continue
            if (esOperadorIncremento()) continue
            if (esOperadorRelacional()) continue
            if (esComentarioLinea()) continue
            if (esCaracter()) continue
            if (esOperadorLogico()) continue
            if (esPalabraReservada())continue


            almacenarToken("" + caracterActual, Categoria.DESCONOCIDO, filaActual, columnaActual)
            reportarError("caracter desconocido" )
            obtenerSiguienteCaracter()
        }
    }



    fun inicializarPalabraReservada() {
        palabrasReservadas.add("_fun")
        palabrasReservadas.add("_entero")
        palabrasReservadas.add("_decimal")
        palabrasReservadas.add("_logico")
        palabrasReservadas.add("_cadena")
        palabrasReservadas.add("_var")
        palabrasReservadas.add("_con")
        palabrasReservadas.add("_inicio")
        palabrasReservadas.add("_fin")
        palabrasReservadas.add("_verdad")
        palabrasReservadas.add("_falso")
        palabrasReservadas.add("_mientras")
        palabrasReservadas.add("_declara")
        palabrasReservadas.add("_EL")
        palabrasReservadas.add("_EA")
        palabrasReservadas.add("_ER")
        palabrasReservadas.add("_EC")
        palabrasReservadas.add("_imprime")
        palabrasReservadas.add("_lea")
        palabrasReservadas.add("_llamar")
        palabrasReservadas.add("_si")
        palabrasReservadas.add("_demas")
        palabrasReservadas.add("_convertir")
        palabrasReservadas.add("_retorne")
        palabrasReservadas.add("_arr")
        palabrasReservadas.add("_inArr")



    }

    /**
     * funcion encargada de verificar palabras reservadas
     */
    fun esPalabraReservada(): Boolean {
        if (caracterActual== '_'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            inicializarPalabraReservada()
            while  (!(caracterActual == finCodigo || caracterActual == ' ' || caracterActual == '\n') ){

                lexema += caracterActual
                obtenerSiguienteCaracter()

            }
            if (palabrasReservadas.contains(lexema)){
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }
            reportarError(lexema + " no es una palabra reservada")
            return false
        }
        return false // RI
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
            reportarError( "ERROR: Despues de 'E', " +
                        "debe de ir por lo menis un numero decimal, \n el lexema leido hasta el momnto " +
                        "no aparecera en la tabla. y se sigue analizando desde el proximo token !. "
            )// RE
            return false
        }
        return false  //RI
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
                    reportarError( "ERROR: despues de un punto, debe de seguir por lo menos un numero. " +
                                "\n este token no se mostrara en la tabla y se procede a seguir analizando los siguientes tokens. "
                    )
                    // RE
                    return false


                }
                else {
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
                    reportarError( "ERROR:  debe de seguir un punto, para que el AFD sea decimal. " +
                                "\n este token no se mostrara en la tabla y se procede a seguir analizando los siguientes tokens. "
                    )
                    // RE
                    return false

                }
            }
            reportarError( "ERROR: despues de 'D', debe de seguir almenos un digito  o un punto " +
                        "\n este token no se mostrara en la tabla y se procede a seguir analizando los siguientes tokens. ")
            // RE
            return false
        }
        return false // RI
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
                                                    reportarError("Error un identificador solo acepta hasta 10 caractres \n " +
                                                                " se procede analizar siguente token despues del decimo caracter del identificador.")
                                                    reportarError("Error unidentificador solo acepta hasta 10 caractres.")
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
            reportarError("se encontro caracter no esperado")
        }
        return false   // RI
    }

    /**
     *funcion que que reprecenta el automata  que define los operadores relacionales
     */
    fun esOperadorRelacional(): Boolean {
        if (caracterActual == 'R') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '<') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {

                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
                almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                return true
            }
            if (caracterActual == '>') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {

                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }

                almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                return true
            }
            if (caracterActual == '=') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
              reportarError(" Error: Despues de el caracter = o !  debe de ingresar un = para que sea un operador relacional ")
                return false  //RE
            }
            if (caracterActual == '!') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '=') {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                    obtenerSiguienteCaracter()
                    return true
                }
               reportarError(" Error: Despues de el caracter = o !  debe de ingresar un = para que sea un operador relacional ")
                return false  //RE
            }


            reportarError( " Error: Despues de R debe de ingresar un operador relacional ")
            return false  //RE
        }
        return false // RI
    }

    /**
     * Metodo que indica si el lexema es un fin de sentancia
     */
    fun esFinSentencia(): Boolean {
        if (caracterActual == ';') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual

            almacenarToken(lexema, Categoria.FIN_SENTENCIA, filaInicial, columnaInicial)
            obtenerSiguienteCaracter()
            return true
        }
        return false // RI
    }

    /**
     *funcion que que reprecenta el automata  que define los operadores logicos
     */
    fun esOperadorLogico(): Boolean{

        if (caracterActual == 'Y' || caracterActual== 'O' || caracterActual== 'N' ){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            if (caracterActual== 'Y'){

                lexema += caracterActual

                almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true

            }else {
                lexema += caracterActual

                almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }

        }
        return false //RI
    }


    /**
     * funcion que que reprecenta el automata  que define los operadores de incremento
     */
    fun esOperadorIncremento(): Boolean {
        if (caracterActual == '+') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '+') {
                lexema += caracterActual
                almacenarToken(lexema, Categoria.INCREMENTO, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }

            return false
        }
        return false//RI
    }

    /**
     * funcion que que reprecenta el automata  que define los operadores de decremento.
     */
    fun esOperadorDecremento(): Boolean {
        if (caracterActual == '-') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '-') {
                lexema += caracterActual
                almacenarToken(lexema, Categoria.DECREMENTO, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }

            return false
        }
        return false
    }

    /**
     * Metodo que verifica si el lexema es un punto
     */
    fun esPunto(): Boolean {
        if (caracterActual == '.') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual

            almacenarToken(lexema, Categoria.PUNTO, filaInicial, columnaInicial)
            obtenerSiguienteCaracter()
            return true
        }
        return false //RI
    }

    /**
     * Metodo que verifica si el lexema es un dos puntos
     */
    fun esDosPuntos(): Boolean {
        if (caracterActual == ':') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual

            almacenarToken(lexema, Categoria.DOS_PUNTOS, filaInicial, columnaInicial)
            obtenerSiguienteCaracter()
            return true
        }
        return false  // RI
    }

    /**
     *Metodo que verifica si el caracter a analizar en un operador de asignacion
     */
    fun esOperadorAsignacion(): Boolean {
        if (caracterActual == 'S') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '=') {
                lexema += caracterActual
                obtenerSiguienteCaracter()



                if (caracterActual == '+' || caracterActual == '-' || caracterActual == '*' || caracterActual == '/' ||
                    caracterActual == '%'
                ) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial)
                    return true
                }


                almacenarToken(lexema, Categoria.OPERADOR_ASIGNACION, filaInicial, columnaInicial)
                return true

            }

            reportarError( "Error: despues de una 'S', debe de ir un caracter de asignacion")
            return false//RE

        }
        return false
    }

    /**
     *Metodo que verifica si el caracter a analizar en un separador (es coma)
     */
    fun esSeparador(): Boolean {
        if (caracterActual == ',') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual

            almacenarToken(lexema, Categoria.ES_SEPARADOR, filaInicial, columnaInicial)
            obtenerSiguienteCaracter()
            return true
        }
        return false
    }

    /**
     *Metodo que verifica si el lexema que se analiza es un operador aritmetrico
     */
    fun esOperadorAritmetico(): Boolean {
        if (caracterActual == 'A') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '+' || caracterActual == '-' || caracterActual == '*' || caracterActual == '/' ||
                caracterActual == '%'
            ) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial)
                return true
            }
            reportarError("Error: En los operadores aritmeticos, despues de una 'A'  debe de ir un ssigno aritmetico!")
            return false // RE

        }
        return false // RI
    }

    /**
    Metodo que indica si el lexema ingresado es una caracter
     */
    fun esCaracter(): Boolean {
        if (caracterActual == '\'') {

            var bandera = true
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '\\') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == '\\' || caracterActual == 't' || caracterActual == 'n' || caracterActual == '\'') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if (caracterActual == '\''){
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(lexema, Categoria.CARACTER, filaInicial, columnaInicial)
                        return true
                    }
                    return false //RE
                   reportarError( "Error cerrando el caracter")

                }
                reportarError( "Error en caracter")
                return false // RE

                //RE
            }
            else {
                if(caracterActual== '\''){

                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.CARACTER, filaInicial, columnaInicial)
                    return true
                }
                else {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual== '\''){
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(lexema, Categoria.CARACTER, filaInicial, columnaInicial)
                        return true
                    }
                    return false
                }
            }
           reportarError( "Error")
            return false

        }
        return false  //RI
    }

    /**
    Metodo que indica si el lexema ingresado es una cadena
     */
    fun esCadena(): Boolean {
        if (caracterActual == '"') {

            var bandera = true
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (!(caracterActual == '"' || caracterActual == finCodigo)) {

                if (caracterActual == '\\') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if (caracterActual == '\\' || caracterActual == 't' || caracterActual == 'n' || caracterActual == '"') {

                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    } else {
                        reportarError( "Error en la cadena")
                        return false // RE}
                    }


                } else {

                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
            }


            if (caracterActual == '"') {

                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.CADENA, filaInicial, columnaInicial)
                return true
            }
            reportarError( "Error cerrando la cadena")
            return false
        }
        return false  //RI
    }

    /**
     * Metodo encargado de indicar si lo que se esta analizando es o no es un
     * comentario de bloque
     */
    fun esComentarioBloque(): Boolean {
        if (caracterActual == 'B') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '#') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                while (!(caracterActual == '#' || caracterActual == finCodigo)) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                if (caracterActual == '#') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'B') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(lexema, Categoria.COMENTARIO_DE_BLOQUE, filaInicial, columnaInicial)
                        return true
                    }
                    reportarError( "ERROR: Despues de '#', " +
                                "debe de ir el caracter 'B' para cerrar el comentario \n el lexema leido hasta el momento " +
                                "no aparecera en la tabla. y se sigue analizando desde el proximo token !. ")
                    return false // RE
                } else if (caracterActual == finCodigo) {
                    reportarError( "ERROR: cerrando el comentario de bloque" +
                                " \n el lexema leido hasta el momento " +
                                "no aparecera en la tabla. y se sigue analizando desde el proximo token !. "
                    )// reportar error.
                    return false
                }
               reportarError( "ERROR: Despues de '#', " +
                            "debe de ir el caracter 'B' para cerrar el comentario \n el lexema leido hasta el momento " +
                            "no aparecera en la tabla. y se sigue analizando desde el proximo token !. "
                )// reportar error.
                return false
            }

            reportarError("ERROR: Despues de 'B', " +
                        "debe de ir el caracter '#' para abrir el comentario \n el lexema leido hasta el momento " +
                        "no aparecera en la tabla. y se sigue analizando desde el proximo token !. "
            )
            return false // RE
        }
        return false // RI
    }

    /**
     * funcion que que reprecenta el automata  para las llaves tanto izquierda como derecha.
     */
    fun esComentarioLinea(): Boolean {
        if (caracterActual == '#') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (!(caracterActual == '\n' || caracterActual == finCodigo)) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            if (caracterActual == '\n') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.COMENTARIO_DE_LINEA, filaInicial, columnaInicial)
                return true
            }
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.COMENTARIO_DE_LINEA, filaInicial, columnaInicial)
            return true
        }
        return false // RI
    }

    /**
     * funcion que que reprecenta el automata  para las llaves tanto izquierda como derecha.
     */
    fun esLlaves(): Boolean {
        if (caracterActual == '{' || caracterActual == '}') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual


            if (caracterActual == '{') {

                almacenarToken(lexema, Categoria.LLAVE_IZQUIERDA, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            } else {
                almacenarToken(lexema, Categoria.LLAVE_DERECHA, filaInicial, columnaInicial)
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


            if (caracterActual == '[') {

                almacenarToken(lexema, Categoria.CORCHETE_IZQUIERDO, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            } else {
                almacenarToken(lexema, Categoria.CORCHETE_DEREHO, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }

        }
        return false
    }

    /**
     *    Metodo que indica si el lexema es un parentesis derecho u izquierdo
     */
    fun esParentesis(): Boolean {
        if (caracterActual == '(' || caracterActual == ')') {

            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual


            if (caracterActual == '(') {

                almacenarToken(lexema, Categoria.PARENTESIS_IZQUIERDO, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            } else {
                almacenarToken(lexema, Categoria.PARENTESIS_DERECHO, filaInicial, columnaInicial)
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

    fun reportarError(mensaje: String) {
        listaErrores.add(Error(mensaje, filaActual, columnaActual))
    }
}

