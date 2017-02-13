<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	href="<c:url value="../resources/vendor/bootstrap/css/bootstrap.min.css"/>"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link
	href="<c:url value="../resources/vendor/metisMenu/metisMenu.min.css"/>"
	rel="stylesheet">
<link
	href="<c:url value="../resources/vendor/bootstrap-social/bootstrap-social.css"/>"
	rel="stylesheet">
<!-- Custom CSS -->
<link href="<c:url value="../resources/dist/css/sb-admin-2.css"/>"
	rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="<c:url value="../resources/vendor/font-awesome/css/font-awesome.min.css"/>"
	rel="stylesheet" type="text/css">

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
						<p class="h4">Welcome to Pull Request Manager</p>
					</div>
					<div class="panel-body">
						<form role="form">
							<div id="formSignIn" class="form-group">
								<label>Identifiant client GitHub</label><input id="clientId"
									class="form-control" />
								<label>Client secret GitHub</label><input id="clientSecret"
									class="form-control" />
								<div class="panel-footer">
									<a class="btn btn-block btn-social btn-github" style="margin-top:5px;"
										href="<c:url value="/authentGit" />"
										onclick="window.location=this.href+'&client_id='+document.getElementById('clientId').value+'&secret_id='+document.getElementById('secretId').value;return false;">
										<i class="fa fa-github"></i>Sign up with GitHub
									</a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- jQuery -->
	<script src="<c:url value="../resources/vendor/jquery/jquery.min.js"/>"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="<c:url value="../resources/vendor/bootstrap/js/bootstrap.min.js"/>"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="<c:url value="../resources/vendor/metisMenu/metisMenu.min.js"/>"></script>

	<!-- Custom Theme JavaScript -->
	<script src="<c:url value="../resources/dist/js/sb-admin-2.js"/>"></script>

	<script src="<c:url value="../resources/js/login.js"/>"></script>
</body>

</html>
