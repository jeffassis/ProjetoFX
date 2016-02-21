package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.bean.DisciplinaModel;
import model.dao.DisciplinaDAO;

/**
 * FXML Controller class
 *
 * @author jassis
 */
public class DisciplinaController implements Initializable {

    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_descricao;

    @FXML
    private Button bt_novo;
    @FXML
    private Button bt_salvar;
    @FXML
    private Button bt_editar;
    @FXML
    private Button bt_excluir;
    @FXML
    private Button bt_cancelar;

    @FXML
    private TableView<DisciplinaModel> tabelaDisciplina;
    @FXML
    private TableColumn<DisciplinaModel, Integer> codigoColuna;
    @FXML
    private TableColumn<DisciplinaModel, String> descricaoColuna;

    // criação da variavel de Class
    private DisciplinaModel disciplinaModel;

    int flag = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Carrega todos as colunas da tabela pegando os dados do BD
         */
        this.codigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.descricaoColuna.setCellValueFactory(cellData -> cellData.getValue().getDescricaoProperty());

        //bt_excluir.disableProperty().bind(tabelaDisciplina.getSelectionModel().selectedItemProperty().isNull());
        //bt_editar.disableProperty().bind(tabelaDisciplina.getSelectionModel().selectedItemProperty().isNull());
    }

    /**
     * Método responsavel por carregar a tabela com a Thread Task.
     */
    public void carregarTabela() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return DisciplinaDAO.executeQuery(null, DisciplinaDAO.QUERY_TODOS);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabelaDisciplina.setItems((ObservableList<DisciplinaModel>) getValue());
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Método para a ação do botão salvar no FXML
     */
    @FXML
    public void onSave() {
        // Se a flag for 1 salva os dados no banco de dados
        if (flag == 1) {
            // Verifica se a Disciplina nao esta vazia
            if (txt_descricao.getText().length() == 0) {
                alert("O campo descrição não pode ser vazio");
                return;
            }
            this.disciplinaModel = new DisciplinaModel();
            disciplinaModel.setDescricao(txt_descricao.getText().trim());
            if (DisciplinaDAO.executeUpdates(disciplinaModel, DisciplinaDAO.CREATE)) {
                alert("Dados inseridos com sucesso!");
                carregarTabela();
                limparCampos();
                posAcao();
            } else {
                alert("Houve um erro ao inserir dados");
            }
        } else {
            // se a flag for 2 edita os dados no Banco de dados
            this.disciplinaModel = new DisciplinaModel();
            disciplinaModel.setCodigo(Integer.parseInt(txt_codigo.getText().trim()));
            disciplinaModel.setDescricao(txt_descricao.getText().trim());
            if (DisciplinaDAO.executeUpdates(disciplinaModel, DisciplinaDAO.UPDATE)) {
                limparCampos();
                posAcao();
                alert("Dados editados com sucesso!");
                carregarTabela();
                flag = 1;
            } else {
                alert("Houve um erro ao editar dados");
            }
        }
    }

    /**
     * Método para a ação do botão Editar no FXML
     */
    @FXML
    public void onEdit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText("");
        alert.setContentText("Deseja excluir?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //verificamos se a tabela foi selecionada
            if (tabelaDisciplina.getSelectionModel().getSelectedIndex() != -1) {
                //habilito o botão salvar e o texto
                this.bt_salvar.setDisable(false);
                this.txt_descricao.setDisable(false);
                this.disciplinaModel = tabelaDisciplina.getSelectionModel().getSelectedItem();
                txt_codigo.setText(Integer.toString(disciplinaModel.getCodigo()));
                txt_descricao.setText(disciplinaModel.getDescricao());
                flag = 2;
            } else {
                alert("Por favor selecione um aluno na Tabela");
            }
        }
    }

    /**
     * Método para a ação do botão Excluir no FXML
     */
    @FXML
    public void onDelete() {
        DisciplinaModel disciplinaModel = tabelaDisciplina.getItems().get(tabelaDisciplina.getSelectionModel().getSelectedIndex());
        if (DisciplinaDAO.executeUpdates(disciplinaModel, DisciplinaDAO.DELETE)) {
            tabelaDisciplina.getItems().remove(tabelaDisciplina.getSelectionModel().getSelectedIndex());
            alert("Excluído com sucesso!");
            posAcao();
        } else {
            alert("Não foi possivel excluir");
        }
    }

    /**
     * Método para a ação do botao NovoF
     */
    @FXML
    public void onNew() {
        // Desmarca qualquer registro que esteja selecionado na tabela 
        tabelaDisciplina.getSelectionModel().clearSelection();

        bt_novo.setDisable(false);
        bt_salvar.setDisable(false);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
        bt_cancelar.setDisable(false);

        txt_descricao.setDisable(false);
        flag = 1;
    }

    /**
     * Método para a ação do botão cancelar
     */
    @FXML
    public void onCancel() {
        // Desmarca qualquer registro que esteja selecionado na tabela 
        tabelaDisciplina.getSelectionModel().clearSelection();

        limparCampos();
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
        txt_descricao.setDisable(true);
    }

    /**
     * Método responsavel por criar as janelas de Dialgo da Aplicação.
     *
     * @param msg
     */
    private void alert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Método criado para ser chamado depois das açoes para que ele possa limpar
     * os campos
     */
    private void limparCampos() {
        txt_codigo.setText("");
        txt_descricao.setText("");
    }

    /**
     * Método para ativar os botões editar e excluir
     */
    public void onClicked() {
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(false);
        bt_excluir.setDisable(false);
    }

    /**
     * Método para ser utilizado quando acontecer alguma ação nos botões
     */
    public void posAcao() {
        txt_descricao.setDisable(true);
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
    }

}
