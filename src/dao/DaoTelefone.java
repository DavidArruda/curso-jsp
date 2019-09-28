package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanTelefone;
import connection.SingleConnection;

public class DaoTelefone {

	Connection connection;

	public DaoTelefone() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanTelefone beanTelefone) {

		String sql = "insert into telefone (numero, tipo, usuario) values (?, ?, ?)";

		try {

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, beanTelefone.getNumero());
			statement.setString(2, beanTelefone.getTipo());
			statement.setLong(3, beanTelefone.getUsuario());
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

	public List<BeanTelefone> listar(Long user) throws Exception {
		List<BeanTelefone> lista = new ArrayList<BeanTelefone>();

		String sql = "select * from telefone where usuario = " + user;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			BeanTelefone telefone = new BeanTelefone();
			telefone.setId(resultSet.getLong("id"));
			telefone.setNumero(resultSet.getString("numero"));
			telefone.setTipo(resultSet.getString("tipo"));
			telefone.setUsuario(resultSet.getLong("usuario"));

			lista.add(telefone);

		}

		return lista;
	}

	public void delete(String id) {
		try {
			String sql = "delete from telefone where id = '" + id + "'";

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

}
