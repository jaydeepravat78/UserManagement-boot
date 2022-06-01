<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Log In</title>
<link href="<@spring.url '/Assets/Libraries/bootstrap/css/bootstrap.min.css' />"
	rel="stylesheet">
<!-- bootstrap -->

<link rel="stylesheet" href="<@spring.url '/Assets/Fonts/font.css' />"></link>
<link type="text/css" rel="stylesheet" href="<@spring.url '/Assets/CSS/custom.css' />"></link>

</head>
<body>
		<#include "header.ftl">
		<div class="container">
		<div class="error text-center">
			<#if errorMessage??> ${errorMessage} </#if>
		</div>
		<form class="form-horizontal uform" action="login"
			method="Post" id="loginForm">
			<h2 class="text-center">Login</h2>
			<div class="form-group">
				<label for="inputEmail" class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<input type="email" name="email" class="form-control"
						id="inputEmail" placeholder="abc@gmail.com" value="<#if errorEmail??>${errorEmail}</#if>" >
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword" class="col-sm-2 control-label">Password</label>
				<div class="col-sm-10">
					<input type="password" name="password" class="form-control"
						id="inputPassword" placeholder="********">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<a href="forgot">Forgot Password?</a>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<a href="registration">Create a new account</a>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" class="btn btn-lg btn-success" value="Log in">
				</div>
			</div>
		</form>
	</div>
	<#include "footer.ftl">
	<script src="<@spring.url '/Assets/JS/jquery-3.6.0.min.js' />"></script>
	<!-- jquery -->
	<script src="<@spring.url '/Assets/Libraries/bootstrap/js/bootstrap.min.js' />"></script>
	<!--  bootstrap -->

	<script src="<@spring.url '/Assets/Libraries/validate/jquery.validate.min.js' />"></script>
	<!-- validate js -->
	<script src="<@spring.url '/Assets/JS/login.js' />"></script>
</body>
</html>