package co.edu.uniquindio.compilador.app

import co.edu.uniquindio.compilador.lexico.AnalizadorLexico
//import javafx.application.Application
//import javafx.fxml.FXMLLoader
//import javafx.scene.Parent
//import javafx.scene.Scene
//import javafx.stage.Stage

/**
 * @author Leonardo Fabio Herrera o
 * @author John Alexander M
 *  @author Diego Armando jimenez
 *
 * Clase encargada de generar el ejecutable de la aplicacion
 */
//class Aplicacion : Application() {

    /**
     * funcion implementada de la clase application

    override fun start(p0: Stage?) {

        val loader = FXMLLoader(Aplicacion::class.java.getResource("/inicio.fxml"))
        val raiz: Parent = loader.load()
        val scene = Scene(raiz)

        p0?.scene = scene
        p0?.title = "compilador"
        p0?.show()

    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            launch(Aplicacion::class.java, *args)


        }
    }
    */

    fun main (){
        val lexico = AnalizadorLexico(codigoFuente = "erov/+nfm++kdjdj")
        lexico.analizar()
        print(lexico.listaTokens)
    }
//}