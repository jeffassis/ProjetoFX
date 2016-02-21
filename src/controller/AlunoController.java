package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.bean.AlunoModel;
import model.dao.AlunoDAO;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class AlunoController implements Initializable {

    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_nome;
    @FXML
    private TextField txt_endereco;
    @FXML
    private TextField txt_cep;
    @FXML
    private TextField txt_mae;
    @FXML
    private TextField txt_pai;
    @FXML
    private TextField txt_telefone;
    @FXML
    private TextField txt_ensino;
    @FXML
    private Button bt_limpar;
    @FXML
    private Button bt_salvar;
    @FXML
    private Button bt_editar;
    @FXML
    private Button bt_excluir;
    @FXML
    private Button bt_novo;

    //Preenchendo DatePicker
    @FXML
    private DatePicker dataAluno;

    // Preenchendo o comboBox Manualmente
    @FXML
    private ComboBox cb_sexo;
    ObservableList<String> listSexo = FXCollections.observableArrayList("Masculino", "Feminino");
    // Preenchendo o comboBox Manualmente
    @FXML
    private ComboBox cb_uf;
    ObservableList<String> listUf = FXCollections.observableArrayList("RJ", "SP", "MG", "RS", "BA", "AM", "AC");
    // Preenchendo o comboBox Manualmente
    @FXML
    private ComboBox cb_serie;
    ObservableList<String> listSerie = FXCollections.observableArrayList("1º Ano", "2º Ano", "3º Ano", "4º Ano", "5º Ano", "6º Ano", "7º Ano", "8º Ano", "9º Ano");

    @FXML
    private TableView<AlunoModel> tabelaAluno;
    @FXML
    private TableColumn<AlunoModel, Integer> codAluno;
    @FXML
    private TableColumn<AlunoModel, String> nomeAluno;
    @FXML
    private TableColumn<AlunoModel, String> sexoAluno;
    @FXML
    private TableColumn<AlunoModel, String> enderecoAluno;
    @FXML
    private TableColumn<AlunoModel, String> cepAluno;
    @FXML
    private TableColumn<AlunoModel, String> nascimentoAluno;
    @FXML
    private TableColumn<AlunoModel, String> ufAluno;
    @FXML
    private TableColumn<AlunoModel, String> maeAluno;
    @FXML
    private TableColumn<AlunoModel, String> paiAluno;
    @FXML
    private TableColumn<AlunoModel, String> telefoneAluno;
    @FXML
    private TableColumn<AlunoModel, String> serieAluno;
    @FXML
    private TableColumn<AlunoModel, String> ensinoAluno;

    // Criando a variavel da Class Model
    private AlunoModel modelo;

    int flag = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Preenche o comboBox sexo
        cb_sexo.setItems(listSexo);
        cb_uf.setItems(listUf);
        cb_serie.setItems(listSerie);

//        // Preenche o comboBox UF
//        this.cb_uf.setConverter(new ConverterDados(ConverterDados.GET_UF));
//        this.cb_uf.setItems(AlunoDAO.executeQuery(null, AlunoDAO.QUERY_TODOS));
//
//        //Preenche o comboBox Serie
//        this.cb_serie.setConverter(new ConverterDados(ConverterDados.GET_SERIE));
//        this.cb_serie.setItems(AlunoDAO.executeQuery(null, AlunoDAO.QUERY_TODOS));
        this.codAluno.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.nomeAluno.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        this.sexoAluno.setCellValueFactory(cellData -> cellData.getValue().getSexoProperty());
        this.enderecoAluno.setCellValueFactory(cellData -> cellData.getValue().getEnderecoProperty());
        this.cepAluno.setCellValueFactory(cellData -> cellData.getValue().getCepProperty());
        this.nascimentoAluno.setCellValueFactory(cellData -> cellData.getValue().getNascimentoProperty());
        this.ufAluno.setCellValueFactory(cellData -> cellData.getValue().getUfProperty());
        this.maeAluno.setCellValueFactory(cellData -> cellData.getValue().getMaeProperty());
        this.paiAluno.setCellValueFactory(cellData -> cellData.getValue().getPaiProperty());
        this.telefoneAluno.setCellValueFactory(cellData -> cellData.getValue().getTelefoneProperty());
        this.serieAluno.setCellValueFactory(cellData -> cellData.getValue().getSerieProperty());
        this.ensinoAluno.setCellValueFactory(cellData -> cellData.getValue().getEnsinoProperty());

        //bt_excluir.disableProperty().bind(tabelaAluno.getSelectionModel().selectedItemProperty().isNull());
        //bt_editar.disableProperty().bind(tabelaAluno.getSelectionModel().selectedItemProperty().isNull());
    }

    /**
     * Método para acao do botao salvar
     */
    @FXML
    public void onSave() {
        if (flag == 1) {
            if (txt_nome.getText().length() == 0) {
                alert("O campo nome não pode ser vazio");
                return;
            }
            this.modelo = new AlunoModel();
            modelo.setNome(txt_nome.getText().trim());
            modelo.setSexo((String) cb_sexo.getSelectionModel().getSelectedItem());
            modelo.setEndereco(txt_endereco.getText().trim());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            modelo.setNascimento(dataAluno.getValue().format(formatter));
            modelo.setMae(txt_mae.getText().trim());
            modelo.setPai(txt_pai.getText().trim());
            modelo.setUf((String) cb_uf.getSelectionModel().getSelectedItem());
            modelo.setSerie((String) cb_serie.getSelectionModel().getSelectedItem());

            //modelo.setSerie(cb_serie.getSelectionModel().getSelectedItem().getSerie());
            modelo.setTelefone(txt_telefone.getText().trim());
            //modelo.setUf(cb_uf.getSelectionModel().getSelectedItem().getUf());
            modelo.setCep(txt_cep.getText().trim());
            modelo.setEnsino(txt_ensino.getText().trim());
            if (AlunoDAO.executeUpdates(modelo, AlunoDAO.CREATE)) {
                limparCampos();
                alert("Dados inseridos com sucesso!");
                carregarTabela();
                desabilitarCampos();
            } else {
                alert("Houve um erro ao inserir Dados");
            }
        } else {
            // se a flag for 2 edita os dados no Banco de dados
            this.modelo = new AlunoModel();
            //é bom criar um método verificar campos :)
            modelo.setCodigo(Integer.parseInt(txt_codigo.getText().trim()));
            modelo.setNome(txt_nome.getText().trim());
            modelo.setCep(txt_cep.getText().trim());
            modelo.setEndereco(txt_endereco.getText());
            modelo.setEnsino(txt_ensino.getText().trim());
            modelo.setPai(txt_pai.getText().trim());
            modelo.setMae(txt_mae.getText().trim());
            modelo.setTelefone(txt_telefone.getText().trim());
            modelo.setSerie((String) cb_serie.getSelectionModel().getSelectedItem());
            modelo.setUf((String) cb_uf.getSelectionModel().getSelectedItem());
            modelo.setSexo((String) cb_sexo.getSelectionModel().getSelectedItem());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            modelo.setNascimento(dataAluno.getValue().format(formatter));
            if (AlunoDAO.executeUpdates(modelo, AlunoDAO.UPDATE)) {
                limparCampos();
                alert("Dados alterados com sucesso!");
                carregarTabela();
                flag = 1;
                desabilitarCampos();
            } else {
                alert("Não foi possivel alterar dados");
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
            AlunoModel alunoModel = tabelaAluno.getItems().get(tabelaAluno.getSelectionModel().getSelectedIndex());

            if (AlunoDAO.executeUpdates(alunoModel, AlunoDAO.DELETE)) {
                tabelaAluno.getItems().remove(tabelaAluno.getSelectionModel().getSelectedIndex());
                alert("Excluido com sucesso!");
                desabilitarCampos();
            } else {
                alert("Não foi possivel excluir");
            }
        }
    }

    /**
     * Método para ação do botão editar
     *
     */
    @FXML
    public void onEdit() {
        //verificamos se a tabela foi selecionada
        if (tabelaAluno.getSelectionModel().getSelectedIndex() != -1) {
            //habilito o botão salvar e desabilita o editar e o excluir
            this.bt_salvar.setDisable(false);
            AlunoModel alunoModel = tabelaAluno.getSelectionModel().getSelectedItem();
            txt_nome.setText(alunoModel.getNome());
            txt_mae.setText(alunoModel.getMae());
            txt_cep.setText(alunoModel.getCep());
            txt_codigo.setText(Integer.toString(alunoModel.getCodigo()));
            txt_endereco.setText(alunoModel.getEndereco());
            txt_ensino.setText(alunoModel.getEnsino());
            txt_telefone.setText(alunoModel.getTelefone());
            txt_pai.setText(alunoModel.getPai());
            /*Usamos a Classe DateTimeFormatter para dizer qual é o formato de Data,
             como nosso getNascimento retornar assim 10/11/2015 então tem que ser
             neste formado ai, se retorna-se assim 10-11-2015 teriamos que usar assim
             dd-MM-yyyy*/
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            /*Passamos no método parse a String contendo a Data e o formato*/
            LocalDate date = LocalDate.parse(alunoModel.getNascimento(), formato);
            dataAluno.setValue(date);
            /*Utilizamos o for para descobrir qual posição é igual ao dado retornado
             de alunoModel veja*/
            for (int i = 0; i < cb_sexo.getItems().size(); i++) {
                if (((String) cb_sexo.getItems().get(i)).equals(alunoModel.getSexo())) {
                    cb_sexo.getSelectionModel().select(i);
                    //agora q ja achamos paramos o for
                    break;
                }
            }
            for (int i = 0; i < cb_serie.getItems().size(); i++) {
                if (((String) cb_serie.getItems().get(i)).equals(alunoModel.getSerie())) {
                    cb_serie.getSelectionModel().select(i);
                    break;
                }
            }
            for (int i = 0; i < cb_uf.getItems().size(); i++) {
                if (((String) cb_uf.getItems().get(i)).equals(alunoModel.getUf())) {
                    cb_uf.getSelectionModel().select(i);
                    break;
                }
            }
            flag = 2;
            habilitarCampos();
            bt_editar.setDisable(true);
            bt_excluir.setDisable(true);
        } else {
            alert("Por favor selecione um aluno na Tabela");
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
        txt_nome.requestFocus();
    }

    /**
     * Método que desativa os botoes editar e excluir
     */
    @FXML
    public void onCancel() {
        // Desmarca qualquer registro que esteja selecionado na tabela 
        tabelaAluno.getSelectionModel().clearSelection();

        desabilitarCampos();
        limparCampos();
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);

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
     * Método que limpa os campos
     */
    public void limparCampos() {
        txt_codigo.setText("");
        txt_nome.setText("");
        txt_endereco.setText("");
        txt_mae.setText("");
        txt_pai.setText("");
        txt_telefone.setText("");
        txt_cep.setText("");
        txt_ensino.setText("");
        cb_serie.getSelectionModel().clearSelection();
        cb_sexo.getSelectionModel().clearSelection();
        cb_uf.getSelectionModel().clearSelection();
        dataAluno.getEditor().setText("");
    }

    /**
     * Método responsavel por habilitar os campos
     */
    public void habilitarCampos() {
        txt_nome.setDisable(false);
        txt_endereco.setDisable(false);
        txt_cep.setDisable(false);
        txt_ensino.setDisable(false);
        txt_mae.setDisable(false);
        txt_pai.setDisable(false);
        txt_telefone.setDisable(false);
        dataAluno.setDisable(false);
        cb_serie.setDisable(false);
        cb_sexo.setDisable(false);
        cb_uf.setDisable(false);
    }

    /**
     * Método responsavel por desabilitar os campos e botôes
     */
    public void desabilitarCampos() {
        txt_nome.setDisable(true);
        txt_endereco.setDisable(true);
        txt_cep.setDisable(true);
        txt_ensino.setDisable(true);
        txt_mae.setDisable(true);
        txt_pai.setDisable(true);
        txt_telefone.setDisable(true);
        dataAluno.setDisable(true);
        cb_serie.setDisable(true);
        cb_sexo.setDisable(true);
        cb_uf.setDisable(true);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
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

    /*Este método será executado em segunda plano para que a interface principal não
     fique esperando que os dados sejam carregados primeiro para depois exibir a tela*/
    public void carregarTabela() {
        /*Iremos utilizar a Classe Task cujo foi feita para trabalhar
         com javaFX, ela é como uma Thread que será executada fora da Thread principal
         para que a tela não trave esperando os dados*/
        Task task = new Task() {
            /*Método que é executado em 2 plano, aqui colocaremos os trabalhos pesados*/
            @Override
            protected Object call() throws Exception {
                /*Podemos retornar um objeto e pegaremos esse objeto no
                 método succeeded*/

                return AlunoDAO.executeQuery(null, AlunoDAO.QUERY_TODOS);
            }

            /*Método é executado apos o processo em 2 plano for concluido*/
            @Override
            protected void succeeded() {
                super.succeeded();
                /*Método getValue retorna o Objeto que foi retornado no método Call
                 ai desta forma a Thread principal não espera os dados serão todos
                 carregados para só depois exibir a tela*/
                tabelaAluno.setItems((ObservableList<AlunoModel>) getValue());
            }
        };
        /*Usamos uma Thread para iniciamos o Task, passamos nosso Task no seu construtor*/
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
