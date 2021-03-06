package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Factory da Aplicação
 *
 * @author jeff-
 */
public class ConnectionFactory {

    /**
     * Declarando as variaveis como static final pois assim utilizamos
     * corretamente as conveções.
     */
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/cesa";
    private static final String USER = "root";
    private static final String PASS = "jean1420";

    /**
     * Método utilizado para abrir a conexão. que possui 3 sobrecargas.
     *
     * public static Connection getConnection(String url).
     * public static Connection getConnection(String url, Properties info)
     * public static Connection getConnection(String url, String user, String password).
     *
     * @return
     */
    public static Connection getConnection() {

        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro na conexão: " + ex);
        }
    }

    /**
     * Método responsavel por fechar uma conexão somente.
     *
     * @param con
     */
    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método responsavel por fechar a conexão e o PreparedStatement
     *
     * @param con
     * @param stmt
     */
    public static void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método responsavel por fechar a conexão, PreraredStatement e o ResultSet
     *
     * @param con
     * @param stmt
     * @param rs
     */
    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
