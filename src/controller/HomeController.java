package controller;

import com.sun.org.apache.bcel.internal.generic.IFEQ;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.security.pkcs11.Secmod;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class HomeController implements Initializable {

    /*Vamos criar variaveis do tipo booleana para verificar
     se a janela já foi aberta antes, para não temos que
     carregar novamente outra janela em memoria*/
    private boolean abriuCadAluno, abriuCadTurma, abriuAlunoTurma, abriuDisciplina, abriuProfessor, abiuProfessorDisciplina;
    /*Precisamos que os Stage de cada tela sejam declarados aqui para
     que possamos utilizar ela em outro if*/
    private Stage cadAlunoPalco, cadTurmaPalco, alunoTurmaPalco, cadDisciplinaPalco, cadProfessorPalco, professorDisciplinaPalco;
    /*Iremos precisar da classe controle para executamos alguns metodos
    dela, então iremos declarar elas aqui*/
    private AlunoController alunoController;
    private TurmaController turmaController;
    private DisciplinaController disciplinaController;
    private ProfessorController professorController;
    private AlunoTurmaController alunoTurmaController;
    private ProfessorDisciplinaController professorDisciplinaController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void cadAluno() {
        /*se não abriu ainda a tela carregamos em memoria*/
        if (!abriuCadAluno) {
            /*Declaramos o FXMLLoader pq vamos precisar que ele retorne a Classe
            de controle para a gente*/
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/Aluno.fxml"));
            try {
                this.cadAlunoPalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                /*Carregamos a Nossa Classe de Controle*/
                this.alunoController = carregar.getController();

                this.cadAlunoPalco.setTitle("Cadastro de Alunos");
                this.cadAlunoPalco.setScene(scene);
                this.cadAlunoPalco.show();
                /*chamamos o método carregarTabela da Classe de controle*/
                this.alunoController.carregarTabela();
                /*informamos que a tela já foi aberta uma vez*/
                this.abriuCadAluno = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            /*Se caso já foi carregada em memoria antes apenas
             pedimos para mostrar a tela novamente melhorando então o nosso desempenho*/
            this.cadAlunoPalco.show();
            /*Precisamos que a tabela seja carregada novamente*/
            this.alunoController.carregarTabela();
        }
        /*Veja a melhoria no desempenho ao abrir a tela segunda vez.*/
    }

    @FXML
    public void cadTurma() {
        if (!abriuCadTurma) {

            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/Turma.fxml"));
            try {
                this.cadTurmaPalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.turmaController = carregar.getController();

                this.cadTurmaPalco.setTitle("Cadastro de Turmas");
                this.cadTurmaPalco.setScene(scene);
                this.cadTurmaPalco.show();

                this.turmaController.carregarTabela();
                this.abriuCadTurma = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.cadTurmaPalco.show();
            /*Precisamos que a tabela seja carregada novamente*/
            this.turmaController.carregarTabela();
        }
    }

    @FXML
    public void cadDisciplina() {
        if (!abriuDisciplina) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/Disciplina.fxml"));
            try {
                this.cadDisciplinaPalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.disciplinaController = carregar.getController();
                this.cadDisciplinaPalco.setTitle("Cadastro de Disciplinas");
                this.cadDisciplinaPalco.setScene(scene);
                this.cadDisciplinaPalco.show();
                this.disciplinaController.carregarTabela();
                this.abriuDisciplina = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.cadDisciplinaPalco.show();
            this.disciplinaController.carregarTabela();
        }
    }

    @FXML
    public void cadProfessor() {
        if (!abriuProfessor) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/Professor.fxml"));
            try {
                this.cadProfessorPalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.professorController = carregar.getController();
                this.cadProfessorPalco.setTitle("Cadastro de Professores");
                this.cadProfessorPalco.setScene(scene);
                this.cadProfessorPalco.show();
                this.professorController.carregarTabela();

                this.abriuProfessor = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.cadProfessorPalco.show();
            this.professorController.carregarTabela();
        }
    }

    @FXML
    public void AlunoTurma() {
        if (!abriuAlunoTurma) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/AlunoTurma.fxml"));
            try {
                this.alunoTurmaPalco = new Stage();
                Parent root = carregar.load();
                Scene scene = new Scene(root);
                this.alunoTurmaController = carregar.getController();

                this.alunoTurmaPalco.setTitle("Cadastro de Alunos em Turmas");
                this.alunoTurmaPalco.setScene(scene);
                this.alunoTurmaPalco.show();

                /*Iniciamos os processos iniciais*/
                this.alunoTurmaController.iniciarProcessos();
                this.abriuAlunoTurma = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.alunoTurmaPalco.show();
            this.alunoTurmaController.iniciarProcessos();
        }
    }

    @FXML
    public void professorDisciplina() {
        if (!abiuProfessorDisciplina) {
            FXMLLoader carregar = new FXMLLoader(getClass().getResource("/view/ProfessorDisciplina.fxml"));
            try {
                this.professorDisciplinaPalco = new Stage();
                Parent root;
                root = carregar.load();
                Scene scene = new Scene(root);
                this.professorDisciplinaController = carregar.getController();
                this.professorDisciplinaPalco.setTitle("Cadastro de Professor em Disciplinas");
                this.professorDisciplinaPalco.setScene(scene);
                this.professorDisciplinaPalco.show();
                this.professorDisciplinaController.iniciarProcessos();
                this.abiuProfessorDisciplina = true;
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.professorDisciplinaPalco.show();
            this.professorDisciplinaController.iniciarProcessos();
        }
    }
    
    
}
