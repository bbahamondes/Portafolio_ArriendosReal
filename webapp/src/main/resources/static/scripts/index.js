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
		}).done(function(e){
			console.log(e);
		});
	});
	
});