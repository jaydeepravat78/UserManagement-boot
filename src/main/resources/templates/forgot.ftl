<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Forgot Password</title>
<link href="<@spring.url '/Assets/Libraries/bootstrap/css/bootstrap.min.css' />"
	rel="stylesheet">
<link rel="stylesheet" href="<@spring.url '/Assets/Fonts/font.css' />"></link>
<link rel="stylesheet" href="<@spring.url '/Assets/CSS/forgot.css' />"></link>
</head>
<body>
	<#include 'header.ftl'>
	<div class="container">
		<#if error??>
			<div class="error text-center">
				${error}
			</div>
		</#if>
		<form class="form-horizontal" action="forgotController" method="post"
			id="forgot-form">
			<h2 class="text-center">Forgot Password</h2>
			<div class="form-group">
				<label for="inputEmail" class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<input type="email" name="email" class="form-control"
						id="inputEmail" placeholder="Email">
				</div>
			</div>
			<div class="form-group">
				<label for="secQues" class="col-sm-2 control-label">Security
					Question</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="secQues" id="secQues"
						placeholder="What is the name of your first school?">
				</div>
			</div>
			<div class="form-group">
				<label for="inputGame" class="col-sm-2 control-label">Favorite
					Game</label>
				<div class="col-sm-10">
					<select id="inputGame" name="game" class="form-control">
						<option value="">Select</option>
						<option value="GTA">GTA</option>
						<option value="Fifa">Fifa</option>
						<option value="Battlefield">Battlefield</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword" class="col-sm-2 control-label">New
					Password</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" name="password"
						id="inputPassword" placeholder="********">
				</div>
			</div>
			<div class="form-group">
				<label for="inputConfirmPassword" class="col-sm-2 control-label">Confirm
					Password</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" name="confirm_psw"
						id="inputConfirmPassword" placeholder="********">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<a href="index">Got password?</a>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-lg btn-success">Change
						Password</button>
				</div>
			</div>
		</form>
	</div>
	<#include 'footer.ftl'>
	<script src="<@spring.url '/Assets/JS/jquery-3.6.0.min.js' />"></script>
	<!-- jquery -->
	<script src="<@spring.url '/Assets/Libraries/bootstrap/js/bootstrap.min.js' />"></script>
	<!--  bootstrap -->
	<script src="<@spring.url '/Assets/Libraries/validate/jquery.validate.min.js' />"></script>
	<!-- validate js -->
	<script src="<@spring.url  '/Assets/JS/forgot.js' />"></script>
	<!--  js script -->
</body>
</html>