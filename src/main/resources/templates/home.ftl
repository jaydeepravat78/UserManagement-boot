<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<link href="<@spring.url '/Assets/Libraries/bootstrap/css/bootstrap.min.css' />"
	rel="stylesheet">
<!-- bootstrap -->

<link rel="stylesheet" href="Assets/Fonts/font.css"></link>

<style>
.profile {
	width: 150px;
	height: 150px;
	max-height: 150px;
	display: block;
	margin: 30px auto;
}
</style>
</head>
<body>
	<#if userSession??>
	<#include "header.ftl">
		<div class="container text-center">
			<nav class="text-right">
				<a href="logoutController" class="btn btn-danger">Logout</a>
			</nav>
			<h2 class="text-center">
				Welcome
				<c:out value="${userSession.name}" />
			</h2>
			<div class="text-center">
				<#if userSession.getProfilePic()??>
					<img class="img-circle img-thumbnail img-responsive profile"
						alt="profilePic"
						src="data:image/jpg;base64,${userSession.profilePic}">
				</#if>
				<h4>
					Email: ${userSession.email }
				</h4>
				<h4>
					Number: ${userSession.phone }
				</h4>
				<h4>
					Gender: ${userSession.gender}
				</h4>
				<h4>
					Game: ${userSession.game}
				</h4>
			</div>
			<form action="userDataController" class="text-center" method="post">
				<input type="text" name="id" value="${userSession.id}"
					hidden="hidden"> <input type="submit"
					class="btn btn-primary" value="Edit Profile">
			</form>
		</div>
	<#include "footer.ftl">
		<#else>
	</#if>
	<script src="Assets/JS/jquery-3.6.0.min.js"></script>
	<!-- jquery -->
	<script src="Assets/Libraries/bootstrap/js/bootstrap.min.js"></script>
	<!--  bootstrap -->
</body>
</html>