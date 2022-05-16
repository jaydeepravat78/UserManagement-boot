<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Header</title>
<link href="<@spring.url '/Assets/Libraries/bootstrap/css/bootstrap.min.css' />"
	rel="stylesheet">
<!-- bootstrap -->
<link rel="stylesheet" href="<@spring.url '/Assets/Fonts/font.css' />"></link>
<style>
html, body {
	margin: 0;
	padding: 0;
	font-family: 'Poppins', sans-serif;
}

.bg-color {
	background-color: aliceblue;
	padding: 20px;
	margin-bottom: 20px;
	width: 100%;
}

.bold {
	font-weight: bold;
}

h2 {
	text-transform: uppercase;
}
</style>
</head>
<body>
	<header class="container-fluid bg-color">
		<h2 class="text-center bold">User Management System</h2>
	</header>
	<script src="<@spring.url '/Assets/JS/jquery-3.6.0.min.js' />"></script>
	<!-- jquery -->
	<script src="<@spring.url '/Assets/Libraries/bootstrap/js/bootstrap.min.js' />"></script>
	<!--  bootstrap -->
</body>
</html>