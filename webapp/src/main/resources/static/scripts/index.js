$(document).ready(function(){
	var loginpath = "/login";
	
	
	$("#btnLogin").on("click", function(){
		
		var form = new FormData();
		let email = $("#email").val();
		let password = $("#password").val();
		
		form.append("email", email);
		form.append("password", password);
		
		$.ajax({
			url: loginpath,
			data: form,
			method: "POST",
			processData: false,
			contentType: false,
			statusCode: {
				200: function(e) {
					document.cookie = "username=" + e.email;
					document.cookie = "profile=" + e.profile;  
					console.log("success!", e);
					window.location.href = '/menu';
				},
				401: function() {
					$(".alert").show();
				}
			}
		}).done(function(e){
			console.log(e);
		});
	});
	
});