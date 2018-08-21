<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Creative - Bootstrap 3 Responsive Admin Template">
<meta name="author" content="GeeksLabs">
<meta name="keyword"
	content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
<link rel="shortcut icon" href="img/favicon.png">

<title>Security Ecosystem</title>

<!-- Bootstrap CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- bootstrap theme -->
<link href="css/bootstrap-theme.css" rel="stylesheet">
<!--external css-->
<!-- font icon -->
<link href="css/elegant-icons-style.css" rel="stylesheet" />
<link href="css/font-awesome.css" rel="stylesheet" />
<!-- Custom styles -->
<link href="css/style.css" rel="stylesheet">
<link href="css/style-responsive.css" rel="stylesheet" />
<link href="https://fonts.googleapis.com/css?family=Gugi" rel="stylesheet"> 
<!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
<!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->

<!-- =======================================================
      Theme Name: NiceAdmin
      Theme URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
      Author: BootstrapMade
      Author URL: https://bootstrapmade.com
    ======================================================= -->
</head>

<body class="login-img3-body">




	<div class="container">
		<%
			String msg = request.getParameter("msg");
		%>
		<%
			if (msg != null) {
		%>
		<div class="alert alert-success alert-dismissable">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>Message!</strong>
			<%=msg%>.
		</div>
		<%
			}
		%>
<h2 class="title">THE SECURITY ECOSYSTEM</h2>
<img class="gify" alt="not visible0" src="img/cube.gif" height=25% width=25%>




		<form class="login-form" method="post" action="account">
			<div class="login-wrap">
				<input type="hidden" name='request_type' value='login' /> <label>Email:</label>
				<input type="text" name="email" class='form-control' />
				<div class="cleaner_h10"></div>
				<br /> <label>Password:</label> <input type="password"
					name="password" class="form-control" />
				<div class="cleaner_h10"></div>
				<br />
				<button class="btn btn-primary btn-lg btn-block" type="submit">Login</button>
				<a href='register.jsp' class="btn btn-lg btn-block btn-default" style="color:white;">Signup</a>
			</div>
		</form>



</body>

</html>
