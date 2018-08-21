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


		<form class="login-form" method="post" action="account">
			<div class="login-wrap">
				<input type="hidden" name='request_type' value='login2' /> 
				<input type="hidden" name='email' value='<%=request.getParameter("email") %>' /> <label>Verification Code:</label>
				<input type="text" name="code" class='form-control' />
				<div class="cleaner_h10"></div>
				<div class="cleaner_h10"></div>
				<br />
				<button class="btn btn-primary btn-lg btn-block" type="submit">Login</button>
				<a href='login.jsp' class="btn btn-info btn-lg btn-block"
					style='color: white;'>Signin</a>
			</div>
		</form>
	


</body>

</html>
