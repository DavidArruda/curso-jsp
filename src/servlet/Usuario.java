package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

@MultipartConfig
@WebServlet("/salvarUsuario")
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUsuario daoUsuario = new DaoUsuario();

	public Usuario() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");

			if (acao != null && acao.equalsIgnoreCase("delete")) {
				daoUsuario.delete(user);

				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} else if (acao != null && acao.equalsIgnoreCase("editar")) {

				BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);

				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("user", beanCursoJsp);
				view.forward(request, response);

			} else if (acao != null && acao.equalsIgnoreCase("listartodos")) {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);

			} else if (acao != null && acao.equalsIgnoreCase("download")) {
				BeanCursoJsp usuario = daoUsuario.consultar(user);

				if (usuario != null) {
					
					String contentType = "";
					byte[] fileBytes = null;
					
					String tipo = request.getParameter("tipo");
					
					if (tipo.equalsIgnoreCase("imagem")) {
						contentType = usuario.getContentType();
						fileBytes = new Base64().decodeBase64(usuario.getFotoBase64()); // converte a base64 da imagem do banco para byte[]
						
					} else if (tipo.equalsIgnoreCase("curriculo")) {
						contentType = usuario.getContentTypeCurriculo();
						fileBytes = new Base64().decodeBase64(usuario.getCurriloBase64()); // converte a base64 do pdf do banco para byte[]
					}
					
					response.setHeader("Content-Disposition",
							"attachment;filename=arquivo." + contentType.split("\\/")[1]);

					// Coloca os bytes em objeto de entrada para processar
					InputStream is = new ByteArrayInputStream(fileBytes);

					// resposta ao navegador
					int read = 0;
					byte[] bytes = new byte[1024];
					OutputStream os = response.getOutputStream();

					while ((read = is.read(bytes)) != -1) {
						os.write(bytes, 0, read);
					}

					os.flush();
					os.close();
				}

			}else {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {

			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			String fone = request.getParameter("fone");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("estado");
			String ibge = request.getParameter("ibge");
			String sexo = request.getParameter("sexo");
			String perfil = request.getParameter("perfil");
			
			BeanCursoJsp usuario = new BeanCursoJsp();

			usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
			usuario.setFone(fone);
			usuario.setCep(cep);
			usuario.setRua(rua);
			usuario.setBairro(bairro);
			usuario.setCidade(cidade);
			usuario.setEstado(estado);
			usuario.setIbge(ibge);
			usuario.setSexo(sexo);
			usuario.setPerfil(perfil);

			
			if (request.getParameter("ativo") != null && request.getParameter("ativo").equalsIgnoreCase("on")) {
				usuario.setAtivo(true);
			}else {
				usuario.setAtivo(false);
			}
			
			try {

				// file upload de imagens e pdf

				if (ServletFileUpload.isMultipartContent(request)) {

					Part imagemFoto = request.getPart("foto");

					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {

						String fotoBase64 = new Base64().encodeBase64String(convertStreamParByte(imagemFoto.getInputStream()));
						usuario.setFotoBase64(fotoBase64);
						usuario.setContentType(imagemFoto.getContentType());
						
						//Inicio miniatura imagem
						
						/*Transformar em buferred image*/
						byte[] imageByteDecode = new Base64().decodeBase64(fotoBase64);
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));
						
						// Pegar o tipo da imagem
						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB: bufferedImage.getType();
						
						//Criar imagem em miniatura
						BufferedImage resizedImage = new BufferedImage(100, 100, type);
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, 100, 100, null);
						g.dispose(); /*finaliza processo*/
						
						//Escrever imagem novamente
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "png", baos);
						
						
						String miniaturaBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
						
						usuario.setFotoBase64Miniatura(miniaturaBase64);
						//Fim miniatura imagem

					}else {
					
						usuario.setAtualizarImagem(false);
					}
					
					//Processa curriculo
					Part curriculoPdf = request.getPart("curriculo");
					
					if (curriculoPdf != null && curriculoPdf.getInputStream().available() > 0) {
						String curriculoBase64 = new Base64()
								.encodeBase64String(convertStreamParByte(curriculoPdf.getInputStream()));
						
						usuario.setCurriloBase64(curriculoBase64);
						usuario.setContentTypeCurriculo(curriculoPdf.getContentType());
						
					}else {
						usuario.setAtualizarPFD(false);
					}

				}

				// fim upload

				// VALIDA SE OS CAMPOS EST�O PREENCHIDOS
				if (login == null || login.isEmpty()) {
					request.setAttribute("msg", "Campo login est� vazio");
					request.setAttribute("user", usuario);
				} else if (senha == null || senha.isEmpty()) {
					request.setAttribute("msg", "Campo senha est� vazio");
					request.setAttribute("user", usuario);

				} else if (nome == null || nome.isEmpty()) {
					request.setAttribute("msg", "Campo nome est� vazio");
					request.setAttribute("user", usuario);

				} else if (id == null || id.isEmpty() && !daoUsuario.validarLogin(login, senha)) { // VERIFICA SE JA
																									// EXISTE UM USUARIO
																									// COM MESMO LOGIN
																									// OU SENHA
					request.setAttribute("msg", "Usu�rio j� existe com o mesmo login ou senha");// SET MENSSAGEM PARA O
																								// USUARIO
					request.setAttribute("user", usuario);

				} else if (id == null || id.isEmpty() && daoUsuario.validarLogin(login, senha)) { // SALVA NOVO USUARIO
					daoUsuario.salvar(usuario);

				} else if (id != null && !id.isEmpty()) {// VERIFICA ID

					if (!daoUsuario.validarLoginUpdate(login, id)) { // return false // VALIDA LOGIN OU SENHA
						request.setAttribute("msg", "Login ou senha j� existe para outro usu�rio");
						request.setAttribute("user", usuario);
					} else {
						daoUsuario.atualizar(usuario); // ATUALIZA O CADASTRO
						request.setAttribute("msg", "Salvo com sucesso");
					}
				}

				// REDIRECIONA A P�GINA E LISTA OS USUARIOS NOVAMENTE
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Converte a entrada de fluxo de dados da imagem para byte[]
	private static byte[] convertStreamParByte(InputStream imagem) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int reads = imagem.read();

		while (reads != -1) { // enquanto tiver dados em reads
			baos.write(reads);
			reads = imagem.read();
		}

		return baos.toByteArray();

	}

}
