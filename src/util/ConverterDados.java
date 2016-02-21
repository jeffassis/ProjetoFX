package Util;

import javafx.util.StringConverter;
import model.bean.AlunoModel;
import model.bean.DisciplinaModel;
import model.bean.ProfessorModel;
import model.bean.TurmaModel;

/**
 *
 * @author jeff-
 */
public class ConverterDados {

    private int retorno;
    /**
     * Retorna o estado do aluno.
     */
    public static final int GET_UF = 0;
    /**
     * Retorna a serie do aluno.
     */
    public static final int GET_SERIE = 1;
    /**
     * Retorna o código da Turma.
     */
    public static final int GET_CODIGO = 2;
    public static final int GET_DESCRICAO = 3;
    public static final int GET_HORARIO = 4;
    public static final int GET_NOME = 5;
    /**
     * Retorna o código da Disciplina
     */
    public static final int GET_DISCIPLINA_CODIGO = 6;
    public static final int GET_DISCIPLINA_DESCRICAO = 7;
    /**
     * Retorna o código do Professor
     */
    public static final int GET_PROFESSOR_CODIGO = 8;
    public static final int GET_PROFESSOR_NOME = 9;

    /**
     * Passe um int estatico da Classe ConverterDados informando o tipo da
     * operação, ex: ConverterDados dados = new
     * ConverterDados(ConverterDados.GET_UF).
     *
     * @param retorno
     */
    public ConverterDados(int retorno) {
        this.retorno = retorno;
    }

    /*Mudamos completamente nossa Classe ConverterDados, pq antes a nossa classe
     retorna um toString somente do AlunoModel, mas vamos precisar tbm que ela
     retorne um to tipo TurmaModel, mudaremos nosso conceito aqui! o método dessa
     Classe vai retornar um Objeto do tipo StringConverter com tudo preparado
     veja a implementação q vc vai entender.*/
    /**
     * Retorna um toString proprio de AlunoModel.
     *
     * @return
     */
    public StringConverter<AlunoModel> getAlunoConverter() {
        /*Em vez de estender nossa Classe converterDados a StringConverter criamos um
         método que vai retornar varios tipos de StringConverter e não somente de AlunoModel
         que nem antes. Assim a nossa Classe em vez de retorna um toString de apenas
         AlunoModel se torna uma Classe que pode retornar varios tipos, ou seja em outras
         palavras aumentamos os poderes da nossa Classe.*/
        StringConverter<AlunoModel> convertido = new StringConverter<AlunoModel>() {

            @Override
            public String toString(AlunoModel object) {
                switch (retorno) {
                    case GET_NOME:
                        return object.getNome();
                    case GET_UF:
                        return object.getUf();
                    case GET_SERIE:
                        return object.getSerie();
                    default:
                        return "";
                }
            }

            @Override
            public AlunoModel fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        /*Retornamos o Objeto já com o toString pronto*/
        return convertido;
    }

    /**
     * Retorna um toString proprio de TurmaModel.
     *
     * @return
     */
    public StringConverter<TurmaModel> getTurmaConverter() {
        StringConverter<TurmaModel> convertido = new StringConverter<TurmaModel>() {

            @Override
            public String toString(TurmaModel object) {
                switch (retorno) {
                    case GET_CODIGO:
                        return object.getCodigoProperty().getValue().toString();
                    case GET_DESCRICAO:
                        return object.getDescricao();
                    case GET_HORARIO:
                        return object.getHorario();
                    default:
                        return "";
                }
            }

            @Override
            public TurmaModel fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return convertido;
    }

    /**
     * Retorna um toString proprio de DisciplinaModel.
     *
     * @return
     */
    public StringConverter<DisciplinaModel> getDisciplinaConverter() {
        StringConverter<DisciplinaModel> convertido = new StringConverter<DisciplinaModel>() {
            @Override
            public String toString(DisciplinaModel object) {
                switch (retorno) {
                    case GET_DISCIPLINA_CODIGO:
                        return object.getCodigoProperty().getValue().toString();
                    case GET_DISCIPLINA_DESCRICAO:
                        return object.getDescricao();
                    default:
                        return "";
                }
            }

            @Override
            public DisciplinaModel fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return convertido;
    }

    /**
     * Retorna um toString proprio de ProfessorModel.
     *
     * @return
     */
    public StringConverter<ProfessorModel> getProfessorConverter() {
        StringConverter<ProfessorModel> convertido = new StringConverter<ProfessorModel>() {
            @Override
            public String toString(ProfessorModel object) {
                switch (retorno) {
                    case GET_PROFESSOR_CODIGO:
                        return object.getCodigoProperty().getValue().toString();
                    case GET_PROFESSOR_NOME:
                        return object.getNome();
                    default:
                        return "";
                }
            }

            @Override
            public ProfessorModel fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return convertido;
    }
}
