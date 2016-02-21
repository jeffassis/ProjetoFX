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
import model.bean.TurmaModel;

/**
 *
 * @author jeff-
 */
public class TurmaDAO {

    /*Vamos criar nossas constantes para depois
     usamos em nossos métodos, colocaremos como publica
     e estatica para que ela seja acessada de qualquer classe
     sem precisar instanciar um Objeto da Classe TurmaDAO*/
    public static final int CREATE = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;

    /*Agora que já temos nossas Constantes para inserção de dados
     vamos criar um metodo que recebe um Objeto da turmaModel e um int
     informando qual operação deve ser feita nele. Chamaremos ele de
     executeUpdates, ele será estatico para que não seja necessario
     criar um Objeto da TurmaDAO, já que apenas faremos uma operação
     no banco de Dados*/
    public static boolean executeUpdates(TurmaModel tm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        /*é agora que escolhemos qual operação fazer de acordo
         com o que foi passado na operacao*/
        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into turma("
                            + "descricao_turma,"
                            + "horario_turma)"
                            + "values(?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, tm.getDescricao());
                    ps.setString(2, tm.getHorario());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    //retornar um true informando que ocorreu com sucesso!
                    return true;
                case UPDATE:
                    sql = "update turma set "
                            + "descricao_turma=?,"
                            + " horario_turma=?"
                            + " where id_turma=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, tm.getDescricao());
                    ps.setString(2, tm.getHorario());
                    ps.setInt(3, tm.getCodigo());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                case DELETE:
                    sql = "delete from turma where id_turma=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, tm.getCodigo());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                default:
                    //Caso nenhuma, fecha a conexão.
                    conexao.close();
                    /*retorna um false para dizer que algo deu errado
                     ou seja passou errado o int operacao*/
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TurmaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static final int QUERY_DESCRICAO = 3;
    public static final int QUERY_TODOS = 4;

    public static ObservableList<TurmaModel> executeQuery(TurmaModel tm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<TurmaModel> listaTurma = FXCollections.observableArrayList();
        TurmaModel turmaModel;
        /*é agora que entramos em ação novamente*/

        try {
            switch (operacao) {
                case QUERY_DESCRICAO:
                    sql = "select * from turma order by descricao_turma";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, tm.getDescricao());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        turmaModel = new TurmaModel();
                        turmaModel.setCodigo(rs.getInt("id_turma"));
                        turmaModel.setDescricao(rs.getString("descricao_turma"));
                        turmaModel.setHorario(rs.getString("horario_turma"));
                        listaTurma.add(turmaModel);
                    }
                    rs.close();
                    ps.close();
                    conexao.close();
                    //retornamos a lista populada
                    return listaTurma;
                case QUERY_TODOS:
                    sql = "select * from turma order by id_turma";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        turmaModel = new TurmaModel();
                        turmaModel.setCodigo(rs.getInt("id_turma"));
                        turmaModel.setDescricao(rs.getString("descricao_turma"));
                        turmaModel.setHorario(rs.getString("horario_turma"));
                        listaTurma.add(turmaModel);
                    }
                    rs.close();
                    ps.close();
                    conexao.close();
                    //retornamos a lista populada
                    return listaTurma;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TurmaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaTurma;
    }

}
