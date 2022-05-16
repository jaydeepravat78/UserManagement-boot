<!DOCTYPE html>
<html>
<link>
<meta charset="ISO-8859-1">
<title>Registration</title>
<link href="Assets/Libraries/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<link rel="stylesheet" href="Assets/Fonts/font.css"></link>
<!-- bootstrap -->
<link rel="stylesheet" href="Assets/CSS/register.css"></link>
</head>
<body>
	<#include "header.ftl">

	<div class="container">
		<div class="error text-center">
			<#if error??>
				${error}
			</#if>
		</div>
		<form class="form-horizontal" id="reg_form" method="post"
			action='<#if (userSession?? || admin??) && userData??>updateController<#else>registerController</#if>'
			enctype="multipart/form-data">
			<#if userData??>
				<input type="text" name="id" value="${userData.id}" hidden="hidden">
			</#if>
			<h2 class="text-center">
				<#if (userSession?? || admin??) && userData??> Update Profile <#else> Register </#if>
			</h2>
			<div class="form-group">
				<label for="inputName" class="col-sm-2 control-label">Name</label>
				<div class="col-sm-10">
					<input type="text" name="name" class="form-control"
						id="inputName"
						placeholder="Sample" maxlength="45" 
						<#if userData?? && userData.name??>value="${userData.name}"</#if>
						<#if userError?? && userError.name??>value="${userError.name}"</#if>
						>
				</div>
			</div>
			<div class="form-group">
				<label for="inputEmail" class="col-sm-2 control-label">Email</label>
				<div class="col-sm-10">
					<input type="text" name="email" class="form-control"
						id="inputEmail" maxlength="45"
						placeholder="sample@gmail.com" 
						<#if userData?? && userData.email??>value="${userData.email}" disabled</#if>
						<#if userError?? && userError.email??>value="${userError.email}" disabled</#if>
						>
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword" class="col-sm-2 control-label">Password</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" name="password"
						id="inputPassword"
						placeholder="********" 
						<#if userData?? && userData.password??> value="${userData.password}" </#if>
						<#if userError?? && userError.password??> value="${userError.password}" </#if>
						>
				</div>
			</div>
			<div class="form-group">
				<label for="inputConfirmPassword" class="col-sm-2 control-label">Confirm
					Password</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" name="confirm_psw"
						placeholder="********" 
						<#if userData?? && userData.password??> value="${userData.password}" </#if>
						<#if userError?? && userError.password??> value="${userError.password}" </#if>
						>
				</div>
			</div>
			<div class="form-group">
				<label for="inputPhone" class="col-sm-2 control-label">Phone
					number</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="phone"
						id="inputPhone"
						placeholder="[7-9]123456789" 
						<#if userData?? && userData.phone??> value="${userData.phone}" </#if>
						<#if userError?? && userError.phone??> value="${userError.phone}" </#if>
						>
				</div>
			</div>
			<div class="form-group">
				<label for="inputGender" class="col-sm-2 control-label">Gender</label>
				<div class="col-sm-6">
					<label class="radio-inline"> <input type="radio"
						id="inputGender1" name="gender" value="male" 
						<#if userData?? && userData.gender?? && userData.gender == 'male'> checked </#if>
						<#if userError?? && userError.gender?? && userError.gender == 'male'> checked </#if>
						>Male
					</label> <label class="radio-inline"> <input type="radio"
						id="inputGender2" name="gender" value="female" 
						<#if userData?? && userData.gender?? && userData.gender == 'female'> checked </#if>
						<#if userError?? && userError.gender?? && userError.gender == 'female'> checked </#if>
						>Female
					</label>
				</div>
			</div>
			<div class="form-group">
				<label for="inputLang" class="col-sm-2 control-label">Language</label>
				<div class="col-sm-6">
					<label class="checkbox-inline"> <input type="checkbox"
						id="inputLang1" name="lang" value="Java" 
						<#if userData??> <#list userData.lang as l> <#if l == 'Java'> checked='checked' </#if> </#list> </#if> 
						<#if userError??> <#list userError.lang as l> <#if l == 'Java'> checked='checked' </#if> </#list> </#if>
						>
						Java
					</label> <label class="checkbox-inline"> <input type="checkbox"
						id="inputLang2" name="lang" value="C++" 
						<#if userData??> <#list userData.lang as l> <#if l == 'C++'> checked='checked' </#if> </#list> </#if> 
						<#if userError??> <#list userError.lang as l> <#if l == 'C++'> checked='checked' </#if> </#list> </#if>
						>
						C++
					</label> <label class="checkbox-inline"> <input type="checkbox"
						id="inputLang3" name="lang" value="Python" 
						<#if userData??> <#list userData.lang as l> <#if l == 'Python'> checked='checked' </#if> </#list> </#if> 
						<#if userError??> <#list userError.lang as l> <#if l == 'Python'> checked='checked' </#if> </#list> </#if>
						>
						Python
					</label> <label class="checkbox-inline"> <input type="checkbox"
						id="inputLang4" name="lang" value="Kotlin" 
						<#if userData??> <#list userData.lang as l> <#if l == 'Kotlin'> checked='checked' </#if> </#list> </#if> 
						<#if userError??> <#list userError.lang as l> <#if l == 'Kotlin'> checked='checked' </#if> </#list> </#if>
						>
						Kotlin 
					</label>
				</div>
			</div>
			<div class="form-group">
				<label for="inputGame" class="col-sm-2 control-label">Favorite
					Game</label>
				<div class="col-sm-10">
					<select id="inputGame" name="game" class="form-control">
						<option value="">Select</option>
						<option value="GTA" 
						<#if userData?? && userData.game == 'GTA'>selected='selected'</#if> 
						<#if userError?? && userError.game == 'GTA'>selected='selected'</#if> 
						>GTA</option>
						<option value="Fifa" 
						<#if userData?? && userData.game == 'Fifa'>selected='selected'</#if>
						<#if userError?? && userError.game == 'Fifa'>selected='selected'</#if> 
						>Fifa</option>
						<option value="Battlefield" 
						<#if userData?? && userData.game == 'Battlefield'>selected='selected'</#if>
						<#if userError?? && userError.game == 'Battlefield'>selected='selected'</#if> 
						>Battlefield</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="secQues" class="col-sm-2 control-label">Security
					Question</label>
				<div class="col-sm-10">
					<label class="control-label">What is the name of your first
						school?</label>
				</div>
				<div class="col-sm-offset-2 col-sm-10">
					<input type="text" class="form-control" name="secQues" id="secQues"
						placeholder="School name" maxlength="45" 
						<#if userData??>value="${userData.secQues}"</#if> 
						<#if userError??>value="${userError.secQues}"</#if> 
						>
				</div>
			</div>
			<div id="main-container">
					<#if (userData?? && userData.addresses?size != 0)>
						<#list userData.addresses as address>
						<div class="container-item">
							<div class="form-group">
								<label for="inputAddress" class="col-sm-2 control-label">Address</label>
								<div class="col-sm-10">
									<div class="row">
										<div class="col-sm-4 mb-10">
											<input type="text" name="addresses[${address?index}].street" class="form-control"
												placeholder="Street"
												maxlength="45"
												value="${address.street}"
												required>
										</div>
										<div class="col-sm-3 mb-10">
											<input type="text" name="addresses[${address?index}].city" class="form-control"
												placeholder="City"
												maxlength="45"
												value="${address.city}"
												required>
										</div>
										<div class="col-sm-3 mb-10">
											<input type="text" name="addresses[${address?index}].state" class="form-control"
												placeholder="State"
												maxlength="45"
												value="${address.state}"
												required>
											<input type="text" name="address_id"
												value="<#if address??>${address.address_id}<#else>0</#if>" hidden="hidden">
										</div>
										<div class="col-sm-2 mb-10">
											<a href="javascript:void(0)"
												class="remove-item btn btn-danger">Delete</a>
										</div>
									</div>
								</div>
							</div>
						</div>	
						</#list>
					<#elseif (userError?? && userError.addresses?size != 0)>
						<#list userError.addresses as address>
						<div class="container-item">
							<div class="form-group">
								<label for="inputAddress" class="col-sm-2 control-label">Address</label>
								<div class="col-sm-10">
									<div class="row">
										<div class="col-sm-4 mb-10">
											<input type="text" name="addresses[${address?index}].street" class="form-control"
												placeholder="Street"
												maxlength="45"
												value="${address.street}"
												required>
										</div>
										<div class="col-sm-3 mb-10">
											<input type="text" name="addresses[${address?index}].city" class="form-control"
												placeholder="City"
												maxlength="45"
												value="${address.city}"
												required>
										</div>
										<div class="col-sm-3 mb-10">
											<input type="text" name="addresses[${address?index}].state" class="form-control"
												placeholder="State"
												maxlength="45"
												value="${address.state}"
												required>
											<input type="text" name="address_id"
												value="<#if address??>${address.address_id}<#else>0</#if>" hidden="hidden">
										</div>
										<div class="col-sm-2 mb-10">
											<a href="javascript:void(0)"
												class="remove-item btn btn-danger">Delete</a>
										</div>
									</div>
								</div>
							</div>
						</div>	
						</#list>
					<#elseif !userData?? || userData.addresses?size <= 0>
						<div class="container-item">
							<div class="form-group">
								<label for="inputAddress" class="col-sm-2 control-label">Address</label>
								<div class="col-sm-10">
									<div class="row">
										<div class="col-sm-4 mb-10">
											<input type="text" name="addresses[0].street" class="form-control"
												placeholder="Street"
												maxlength="45"
												required>
										</div>
										<div class="col-sm-3 mb-10">
											<input type="text" name="addresses[0].city" class="form-control"
												placeholder="City"
												maxlength="45"
												required>
										</div>
										<div class="col-sm-3 mb-10">
											<input type="text" name="addresses[0].state" class="form-control"
												placeholder="State"
												maxlength="45"
												required>
											<input type="text" name="address_id"
												value="<#if address??>${address.address_id}</#if>" hidden="hidden">
										</div>
										<div class="col-sm-2 mb-10">
											<a href="javascript:void(0)"
												class="remove-item btn btn-danger">Delete</a>
										</div>
									</div>
								</div>
							</div>
						</div>	
						</#if>
				</div>
				<div class="row">
				<div class="col-sm-offset-2 col-sm-10">
					<a id="add-more" href="javascript:;"
						class="btn btn-sm btn-warning addbtn">Add</a>
				</div>
			</div>
			<div class="form-group">
				<label for="inputPhoto" class="col-sm-2 control-label">Photo</label>
				<div class="col-sm-5">
					<input type="file" name="user_photo" class="btn"
						accept="image/png, image/gif, image/jpeg">
				</div>
				<#if userData??>
				<div class="col-sm-5 text-center">

						<#if userData.profilePic??>
							<img class="img-circle img-thumbnail img-responsive profile"
								alt="profilePic"
								src="data:image/jpg;base64,${userData.profilePic}">
						
						</#if>
				</div>
				</#if>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
						<#if userSession??>
							<a href="home">Home</a>
						<#elseif admin??>
							<a href="dashboard">Dashboard</a>
						<#else>
							<a href="index">Already have an account?</a>
						</#if>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-lg btn-success">
						<#if (userSession?? || admin??) && userData??>Update<#else>Register</#if>
					</button>
					<button type="reset" class="btn btn-lg btn-danger btn-reset">Reset</button>
				</div>
			</div>
		</form>
	</div>
	<#include "footer.ftl">
	<script src="Assets/JS/jquery-3.6.0.min.js"></script>
	<!-- jquery -->
	<script src="Assets/Libraries/clonedata/cloneData.js"></script>
	<!-- clone data -->
	<script src="Assets/Libraries/bootstrap/js/bootstrap.min.js"></script>
	<!-- bootstrap jquery -->
	<script src="Assets/Libraries/validate/jquery.validate.min.js"></script>
	<!-- validate js -->
	<script src="Assets/JS/resgister.js"></script>
	<!--  js script -->
</body>
</html>