<%@page import="java.util.Base64"%>
<%@page import="java.util.List"%>
<%@page import="com.se.pojo.User"%>
<%
	User u1 = (User) session.getAttribute("user");
	if (u1 == null) {
		response.sendRedirect("login.jsp?msg=Session expired. Login again");
	} else {
%>

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
<link href="css/font-awesome.min.css" rel="stylesheet" />
<!-- full calendar css-->
<link href="assets/fullcalendar/fullcalendar/bootstrap-fullcalendar.css"
	rel="stylesheet" />
<link href="assets/fullcalendar/fullcalendar/fullcalendar.css"
	rel="stylesheet" />
<!-- easy pie chart-->
<link href="assets/jquery-easy-pie-chart/jquery.easy-pie-chart.css"
	rel="stylesheet" type="text/css" media="screen" />
<!-- owl carousel -->
<link rel="stylesheet" href="css/owl.carousel.css" type="text/css">
<link href="css/jquery-jvectormap-1.2.2.css" rel="stylesheet">
<!-- Custom styles -->
<link rel="stylesheet" href="css/fullcalendar.css">
<link href="css/widgets.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link href="css/style-responsive.css" rel="stylesheet" />
<link href="css/xcharts.min.css" rel=" stylesheet">
<link href="css/jquery-ui-1.10.4.min.css" rel="stylesheet">
<!-- =======================================================
    Theme Name: NiceAdmin
    Theme URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
    Author: BootstrapMade
    Author URL: https://bootstrapmade.com
  ======================================================= -->
</head>

<body>
	<!-- container section start -->
	<section id="container" class="">


		<header class="header dark-bg">
			<div class="toggle-nav">
				<div class="icon-reorder tooltips"
					data-original-title="Toggle Navigation" data-placement="bottom">
					<i class="icon_menu"></i>
				</div>
			</div>

			<!--logo start-->
			<a href="index.html" class="logo">Security <span class="lite">Ecosystem</span></a>
			<!--logo end-->



			<div class="top-nav notification-row">
				<!-- notificatoin dropdown start-->
				<ul class="nav pull-right top-menu">


					<!-- alert notification end-->
					<!-- user login dropdown start-->
					<li class="dropdown"><a data-toggle="dropdown"
						class="dropdown-toggle" href="#"> <span class="profile-ava">
								<img alt="" src="img/avatar1_small.jpg" width=30>
						</span> <span class="username"><%=u1.getFname()%> <%=u1.getLname()%></span>
							<b class="caret"></b>
					</a>
						<ul class="dropdown-menu extended logout">
							<div class="log-arrow-up"></div>
							<li class="eborder-top"><a href="updateprofile.jsp"> Edit Profile</a></li>
							<li><a href="changepassword.jsp"> Change Password</a></li>
							<li><a href="account?request_type=deleteprofile"> Delete Profile</a></li>
							<li><a href="account?request_type=logout"> Logout</a></li>
						</ul></li>
					<!-- user login dropdown end -->
				</ul>
				<!-- notificatoin dropdown end-->
			</div>
		</header>
		<!--header end-->

		<!--sidebar start-->
		<aside>
			<div id="sidebar" class="nav-collapse ">
				<!-- sidebar menu start-->
				<ul class="sidebar-menu">
					<li ><a class="" href="welcome.jsp"> <span>Welcome</span>
					</a></li>
					<li class="sub-menu"><a href="javascript:;" class=""> <span>key Management</span>
							<span class="menu-arrow arrow_carrot-right"></span>
					</a>
						<ul class="sub">
							<li><a class="" href="uploadkey.jsp">Upload new key</a></li>
							<li><a class="" href="keys?type=view">View all keys</a></li>
						</ul></li>
					<li ><a class="" href="keys?type=getkeyforupload"> <span>Upload Files</span>
					</a></li>
					<li ><a class="" href="view?type=get"> <span>View Files</span>
					</a></li>
					<li class="sub-menu active"><a href="javascript:;" class=""> <span>Data Transmission</span>
							<span class="menu-arrow arrow_carrot-right"></span>
					</a>
						<ul class="sub">
							<li><a class="" href="send_data.jsp">Send Data</a></li>
							<li><a class="" href="dt?type=get">Read Data</a></li>
						</ul></li>



				</ul>
				<!-- sidebar menu end-->
			</div>
		</aside>
		<!--sidebar end-->

		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
<!-- 				<div class="row"> -->
<!-- 					<div class="col-lg-12"> -->
<!-- 						<h3 class="page-header">Welcome</h3> -->

<!-- 					</div> -->
<!-- 				</div> -->
				<div class="col-md-12">
					<section class="panel">
						<header class="panel-heading">
							<h3>Send Data - Transmission of User data Demonstration</h3>

						</header>
						<div class="panel-body" style='min-height:600px;'>
							<%
								String msg = request.getParameter("msg");
							%>
							<%
								if (msg != null) {
							%>
							<div class="alert alert-danger alert-dismissable">
								<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
								<strong>Message!</strong>
								<%=msg%>.
							</div>
							<%
								}
							%>
							
							<%
								List<String> data_c = (List<String>) request.getAttribute("data_c");
								List<String> data_nc = (List<String>) request.getAttribute("data_nc");
								
								if (data_c!=null && data_c.size()>0) {
									%>
										
										<div class="panel-group col-md-6" id="accordion" role="tablist" aria-multiselectable="true">
										  <div class="panel panel-default">
										    <div class="panel-heading" role="tab" id="headingOne">
										      <h4 class="panel-title">
										        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
										          Confidential Data
										        </a>
										      </h4>
										    </div>
										    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
										      <div class="panel-body">
										        
										        <table class='table'>
										        	<tr>
										        		<th> Data Received</th>
										        		<th> Decrypted Data</th>
										        	</tr>
										        	<%
										        		for (String s: data_c) {
										        			%>
										        				<tr>
										        					<td> <%=s %> </td>
										        					<td> <%= new String(Base64.getDecoder().decode(s)) %></td>
										        				</tr>
										        			<%
										        		}
										        	%>
										        </table>
										        
										        
										      </div>
										    </div>
										  </div>
										  <div class="panel panel-default">
										    <div class="panel-heading" role="tab" id="headingTwo">
										      <h4 class="panel-title">
										        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
										          Non Confidential Data
										        </a>
										      </h4>
										    </div>
										    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
										      <div class="panel-body">
										        <table class='table'>
										        	<tr>
										        		<th> Data received</th>
										        	</tr>
										        	<%
										        		for (String s: data_nc) {
										        			%>
										        				<tr>
										        					<td> <%=s %> </td>
										        				</tr>
										        			<%
										        		}
										        	%>
										        </table>
										        
										      </div>
										    </div>
										  </div>
										</div>
										
									<%
								} else {
									%>
										<h3> No Data found in the session</h3>
									<%
								}
							%>
						
						</div>
					</section>
				</div>

			</section>
			<div class="text-right">
				<div class="credits">
					<!--
            All the links in the footer should remain intact.
            You can delete the links only if you purchased the pro version.
            Licensing information: https://bootstrapmade.com/license/
            Purchase the pro version form: https://bootstrapmade.com/buy/?theme=NiceAdmin
          -->
					<a href="#">CITECH</a> &copy; Copyright
				</div>
			</div>
		</section>
		<!--main content end-->
	</section>
	<!-- container section start -->

	<!-- javascripts -->
	<script src="js/jquery.js"></script>
	<script src="js/jquery-ui-1.10.4.min.js"></script>
	<script src="js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.9.2.custom.min.js"></script>
	<!-- bootstrap -->
	<script src="js/bootstrap.min.js"></script>
	<!-- nice scroll -->
	<script src="js/jquery.scrollTo.min.js"></script>
	<script src="js/jquery.nicescroll.js" type="text/javascript"></script>
	<!-- charts scripts -->
	<script src="assets/jquery-knob/js/jquery.knob.js"></script>
	<script src="js/jquery.sparkline.js" type="text/javascript"></script>
	<script src="assets/jquery-easy-pie-chart/jquery.easy-pie-chart.js"></script>
	<script src="js/owl.carousel.js"></script>
	<!-- jQuery full calendar -->
	<
	<script src="js/fullcalendar.min.js"></script>
	<!-- Full Google Calendar - Calendar -->
	<script src="assets/fullcalendar/fullcalendar/fullcalendar.js"></script>
	<!--script for this page only-->
	<script src="js/calendar-custom.js"></script>
	<script src="js/jquery.rateit.min.js"></script>
	<!-- custom select -->
	<script src="js/jquery.customSelect.min.js"></script>
	<script src="assets/chart-master/Chart.js"></script>

	<!--custome script for all page-->
	<script src="js/scripts.js"></script>
	<!-- custom script for this page-->
	<script src="js/sparkline-chart.js"></script>
	<script src="js/easy-pie-chart.js"></script>
	<script src="js/jquery-jvectormap-1.2.2.min.js"></script>
	<script src="js/jquery-jvectormap-world-mill-en.js"></script>
	<script src="js/xcharts.min.js"></script>
	<script src="js/jquery.autosize.min.js"></script>
	<script src="js/jquery.placeholder.min.js"></script>
	<script src="js/gdp-data.js"></script>
	<script src="js/morris.min.js"></script>
	<script src="js/sparklines.js"></script>
	<script src="js/charts.js"></script>
	<script src="js/jquery.slimscroll.min.js"></script>
	<script>
	
	
	$(document).ready(function() {
		$('#sbtn').click(function() {
			var val = $('#data_c').val();
			val = window.btoa(val);
			$('#chidden').val(val);
			$('#frm').submit();
		});
	});
	
	
	
	
		//knob
		$(function() {
			$(".knob").knob({
				'draw' : function() {
					$(this.i).val(this.cv + '%')
				}
			})
		});

		//carousel
		$(document).ready(function() {
			$("#owl-slider").owlCarousel({
				navigation : true,
				slideSpeed : 300,
				paginationSpeed : 400,
				singleItem : true

			});
		});

		//custom select box

		$(function() {
			$('select.styled').customSelect();
		});

		/* ---------- Map ---------- */
		$(function() {
			$('#map').vectorMap({
				map : 'world_mill_en',
				series : {
					regions : [ {
						values : gdpData,
						scale : [ '#000', '#000' ],
						normalizeFunction : 'polynomial'
					} ]
				},
				backgroundColor : '#eef3f7',
				onLabelShow : function(e, el, code) {
					el.html(el.html() + ' (GDP - ' + gdpData[code] + ')');
				}
			});
		});
	</script>

</body>

</html>

<%
	}
%>