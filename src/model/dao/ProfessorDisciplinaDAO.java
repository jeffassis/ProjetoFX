package model.dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.bean.DisciplinaModel;
import model.bean.ProfessorDisciplinaModel;
import model.bean.ProfessorModel;

/**
 *
 * @author jeff-
 */
public class ProfessorDisciplinaDAO {

    public static final int QUERY_TODOS = 0;
    public static final int CREATE = 1;
    public static final int QUERY_PROFESSOR = 2;
    public static final int DELETE = 3;

    public static ObservableList<ProfessorDisciplinaModel> executeQuery(ProfessorDisciplinaModel professorDisciplinaModel, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<ProfessorDisciplinaModel> lista = FXCollections.observableArrayList();
        ProfessorDisciplinaModel pD;

        try {
            switch (operacao) {
                case QUERY_TODOS:
                    sql = "select * from professor left join professor_disciplina"
                            + " on id_professor = id_professor_disciplina left join disciplina"
                            + " on id_disciplina_professor = id_disciplina";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        DisciplinaModel disciplinaModel = new DisciplinaModel();
                        disciplinaModel.setCodigo(rs.getInt("id_disciplina"));
                        disciplinaModel.setDescricao(rs.getString("descricao_disciplina"));
                        /*Dados do Professor*/
                        ProfessorModel professorModel = new ProfessorModel();
                        professorModel.setCodigo(rs.getInt("id_professor"));
                        professorModel.setNome(rs.getString("nome_professor"));
                        professorModel.setEndereco(rs.getString("endereco_professor"));
                        professorModel.setNascimento(rs.getString("nascimento_professor"));
                        professorModel.setTelefone(rs.getString("nascimento_professor"));
                        professorModel.setCep(rs.getString("cep_professor"));
                        /*agora adicionamos a nossa Classe que representa todos eles juntos*/
                        pD = new ProfessorDisciplinaModel(professorModel, disciplinaModel);
                        /*Adicionamos a nossa lista*/
                        lista.add(pD);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return lista;
                case QUERY_PROFESSOR:
                    sql = "select * from professor left join professor_disciplina"
                            + " on id_professor = id_professor_disciplina left join disciplina"
                            + " on id_disciplina_professor = id_disciplina where id_professor = ?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, professorDisciplinaModel.getProfessorModel().getCodigo());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        DisciplinaModel disciplinaModel = new DisciplinaModel();
                        disciplinaModel.setCodigo(rs.getInt("id_disciplina"));
                        disciplinaModel.setDescricao(rs.getString("descricao_disciplina"));
                        /*Dados do Professor*/
                        ProfessorModel professorModel = new ProfessorModel();
                        professorModel.setCodigo(rs.getInt("id_professor"));
                        professorModel.setNome(rs.getString("nome_professor"));
                        professorModel.setEndereco(rs.getString("endereco_professor"));
                        professorModel.setNascimento(rs.getString("nascimento_professor"));
                        professorModel.setTelefone(rs.getString("nascimento_professor"));
                        professorModel.setCep(rs.getString("cep_professor"));
                        /*agora adicionamos a nossa Classe que representa todos eles juntos*/
                        pD = new ProfessorDisciplinaModel(professorModel, disciplinaModel);
                        /*Adicionamos a nossa lista*/
                        lista.add(pD);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return lista;
                default:
                    conexao.close();
                    return lista;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorDisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return lista;
        }
    }

    public static boolean executeUpdates(ProfessorDisciplinaModel professorDisciplinaModel, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;

        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into professor_disciplina("
                            + "id_professor_disciplina,"
                            + "id_disciplina_professor)"
                            + "values (?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, professorDisciplinaModel.getProfessorModel().getCodigo());
                    ps.setInt(2, professorDisciplinaModel.getDisciplinaModel().getCodigo());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                case DELETE:
                    sql = "delete from professor_disciplina where id_professor_disciplina=?"
                            + " and id_disciplina_professor = ?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, professorDisciplinaModel.getProfessorModel().getCodigo());
                    ps.setInt(2, professorDisciplinaModel.getDisciplinaModel().getCodigo());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorDisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
