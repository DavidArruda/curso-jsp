<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Cadastro de Telefones</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

</head>

<body>
	<a href="acessoliberado.jsp"><img alt="Inicio" title="Início" src="resources/img/home.jpeg" height="48px" width="48px"></a>
	<a href="index.jsp"><img alt="Sair" title="Sair" src="resources/img/sair.png" height="48px" width="48px"></a>
	<center>
		<h1>Cadastro de telefone</h1>
		<h2 style="color: red;">${msg}</h2>
	</center>

	<form action="salvarTelefones" method="post" id="formUser">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>User:</td>
						<td><input type="text" id="user" name="user" readonly="readonly"   class="field-long" 
							value="${userEscolhido.id}"></td>

						<td><input type="text" id="nome" name="nome" readonly="readonly" 
							class="field-long" value="${userEscolhido.nome}"></td>
					</tr>

					<tr>
						<td>Número:</td>
						<td><input type="text" id="numero" name="numero" placeholder="Informe o número"></td>

						<td><select id="tipo" name="tipo" style="width: 173px;">
								<option>Casa</option>
								<option>Contato</option>
								<option>Celular</option>

						</select></td>

					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="Salvar" style="width: 173px;"/>
						</td>
						<td>
						<input type="submit" value="Voltar" style="width: 173px;" onclick="document.getElementById('formUser').action ='salvarTelefones?acao=voltar'">
						</td>
					</tr>
				</table>

			</li>
		</ul>
	</form>

	<div class="container">
		<table class="responsive-table">
			<caption>Lista de Telefones</caption>
			<tr>
				<th>Id</th>
				<th>Número</th>
				<th>Tipo</th>
				<th>Excluir</th>

			</tr>
			<c:forEach items="${telefones}" var="fone">
				<tr>
					<td style="width: 150px"><c:out value="${fone.id}">
						</c:out></td>
					<td style="width: 150px"><c:out value="${fone.numero}">
						</c:out></td>
					<td><c:out value="${fone.tipo}"></c:out></td>

					<td><a href="salvarTelefones?user=${fone.usuario}&acao=deleteFone&foneId=${fone.id}" onclick="return confirm('Confirmar a exclusão?');"><img
							src="resources/img/excluir.png" alt="excluir" title="Excluir"
							width="20px" height="20px"> </a></td>

				</tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		function validarCampos() {

			if (document.getElementById("numero").value == '') {
				alert('Informe o número');
				return false;
			}
			else
			if (document.getElementById("tipo").value == '') {
				alert('Informe o tipo');
				return false;
			}

			return true;
		}
	</script>

</body>

</html>