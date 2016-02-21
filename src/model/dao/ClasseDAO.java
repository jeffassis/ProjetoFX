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
import model.bean.ClasseModel;
import model.bean.TurmaModel;

/**
 * Classe responsavel por trabalhar com valores de várias tabelas juntas.
 *
 * @author jassis
 */
public class ClasseDAO {

    public static final int QUERY_TODOS = 0;
    /**
     * Passe uma ClasseModel com aluno e turma.
     */
    public static final int EDITAR_TURMA = 1;
    /*Retorna uma ClasseModel com Aluno e turma.*/
    public static final int QUERY_ALUNO = 2;

    public static final int DELETE = 3;

    public static ObservableList<ClasseModel> executeQuery(ClasseModel classeModel, int funcao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<ClasseModel> lista = FXCollections.observableArrayList();
        ClasseModel cm;

        try {
            switch (funcao) {
                case QUERY_TODOS:
                    sql = "select * from aluno left join aluno_turma"
                            + " on id_aluno = id_aluno_turma left join turma"
                            + " on id_turma_aluno = id_turma";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        AlunoModel alunoModel = new AlunoModel();
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
                        /*Dados da turma*/
                        TurmaModel turmaModel = new TurmaModel();
                        turmaModel.setCodigo(rs.getInt("id_turma"));
                        turmaModel.setDescricao(rs.getString("descricao_turma"));
                        turmaModel.setHorario(rs.getString("horario_turma"));
                        /*agora adicionamos a nossa Classe que representa todos eles juntos*/
                        cm = new ClasseModel(alunoModel, turmaModel);
                        /*Adicionamos a nossa lista*/
                        lista.add(cm);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return lista;
                case QUERY_ALUNO:
                    sql = "select * from turma left join aluno_turma"
                            + " on id_turma = id_turma_aluno left join aluno"
                            + " on id_aluno_turma = id_aluno where id_turma = ?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, classeModel.getTurmaModel().getCodigo());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        AlunoModel alunoModel = new AlunoModel();
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
                        /*A turma*/
                        TurmaModel turmaModel = new TurmaModel();
                        turmaModel.setCodigo(rs.getInt("id_turma"));
                        turmaModel.setDescricao(rs.getString("descricao_turma"));
                        turmaModel.setHorario(rs.getString("horario_turma"));

                        cm = new ClasseModel(alunoModel, turmaModel);
                        lista.add(cm);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return lista;
                default:
                    conexao.close();
                    return lista;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClasseDAO.class.getName()).log(Level.SEVERE, null, ex);
            /*Retorna a lista vazia*/
            return lista;
        }
    }

    public static boolean executeUpdates(ClasseModel classeModel, int funcao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        try {
            switch (funcao) {
                case DELETE:
                    sql = "delete from aluno_turma where id_aluno_turma = ?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, classeModel.getAlunoModel().getCodigo());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                case EDITAR_TURMA:
                    sql = "select count(*) from aluno_turma where id_aluno_turma = ?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, classeModel.getAlunoModel().getCodigo());
                    rs = ps.executeQuery();
                    /*variavel que vai conter os resuldado do if ou do else*/
                    boolean resultado = false;
                    /*Se o resultado for mais que 0 significa que o aluno
                     já estar cadastrado em alguma turma.*/
                    rs.next();
                    int count = rs.getInt(1);
                    if (count > 0) {
                        rs.close();
                        ps.close();
                        sql = "update aluno_turma set id_turma_aluno = ? where id_aluno_turma = ?";
                        ps = conexao.prepareStatement(sql);
                        ps.setInt(1, classeModel.getTurmaModel().getCodigo());
                        ps.setInt(2, classeModel.getAlunoModel().getCodigo());
                        ps.executeUpdate();
                        resultado = true;
                    } else {
                        rs.close();
                        ps.close();
                        sql = "insert into aluno_turma (id_aluno_turma,id_turma_aluno) values(?,?)";
                        ps = conexao.prepareStatement(sql);
                        ps.setInt(1, classeModel.getAlunoModel().getCodigo());
                        ps.setInt(2, classeModel.getTurmaModel().getCodigo());
                        ps.executeUpdate();
                        resultado = true;
                    }
                    ps.close();
                    conexao.close();
                    return resultado;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClasseDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
