package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCategoria;
import beans.BeanProduto;
import connection.SingleConnection;

public class DaoProduto {

	Connection connection;

	public DaoProduto() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanProduto beanProduto) {

		String sql = "insert into produtos (nome, quantidade, valor, categoria_id) values (?, ?, ?, ?)";

		try {

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, beanProduto.getNome());
			statement.setDouble(2, beanProduto.getQuantidade());
			statement.setDouble(3, beanProduto.getValor());
			statement.setLong(4, beanProduto.getCategoria_id());
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

	public List<BeanProduto> listar() throws Exception {
		List<BeanProduto> lista = new ArrayList<BeanProduto>();

		String sql = "select * from produtos";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			BeanProduto produto = new BeanProduto();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQuantidade(resultSet.getDouble("quantidade"));
			produto.setValor(resultSet.getDouble("valor"));
			produto.setCategoria_id(resultSet.getLong("categoria_id"));

			lista.add(produto);

		}

		return lista;
	}
	
	public List<BeanCategoria> listaCategorias() throws Exception {
		List<BeanCategoria> retorno = new ArrayList<BeanCategoria>();
		
		String sql = "select * from categoria";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			BeanCategoria categoria = new BeanCategoria();
			categoria.setId(resultSet.getLong("id"));
			categoria.setNome(resultSet.getString("nome"));
			
			retorno.add(categoria);
		}
		
		return retorno;
		
	}
	

	public Boolean validarNome(String nome) throws SQLException {

		String sql = ("select count(1) as qtd from produtos where nome = '" + nome + "'");

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			return resultSet.getInt("qtd") <= 0; // Return true

		}

		return false;
	}

	public BeanProduto consultar(String id) throws Exception {

		String sql = ("select * from produtos where id ='" + id + "'");

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			BeanProduto produto = new BeanProduto();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQuantidade(resultSet.getDouble("quantidade"));
			produto.setValor(resultSet.getDouble("valor"));
			produto.setCategoria_id(resultSet.getLong("categoria_id"));

			return produto;
		}
		return null;
	}

	public void atualizar(BeanProduto produto) {
		String sql = "update produtos set nome = ?, quantidade = ?, valor = ?, categoria_id = ? where id = " + produto.getId();

		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produto.getNome());
			statement.setDouble(2, produto.getQuantidade());
			statement.setDouble(3, produto.getValor());
			statement.setLong(4, produto.getCategoria_id());
			statement.executeUpdate();

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

	public void delete(String id) {
		try {
			String sql = "delete from produtos where id = '" + id + "'";

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
