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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.bean.AlunoModel;
import model.bean.ClasseModel;
import model.bean.TurmaModel;
import model.dao.AlunoDAO;
import model.dao.ClasseDAO;
import model.dao.TurmaDAO;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class AlunoTurmaController implements Initializable {

    @FXML
    private ListView<AlunoModel> listAluno;
    /*Colocamos do tipo ClasseModel para vamos adicionar
     dados nelas que é a junção de duas ou mais tabelas, então
     preferi usar a Classe que represente todos juntos*/
    @FXML
    private ListView<ClasseModel> listTurma;
    @FXML
    private Button bt_incluir;
    @FXML
    private Button bt_excluir;
    @FXML
    private Button bt_pesquisar;
    @FXML
    private ComboBox<TurmaModel> cb_turma;
    @FXML
    private TextField txt_pesquisa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Precisamos informar qual dados vai preencher da listView, assim como fazemos
         assim como fazemos na TableView, mas na ListView é diferente a forma que iremos
         informar qual dados vai popular os campos, no TableView informamos qual dado vai
         popular a Coluna da nossa TableView, no caso da ListView temos que informar qual
         dado vai popular cada linha(celula) da lista veja*/
        this.listAluno.setCellFactory(new Callback<ListView<AlunoModel>, ListCell<AlunoModel>>() {

            @Override
            public ListCell<AlunoModel> call(ListView<AlunoModel> param) {
                ListCell<AlunoModel> cell = new ListCell<AlunoModel>() {

                    @Override
                    protected void updateItem(AlunoModel item, boolean empty) {
                        super.updateItem(item, empty);
                        /*não podemos esquecer de verificar se o item é null, pq esse método e chamado
                         varias vezes, mesmo não tendo muitos itensF*/
                        if (item != null) {
                            /*utilizamos o método setText da Classe ListCell, pois é ele
                             que alterar o texto que é exibido na listView*/
                            setText(item.getNome());
                            /*Veja a cima que no setText usamos o objeto item que é do tipo AlunoModel
                             para alterar o dado que é exibido na tela, ou seja queremos que mostre o nome
                             do aluno*/
                        } else {
                            setText(null);
                        }
                    }
                };
                /*retornamos a Nossa celula pronta da forma que queremos que seja*/
                return cell;
            }
        });
        /*Mesma coisa para a outra listView*/
        this.listTurma.setCellFactory(new Callback<ListView<ClasseModel>, ListCell<ClasseModel>>() {

            @Override
            public ListCell<ClasseModel> call(ListView<ClasseModel> param) {
                ListCell<ClasseModel> cell = new ListCell<ClasseModel>() {

                    @Override
                    protected void updateItem(ClasseModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            /*Iremos exibir apenas o nome do aluno que vai estar
                             na lista*/
                            setText(item.getAlunoModel().getNome());
                            System.out.println(item.getAlunoModel().getNome());

                        } else {
                            /*Com isso aqui remove aquele BUG q eu te falei, pelo oq eu vi
                             ele alterar o texto para null quando não há item para popular*/
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
        /*Utilizando a nossa Classe converter mas agora para popular com os dados da TurmaModel*/
        this.cb_turma.setConverter(new ConverterDados(ConverterDados.GET_DESCRICAO).getTurmaConverter());
    }

    /*Executa as funções iniciais como preencher a listView com os alunos e o comboBox com as turmas
     utilizando o Task já que pode ser um processo pesado.*/
    public void iniciarProcessos() {
        /*para evitamos uma exceção de Thread temos que limpar a lista antes, pq ao abrir a tela a segunda vez
        ele acaba chamando o evento do combobox.*/
        cb_turma.getItems().clear();
        listAluno.getItems().clear();
        listTurma.getItems().clear();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                listAluno.setItems(AlunoDAO.executeQuery(null, AlunoDAO.QUERY_TODOS));
                cb_turma.setItems(TurmaDAO.executeQuery(null, TurmaDAO.QUERY_TODOS));
                return null;
            }
        };
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
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

    //Ações dos botões.
    @FXML
    private void btnIncluirAction() {
        /*Verificamos se foi selecionado o Aluno e a Turma.*/
        if (this.listAluno.getSelectionModel().getSelectedIndex() != -1 && this.cb_turma.getSelectionModel().getSelectedIndex() != -1) {
            /*Nossa Classe que representa todos os dados juntos Aluno e Turma*/
            ClasseModel cm = new ClasseModel();
            /*Pegamos o aluno selecionado*/
            AlunoModel aluno = listAluno.getSelectionModel().getSelectedItem();
            /*Pegamos a turma selecionada*/
            TurmaModel turma = cb_turma.getSelectionModel().getSelectedItem();
            /*Variavel que vamos usar para verificar se já existe aluno naquela turma*/
            boolean jaExisteAluno = false;
            for (int i = 0; i < listTurma.getItems().size(); i++) {
                /*Verificamos se já tem um aluno iqual naquela turma*/
                if (listTurma.getItems().get(i).getAlunoModel().getCodigo() == aluno.getCodigo()) {
                    jaExisteAluno = true;
                    break;
                }
            }
            /*se não houve um aluno iqual na mesma turma então add ele*/
            if (!jaExisteAluno) {
                /*Adicionamos a nossa Classe que representa eles juntos*/
                cm.setAlunoModel(aluno);
                cm.setTurmaModel(turma);
                if (ClasseDAO.executeUpdates(cm, ClasseDAO.EDITAR_TURMA)) {
                    /*Para evitar outro bug temos q verificar se a primeira linha tem o nome
                    null, se tiver nos removemos, isso acontencia quando eu adicionava
                    um aluna naquela turma e ele era adicionado na segunda linha e não
                    na primeira*/
                    if (listTurma.getItems().get(0).getAlunoModel().getNome() == null) {
                        listTurma.getItems().remove(0);
                    }
                    listTurma.getItems().add(cm);
                } else {
                    alert("Houve um erro ao incluir o aluno a turma", Alert.AlertType.ERROR);
                }
            } else {
                alert("Este aluno já pertence a está turma.", Alert.AlertType.WARNING);
            }
        } else {
            alert("Por favor verifique se você selecionou um aluno, ou a Turma.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void cbTurmaAction() {
        /*Pegamos a turma q foi selecionada*/
        TurmaModel turma = cb_turma.getSelectionModel().getSelectedItem();
        /*Adicionamos nossa Turma a Nossa ClasseModel para a pesquisa*/
        ClasseModel cm = new ClasseModel(null, turma);
        /*Podera ser um trabalho pesado, então vamos usar o Task novamente*/
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                /*Mesma coisa que fizemos para a tabela*/
                return ClasseDAO.executeQuery(cm, ClasseDAO.QUERY_ALUNO);
            }

            @Override
            protected void succeeded() {
                listTurma.setItems((ObservableList<ClasseModel>) getValue());
            }
        };
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void onExcluir() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText("");
        alert.setContentText("Deseja excluir Aluno?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ClasseModel classeModel = listTurma.getItems().get(listTurma.getSelectionModel().getSelectedIndex());
            if (ClasseDAO.executeUpdates(classeModel, ClasseDAO.DELETE)) {
                listTurma.getItems().remove(listTurma.getSelectionModel().getSelectedIndex());
                alert("Excluido com sucesso!", Alert.AlertType.INFORMATION);
            } else {
                alert("Não foi possivel excluir", Alert.AlertType.ERROR);
            }
        }
    }
}
