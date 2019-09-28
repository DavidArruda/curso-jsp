package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

@WebServlet("/servletPesquisa")
public class ServletPesquisa extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DaoUsuario daoUsuario = new DaoUsuario();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String descricaoPesquisa = request.getParameter("descricaoConsulta");
		
		if (descricaoPesquisa != null && !descricaoPesquisa.trim().isEmpty()) { //TRIM: RETIRA OS ESPAÇOS DA STRING
			try {
				List<BeanCursoJsp> listaUsuarios = daoUsuario.listar(descricaoPesquisa);
				
				// REDIRECIONA A P�GINA E LISTA OS USUARIOS NOVAMENTE
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", listaUsuarios);
				view.forward(request, response);
				
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}else {
			// REDIRECIONA A P�GINA E LISTA OS USUARIOS NOVAMENTE
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			try {
				request.setAttribute("usuarios", daoUsuario.listar());
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			view.forward(request, response);
		}
		
		
		
	}

}
