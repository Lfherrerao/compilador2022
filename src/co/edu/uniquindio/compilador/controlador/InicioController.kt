package co.edu.uniquindio.compilador.controlador

import co.edu.uniquindio.compilador.lexico.AnalizadorLexico
import javafx.fxml.FXML
import javafx.scene.control.TextArea


class InicioController {


    @FXML lateinit var codigoFuente:TextArea

    fun analizarCodigo() {


        if (codigoFuente.text.length > 0) {
            val lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()
            print(lexico.listaTokens)

        }
    }
}