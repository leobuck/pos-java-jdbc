package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.Telefone;
import model.UserFone;
import model.Userposjava;

public class UserposDAO {

	private Connection connection;
	
	public UserposDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(Userposjava userposjava) {
		
		try {
			String sql = "insert into userposjava (nome, email ) values (?, ?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, userposjava.getNome());
			insert.setString(2, userposjava.getEmail());
			insert.execute();
			connection.commit();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	public void salvarTelefone(Telefone telefone) {
		
		try {
			String sql = "insert into telefone_user (numero, tipo, usuariopessoa) values (?, ?, ?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuario());
			insert.execute();
			connection.commit();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	public List<Userposjava> listar() throws SQLException {
		List<Userposjava> list = new ArrayList<Userposjava>();
		
		String sql = "select * from userposjava";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			Userposjava userposjava = new Userposjava();
			userposjava.setId(resultSet.getLong("id"));
			userposjava.setNome(resultSet.getString("nome"));
			userposjava.setEmail(resultSet.getString("email"));
			
			list.add(userposjava);
		}
		
		return list;
	}
	
	public Userposjava buscar(Long id) throws SQLException {
		Userposjava userposjava = new Userposjava();
		
		String sql = "select * from userposjava where id = " + id;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			userposjava.setId(resultSet.getLong("id"));
			userposjava.setNome(resultSet.getString("nome"));
			userposjava.setEmail(resultSet.getString("email"));
		}
		
		return userposjava;
	}

	public List<UserFone> buscarUserFone(Long id) throws SQLException {
		List<UserFone> userFones = new ArrayList<UserFone>();
		
		String sql = " select numero, nome, email ";
		sql += " from telefone_user as fone ";
		sql += " inner join userposjava as userp on fone.usuariopessoa = userp.id ";
		sql += " where userp.id = " + id;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			UserFone userFone = new UserFone();
			userFone.setNumero(resultSet.getString("numero"));
			userFone.setNome(resultSet.getString("nome"));
			userFone.setEmail(resultSet.getString("email"));
			userFones.add(userFone);
		}
		
		return userFones;
	}

	public void atualizar(Userposjava userposjava) {
		
		try {
			
			String sql = "update userposjava set nome = ? where id = " + userposjava.getId();
			
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, userposjava.getNome());
			
			statement.execute();
			
			connection.commit();
		
		} catch (SQLException e) {
			try {
				
				connection.rollback();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	public void deletar(Long id) {
		try {
			String sql = "delete from userposjava where id = " + id;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public void deletarFonesPorUser(Long id) {
		try {
			String sqlFone = "delete from telefone_user where usuariopessoa = " + id;
			String sqlUser = "delete from userposjava where id = " + id;
			
			PreparedStatement statement = connection.prepareStatement(sqlFone);
			statement.executeUpdate();
			connection.commit();
			
			statement = connection.prepareStatement(sqlUser);
			statement.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
}
