package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanProduto;
import dao.DaoProduto;

@WebServlet("/salvarProduto")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProdutoServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "listarTodos";
			String produto = request.getParameter("produto");

			DaoProduto daoProduto = new DaoProduto();

			RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");

			if (acao.equalsIgnoreCase("delete")) {
				daoProduto.delete(produto);
				request.setAttribute("produtos", daoProduto.listar());

			} else if (acao.equalsIgnoreCase("editar")) {
				BeanProduto beanproduto = daoProduto.consultar(produto);
				request.setAttribute("produto", beanproduto);

			} else if (acao.equalsIgnoreCase("listartodos")) {
				request.setAttribute("produtos", daoProduto.listar());
			}

			request.setAttribute("categorias", daoProduto.listaCategorias());
			view.forward(request, response); // Confirmação do redirecionamento

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		DaoProduto daoProduto = new DaoProduto();

		if (acao != null && acao.equalsIgnoreCase("reset")) {

			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");
			String categoria = request.getParameter("categoria_id");

			BeanProduto beanproduto = new BeanProduto();
			beanproduto.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			beanproduto.setNome(nome);
			beanproduto.setQuantidade(
					quantidade.isEmpty() ? Double.parseDouble(quantidade = "0") : Double.parseDouble(quantidade));
			
			beanproduto.setCategoria_id(Long.parseLong(categoria));

			if (valor != null && !valor.isEmpty()) {
				String valorParse = valor.replaceAll("\\.", "");
				valorParse = valorParse.replaceAll("\\,", ".");
				beanproduto.setValor(Double.parseDouble(valorParse));
			}

			try {
				// VALIDA SE OS CAMPOS EST�O PREENCHIDOS
				if (nome == null || nome.isEmpty()) {
					request.setAttribute("msg", "Campo nome está vazio");
					request.setAttribute("produto", beanproduto);

				} else if (quantidade.equalsIgnoreCase("0")) {
					request.setAttribute("msg", "Campo quantidade está vazio");
					request.setAttribute("produto", beanproduto);

				} else if (valor.equalsIgnoreCase("0")) {
					request.setAttribute("msg", "Campo valor está vazio");
					request.setAttribute("produto", beanproduto);

				} else if (id == null || id.isEmpty() && daoProduto.validarNome(nome)) { // VERIFICA ID E SALVA PRODUTO
					daoProduto.salvar(beanproduto);

				} else if (id == null || id.isEmpty() && !daoProduto.validarNome(nome)) { // VALIDA NOME E SET MENSAGEM
																							// PARA O USUARIO
					request.setAttribute("msg", "Produto já existe com o mesmo nome");
					request.setAttribute("produto", beanproduto);

				} else { // ATUALIZA REGISTRO
					daoProduto.atualizar(beanproduto);
				}

				// REDIRECIONA A P�GINA E LISTA OS PRODUTOS NOVAMENTE
				RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				request.setAttribute("categorias", daoProduto.listaCategorias());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
