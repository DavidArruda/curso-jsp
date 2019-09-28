<jsp:useBean id="calcula" class="beans.BeanCursoJsp"
	type="beans.BeanCursoJsp" scope="page"></jsp:useBean>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>

<link rel="stylesheet" href="resources/css/estilo.css">

</head>
<body>

	<div class="login-page" align="center">
	<h3>Projeto Didático</h3> <br/>
	<h1>Servlet + JSP + JDBC</h1>
	<span><b>Usuário:</b> admin <br/> <b>Senha:</b> admin</span><br/>
		<div class="form">
			<form action="LoginServlet" method="post" class="login-form">
				Login: <input type="text" id="login" name="login" placeholder="Login"> <br />
				Senha: <input type="password" id="senha" name="senha" placeholder="Senha"> <br />
				 <button type="submit" value="logar">Logar</button>
			</form>

		</div>
		<h3><a style="text-decoration: none" href="https://www.jdevtreinamento.com.br"> Formação Java Web</a></h3>
	</div>

</body>
</html>