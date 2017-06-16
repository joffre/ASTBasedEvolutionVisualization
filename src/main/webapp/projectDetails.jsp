<%@page import="entity.CommitDTO"%>
<%@page import="entity.RepositoryDTO"%>
<%@page import="org.kohsuke.github.GHCommit"%>
<%@page import="org.kohsuke.github.GHRepository"%>
<%-- <%@page import="org.eclipse.egit.github.core.Repository"%> --%>
<%-- <%@page import="org.eclipse.egit.github.core.RepositoryCommit"%> --%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.hamesc.opl.utils.ConstantUtils"%>
<%@page import="java.util.Map"%>
<%-- <%@page import="org.eclipse.egit.github.core.PullRequest"%> --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Project Details - AST.B.E.V</title>

<!-- Bootstrap Core CSS -->
<link
	href="<%= request.getContextPath() %>/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">

<!-- MetisMenu CSS -->
<link
	href="<%= request.getContextPath() %>/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet" type="text/css">

<!-- DataTables CSS -->
<link
	href="<%= request.getContextPath() %>/resources/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet" type="text/css">

<!-- DataTables Responsive CSS -->
<link
	href="<%= request.getContextPath() %>/resources/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet" type="text/css">

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
			<a class="navbar-brand" href="<%= request.getContextPath() %>/dashboard">AST Based Evolution Visualization</a>
		</div>
		<!-- /.navbar-header -->

		<ul class="nav navbar-top-links navbar-right">
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
					<li><a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>User
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
					<h1 class="page-header">Java Evolution of <%= request.getParameter("name") %> Project</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
			   <script type="text/javascript">
			     google.charts.load('current', {packages: ['corechart']});
			     
			     google.charts.setOnLoadCallback(drawChart);
			     function drawChart() {
						var diffs = google.visualization.arrayToDataTable([
					     	<%= (String) request.getAttribute("diffs") %>
					     ]);	
						console.log(diffs);
					
					   // Define the chart to be drawn.
					   var chart = new google.visualization.ColumnChart(
	            		document.getElementById('chart_div'));
	        			chart.draw(diffs, {'isStacked': true, 'legend': 'bottom',
	            		'vAxis': {'title': 'Number of Actions'}});
					   
					}

			   </script>
			   
			   <div id="chart_div" style="width: 750px; height: 400px; margin: 0 auto"></div>
			   
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Commit on project :
						<%= request.getParameter("name") %></h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
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
										
										<th>Detail</th>
										<th>Author</th>
										<th>Date</th>
										<th>Additions</th>
										<th>Deletions</th>
										<th>See on Github</th>
									</tr>
								</thead>
								<tbody>
									<% if(request.getAttribute("commits") != null){ %>
									<% for(CommitDTO commit : (List<CommitDTO>) request.getAttribute("commits")){ %>
										<tr>
											
											<td><%= commit.getMessage() %>
											<td><a href="<%= (commit.getAuthor() != null)?commit.getAuthor().getHtmlUrl():"#" %>"><strong><%= (commit.getAuthor()!=null)?commit.getAuthor().getName() : "<unknow>" %></strong></a></td>
											<td><%= commit.getCommitDate() %></td>
											<td class="center"><%= commit.getLinesAdded() %></td>
											<td class="center"><%= commit.getLinesDeleted() %></td>
											<td><a href="<%= commit.getHtmlUrl() %>"><strong>Link</strong></a></td>
										</tr>
										<% } }%>
								</tbody>
							</table>							
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
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

		<!-- DataTables JavaScript -->
		<script
			src="<%= request.getContextPath() %>/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
		<script
			src="<%= request.getContextPath() %>/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
		<script
			src="<%= request.getContextPath() %>/resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

		<!-- Custom Theme JavaScript -->
		<script src="<%= request.getContextPath() %>/resources/dist/js/sb-admin-2.js"></script>

		<!-- Page-Level Demo Scripts - Tables - Use for reference -->
		<script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
    });
    </script>
</body>

</html>
