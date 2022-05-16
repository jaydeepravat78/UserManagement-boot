jQuery.validator.addMethod("password_regex", function(value, element) {
	return this.optional(element) || /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,30}$/i.test(value);
}, "*Password should have minimum 8 and maximum 30 character with a number, alphabet character and a special character"); // password check 
jQuery.validator.addMethod("validate_email", function(value, element) {
	return this.optional(element) || /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/i.test(value);	
}, "Please enter a valid Email.");

$("#forgot-form").validate({
	rules: {
		'email': {
			required: true,
			validate_email: true,
		},
		'password': {
			required: true,
			password_regex: true,
		},
		'confirm_psw': {
			required: true,
			equalTo: '#inputPassword',
		},
		'game': {
			required: true,
		},
		'secQues': {
			required: true,
		}
	},
	messages: {
		'email': {
			required: "*Please enter your email",
			validate_email: "*Please enter a valid email address!",
		},
		'password': {
			required: "*Please enter a password",
		},
		"confirm_psw": {
			required: "*Please confirm the password",
			equalTo: "*Doesn't match with password"
		},
		'game': {
			required: "*Please select a game",
		},
		'secQues': {
			required: "*Please answer this question",
		}
	},
	submitHandler: function(form) {
		form.submit();
	}
});