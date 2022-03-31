package co.edu.uniquindio.compilador.controlador

import co.edu.uniquindio.compilador.lexico.AnalizadorLexico
import co.edu.uniquindio.compilador.lexico.Token
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*


class InicioController:Initializable {


    @FXML lateinit var codigoFuente:TextArea
    @FXML lateinit var tablaTokens:TableView<Token>
    @FXML lateinit var columnaLexema:TableColumn<Token,String>
    @FXML lateinit var columnaCtegoria:TableColumn<Token,String>
    @FXML lateinit var columnaFila:TableColumn<Token,Int>
    @FXML lateinit var columnaColumna:TableColumn<Token,Int>

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        columnaLexema.cellValueFactory=PropertyValueFactory("lexema")
        columnaCtegoria.cellValueFactory=PropertyValueFactory("categoria")
        columnaFila.cellValueFactory=PropertyValueFactory("fila")
        columnaColumna.cellValueFactory=PropertyValueFactory("columna")
    }

    fun analizarCodigo() {


        if (codigoFuente.text.length > 0) {
            val lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()


            tablaTokens.items=FXCollections.observableArrayList(lexico.listaTokens)

        }
    }


}