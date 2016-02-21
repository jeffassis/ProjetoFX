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
import model.bean.ProfessorModel;

/**
 *
 * @author jeff-
 */
public class ProfessorDAO {

    public static final int CREATE = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;

    public static boolean executeUpdates(ProfessorModel pm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;

        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into professor("
                            + "nome_professor,"
                            + "endereco_professor,"
                            + "nascimento_professor,"
                            + "telefone_professor,"
                            + "cep_professor)"
                            + "values(?,?,?,?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, pm.getNome());
                    ps.setString(2, pm.getEndereco());
                    ps.setString(3, pm.getNascimento());
                    ps.setString(4, pm.getTelefone());
                    ps.setString(5, pm.getCep());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                case UPDATE:
                    sql = "update professor set "
                            + "nome_professor=?, "
                            + "endereco_professor=?, "
                            + "nascimento_professor=?, "
                            + "telefone_professor=?, "
                            + "cep_professor=? "
                            + "where id_professor=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, pm.getNome());
                    ps.setString(2, pm.getEndereco());
                    ps.setString(3, pm.getNascimento());
                    ps.setString(4, pm.getTelefone());
                    ps.setString(5, pm.getCep());
                    ps.setInt(6, pm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case DELETE:
                    sql = "delete from professor where id_professor=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, pm.getCodigo());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static final int QUERY_DESCRICAO = 3;
    public static final int QUERY_TODOS = 4;

    public static ObservableList<ProfessorModel> executeQuery(ProfessorModel pm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<ProfessorModel> listaProfessor = FXCollections.observableArrayList();
        ProfessorModel professorModel;

        try {
            switch (operacao) {
                case QUERY_DESCRICAO:
                    sql = "select * from professor order by nome_professor";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, pm.getNome());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        professorModel = new ProfessorModel();
                        professorModel.setCodigo(rs.getInt("id_professor"));
                        professorModel.setNome(rs.getString("nome_professor"));
                        professorModel.setEndereco(rs.getString("endereco_professor"));
                        professorModel.setNascimento(rs.getString("nascimento_professor"));
                        professorModel.setTelefone(rs.getString("telefone_professor"));
                        professorModel.setCep(rs.getString("cep_professor"));
                        listaProfessor.add(professorModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaProfessor;
                case QUERY_TODOS:
                    sql = "select * from professor order by id_professor";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        professorModel = new ProfessorModel();
                        professorModel.setCodigo(rs.getInt("id_professor"));
                        professorModel.setNome(rs.getString("nome_professor"));
                        professorModel.setEndereco(rs.getString("endereco_professor"));
                        professorModel.setNascimento(rs.getString("nascimento_professor"));
                        professorModel.setTelefone(rs.getString("telefone_professor"));
                        professorModel.setCep(rs.getString("cep_professor"));
                        listaProfessor.add(professorModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaProfessor;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaProfessor;
    }

}
