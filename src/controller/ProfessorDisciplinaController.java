package controller;

import Util.ConverterDados;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.bean.DisciplinaModel;
import model.bean.ProfessorDisciplinaModel;
import model.bean.ProfessorModel;
import model.dao.DisciplinaDAO;
import model.dao.ProfessorDAO;
import model.dao.ProfessorDisciplinaDAO;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class ProfessorDisciplinaController implements Initializable {

    @FXML
    private Button bt_incluir;
    @FXML
    private Button bt_excluir;

    @FXML
    private ListView<ProfessorDisciplinaModel> lista;
    @FXML
    private ComboBox<ProfessorModel> cb_professor;
    @FXML
    private ComboBox<DisciplinaModel> cb_disciplina;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.lista.setCellFactory(new Callback<ListView<ProfessorDisciplinaModel>, ListCell<ProfessorDisciplinaModel>>() {

            @Override
            public ListCell<ProfessorDisciplinaModel> call(ListView<ProfessorDisciplinaModel> param) {
                ListCell<ProfessorDisciplinaModel> cell = new ListCell<ProfessorDisciplinaModel>() {
                    @Override
                    protected void updateItem(ProfessorDisciplinaModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getDisciplinaModel().getDescricao());
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
        this.cb_disciplina.setConverter(new ConverterDados(ConverterDados.GET_DISCIPLINA_DESCRICAO).getDisciplinaConverter());
        this.cb_professor.setConverter(new ConverterDados(ConverterDados.GET_PROFESSOR_NOME).getProfessorConverter());
    }

    public void iniciarProcessos() {
        /*para evitamos uma exceção de Thread temos que limpar a lista antes, pq ao abrir a tela a segunda vez
        ele acaba chamando o evento do combobox.*/
        cb_disciplina.getItems().clear();
        cb_professor.getItems().clear();
        lista.getItems().clear();
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                cb_disciplina.setItems(DisciplinaDAO.executeQuery(null, DisciplinaDAO.QUERY_TODOS));
                cb_professor.setItems(ProfessorDAO.executeQuery(null, ProfessorDAO.QUERY_TODOS));
                return null;
            }
        };
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void cbProfessor() {

        ProfessorModel professor = cb_professor.getSelectionModel().getSelectedItem();

        ProfessorDisciplinaModel pD = new ProfessorDisciplinaModel(null, professor);

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return ProfessorDisciplinaDAO.executeQuery(pD, ProfessorDisciplinaDAO.QUERY_PROFESSOR);
            }

            @Override
            protected void succeeded() {
                lista.setItems((ObservableList<ProfessorDisciplinaModel>) getValue());
            }
        };
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void onIncluir() {
        /*Verificamos se foi selecionado o Professor e a Disciplina.*/
        if (this.cb_disciplina.getSelectionModel().getSelectedIndex() != -1 && this.cb_professor.getSelectionModel().getSelectedIndex() != -1) {
            /*Nossa Classe que representa todos os dados juntos Professor e Disciplina*/
            ProfessorDisciplinaModel pD = new ProfessorDisciplinaModel();
            /*Pegamos o disciplina selecionada*/
            DisciplinaModel disciplina = cb_disciplina.getSelectionModel().getSelectedItem();
            /*Pegamos o professor selecionado*/
            ProfessorModel professor = cb_professor.getSelectionModel().getSelectedItem();

            boolean jaExisteDisciplina = false;
            for (int i = 0; i < lista.getItems().size(); i++) {
                if (lista.getItems().get(i).getDisciplinaModel().getCodigo() == disciplina.getCodigo()) {
                    jaExisteDisciplina = true;
                    break;
                }
            }
            if (!jaExisteDisciplina) {
                pD.setDisciplinaModel(disciplina);
                pD.setProfessorModel(professor);
                if (ProfessorDisciplinaDAO.executeUpdates(pD, ProfessorDisciplinaDAO.CREATE)) {

                    if (lista.getItems().get(0).getDisciplinaModel().getDescricao() == null) {
                        lista.getItems().remove(0);
                    }
                    lista.getItems().add(pD);
                } else {
                    alert("Houve um erro ao incluir a disciplina no professor", Alert.AlertType.ERROR);
                }
            } else {
                alert("Esta disciplina já pertence a um professor.", Alert.AlertType.WARNING);
            }

        } else {
            alert("Por favor verifique se você selecionou um Professor, ou a Disciplina.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void onExcluir() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText("");
        alert.setContentText("Deseja excluir Disciplina?");
        ButtonType btnSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        /*no construtor passamos no segundo paramentro o efeito que este botão tera
         no Dialog q no caso ele cancelar e fechar.*/
        ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
        /*usamos o getButtonType para retornar os botões que o alert dialog possui e usamos
         o metodo setAll para trocar todos eles por este dois que nós criamos*/
        alert.getButtonTypes().setAll(btnSim, btnNao);
        Optional<ButtonType> result = alert.showAndWait();
        /*como criamos o nosso proprio botão, podemos então comparar qual botão foi clicado já que o result.get
         retorna o botão clicado.*/
        if (result.get() == btnSim) {
            ProfessorDisciplinaModel professorDisciplinaModel = lista.getItems().get(lista.getSelectionModel().getSelectedIndex());
            if (ProfessorDisciplinaDAO.executeUpdates(professorDisciplinaModel, ProfessorDisciplinaDAO.DELETE)) {
                lista.getItems().remove(lista.getSelectionModel().getSelectedIndex());
                alert("Excluido com sucesso!", Alert.AlertType.INFORMATION);
            } else {
                alert("Não foi possivel excluir", Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * Método que cria as Janelas de Dialog com Informação para usuario
     *
     * @param msg
     * @param type
     */
    private void alert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Mensagem");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
