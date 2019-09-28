<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"
          prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Cadastro de Produto</title>
<script src="resources/javascript/jquery.min.js" type="text/javascript"></script>
<script src="resources/javascript/jquery.maskMoney.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="resources/css/cadastro.css">

</head>

<body>
<a href="acessoliberado.jsp"><img alt="Inicio" title="Início" src="resources/img/home.jpeg" height="48px" width="48px"></a>
<a href="index.jsp"><img alt="Sair" title="Sair" src="resources/img/sair.png" height="48px" width="48px"></a>
	<center>
		<h1>Cadastro de Produto</h1>
		<h3 style="color: red;">${msg}</h3>
	</center>

	<form action="salvarProduto" method="post" id="formUser" onsubmit="return validarCampos() ? true : false;">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${produto.id}" class="field-long"></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome" placeholder="Informe o produto" maxlength="45"
							value="${produto.nome}"></td>
					</tr>

					<tr>
						<td>Quantidade:</td>
						<td><input type="text" id="quantidade" name="quantidade" placeholder="Informe a quantidade" maxlength="6"
							value="${produto.quantidade}"></td>
					</tr>
					
					<tr>
						<td>Valor:</td>
						<td>
							<input type="text" id="valor" name="valor" placeholder="Informe o valor" data-thousands="." data-decimal="," data-precision="2" 
							value="${produto.valorEmTexto}" maxlength="8">
						</td>							
					</tr>
					
					<tr>
						<td>Categoria:</td>
						<td>
							<select id="categorias" name="categoria_id">
								<c:forEach items="${categorias}" var="cat">
									<option value="${cat.id}" id="${cat.id}"
										<c:if test="${cat.id == produto.categoria_id}">
										<c:out value="selected=selected" />
										</c:if>>
									${cat.nome}
									</option>
								</c:forEach>
							
							</select>
						</td>
					</tr>
					
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar" style="width: 95px;"> <input
							type="submit" value="Cancelar" style="width: 95px;"
							onclick="document.getElementById('formUser').action ='salvarProduto?acao=reset'"></td>
					</tr>
				</table>

			</li>
		</ul>
	</form>

	<div class="container">
		<table class="responsive-table">
			<caption>Lista de Produtos</caption>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Quantidade</th>
				<th>Preço R$</th>
				<th>Delete</th>
				<th>Editar</th>
			</tr>
			<c:forEach items="${produtos}" var="produto">
				<tr>
					<td style="width: 150px"><c:out value="${produto.id}">
						</c:out></td>
						
					<td style="width: 150px"><c:out value="${produto.nome}">
						</c:out></td>
						
					<td><c:out value="${produto.quantidade}"></c:out></td>
					
					<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${produto.valor}" /></td>

					<td><a href="salvarProduto?acao=delete&produto=${produto.id}" onclick="return confirm('Confirmar a exclusão?');"><img
							src="resources/img/excluir.png" alt="excluir" title="Excluir"
							width="20px" height="20px"> </a></td>

					<td><a href="salvarProduto?acao=editar&produto=${produto.id}"><img
							alt="Editar" title="Editar" src="resources/img/editar.png"
							width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		function validarCampos() {

			if (document.getElementById("nome").value == '') {
				alert('Informe o nome');
				return false;
			} else if (document.getElementById("nome").value == '') {
				alert('Informe o quantidade');
				return false;
			} else if (document.getElementById("valor").value == '') {
				alert('Informe a valor');
				return false;
			}

			return true;

		}
	</script>

</body>

<script type="text/javascript">
$(function() {
    $('#valor').maskMoney();
 })
  
 $(document).ready(function() {
  $("#quantidade").keyup(function() {
      $("#quantidade").val(this.value.match(/[0-9]*/));
  });
});

</script>

</html>