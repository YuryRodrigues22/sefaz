package br.com.sefaz.dao;

import br.com.sefaz.entity.Usuario;
import br.com.sefaz.util.Conexao;
import br.com.sefaz.util.exception.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements CrudDAO<Usuario> {

    @Override
    public void salvar(Usuario usuario) throws ErroSistema {
        try {
            Connection conexao = Conexao.getConexao();
            PreparedStatement ps;
            if (usuario.getId() == null) {
                ps = conexao.prepareStatement("INSERT INTO usuario (Nome, Email, Senha, Ddd, Numero, Tipo) VALUES (?,?,?,?,?,?);");
            } else {
                ps = conexao.prepareStatement("UPDATE usuario SET Nome=?, Email=?, Senha=?, Ddd=?, Numero=?, Tipo=? WHERE id=?");
                ps.setInt(6, usuario.getId());
            }
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setInt(4, usuario.getDdd());
            ps.setString(5, usuario.getNumero());
            ps.setString(6, usuario.getTipo());

            ps.execute();
            Conexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao Inserir o Usuario no BD", ex);
        }
    }

    @Override
    public List<Usuario> consultar() throws ErroSistema {
        try {
            Connection conexao = Conexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM USUARIO");
            ResultSet resultSet = ps.executeQuery();
            List<Usuario> usuarios = new ArrayList<>();
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNome(resultSet.getString("Nome"));
                usuario.setEmail(resultSet.getString("Email"));
                usuario.setSenha(resultSet.getString("Senha"));
                usuario.setDdd(resultSet.getInt("Ddd"));
                usuario.setNumero(resultSet.getString("Numero"));
                usuario.setTipo(resultSet.getString("Tipo"));
                usuarios.add(usuario);
            }
            return usuarios;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro na Listagem dos Usuários", ex);
        }
    }

    @Override
    public void deletar(Usuario usuario) throws ErroSistema {
        try {
            Connection conexao = Conexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("DELETE FROM usuario WHERE id=?");
            ps.setInt(1, usuario.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao Deletar o Registro do BD", ex);
        }
    }
}
