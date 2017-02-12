<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Login ASTBasedEvolutionVisualization</title>

<!-- Bootstrap Core CSS -->
<link
	href="<%= request.getContextPath() %>/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

<!-- MetisMenu CSS -->
<link
	href="<%= request.getContextPath() %>/resources/vendor/metisMenu/metisMenu.min.css"  rel="stylesheet" type="text/css">
<link
	href="<%= request.getContextPath() %>/resources/vendor/bootstrap-social/bootstrap-social.css" rel="stylesheet" type="text/css">
<!-- Custom CSS -->
<link href="<%= request.getContextPath() %>/resources/dist/css/sb-admin-2.css" rel="stylesheet" type="text/css">

<!-- Custom Fonts -->
<link
	href="<%= request.getContextPath() %>/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"/>"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"/>"></script>
    <![endif]-->

</head>

<body>

	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading">
						<p class="h4">Welcome to AST Based Evolution Visualization</p>
					</div>
					<div class="panel-body">
						<form action="<%= request.getContextPath() %>/login" method="post">
							<div id="formSignIn" class="form-group">
								<label>Nom d'utilisateur GitHub</label><input id="usernameGitHub" name="usernameGitHub" class="form-control" />
								<label>Mot de passe GitHub</label><input id="passwordGitHub" name="passwordGitHub" type="password" class="form-control" />
								<div class="panel-footer">
								<button type="submit" class="btn btn-block btn-social btn-github" style="margin-top:5px;">
									<i class="fa fa-github"></i>Sign up with GitHub</button>
<%-- 									<a class="btn btn-block btn-social btn-github" style="margin-top:5px;"
										href="<c:url value="/dashboard"/>"
										onclick="window.location=this.href+'?username='+document.getElementById('usernameGitHub').value;return false;">
										<i class="fa fa-github"></i>Sign up with GitHub
									</a> --%>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- jQuery -->
	<script src="<%= request.getContextPath() %>/resources/vendor/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="<%= request.getContextPath() %>/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="<%= request.getContextPath() %>/resources/vendor/metisMenu/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="<%= request.getContextPath() %>/resources/dist/js/sb-admin-2.js"></script>

	<script src="<%= request.getContextPath() %>/resources/js/login.js"></script>
</body>

</html>
