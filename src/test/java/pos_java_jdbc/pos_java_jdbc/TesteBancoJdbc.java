package pos_java_jdbc.pos_java_jdbc;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import conexaojdbc.SingleConnection;
import dao.UserposDAO;
import model.Telefone;
import model.UserFone;
import model.Userposjava;

public class TesteBancoJdbc {

	@Test
	public void initBanco() {
		SingleConnection.getConnection();
	}
	
	@Test
	public void initSalvar() {
		UserposDAO userposDAO = new UserposDAO();
		Userposjava userposjava = new Userposjava();
		
		userposjava.setNome("Paulo");
		userposjava.setEmail("paulo@email.com");
		userposDAO.salvar(userposjava);
	}

	@Test
	public void initListar() {
		UserposDAO dao = new UserposDAO();
		
		try {
			List<Userposjava> list = dao.listar();
			
			for (Userposjava userposjava : list) {
				System.out.println(userposjava);
				System.out.println("-------------------------------------");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void initBuscar() {
		UserposDAO dao = new UserposDAO();
		
		try {
			Userposjava user = dao.buscar(1L);
			
			System.out.println(user);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void initAtualizar() {
		UserposDAO dao = new UserposDAO();
		
		try {
			Userposjava objBanco = dao.buscar(4L);
			
			objBanco.setNome("Ana Beatriz");
			
			dao.atualizar(objBanco);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void initDeletar() {
		UserposDAO dao = new UserposDAO();
		
		dao.deletar(3L);
	}

	@Test
	public void testInsertTelefone() {
		Telefone telefone = new Telefone();
		telefone.setNumero("(19) 3455-0101");
		telefone.setTipo("residencial");
		telefone.setUsuario(1L);
		
		UserposDAO dao = new UserposDAO();
		dao.salvarTelefone(telefone);
		
	}
	
	@Test
	public void testBuscarUserFone() {
		UserposDAO dao = new UserposDAO();
		
		try {
			List<UserFone> userFones = dao.buscarUserFone(1L);
			
			for (UserFone userFone : userFones) {
				System.out.println(userFone);
				System.out.println("-------------------------------------");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testDeleteUserFone() {
		UserposDAO dao = new UserposDAO();
		
		dao.deletarFonesPorUser(4L);
	}

}
