jQuery.validator.addMethod("validate_email", function(value, element) {
	return this.optional(element) || /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/i.test(value);	
}, "Please enter a valid Email.");
$("#loginForm").validate({
	rules: {
		'email': {
			required: true,
			validate_email: true,
		},
		'password': {
			required: true,
		},
	},
	messages: {
		'email': {
			required: "*Please enter your email",
			validate_email: "*Please enter a valid email address!",
		},
		'password': {
			required: "*Please enter a password",
		},
	}
});