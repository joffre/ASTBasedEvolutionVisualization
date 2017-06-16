<%@page import="entity.CommitDTO"%>
<%@page import="entity.RepositoryDTO"%>
<%@page import="org.kohsuke.github.GHCommit"%>
<%@page import="org.kohsuke.github.GHRepository"%>
<%-- <%@page import="org.eclipse.egit.github.core.RepositoryCommit"%> --%>
<%-- <%@page import="org.eclipse.egit.github.core.Repository"%> --%>
<%@page import="com.hamesc.opl.utils.ConstantUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%-- <%@page import="java.util.Map"%> --%>
<%-- <%@page import="org.eclipse.egit.github.core.PullRequest"%> --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Dashboard AST Based Evolution Visualization</title>
<!-- Bootstrap Core CSS -->
<link
	href="<%= request.getContextPath() %>/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

<!-- MetisMenu CSS -->
<link
	href="<%= request.getContextPath() %>/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet" type="text/css">

<!-- Custom CSS -->
<link href="<%= request.getContextPath() %>/resources/dist/css/sb-admin-2.css" rel="stylesheet" type="text/css">

<!-- Morris Charts CSS -->
<link href="<%= request.getContextPath() %>/resources/vendor/morrisjs/morris.css" rel="stylesheet" type="text/css">

<!-- Custom Fonts -->
<link
	href="<%= request.getContextPath() %>/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.html">AST Based Evolution Visualization</a>
		</div>
		<!-- /.navbar-header -->

		<ul class="nav navbar-top-links navbar-right">
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#"> <i class="fa fa-envelope fa-fw"></i>
					<i class="fa fa-caret-down"></i>
			</a>
				<ul class="dropdown-menu dropdown-messages">
					<li><a href="#">
							<div>
								<strong>John Smith</strong> <span class="pull-right text-muted">
									<em>Yesterday</em>
								</span>
							</div>
							<div>Lorem ipsum dolor sit amet, consectetur adipiscing
								elit. Pellentesque eleifend...</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<strong>John Smith</strong> <span class="pull-right text-muted">
									<em>Yesterday</em>
								</span>
							</div>
							<div>Lorem ipsum dolor sit amet, consectetur adipiscing
								elit. Pellentesque eleifend...</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<strong>John Smith</strong> <span class="pull-right text-muted">
									<em>Yesterday</em>
								</span>
							</div>
							<div>Lorem ipsum dolor sit amet, consectetur adipiscing
								elit. Pellentesque eleifend...</div>
					</a></li>
					<li class="divider"></li>
					<li><a class="text-center" href="#"> <strong>Read
								All Messages</strong> <i class="fa fa-angle-right"></i>
					</a></li>
				</ul> <!-- /.dropdown-messages --></li>
			<!-- /.dropdown -->
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#"> <i class="fa fa-tasks fa-fw"></i>
					<i class="fa fa-caret-down"></i>
			</a>
				<ul class="dropdown-menu dropdown-tasks">
					<li><a href="#">
							<div>
								<p>
									<strong>Task 1</strong> <span class="pull-right text-muted">40%
										Complete</span>
								</p>
								<div class="progress progress-striped active">
									<div class="progress-bar progress-bar-success"
										role="progressbar" aria-valuenow="40" aria-valuemin="0"
										aria-valuemax="100" style="width: 40%">
										<span class="sr-only">40% Complete (success)</span>
									</div>
								</div>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<p>
									<strong>Task 2</strong> <span class="pull-right text-muted">20%
										Complete</span>
								</p>
								<div class="progress progress-striped active">
									<div class="progress-bar progress-bar-info" role="progressbar"
										aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
										style="width: 20%">
										<span class="sr-only">20% Complete</span>
									</div>
								</div>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<p>
									<strong>Task 3</strong> <span class="pull-right text-muted">60%
										Complete</span>
								</p>
								<div class="progress progress-striped active">
									<div class="progress-bar progress-bar-warning"
										role="progressbar" aria-valuenow="60" aria-valuemin="0"
										aria-valuemax="100" style="width: 60%">
										<span class="sr-only">60% Complete (warning)</span>
									</div>
								</div>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<p>
									<strong>Task 4</strong> <span class="pull-right text-muted">80%
										Complete</span>
								</p>
								<div class="progress progress-striped active">
									<div class="progress-bar progress-bar-danger"
										role="progressbar" aria-valuenow="80" aria-valuemin="0"
										aria-valuemax="100" style="width: 80%">
										<span class="sr-only">80% Complete (danger)</span>
									</div>
								</div>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a class="text-center" href="#"> <strong>See
								All Tasks</strong> <i class="fa fa-angle-right"></i>
					</a></li>
				</ul> <!-- /.dropdown-tasks --></li>
			<!-- /.dropdown -->
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#"> <i class="fa fa-bell fa-fw"></i>
					<i class="fa fa-caret-down"></i>
			</a>
				<ul class="dropdown-menu dropdown-alerts">
					<li><a href="#">
							<div>
								<i class="fa fa-comment fa-fw"></i> New Comment <span
									class="pull-right text-muted small">4 minutes ago</span>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<i class="fa fa-twitter fa-fw"></i> 3 New Followers <span
									class="pull-right text-muted small">12 minutes ago</span>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<i class="fa fa-envelope fa-fw"></i> Message Sent <span
									class="pull-right text-muted small">4 minutes ago</span>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<i class="fa fa-tasks fa-fw"></i> New Task <span
									class="pull-right text-muted small">4 minutes ago</span>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<i class="fa fa-upload fa-fw"></i> Server Rebooted <span
									class="pull-right text-muted small">4 minutes ago</span>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a class="text-center" href="#"> <strong>See
								All Alerts</strong> <i class="fa fa-angle-right"></i>
					</a></li>
				</ul> <!-- /.dropdown-alerts --></li>
			<!-- /.dropdown -->
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
					<i class="fa fa-caret-down"></i>
			</a>
				<ul class="dropdown-menu dropdown-user">
					<li><a href="#"><i class="fa fa-user fa-fw"></i> User
							Profile</a></li>
					<li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
					</li>
					<li class="divider"></li>
					<li><a href="logout"><i class="fa fa-sign-out fa-fw"></i>
							Logout</a></li>
				</ul> <!-- /.dropdown-user --></li>
			<!-- /.dropdown -->
		</ul>
		<!-- /.navbar-top-links -->

		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">
					<li class="sidebar-search">
						<div class="input-group custom-search-form">
							<input type="text" class="form-control" placeholder="Search...">
							<span class="input-group-btn">
								<button class="btn btn-default" type="button">
									<i class="fa fa-search"></i>
								</button>
							</span>
						</div> <!-- /input-group -->
					</li>
					<li><a href="dashboard"><i class="fa fa-dashboard fa-fw"></i>
							Dashboard</a></li>
					<li><a id="expandProject" href="#"><i class="fa fa-bar-chart-o fa-fw active"></i>User
							projects<span class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<% for(RepositoryDTO repo : (List<RepositoryDTO>) session.getAttribute(ConstantUtils.ID_SESSION_REPOS)){ %>
							
							<li><a href="<%= request.getContextPath() %>/projectDetails?name=<%=repo.getFullName() %>"><%=repo.getName() %></a>
							</li>
							<% } %>
						</ul> <!-- /.nav-second-level --></li>
					<li><a href="dashboard"><i class="fa fa-tasks fa-fw"></i>Tasks</a>
					</li>
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side --> </nav>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Dashboard</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-file-o fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge"><%= ((List<GHCommit>) session.getAttribute(ConstantUtils.ID_SESSION_COMMITS)).size() %></div>
									<div>Commits !</div>
								</div>
							</div>
						</div>
						<a href="#">
							<div class="panel-footer">
								<span class="pull-left">View Details</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-green">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-tasks fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge"><%= ((List<GHRepository>) session.getAttribute(ConstantUtils.ID_SESSION_REPOS)).size() %></div>
									<div>Github project</div>
								</div>
							</div>
						</div>
						<a href="#" onclick="document.getElementById('expandProject').click();">
							<div class="panel-footer">
								<span class="pull-left">View Details</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
                <!-- <div class="col-lg-3 col-md-6">
                    <div class="panel panel-red">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-support fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">${sessionScope.PLUGIN_NB}</div>
                                    <div>Plugins</div>
                                </div>
                            </div>
                        </div>
                        <a href="/PRM-Web/plugin">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>-->
			</div>
				<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">All your commit</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<!-- /.panel-heading -->
						<div class="panel-body">
							<table width="100%"
								class="table table-striped table-bordered table-hover"
								id="dataTables-example">
								<thead>
									<tr>
										<th>Date</th>
										<th>Detail</th>
										<th>Author</th>
										<th>See on Github</th>
									</tr>
								</thead>
								<tbody>
									<% for (CommitDTO commit : (List<CommitDTO>) session.getAttribute(ConstantUtils.ID_SESSION_COMMITS)){ %>
									
										<tr>
											<td><%= commit.getCommitDate() %></td>
											<td><%= commit.getMessage() %></td>
											<td><%= (commit.getAuthor() != null)?commit.getAuthor().getName():"<unknown>" %></td>
											<td><a href="<%= (commit.getHtmlUrl() != null)?commit.getHtmlUrl():"#" %>"><strong>Link</strong></a></td>
										</tr>
									<% } %>
								</tbody>
							</table>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
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

			<!-- Morris Charts JavaScript -->
			<script
				src="<c:url value="../resources/vendor/raphael/raphael.min.js"/>"></script>
			<script
				src="<c:url value="../resources/vendor/morrisjs/morris.min.js"/>"></script>
			<script src="<c:url value="../resources/data/morris-data.js"/>"></script>

			<!-- Custom Theme JavaScript -->
			<script src="<c:url value="../resources/dist/js/sb-admin-2.js"/>"></script>
			<!-- DataTables JavaScript -->
		<script
			src="<c:url value="../resources/vendor/datatables/js/jquery.dataTables.min.js"/>"></script>
		<script
			src="<c:url value="../resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"/>"></script>
		<script
			src="<c:url value="../resources/vendor/datatables-responsive/dataTables.responsive.js"/>"></script>
					<!-- Page-Level Demo Scripts - Tables - Use for reference -->
		<script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
    });
    $(document).ready(function() {
        $('#dataTables-example_filter').css("float","right");
    });
    </script>
</body>

</html>
