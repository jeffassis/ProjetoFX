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
import model.bean.TurmaModel;
import model.dao.TurmaDAO;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class TurmaController implements Initializable {

    @FXML
    private TableView<TurmaModel> tabelaTurma;
    @FXML
    private TableColumn<TurmaModel, Integer> codColuna;
    @FXML
    private TableColumn<TurmaModel, String> descricaoColuna;
    @FXML
    private TableColumn<TurmaModel, String> horarioColuna;

    //private ObservableList<TurmaModel> listaTurma;
    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_descricao;
    @FXML
    private TextField txt_horario;

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

    // Criando a variavel da class
    private TurmaModel modelo;

    int flag = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //listaTurma = FXCollections.observableArrayList();
        // Preenche as colunas da tabela
        this.codColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.descricaoColuna.setCellValueFactory(cellData -> cellData.getValue().getDescricaoProperty());
        this.horarioColuna.setCellValueFactory(cellData -> cellData.getValue().getHorarioProperty());

        //this.tabelaTurma.setItems(TurmaDAO.executeQuery(null, TurmaDAO.QUERY_TODOS));
    }

    /**
     * Método para ação do botao salvar
     */
    @FXML
    public void onSave() {
        // Se a flag for 1 salva os dados no banco de dados
        if (flag == 1) {
            if (txt_descricao.getText().length() == 0) {
                alert("O campo descrição não pode ser vazio");
                return;
            }
            if (txt_horario.getText().length() == 0) {
                alert("O campo descrição não pode ser vazio");
                return;
            }
            this.modelo = new TurmaModel();
            modelo.setDescricao(txt_descricao.getText().trim());
            modelo.setHorario(txt_horario.getText().trim());
            if (TurmaDAO.executeUpdates(modelo, TurmaDAO.CREATE)) {
                limparCampos();
                alert("Dados inseridos com sucesso!");
                carregarTabela();
            } else {
                alert("Houve um erro ao inserir Dados");
            }
        } else {
            // se a flag for 2 edita os dados no Banco de dados
            this.modelo = new TurmaModel();
            modelo.setCodigo(Integer.parseInt(txt_codigo.getText().trim()));
            modelo.setDescricao(txt_descricao.getText().trim());
            modelo.setHorario(txt_horario.getText().trim());
            if (TurmaDAO.executeUpdates(modelo, TurmaDAO.UPDATE)) {
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
     * Metodo criado para acao do botao Excluir
     */
    @FXML
    public void onDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText("");
        alert.setContentText("Deseja excluir?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            /**
             * Pegamos dessa forma da tabela um Objeto de acordo com o Index que
             * foi selecionado na tabela isso é a vantagem do javaFX a Tabela já
             * pode retorna um Objeto
             */
            TurmaModel turmaModel = tabelaTurma.getItems().get(tabelaTurma.getSelectionModel().getSelectedIndex());
            /*Agora aqui usamos nosso DAO para excluir o Objeto*/
            if (TurmaDAO.executeUpdates(turmaModel, TurmaDAO.DELETE)) {
                /*Removemos da tabela o item excluido do BD*/
                tabelaTurma.getItems().remove(tabelaTurma.getSelectionModel().getSelectedIndex());
                alert("Excluido com sucesso!");
            } else {
                alert("Não foi possivel excluir");
            }
        }
    }

    /**
     * Método de ação do botão editar
     */
    @FXML
    public void onEdit() {
        if (tabelaTurma.getSelectionModel().getSelectedIndex() != -1) {
            //habilito o botão salvar e o texto
            this.bt_salvar.setDisable(false);
            this.txt_descricao.setDisable(false);
            this.txt_horario.setDisable(false);
            this.bt_editar.setDisable(true);
            this.bt_excluir.setDisable(true);
            this.modelo = tabelaTurma.getSelectionModel().getSelectedItem();
            txt_codigo.setText(Integer.toString(modelo.getCodigo()));
            txt_descricao.setText(modelo.getDescricao());
            txt_horario.setText(modelo.getHorario());
            flag = 2;
        } else {
            alert("Por favor selecione um aluno na Tabela");
        }
    }

    /**
     * Método de ação do botão Novo
     */
    @FXML
    public void onNew() {
        bt_novo.setDisable(false);
        bt_salvar.setDisable(false);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);

        txt_descricao.setDisable(false);
        txt_horario.setDisable(false);
    }

    /**
     * Método de ação do botão Cancelar.
     */
    @FXML
    public void onCancel() {
        // Desmarca qualquer registro que esteja selecionado na tabela 
        tabelaTurma.getSelectionModel().clearSelection();

        limparCampos();
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
        txt_descricao.setDisable(true);
        txt_horario.setDisable(true);
    }

    /**
     * Método que cria as Janelas de Dialog com Informação para usuario
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
     * Método que limpa os campos apos ações.
     */
    public void limparCampos() {
        txt_codigo.setText("");
        txt_descricao.setText("");
        txt_horario.setText("");
    }

    /**
     * Método para executar ação nos botões salvar / editar / excluir
     */
    public void posAcao() {
        txt_descricao.setDisable(true);
        txt_horario.setDisable(true);
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
    }

    /**
     * Método para que seja acionado os botoes editar e excluir quando a tabela
     * for clickada
     */
    public void mouseTable() {
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(false);
        bt_excluir.setDisable(false);

        txt_descricao.setDisable(true);
        txt_horario.setDisable(true);
    }

    /**
     * Método criado para carregar a TableView usando a Thread
     */
    public void carregarTabela() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return TurmaDAO.executeQuery(null, TurmaDAO.QUERY_TODOS);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabelaTurma.setItems((ObservableList<TurmaModel>) getValue());
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
