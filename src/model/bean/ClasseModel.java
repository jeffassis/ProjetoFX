package model.bean;

/**
 * Class de junção do Aluno com a Turma
 *
 * @author jassis
 */
public class ClasseModel {

    /*Essa Classe representa os dados das Tabelas todas juntas*/
    private AlunoModel alunoModel;
    private TurmaModel turmaModel;

    public ClasseModel() {
    }

    public ClasseModel(AlunoModel alunoModel, TurmaModel turmaModel) {
        this.alunoModel = alunoModel;
        this.turmaModel = turmaModel;
    }

    public AlunoModel getAlunoModel() {
        return alunoModel;
    }

    public void setAlunoModel(AlunoModel alunoModel) {
        this.alunoModel = alunoModel;
    }

    public TurmaModel getTurmaModel() {
        return turmaModel;
    }

    public void setTurmaModel(TurmaModel turmaModel) {
        this.turmaModel = turmaModel;
    }
}
