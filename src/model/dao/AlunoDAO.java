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
import model.bean.AlunoModel;

/**
 *
 * @author jeff-
 */
public class AlunoDAO {

    public static final int CREATE = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;

    public static boolean executeUpdates(AlunoModel al, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;

        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into aluno("
                            + "nome_aluno,"
                            + "sexo_aluno,"
                            + "endereco_aluno,"
                            + "nascimento_aluno,"
                            + "mae_aluno,"
                            + "pai_aluno,"
                            + "serie_aluno,"
                            + "telefone_aluno,"
                            + "uf_aluno,"
                            + "cep_aluno,"
                            + "ensino_aluno)"
                            + "values(?,?,?,?,?,?,?,?,?,?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, al.getNome());
                    ps.setString(2, al.getSexo());
                    ps.setString(3, al.getEndereco());
                    ps.setString(4, al.getNascimento());
                    ps.setString(5, al.getMae());
                    ps.setString(6, al.getPai());
                    ps.setString(7, al.getSerie());
                    ps.setString(8, al.getTelefone());
                    ps.setString(9, al.getUf());
                    ps.setString(10, al.getCep());
                    ps.setString(11, al.getEnsino());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                case UPDATE:
                    sql = "update aluno set "
                            + "nome_aluno=?,"
                            + "endereco_aluno=?,"
                            + "sexo_aluno=?,"
                            + "nascimento_aluno=?,"
                            + "mae_aluno=?,"
                            + "pai_aluno=?,"
                            + "serie_aluno=?,"
                            + "telefone_aluno=?,"
                            + "uf_aluno=?,"
                            + "cep_aluno=?,"
                            + "ensino_aluno=?"
                            + "where id_aluno=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, al.getNome());
                    ps.setString(2, al.getEndereco());
                    ps.setString(3, al.getSexo());
                    ps.setString(4, al.getNascimento());
                    ps.setString(5, al.getMae());
                    ps.setString(6, al.getPai());
                    ps.setString(7, al.getSerie());
                    ps.setString(8, al.getTelefone());
                    ps.setString(9, al.getUf());
                    ps.setString(10, al.getCep());
                    ps.setString(11, al.getEnsino());
                    ps.setInt(12, al.getCodigo());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                case DELETE:
                    sql = "delete from aluno where id_aluno=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, al.getCodigo());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public static final int QUERY_DESCRICAO = 3;
    public static final int QUERY_TODOS = 4;

    public static ObservableList<AlunoModel> executeQuery(AlunoModel al, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<AlunoModel> listaAluno = FXCollections.observableArrayList();
        AlunoModel alunoModel;

        try {
            switch (operacao) {
                case QUERY_DESCRICAO:
                    sql = "select * from aluno order by nome_aluno";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, al.getNome());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        alunoModel = new AlunoModel();
                        alunoModel.setCodigo(rs.getInt("id_aluno"));
                        alunoModel.setNome(rs.getString("nome_aluno"));
                        alunoModel.setSexo(rs.getString("sexo_aluno"));
                        alunoModel.setEndereco(rs.getString("endereco_aluno"));
                        alunoModel.setNascimento(rs.getString("nascimento_aluno"));
                        alunoModel.setMae(rs.getString("mae_aluno"));
                        alunoModel.setPai(rs.getString("pai_aluno"));
                        alunoModel.setSerie(rs.getString("serie_aluno"));
                        alunoModel.setTelefone(rs.getString("telefone_aluno"));
                        alunoModel.setUf(rs.getString("uf_aluno"));
                        alunoModel.setCep(rs.getString("cep_aluno"));
                        alunoModel.setEnsino(rs.getString("ensino_aluno"));
                        listaAluno.add(alunoModel);
                    }
                    rs.close();
                    ps.close();
                    conexao.close();
                    //retornamos a lista populada
                    return listaAluno;
                case QUERY_TODOS:
                    sql = "select * from aluno order by id_aluno";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        alunoModel = new AlunoModel();
                        alunoModel.setCodigo(rs.getInt("id_aluno"));
                        alunoModel.setNome(rs.getString("nome_aluno"));
                        alunoModel.setSexo(rs.getString("sexo_aluno"));
                        alunoModel.setEndereco(rs.getString("endereco_aluno"));
                        alunoModel.setNascimento(rs.getString("nascimento_aluno"));
                        alunoModel.setMae(rs.getString("mae_aluno"));
                        alunoModel.setPai(rs.getString("pai_aluno"));
                        alunoModel.setSerie(rs.getString("serie_aluno"));
                        alunoModel.setTelefone(rs.getString("telefone_aluno"));
                        alunoModel.setUf(rs.getString("uf_aluno"));
                        alunoModel.setCep(rs.getString("cep_aluno"));
                        alunoModel.setEnsino(rs.getString("ensino_aluno"));
                        listaAluno.add(alunoModel);
                    }
                    rs.close();
                    ps.close();
                    conexao.close();
                    //retornamos a lista populada
                    return listaAluno;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAluno;

    }
}
