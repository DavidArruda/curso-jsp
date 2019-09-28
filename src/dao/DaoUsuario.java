/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCursoJsp;
import connection.SingleConnection;

/**
 * @author deh0_
 *
 */
public class DaoUsuario {

	private Connection connection;

	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanCursoJsp usuario) {

		String sql = "insert into usuario (login, senha, nome, fone, rua, bairro"
				+ ", cidade, estado, ibge, cep, fotoBase64, fcontenttype, curriloBase64"
				+ ", contentTypeCurriculo,fotoBase64Miniatura, ativo, sexo, perfil)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			statement.setString(3, usuario.getNome());
			statement.setString(4, usuario.getFone());
			statement.setString(5, usuario.getRua());
			statement.setString(6, usuario.getBairro());
			statement.setString(7, usuario.getCidade());
			statement.setString(8, usuario.getEstado());
			statement.setString(9, usuario.getIbge());
			statement.setString(10, usuario.getCep());
			statement.setString(11, usuario.getFotoBase64());
			statement.setString(12, usuario.getContentType());
			statement.setString(13, usuario.getCurriloBase64());
			statement.setString(14, usuario.getContentTypeCurriculo());
			statement.setString(15, usuario.getFotoBase64Miniatura());
			statement.setBoolean(16, usuario.isAtivo());
			statement.setString(17, usuario.getSexo());
			statement.setString(18, usuario.getPerfil());
			statement.execute();

			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}
	}
	
	public List<BeanCursoJsp> listar(String descricaoconsulta) throws SQLException {
		String sql = "select * from usuario where login <> 'admin' and nome like'%"+descricaoconsulta+"%'";
		return consultarUsuarios(sql);
	}
	

	public List<BeanCursoJsp> listar() throws Exception {

		String sql = "select * from usuario where login <> 'admin'";
		return consultarUsuarios(sql);

		
	}

	private List<BeanCursoJsp> consultarUsuarios(String sql)
			throws SQLException {
		
		List<BeanCursoJsp> lista = new ArrayList<BeanCursoJsp>();
		PreparedStatement select = connection.prepareStatement(sql);
		ResultSet resultSet = select.executeQuery();

		while (resultSet.next()) {
			BeanCursoJsp usuario = new BeanCursoJsp();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setFone(resultSet.getString("fone"));
			usuario.setRua(resultSet.getString("rua"));
			usuario.setBairro(resultSet.getString("bairro"));
			usuario.setCidade(resultSet.getString("cidade"));
			usuario.setEstado(resultSet.getString("estado"));
			usuario.setIbge(resultSet.getString("ibge"));
			usuario.setCep(resultSet.getString("cep"));
			// usuario.setFotoBase64(resultSet.getString("fotoBase64"));
			usuario.setFotoBase64Miniatura(resultSet.getString("fotoBase64Miniatura"));
			usuario.setContentType(resultSet.getString("fcontenttype"));
			usuario.setCurriloBase64(resultSet.getString("curriloBase64"));
			usuario.setContentTypeCurriculo(resultSet.getString("contentTypeCurriculo"));
			usuario.setAtivo(resultSet.getBoolean("ativo"));
			usuario.setSexo(resultSet.getString("sexo"));
			usuario.setPerfil(resultSet.getString("perfil"));

			lista.add(usuario);
		}
		return lista;
		
	}

	public void delete(String id) {

		try {

			String sql = "delete from usuario where id = '" + id + "' and login <> 'admin'";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();

			try {
				connection.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}

		}

	}

	public BeanCursoJsp consultar(String id) throws Exception { // consulta para realizar update

		String sql = ("select * from usuario where id ='" + id + "' and login <> 'admin'");

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();

			beanCursoJsp.setId(resultSet.getLong("id"));
			beanCursoJsp.setLogin(resultSet.getString("login"));
			beanCursoJsp.setSenha(resultSet.getString("senha"));
			beanCursoJsp.setNome(resultSet.getString("nome"));
			beanCursoJsp.setFone(resultSet.getString("fone"));
			beanCursoJsp.setRua(resultSet.getString("rua"));
			beanCursoJsp.setBairro(resultSet.getString("bairro"));
			beanCursoJsp.setCidade(resultSet.getString("cidade"));
			beanCursoJsp.setEstado(resultSet.getString("estado"));
			beanCursoJsp.setIbge(resultSet.getString("ibge"));
			beanCursoJsp.setCep(resultSet.getString("cep"));
			beanCursoJsp.setFotoBase64(resultSet.getString("fotoBase64"));
			beanCursoJsp.setContentType(resultSet.getString("fcontenttype"));
			beanCursoJsp.setCurriloBase64(resultSet.getString("curriloBase64"));
			beanCursoJsp.setContentTypeCurriculo(resultSet.getString("contentTypeCurriculo"));
			beanCursoJsp.setFotoBase64Miniatura(resultSet.getString("fotoBase64Miniatura"));
			beanCursoJsp.setAtivo(resultSet.getBoolean("ativo"));
			beanCursoJsp.setSexo(resultSet.getString("sexo"));
			beanCursoJsp.setPerfil(resultSet.getString("perfil"));

			return beanCursoJsp;
		}
		return null;
	}

	public Boolean validarLogin(String login, String senha) throws Exception {

		String sql = ("select count(1) as qtd from usuario where login ='" + login + "' or senha = '" + senha + "'");

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0; // Return true
		}
		return false;
	}

	public Boolean validarLoginUpdate(String login, String id) throws Exception {

		String sql = ("select count(1) as qtd from usuario where login ='" + login + "' and id <> " + id);

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0; // Return true
		}
		return false;
	}

	public void atualizar(BeanCursoJsp usuario) {

		StringBuilder sql = new StringBuilder();

		try {
			sql.append(" update usuario set login = ?, senha = ?, nome = ?, fone = ?");
			sql.append(", rua = ?, bairro = ?, cidade = ?, estado = ?");
			sql.append(", ibge = ?, cep = ?, ativo = ?, sexo = ?, perfil = ?");

			if (usuario.isAtualizarImagem()) {
				sql.append(", fotoBase64 = ?, fcontenttype = ?");
			}

			if (usuario.isAtualizarPFD()) {
				sql.append(", curriloBase64 = ?, contentTypeCurriculo = ?");
			}

			if (usuario.isAtualizarImagem()) {
				sql.append(", fotoBase64Miniatura = ?");
			}
			sql.append(" where id = " + usuario.getId());

			PreparedStatement statement = connection.prepareStatement(sql.toString());
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			statement.setString(3, usuario.getNome());
			statement.setString(4, usuario.getFone());
			statement.setString(5, usuario.getRua());
			statement.setString(6, usuario.getBairro());
			statement.setString(7, usuario.getCidade());
			statement.setString(8, usuario.getEstado());
			statement.setString(9, usuario.getIbge());
			statement.setString(10, usuario.getCep());
			statement.setBoolean(11, usuario.isAtivo());
			statement.setString(12, usuario.getSexo());
			statement.setString(13, usuario.getPerfil());

			if (usuario.isAtualizarImagem()) {
				statement.setString(14, usuario.getFotoBase64());
				statement.setString(15, usuario.getContentType());

			}

			if (usuario.isAtualizarPFD()) {
				if (usuario.isAtualizarPFD() && !usuario.isAtualizarImagem()) {
					statement.setString(14, usuario.getCurriloBase64());
					statement.setString(15, usuario.getContentTypeCurriculo());
				} else {

					statement.setString(16, usuario.getCurriloBase64());
					statement.setString(17, usuario.getContentTypeCurriculo());
				}

			} else {
				if (usuario.isAtualizarImagem()) {
					statement.setString(16, usuario.getFotoBase64Miniatura());
				}

			}

			if (usuario.isAtualizarImagem() && usuario.isAtualizarPFD()) {
				statement.setString(18, usuario.getFotoBase64Miniatura());
			}

			statement.executeUpdate();

			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();

			try {
				connection.rollback();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
