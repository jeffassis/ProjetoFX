package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.bean.ProfessorModel;
import model.dao.ProfessorDAO;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class ProfessorController implements Initializable {

    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_nome;
    @FXML
    private TextField txt_endereco;
    @FXML
    private TextField txt_cep;
    @FXML
    private TextField txt_telefone;
    @FXML
    private DatePicker dt_nascimento;

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
    private TableView<ProfessorModel> tabelaProfessor;
    @FXML
    private TableColumn<ProfessorModel, Integer> codigoColuna;
    @FXML
    private TableColumn<ProfessorModel, String> nomeColuna;

    private ProfessorModel professorModel;

    int flag = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.codigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.nomeColuna.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
    }

    /**
     * Método responsavel por carregar a tabela com a Thread Task.
     */
    public void carregarTabela() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return ProfessorDAO.executeQuery(null, ProfessorDAO.QUERY_TODOS);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabelaProfessor.setItems((ObservableList<ProfessorModel>) getValue());
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void onSave() {
        if (flag == 1) {

            // Verifica se o campo nome não esta vazio
            if (txt_nome.getText().length() == 0) {
                alert("O campo nome não pode ser vazio");
                return;
            }
            professorModel = new ProfessorModel();
            professorModel.setNome(txt_nome.getText().trim());
            professorModel.setEndereco(txt_endereco.getText().trim());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            professorModel.setNascimento(dt_nascimento.getValue().format(formatter));
            professorModel.setTelefone(txt_telefone.getText().trim());
            professorModel.setCep(txt_cep.getText().trim());
            if (ProfessorDAO.executeUpdates(professorModel, ProfessorDAO.CREATE)) {
                alert("Dados inseridos com sucesso!");
                carregarTabela();
                limparCampos();
                desabilitarCampos();
            } else {
                alert("Houve um erro ao inserir Dados");
            }
        } else {
            professorModel = new ProfessorModel();
            professorModel.setCodigo(Integer.parseInt(txt_codigo.getText().trim()));
            professorModel.setNome(txt_nome.getText().trim());
            professorModel.setEndereco(txt_endereco.getText().trim());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            professorModel.setNascimento(dt_nascimento.getValue().format(formatter));
            professorModel.setTelefone(txt_telefone.getText().trim());
            professorModel.setCep(txt_cep.getText().trim());
            if (ProfessorDAO.executeUpdates(professorModel, ProfessorDAO.UPDATE)) {
                alert("Dados alterados com sucesso!");
                carregarTabela();
                desabilitarCampos();
                limparCampos();
            } else {
                alert("Houve um erro ao atualizar Dados");
            }
        }
    }

    /**
     * Método para ação do botao editar
     */
    public void onEdit() {
        if (tabelaProfessor.getSelectionModel().getSelectedIndex() != -1) {
            //habilito o botão salvar e o texto
            this.bt_salvar.setDisable(false);
            habilitarCampos();
            this.professorModel = tabelaProfessor.getSelectionModel().getSelectedItem();
            txt_codigo.setText(Integer.toString(professorModel.getCodigo()));
            txt_nome.setText(professorModel.getNome());
            txt_endereco.setText(professorModel.getEndereco());
            txt_telefone.setText(professorModel.getTelefone());
            txt_cep.setText(professorModel.getCep());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(professorModel.getNascimento(), formatter);
            dt_nascimento.setValue(date);
            flag = 2;
            this.bt_editar.setDisable(true);
            this.bt_excluir.setDisable(true);
        } else {
            alert("Por favor selecione um aluno na Tabela");
        }
    }

    /**
     * Método para a ação do botao Excluir
     */
    public void onDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText("");
        alert.setContentText("Deseja excluir?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ProfessorModel professorModel = tabelaProfessor.getItems().get(tabelaProfessor.getSelectionModel().getSelectedIndex());
            if (ProfessorDAO.executeUpdates(professorModel, ProfessorDAO.DELETE)) {
                tabelaProfessor.getItems().remove(tabelaProfessor.getSelectionModel().getSelectedIndex());
                alert("Excluído com sucesso!");
                limparCampos();
                desabilitarCampos();
            }
        }
    }

    /**
     * Método ação do Botao Novo
     */
    @FXML
    public void onNew() {
        bt_novo.setDisable(false);
        bt_salvar.setDisable(false);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
        habilitarCampos();
        limparCampos();
        flag = 1;
    }

    /**
     * Método que desativa os botoes editar e excluir
     */
    @FXML
    public void onCancel() {
        // Desmarca qualquer registro que esteja selecionado na tabela 
        tabelaProfessor.getSelectionModel().clearSelection();

        desabilitarCampos();
        limparCampos();
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
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
        txt_nome.setText("");
        txt_endereco.setText("");
        txt_telefone.setText("");
        txt_cep.setText("");
        dt_nascimento.getEditor().setText("");
    }

    /**
     * Método para que seja acionado os botoes editar e excluir quando a tabela
     * for clickada
     */
    public void onClicked() {
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(false);
        bt_excluir.setDisable(false);
    }

    /**
     * Método para habilitar os campos
     */
    public void habilitarCampos() {
        txt_nome.setDisable(false);
        txt_endereco.setDisable(false);
        txt_telefone.setDisable(false);
        txt_cep.setDisable(false);
        dt_nascimento.setDisable(false);
        txt_nome.requestFocus();
    }

    /**
     * Método para Desabilitar os campos
     */
    public void desabilitarCampos() {
        txt_nome.setDisable(true);
        txt_endereco.setDisable(true);
        txt_telefone.setDisable(true);
        txt_cep.setDisable(true);
        dt_nascimento.setDisable(true);
        bt_novo.setDisable(false);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
        bt_salvar.setDisable(true);
    }
}
