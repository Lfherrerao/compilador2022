package co.edu.uniquindio.compilador

import co.edu.uniquindio.compilador.lexico.AnalizadorLexico
import co.edu.uniquindio.compilador.sintaxis.AnalizadorSintactico

fun main ()  {


    var lexico= AnalizadorLexico("_inicio _fun &abe :() { _si (_EL _falso ){ } _demas { } }  _fin")
    lexico.analizar()



  val sintaxis=AnalizadorSintactico(lexico.listaTokens)
    println(sintaxis.esUnidadDeCompilacion())
    println("ERRORES:  "+ sintaxis.listaDeErrores)
}

//"_inicio _fun &abe  : _cadena ( _entero &l1 , _decimal &leo ) { }  _fin"
// _inicio  _var _entero &variable1  S= (E23 A* A-D56.)  _fin"
// _var _entero &variable1  S= E23  R>=  A-D56.
// "_inicio _fun &abe  : _cadena ( _entero &l1 , _decimal &leo ) { _mientras ( _falso R>= _verdad ){ _mientras ( _falso R>= _verdad ){ } } }  _fin"
// "_inicio _fun &abe :() { _convertir &leo ( _entero ) }  _fin"


/**
_inicio

_fun &prueba : ( ) {

_mientras ( _EL _falso ){
&leo S= _EA D34.
}
_convertir &kilo (  _decimal )

&leo S= _EA D34.

}
_fin
 */
//---------------------------------------------------------------------------------------------

/**

_inicio

_fun  &pru  : _entero ( _entero &p1 , _decimal &p2 ){



_var _entero &var1  S= _EC " hola desde aplicacion"  .

}

_fin
 */
//______________________________________________________________________________________________

/**
_inicio

_fun  &pru  : _entero ( _entero &p1 , _decimal &p2 ){

&p1 S=* _EA E12

_var _entero &var1  S= _EA A-E45

_si (_EL _falso ) { } _demas { }


_mientras ( _EL _falso ){

&p1 S=* _EA E12
}

_imprime : _EA &leo

_llamar &p2 ( _entero &p1 )

_lea :  &p2

_retorne  _EA E34

}

_fin
 */

//---------------------------------------------------------

/**
_inicio

_fun &pEc : ( ) {

_var _entero &cade S=

}

_fin
 */

//___________________________________________________

        /**
         * _inicio

        _var _entero &hh S= (  ( E4 R< E6 ) Y  ( E4 R< E6 )  )

        _fin
         **/