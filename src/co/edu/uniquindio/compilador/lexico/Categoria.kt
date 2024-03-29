package co.edu.uniquindio.compilador.lexico


/**
 * @author leonardo Fabio Herrera o.
 *@author John Alexander Martinez N.
 *
 * Enumeraciones que hacen referencia a las categorias a laas cuales pertenecen los tokens.
 */
enum class Categoria {
    ENTERO, DECIMAL, IDENTIFICADOR, OPERADOR_ARITMETICO, PERADOR_LOGICO, PARENTESIS_IZQUIERDO, PARENTESIS_DERECHO,
    LLAVE_IZQUIERDA, LLAVE_DERECHA, OPERADOR_RELACIONAL, DESCONOCIDO, OPERADOR_ASIGNACION, ES_COMA, CORCHETE_IZQUIERDO, CORCHETE_DEREHO, FIN_SENTENCIA,
    OPERADOR_LOGICO,INCREMENTO, DECREMENTO, PUNTO, DOS_PUNTOS,CADENA,COMENTARIO_DE_LINEA,COMENTARIO_DE_BLOQUE,REPORTE_DE_ERROR,PALABRA_RESERVADA,ES_SEPARADOR, CARACTER
}