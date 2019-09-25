package br.com.sefaz.util;

import br.com.sefaz.util.exception.ErroSistema;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static Connection conexao;
    private static final String URL_CONEXAO = "jdbc:mysql://localhost:3306/schema";
    private static final String USUARIO = "root";
    private static final String SENHA = "pass123";

    public static Connection getConexao() throws ErroSistema {
        if (conexao == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexao = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
            } catch (SQLException ex) {
                throw new ErroSistema("Não Conectou ao BD", ex);
            } catch (ClassNotFoundException ex) {
                throw new ErroSistema("Driver não encontrado", ex);
            }
        }
        return conexao;
    }

    public static void fecharConexao() throws ErroSistema {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException ex) {
                throw new ErroSistema("Erro ao fechar conexão com o BD", ex);
            }
        }
    }
}
