package co.edu.uniquindio.compilador.controlador

import co.edu.uniquindio.compilador.lexico.AnalizadorLexico
import co.edu.uniquindio.compilador.lexico.Error
import co.edu.uniquindio.compilador.lexico.Token
import co.edu.uniquindio.compilador.sintaxis.AnalizadorSintactico
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*


class InicioController : Initializable {


    @FXML lateinit var codigoFuente: TextArea
    @FXML lateinit var tablaTokens: TableView<Token>
    @FXML lateinit var columnaLexema: TableColumn<Token, String>
    @FXML lateinit var columnaCtegoria: TableColumn<Token, String>
    @FXML lateinit var columnaFila: TableColumn<Token, Int>
    @FXML lateinit var columnaColumna: TableColumn<Token, Int>

    @FXML lateinit var tablaErrores: TableView<Error>
    @FXML lateinit var mensajeError: TableColumn<Error, String>
    @FXML lateinit var filaError: TableColumn<Error, Int>
    @FXML  lateinit var columnaError: TableColumn<Error, Int>

    @FXML lateinit var  tablaErrorSintactico: TableView<Error>
    @FXML lateinit var columMensajeEsintactico: TableColumn<Error, String>
    @FXML lateinit var columFilaErrorSintactico: TableColumn<Error, Int>
    @FXML  lateinit var columColumnaErrorSintactico: TableColumn<Error, Int>




    @FXML
    lateinit var arbolVisual: TreeView<String>

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        columnaLexema.cellValueFactory = PropertyValueFactory("lexema")
        columnaCtegoria.cellValueFactory = PropertyValueFactory("categoria")
        columnaFila.cellValueFactory = PropertyValueFactory("fila")
        columnaColumna.cellValueFactory = PropertyValueFactory("columna")

        mensajeError.cellValueFactory = PropertyValueFactory("mensaje")
        filaError.cellValueFactory = PropertyValueFactory("fila")
        columnaError.cellValueFactory = PropertyValueFactory("columna")

        columMensajeEsintactico.cellValueFactory = PropertyValueFactory("mensaje")
        columFilaErrorSintactico.cellValueFactory = PropertyValueFactory("fila")
        columColumnaErrorSintactico.cellValueFactory = PropertyValueFactory("columna")

    }

    fun analizarCodigo() {


        if (codigoFuente.text.length > 0) {
            val lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()

            tablaTokens.items = FXCollections.observableArrayList(lexico.listaTokens)
            tablaErrores.items = FXCollections.observableArrayList(lexico.listaErrores)



                var sintaxis = AnalizadorSintactico(lexico.listaTokens)
                var uc = sintaxis.esUnidadDeCompilacion()

                tablaErrorSintactico.items = FXCollections.observableArrayList(sintaxis.listaDeErrores)

                if (uc != null) {
                    arbolVisual.root = uc.getArbolVisual()
                }



        }
    }


}