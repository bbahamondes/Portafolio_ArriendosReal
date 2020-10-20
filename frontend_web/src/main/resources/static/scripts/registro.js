$(document).ready(function(){
	
	
	var url = "http://40.117.177.9:8080";
	var path1 = "/api/v1/user";
	var path2 = "/api/v1/persona";
	
	
	$("#btnLogin").on("click", function(){
		
		
		//Create User
		var form = new FormData();
		let email = $("#email").val();
		let password = $("#password").val();
		let username = $("#username").val();
		let profile = $('#profile').val();
		let userResult = null;
		
		form.append("email", email);
		form.append("username", username);
		form.append("profileId", profile);
		form.append("password", password);
		
		var settings = {
		  "url": url+path1,
		  "method": "POST",
		  "timeout": 0,
		  "processData": false,
		  "mimeType": "multipart/form-data",
		  "contentType": false,
		  "data": form
		};
		
		$.ajax(settings).done(function (response) {
		  userResult = JSON.parse(response);
		  console.log(userResult);
		  let rut = $('#rut').val();
		  let nombre = $('#nombre').val();		
		  let apellidos = $('#apellidos').val();
		  let telefonos = $('#telefonos').val();
		 
		  var form = new FormData();
		  form.append("rut", rut);
		  form.append("nombre", nombre);
		  form.append("apellidos", apellidos);
		  form.append("telefono", telefonos);
		  form.append("userId", userResult.userId);
		
		  var settings = {
  		    "url": url+path2,
		    "method": "POST",
		    "timeout": 0,
		    "processData": false,
		    "mimeType": "multipart/form-data",
		    "contentType": false,
		    "data": form,
			"statusCode": {
				200: function(e){
					alert('Usuario creado exitosamente!');
					window.location.href = '/login';
				}
			}
		  };
		
		  $.ajax(settings).done(function (response) {
		    console.log(response);
		  });
		});
		
		
	});
	
});