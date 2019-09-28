<%@page import="beans.BeanCursoJsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Cadastro de Usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">


<script src="https://code.jquery.com/jquery-3.4.1.min.js"
	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
	crossorigin="anonymous"></script>

</head>

<body>
	<a href="acessoliberado.jsp"><img alt="Inicio" title="Início"
		src="resources/img/home.jpeg" height="48px" width="48px"></a>
	<a href="index.jsp"><img alt="Sair" title="Sair"
		src="resources/img/sair.png" height="48px" width="48px"></a>
	<center>
		<h1>Cadastro de usuário</h1>
		<h3 style="color: red;">${msg}</h3>
	</center>

	<form action="salvarUsuario" method="post" id="formUser"
		onsubmit="return validarCampos() ? true : false;"
		enctype="multipart/form-data">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${user.id}" style="width: 193px;" class="field-long"></td>
						<td>Cep:</td>
						<td><input type="text" id="cep" name="cep"
							placeholder="Informe um cep válido" value="${user.cep}"
							onblur="consultaCep();"></td>
					</tr>
					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login" maxlength="10"
							placeholder="Informe um login" value="${user.login}"></td>
						<td>Rua:</td>
						<td><input type="text" id="rua" name="rua" maxlength="50"
							placeholder="Informe a rua" value="${user.rua}"></td>
					</tr>

					<tr>
						<td>Senha:</td>
						<td><input type="password" id="senha" name="senha"
							maxlength="10" placeholder="Informe uma senha"
							value="${user.senha}"></td>
						<td>Bairro:</td>
						<td><input type="text" id="bairro" name="bairro"
							maxlength="50" placeholder="Informe o bairro"
							value="${user.bairro}"></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome" maxlength="50"
							placeholder="Informe o nome do usuário" value="${user.nome}"></td>
						<td>Cidade:</td>
						<td><input type="text" id="cidade" name="cidade"
							maxlength="50" placeholder="Informe a cidade"
							value="${user.cidade}"></td>
					</tr>
					<tr>
						<td>Estado:</td>
						<td><input type="text" id="estado" name="estado"
							maxlength="2" placeholder="Informe o estado"
							value="${user.estado}"><td>Ativo:</td>

						<td><input type="checkbox" id="ativo" name="ativo"
							<%if (request.getAttribute("user") != null) {
								BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
			
								if (user.isAtivo()) {
									out.print(" ");
									out.print("checked=\"checked\"");
									out.print(" ");
								}
							}%>>
						</td>



					</tr>

					<tr>
						<td>IBGE:</td>
						<td><input type="text" id="ibge" name="ibge" maxlength="20"
							placeholder="Informe um nº do IBGE" value="${user.ibge}">
							
							<td>Currículo:</td>
						<td><input type="file" name="curriculo" value="curriculo"></td>
						
					</tr>

					<tr>
					
					<td>Perfil:</td>
						<td>
							<select id="perfil" name="perfil" style="width: 193px; height: 30px">
								<option value="nao_informado">[--SELECIONE--]</option>
								
								<option value="administrador"
								
								<%if (request.getAttribute("user") != null) {
									BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
					
									if (user.getPerfil().equalsIgnoreCase("administrador")){
										out.print(" ");
										out.print("selected=\"selected\"");
										out.print(" ");
									}
								}%>
								>Administrador</option>
								
								<option value="secretario"
								
								<%if (request.getAttribute("user") != null) {
									BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
					
									if (user.getPerfil().equalsIgnoreCase("secretario")){
										out.print(" ");
										out.print("selected=\"selected\"");
										out.print(" ");
									}
								}%>
								>Secretário(a)</option>
								
								<option value="gerente"
								
								<%if (request.getAttribute("user") != null) {
									BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
					
									if (user.getPerfil().equalsIgnoreCase("gerente")){
										out.print(" ");
										out.print("selected=\"selected\"");
										out.print(" ");
									}
								}%>
								>Gerente</option>
								
							</select>
						</td>
						
							<td>Foto:</td>
						<td><input type="file" name="foto" value="foto"></td>
						
						
					</tr>

					<tr>
					
						<td>Sexo:</td>
						<td><input type="radio" name="sexo"
							<%if (request.getAttribute("user") != null) {

				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.getSexo().equalsIgnoreCase("masculino")) {
					out.print(" ");
					out.print("checked=\"checked\"");
					out.print(" ");
				}

			}%>
							value="masculino">Masculino</input> <input type="radio"
							name="sexo"
							<%if (request.getAttribute("user") != null) {

				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.getSexo().equalsIgnoreCase("feminino")) {
					out.print(" ");
					out.print("checked=\"checked\"");
					out.print(" ");
				}

			}%>
							value="feminino">Feminino</input></td>
					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="Salvar"> <input
							type="submit" value="Cancelar"
							onclick="document.getElementById('formUser').action ='salvarUsuario?acao=reset'"></td>
					</tr>
				</table>

			</li>
		</ul>
	</form>
	
	<form method="post" action="servletPesquisa" style="width: 90%">
	<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Descrição </td>
						<td><input type="text" id="descricaoConsulta" name="descricaoConsulta"> </td>
						<td><input type="submit" value="Pesquisar"> </td>
					</tr>

				</table> 
			</li> 
	</ul>
	</form>				


	<div class="container">
		<table class="responsive-table">
			<caption>Lista de Usuários</caption>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Foto</th>
				<th>Currículo</th>
				<th>Telefones</th>
				<th>Delete</th>
				<th>Editar</th>

			</tr>
			<c:forEach items="${usuarios}" var="user">
				<tr>
					<td style="width: 150px"><c:out value="${user.id}"></c:out></td>
					<td><c:out value="${user.nome}"></c:out></td>

					<c:if test="${user.fotoBase64Miniatura.isEmpty() == false}">
						<td><a
							href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}"><img
								src='<c:out value="${user.fotoBase64Miniatura}"></c:out>'
								alt="Imagem User" title="Imagem User" width="20px" height="20px"></a></td>
					</c:if>
					<c:if test="${user.fotoBase64Miniatura == null}">
						<td><img alt="Imagem User" src="resources/img/fotoPadrao.jpg"
							width="32px" height="32px" onclick="alert('Não possui imagem')">
						</td>
					</c:if>

					<c:if test="${user.curriloBase64.isEmpty() == false}">=		
					<td><a
							href="salvarUsuario?acao=download&tipo=curriculo&user=${user.id}"><img
								alt="Curriculo" src="resources/img/fotoPDF.png" width="32px"
								height="32px"></a></td>
					</c:if>

					<c:if test="${user.curriloBase64 == null}">
						<td><img alt="Curriculo" src="resources/img/pdfvazio.png"
							width="32px" height="32px"
							onclick="alert('Não possui curriculo')"></td>
					</c:if>

					<td><a href="salvarTelefones?acao=addFone&user=${user.id}"><img
							alt="Telefone" title="Telefones" src="resources/img/phone.png"
							width="20px" height="20px"></a></td>

					<td><a href="salvarUsuario?acao=delete&user=${user.id}"
						onclick="return confirm('Confirmar a exclusão?');"><img
							src="resources/img/excluir.png" alt="excluir" title="Excluir"
							width="20px" height="20px"> </a></td>

					<td><a href="salvarUsuario?acao=editar&user=${user.id}"><img
							alt="Editar" title="Editar" src="resources/img/editar.png"
							width="20px" height="20px"></a></td>

				</tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		function validarCampos() {

			if (document.getElementById("login").value == '') {
				alert('Informe o login');
				return false;
			} else if (document.getElementById("nome").value == '') {
				alert('Informe o nome');
				return false;
			} else if (document.getElementById("senha").value == '') {
				alert('Informe a senha');
				return false;
			} else if (document.getElementById("fone").value == '') {
				alert('Informe o telefone');
				return false;
			}

			return true;
		}

		function consultaCep() {
			var cep = $("#cep").val();

			//Consulta o webservice viacep.com.br/
			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {

						if (!("erro" in dados)) {
							$("#rua").val(dados.logradouro);
							$("#bairro").val(dados.bairro);
							$("#cidade").val(dados.localidade);
							$("#estado").val(dados.uf);
							$("#ibge").val(dados.ibge);

						} else {
							$("#cep").val('');
							$("#rua").val('');
							$("#bairro").val('');
							$("#cidade").val('');
							$("#estado").val('');
							$("#ibge").val('');

							alert("CEP não encontrado.");
						}
					});

		}
	</script>

</body>

</html>