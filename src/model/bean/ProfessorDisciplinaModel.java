package model.bean;

/**
 *
 * @author jeff-
 */
public class ProfessorDisciplinaModel {

    private ProfessorModel professorModel;
    private DisciplinaModel disciplinaModel;

    public ProfessorDisciplinaModel() {
    }

    public ProfessorDisciplinaModel(ProfessorModel professorModel, DisciplinaModel disciplinaModel) {
        this.professorModel = professorModel;
        this.disciplinaModel = disciplinaModel;
    }

    public ProfessorDisciplinaModel(DisciplinaModel disciplinaModel, ProfessorModel professorModel) {
        this.disciplinaModel = disciplinaModel;
        this.professorModel = professorModel;
    }

    public ProfessorModel getProfessorModel() {
        return professorModel;
    }

    public void setProfessorModel(ProfessorModel professorModel) {
        this.professorModel = professorModel;
    }

    public DisciplinaModel getDisciplinaModel() {
        return disciplinaModel;
    }

    public void setDisciplinaModel(DisciplinaModel disciplinaModel) {
        this.disciplinaModel = disciplinaModel;
    }

}
