package co.edu.uniquindio.compilador.sintaxis

import co.edu.uniquindio.compilador.lexico.Categoria
import co.edu.uniquindio.compilador.lexico.Token
import co.edu.uniquindio.compilador.lexico.Error
import kotlin.math.sign


class AnalizadorSintactico(var listaTokens: ArrayList<Token>) {

    var posicionActual = 0
    var tokenActual = listaTokens[0]
    var listaDeErrores = ArrayList<Error>()


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

            }
            obtenerSiguienteToken()
            reportarError("Falta cerrar con _fin")
            return UnidadDeCompilacion(listaElementos)


        }
        return null
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

        if (elementoF != null) {
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

        while (funcion != null) {
            listaFunciones.add(funcion)
            funcion = esFuncion()
        }
        return listaFunciones
    }

    /**
     * <Funcion> :: _fun identificador ":" [<TipoDato>] "("  [<ListaParametros>] ")" "{" [<ListaSentencias>] "}"
     */
    fun esFuncion(): Funcion? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_fun") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var nombreFuncion = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                    obtenerSiguienteToken()
                    var tipoDato = esTipoDato()
                    if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {
                        obtenerSiguienteToken()
                        var listaParametro = esLIstaParametro()
                        if (tokenActual.categoria == Categoria.PARENTESIS_DERECHO) {
                            obtenerSiguienteToken()
                            if (tokenActual.categoria == Categoria.LLAVE_IZQUIERDA) {
                                obtenerSiguienteToken()
                                var listaSentencias = esListaSentencias()
                                if (tokenActual.categoria == Categoria.LLAVE_DERECHA) {
                                    obtenerSiguienteToken()

                                    return Funcion(nombreFuncion, tipoDato, listaParametro, listaSentencias)

                                }
                                reportarError("falta cerrar la funcion con llave derecha")
                            }
                            reportarError("falta parentesis izquierdo en la funcion")
                        }
                        reportarError("falta parentesis derecho en la funcion")
                    }
                    reportarError("Falta el parentesis izquierdo en la funcion")
                }
                reportarError("ERROR SINTACTICO:Faltan los :")
            } else {
                reportarError("ERROR SISNTACTICO: la funcion esta al nombrada")
            }
        }
        return null //RI

    }

    /**
     * <DeclaracionVariable>:: <variable> | <Constante>
     */
    fun esDeclaracioVariable(): DeclaracionVariable? {
        var variable = esVariable()

        if (variable != null) {
            return variable
        }

        var constante = esConstante()
        return constante
    }

    /**
     * <TipoDato>:: _logico | _cadena  | _entero  | _decimal
     */
    fun esTipoDato(): Token? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {
            if (tokenActual.lexema == "_entero" || tokenActual.lexema == "_decimal" || tokenActual.lexema == "_logico" || tokenActual.lexema == "_cadena") {
                var nombreDato = tokenActual
                obtenerSiguienteToken()
                return nombreDato
            }
            reportarError("ingrese un tipo de dato valido")
        }
        return null
    }

    /**
     * <ListaSentencias>:: <Sentencia>[<ListaSentencias>]
     */
    fun esListaSentencias(): ArrayList<Sentencia>? {

        var listaSentencias = ArrayList<Sentencia>()
        var sentencia = esSentencia()

        while (sentencia != null) {
            listaSentencias.add(sentencia)
            sentencia = esSentencia()

        }
        return listaSentencias
    }

    /**
     * <Sentencias>:: <Ciclo> | <DeclaracionVariable> | <Asignacion> | <Impresion> | <Lectura> | <InvocacionFuncion>
    | <Decision> | <Convertir> | <DeclaracionArreglo> | <Retorno>
     */
    fun esSentencia(): Sentencia? {


        var s: Sentencia? = esCiclo()


        if (s != null) {
            return s
        }

        s = esAsignacion()
        if (s != null) {
            return s
        }


        s = esImpresion()
        if (s != null) {
            return s
        }


        s = esLectura()
        if (s != null) {
            return s
        }


        s = esInvocacion()
        if (s != null) {
            return s
        }


        s = esDecision()
        if (s != null) {

            return s
        }


        s = esConversion()
        if (s != null) {
            return s
        }

        s= esDeclaracionArreglo()
        if (s != null){
            return s
        }

        s= esInicializarArreglo()
        if (s != null){
            return s
        }

        s = esRetorno()
        if (s != null) {
            return s
        }

        s = esIncrementoDecremento()
        if (s != null){
            return s
        }


        var v: DeclaracionVariable? = esDeclaracioVariable()

        if (v != null) {
            return v
        }

        return null
    }

    /**
     * <ListaParametros>:: <Parametro> ["," <ListaParametros>]
     */
    fun esLIstaParametro(): ArrayList<Parametro>? {
        var listaParametro = ArrayList<Parametro>()
        var parametro = esParametro()

        while (parametro != null) {
            listaParametro.add(parametro)
            if (tokenActual.categoria == Categoria.ES_SEPARADOR) {
                obtenerSiguienteToken()
                parametro = esParametro()
            } else {
                break
            }
        }
        return listaParametro
    }

    /**
     * <Variable>:: _var <TipoDato> identificador [operadorAsignacion <Expresion>]
     */
    fun esVariable(): Variable? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_var") {
            obtenerSiguienteToken()
            var tipoDato = esTipoDato()
            if (tipoDato != null) {
                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    var nombreVariable = tokenActual
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION) {
                        var operador = tokenActual
                        obtenerSiguienteToken()
                        var expresion = esExpresion()

                        if (expresion != null) {
                            return Variable(tipoDato, nombreVariable, operador, expresion)
                        }
                        reportarError("no se encontro una expresion al momento de inicializar la variable ")
                    }
                    return Variable(tipoDato, nombreVariable, null, null)
                }
                reportarError("identificador invalido al declarar una variable")
            }
            reportarError("tipo de dato invalido en la declaracion de variable")

        }
        return null //RI
    }

    /**
     * <Constante>:: _con <TipoDato> identificador "=" <Expresion>
     */
    fun esConstante(): Constante? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_con") {
            obtenerSiguienteToken()
            var tipoDato = esTipoDato()
            if (tipoDato != null) {
                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    var nombreConstante = tokenActual
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION) {
                        var operador = tokenActual
                        obtenerSiguienteToken()
                        var expresion = esExpresion()

                        if (expresion != null) {
                            return Constante(tipoDato!!, nombreConstante!!, operador!!, expresion!!)
                        }
                        reportarError("no se encontro una expresion al momento de inicializar la variable ")
                    }
                    reportarError("no se logra reconocer el operador de asignacion de la constante")
                }
                reportarError("identificador invalido al declarar una variable")
            }
            reportarError("tipo de dato invalido en la declaracion de variable")

        }
        return null //RI

    }

    /**
     * <Parametro>:: <TipoDato> identificador
     */
    fun esParametro(): Parametro? {
        var tipoDato = esTipoDato()
        if (tipoDato != null) {
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var nombreParametro = tokenActual
                obtenerSiguienteToken()
                return Parametro(tipoDato, nombreParametro)
            }

            reportarError("Ingrese un nombre de parametro valido")
        }
        return null // RI
    }

    /**
     * <Expresion>::<ExpresionAritmetica> | <ExpresionLogica> | <ExpresionRelacional> |
    <ExpresionCadena>
     */
    fun esExpresion(): Expresion? {

        var posicionInicial = posicionActual
        var fila = tokenActual.fila
        var columna = tokenActual.columna

        var e: Expresion? = esExpresionLogica()
        if (e != null) {

            e as ExpresionLogica
            if (e.operador != null) {
                return e
            }
            hacerBT(posicionInicial, fila, columna)
        }

        e = esExpresionRelacional()
        if (e != null) {

            return e
        }
        e = esExpresionAritmetica()
        if (e != null) {

            return e
        }

        e = esExpresionCadena()
        if (e != null) {

            return e
        }

        return null
    }

    /**
     * <ExpresionCadena>::  cadena[ '.' <ExpresionCadena>] | identificador
     */
    fun esExpresionCadena(): ExpresionCadena? {

        var cadena = tokenActual
        if (tokenActual.categoria == Categoria.CADENA) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PUNTO) {
                obtenerSiguienteToken()
                var cadena2 = esExpresionCadena()
                if (cadena2 != null) {
                    cadena.lexema += cadena2.cadena.lexema
                    return ExpresionCadena(cadena)
                } else {
                    reportarError("No se encotro una cadena concatenada despues de un punto.")
                }
            } else {
                return ExpresionCadena(cadena)
            }
        }

        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            var identificador = tokenActual
            obtenerSiguienteToken()
            if (identificador != null) {
                return ExpresionCadena(cadena)
            } else {
                reportarError("No se encotro una cadena concatenada despues de un punto.")
            }
        }
        return null // RI
    }

    /**
     * <ExpresionAritmetica>: : <ExpresionAritmetica> OperadorAritmetico <ExpresionAritmetica> |
    "(" <ExpresionAritmetica> ")" | <ValorNumerico>
     *
     * equivalente a:
     *
     * <ExpresionAritmetica::= "(" <ExpresionAritmetica> ")"[operadorAritmetico <ExpresionAritmetica>]
     * | <ValorNumerico>[operadorAritmetico <ExpresionAritmetica>]
     */
    fun esExpresionAritmetica(): ExpresionAritmetica? {

        if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {
            obtenerSiguienteToken()
            var ex1 = esExpresionAritmetica()
            if (ex1 != null) {
                if (tokenActual.categoria == Categoria.PARENTESIS_DERECHO) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO) {
                        var operador = tokenActual
                        obtenerSiguienteToken()
                        var ex2 = esExpresionAritmetica()
                        if (ex2 != null) {
                            return ExpresionAritmetica(ex1, operador, ex2)
                        } else {
                            reportarError("falta una expresion aritmetica 343")
                        }
                    } else {
                        return ExpresionAritmetica(ex1)
                    }
                }
            }

        } else {
            var valorNumerico = esValorNumerico()
            if (valorNumerico != null) {

                if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO) {
                    var operador = tokenActual
                    obtenerSiguienteToken()
                    var ex = esExpresionAritmetica()
                    if (ex != null) {
                        return ExpresionAritmetica(valorNumerico, operador, ex)
                    } else {
                        reportarError("falta una expresion aritmetica 361")
                    }
                } else {
                    return ExpresionAritmetica(valorNumerico)
                }
            }
        }

        return null
    }


    /**
     * <ExpresionLogica>::=  <ExpresionLogica> operadorLogico <ExpresionLogica> |  "(" <ExpresionLogica> ")" | <ExpresionRelacional>
     *
     *     equivalente a:
     *
     * <ExpresionLogica>::=  '(' <ExpresionLogica> ')'[operadorLogico <ExpresionLogica>] |
     *  <ExpresionRelacional>[operadorLogico <ExpresionLogica>]
     *
     */
    fun esExpresionLogica(): ExpresionLogica? {


        var posicionInicial = posicionActual
        var fila = tokenActual.fila
        var columna = tokenActual.columna




        if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {
            obtenerSiguienteToken()

            var expresion1 = esExpresionLogica()

            if (expresion1 != null) {
                if (tokenActual.categoria == Categoria.PARENTESIS_DERECHO) {

                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.OPERADOR_LOGICO) {

                        var operador = tokenActual
                        obtenerSiguienteToken()

                        var expresion2 = esExpresionLogica()

                        if (expresion2 != null) {
                            return ExpresionLogica(expresion1, operador, expresion2)

                        } else {

                            reportarError("error sintactico en una segunda expresion")
                        }

                    } else {
                        return ExpresionLogica(expresion1)
                    }
                }
                reportarError("error cerrando parentesis en expresion logica")

            }
            hacerBT(posicionInicial, fila, columna)
            return null


        } else {
            var expresionRelacional = esExpresionRelacional()
            if (expresionRelacional != null) {

                if (tokenActual.categoria == Categoria.OPERADOR_LOGICO) {
                    var operador = tokenActual
                    obtenerSiguienteToken()
                    var expresion2 = esExpresionLogica()
                    if (expresion2 != null) {
                        return ExpresionLogica(expresionRelacional, operador, expresion2)
                    } else {
                        reportarError("error sintactico en una segunda expresion")
                    }
                } else {
                    return ExpresionLogica(expresionRelacional)
                }
            }
            return null
        }



        return null
    }

    /**
     * <ExpresionRelacional>:: <ExpresionAritmetica> operadorRelacional <ExpresionAritmetica> | '('<ExpresionAritmetica>')'
     *     | _verdadero | _falso
     */
    fun esExpresionRelacional(): ExpresionRelacional? {

        var posicionInicial = posicionActual
        var fila = tokenActual.fila
        var columna = tokenActual.columna


        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_verdad"
            || tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_falso"
        ) {


            var expresion = tokenActual
            obtenerSiguienteToken()

            return ExpresionRelacional(expresion)


        }

        if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {

            obtenerSiguienteToken()
            var expresion1 = esExpresionAritmetica()
            if (expresion1 != null) {
                if (tokenActual.categoria == Categoria.OPERADOR_RELACIONAL) {
                    var operador = tokenActual
                    obtenerSiguienteToken()
                    var expresion2 = esExpresionAritmetica()
                    if (expresion2 != null) {
                        if (tokenActual.categoria == Categoria.PARENTESIS_DERECHO) {
                            obtenerSiguienteToken()
                            return ExpresionRelacional(expresion1, operador, expresion2)
                        } else {
                            reportarError("falta cerrar parentesis en expresion relacional")
                        }

                    } else {
                        reportarError(" falta una expresion aritmetica valida")
                    }
                }
                hacerBT(posicionInicial, fila, columna)
                return null
            }

        }
        else {
            var expresion1 = esExpresionAritmetica()
            if (expresion1 != null) {
                if (tokenActual.categoria == Categoria.OPERADOR_RELACIONAL) {
                    var operador = tokenActual
                    obtenerSiguienteToken()
                    var expresion2 = esExpresionAritmetica()
                    if (expresion2 != null) {
                        return ExpresionRelacional(expresion1, operador, expresion2)
                    } else {
                        reportarError(" falta una expresion aritmetica valida")
                    }
                }
                hacerBT(posicionInicial, fila, columna)
                return null
            }
            return null //RI
        }
        return null
    }

    /**
     * <ValorNumerico>:: [<Signo>] Real | [<Signo>] entero | [<Signo>] identificador
     */
    fun esValorNumerico(): ValorNumerico? {

        var signo: Token? = null
        if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO && tokenActual.lexema == "A-" || tokenActual.lexema == "A+") {
            signo = tokenActual
            obtenerSiguienteToken()
        }
        if (tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL || tokenActual.categoria == Categoria.IDENTIFICADOR) {
            var termino = tokenActual
            obtenerSiguienteToken()
            return ValorNumerico(signo, termino)
        }
        return null
    }

    /**
     * <Ciclo>:: _mientras "(" <ExpresionLogica> ")" "{" [<ListaSentencias>] "}"
     */
    fun esCiclo(): Ciclo? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_mientras") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {
                obtenerSiguienteToken()
                var expresionLogica = esExpresionLogica()
                if (expresionLogica != null) {
                    if (tokenActual.categoria == Categoria.PARENTESIS_DERECHO) {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.LLAVE_IZQUIERDA) {
                            obtenerSiguienteToken()
                            var listaSentencias: ArrayList<Sentencia>? = esListaSentencias()
                            if (tokenActual.categoria == Categoria.LLAVE_DERECHA) {
                                obtenerSiguienteToken()
                                return Ciclo(expresionLogica, listaSentencias)

                            } else {
                                reportarError("no se ecuentra la llave que cierra el bloque de sentencias en el ciclo")
                            }

                        } else {
                            reportarError("no se encuentra la llave que abre el bloque se sentencias en el ciclo ")
                        }

                    } else {
                        reportarError("no se encuentra el parentesis que sierra la expresion en el ciclo")
                    }
                } else {
                    reportarError("no se encontro una expresion logica en el ciclo")
                }

            } else {
                reportarError("no se encuentra el parentesis que abre la expresion en el ciclo")
            }
        }
        return null //RI
    }

    /**
     * <Asignacion>:: identificador operadorAsignacion <Expresion>
     */
    fun esAsignacion(): Asignacion? {


        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            var nombreAsignacion = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OPERADOR_ASIGNACION) {
                var operador = tokenActual
                obtenerSiguienteToken()
                var expresion = esExpresion()

                if (expresion != null) {
                    return Asignacion(nombreAsignacion, operador, expresion)
                } else {
                    reportarError("falta una expresion valida en una asignacion")
                }


            } else {
                reportarError("Falta operador de asignacion")
            }
        }
        return null
    }

    /**
     * <Impresion>:: _imprime ":" <Expresion>
     */
    fun esImpresion(): Impresion? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_imprime") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                obtenerSiguienteToken()
                var expresion = esExpresion()

                if (expresion != null) {
                    return Impresion(expresion)
                }
                reportarError("no se puede imprimir una expresion invalida")

            }
            reportarError("falta los : en la impresion")
        }
        return null
    }

    /**
     * <Lectura>:: _lea ":" identificador
     */
    fun esLectura(): Lectura? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_lea") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    var identificador = tokenActual
                    obtenerSiguienteToken()
                    return Lectura(identificador)
                }
                reportarError("falta el identificador en la lectura")
            }
            reportarError("falta los : en la lectura")
        }
        return null
    }

    /**
     * <InvocacionFuncion> _llamar identificador "(" [<ListaParameros>] ")"
     */
    fun esInvocacion(): Invocacion? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_llamar") {

            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var identificador = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {
                    obtenerSiguienteToken()
                    var listaParameetros = esLIstaParametro()

                    if (tokenActual.categoria == Categoria.PARENTESIS_DERECHO) {
                        obtenerSiguienteToken()
                        return Invocacion(identificador, listaParameetros)
                    } else {
                        reportarError("falta parentesis derecho en la invocacion")
                    }
                } else {
                    reportarError(" falta parentesis izquierdo en la invocaion ")
                }
            } else {
                reportarError("el identificador de la invocaion es invalido")
            }
        }
        return null
    }

    /**
     * <Decision> :: _si "(" <ExpresionLogica> ")" "{" [<ListaSentencias>] "}" [_demas "{" [<ListaSentencias>] "}"]
     */
    fun esDecision(): Decision? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_si") {

            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {

                obtenerSiguienteToken()

                var expresion: ExpresionLogica? = esExpresionLogica()

                if (expresion != null) {

                    if (tokenActual.categoria == Categoria.PARENTESIS_DERECHO) {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.LLAVE_IZQUIERDA) {
                            obtenerSiguienteToken()
                            var sentencias = esListaSentencias()

                            if (tokenActual.categoria == Categoria.LLAVE_DERECHA) {
                                obtenerSiguienteToken()

                                if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_demas") {
                                    obtenerSiguienteToken()
                                    if (tokenActual.categoria == Categoria.LLAVE_IZQUIERDA) {
                                        obtenerSiguienteToken()
                                        var sentencias_demas = esListaSentencias()
                                        if (tokenActual.categoria == Categoria.LLAVE_DERECHA) {
                                            obtenerSiguienteToken()
                                            return Decision(expresion, sentencias, sentencias_demas)

                                        } else {
                                            reportarError("no se encontro la llave que cierra  el _demas")
                                        }

                                    } else {
                                        reportarError("no se encontro la llave que abre el _demas")
                                    }

                                } else {
                                    return Decision(expresion, sentencias, null)
                                }

                            } else {
                                reportarError("falta cerrar la llave de la decicion _es")
                            }


                        } else {
                            reportarError("falta llave izquierda en decision")
                        }
                    } else {
                        reportarError("falta parentesis izquierdo en decision")
                    }
                } else {
                    reportarError("falta expresion logica valida en la decicion ")
                }
            } else {
                reportarError("falta parentesis izquierdo en la decision")
            }

        }
        return null
    }

    /**
     * <Conversio>:: _convertir identificador "(" <TipoDto> ")"
     */
    fun esConversion(): Conversion? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_convertir") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var identificador = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.PARENTESIS_IZQUIERDO) {
                    obtenerSiguienteToken()
                    var tipoDato = esTipoDato()

                    if (tipoDato != null) {
                        if (tokenActual.categoria == Categoria.PARENTESIS_DERECHO) {
                            obtenerSiguienteToken()
                            return Conversion(identificador, tipoDato)
                        } else {
                            reportarError("no se encontro el parentesis que cierra la conversion")
                        }
                    } else {
                        reportarError("no se encontro el tipo de dato en la conversion")
                    }
                } else {
                    reportarError("n se encontro parentesis en conversion")
                }
            } else {
                reportarError("identificador no valido en la conversion")
            }
        }

        return null

    }

    /**
     * <Retorno>:: _retorne expresion
     */
    fun esRetorno(): Retorno? {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_retorne") {
            obtenerSiguienteToken()

            var expresion = esExpresion()
            if (expresion != null) {
                return Retorno(expresion)

            } else {
                reportarError("no se encontro una expresion valida para retonar ")
            }
        }

        return null
    }

    /**
     * <IncrementoDecremento>::= ++ | --
     */
    fun esIncrementoDecremento(): IncrementoDecremento?{
        if (tokenActual.categoria == Categoria.INCREMENTO ||  tokenActual.categoria == Categoria.DECREMENTO){
            var inOde= tokenActual
            obtenerSiguienteToken()
           return IncrementoDecremento (inOde)
        }
        return null
    }

    /**
     * <DeclaraArreglo>::= _arr <TioDato> "[]" identificador
     */
    fun esDeclaracionArreglo (): DeclaracionArreglo? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_arr"){
            obtenerSiguienteToken()
            var tipoDato= esTipoDato()

            if (tipoDato != null){
                if (tokenActual.categoria == Categoria.CORCHETE_IZQUIERDO){
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.CORCHETE_DEREHO){
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.IDENTIFICADOR){
                            var identificador= tokenActual
                            obtenerSiguienteToken()
                            return DeclaracionArreglo(tipoDato, identificador)

                        }else{
                            reportarError("no se logra identificar el nombre al momento de declarar arreglo")
                        }
                    }else{
                        reportarError("no se encuentra corchete al declarar arreglo")
                    }
                }else {
                    reportarError("no se encuentra corchete al declara arreglo")
                }

            }else {
                reportarError("falta tipo de dato declarando arreglo")
            }
        }

        return null
    }


    /**
     * <InicializaArreglo>::= _inArr   identificador operadorAsignacion  <TipoDato>  "[" <ValorNumerico> "]"
     */
    fun esInicializarArreglo (): InicializarArreglo?{
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "_inArr"){
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR){
                var identificador  = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria== Categoria.OPERADOR_ASIGNACION){
                    var operador = tokenActual
                    obtenerSiguienteToken()
                    var tipD=esTipoDato()

                    if (tipD != null){
                        if (tokenActual.categoria== Categoria.CORCHETE_IZQUIERDO){
                            obtenerSiguienteToken()
                            var filas= esValorNumerico()
                            if (filas != null){
                                if(tokenActual.categoria== Categoria.CORCHETE_DEREHO){
                                    obtenerSiguienteToken()
                                    return InicializarArreglo(identificador,operador,tipD,filas)
                                }else {
                                    reportarError("no se identifica el corchete derecho al inicializar un arreglo")
                                }
                            }else {
                                reportarError("no se identifica la cantidad de columnas del arreglo")
                            }
                        }else{
                            reportarError("falta un corchete izquierdo al inicializar un arreglo")
                        }
                    }else {
                        reportarError("no se identifica tipo de dato al iniializr un arreglo")
                    }
                }else{
                    reportarError("falta un operador de asignacion al declarar un arreglo")
                }
            }else {
                reportarError("no se encuentra identificador al momento de inicializar arreglo")
            }
        }
        return null
    }




    private fun hacerBT(posicionInicial: Int, fila: Int, columna: Int) {

        posicionActual = posicionInicial
        tokenActual.fila = fila
        tokenActual.columna = columna

        tokenActual = listaTokens[posicionActual]

    }


}




