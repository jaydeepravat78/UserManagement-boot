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

.footer {
	position: fixed;
	bottom: 0;
	left: 0;
	width: 100%;
	font-size: 18px;
	font-weight: bold;
	padding: 1rem;
	background-color: aliceblue;
	text-align: center;
}
</style>
</head>
<body>
	<footer class="bg-light text-center text-lg-start modal-footer footer">
		<!-- Copyright -->
		<div class="text-center p-3">&#169; 2022 Copyright Jaydeep Ravat</div>
		<!-- Copyright -->
	</footer>
	<script src="<@spring.url '/Assets/JS/jquery-3.6.0.min.js' />"></script>
	<!-- jquery -->
	<script src="<@spring.url '/Assets/Libraries/bootstrap/js/bootstrap.min.js' />"></script>
	<!--  bootstrap -->
</body>
</html>