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

/**
 *
 * @author jassis
 */
public class DisciplinaDAO {

    public static final int CREATE = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;

    public static boolean executeUpdates(DisciplinaModel dm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;

        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into disciplina("
                            + "descricao_disciplina)"
                            + "values(?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, dm.getDescricao());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                case UPDATE:
                    sql = "update disciplina set "
                            + "descricao_disciplina=? "
                            + "where id_disciplina=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, dm.getDescricao());
                    ps.setInt(2, dm.getCodigo());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                case DELETE:
                    sql = "delete from disciplina where id_disciplina=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, dm.getCodigo());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static final int QUERY_DESCRICAO = 3;
    public static final int QUERY_TODOS = 4;

    public static ObservableList<DisciplinaModel> executeQuery(DisciplinaModel dm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<DisciplinaModel> listaDisciplina = FXCollections.observableArrayList();
        DisciplinaModel disciplinaModel;
        try {

            switch (operacao) {
                case QUERY_DESCRICAO:
                    sql = "select * from disciplina order by descricao_disciplina";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, dm.getDescricao());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        disciplinaModel = new DisciplinaModel();
                        disciplinaModel.setCodigo(rs.getInt("id_disciplina"));
                        disciplinaModel.setDescricao(rs.getString("descricao_disciplina"));
                        listaDisciplina.add(disciplinaModel);
                    }
                    rs.close();
                    ps.close();
                    conexao.close();
                    return listaDisciplina;
                case QUERY_TODOS:
                    sql = "select * from disciplina order by id_disciplina";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        disciplinaModel = new DisciplinaModel();
                        disciplinaModel.setCodigo(rs.getInt("id_disciplina"));
                        disciplinaModel.setDescricao(rs.getString("descricao_disciplina"));
                        listaDisciplina.add(disciplinaModel);
                    }
                    rs.close();
                    ps.close();
                    conexao.close();
                    return listaDisciplina;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DisciplinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaDisciplina;
    }
}
