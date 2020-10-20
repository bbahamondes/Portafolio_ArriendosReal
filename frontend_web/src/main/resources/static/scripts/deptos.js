$(document).ready(function(){

	var dt = $('#dtDeptos').DataTable({
		ajax:{
			url: "http://40.117.177.9:8080/api/v1/departamento/all",
			dataSrc: ''
		},
		columns: [
			{data: 'idDepartmento'},
			{data: 'nombre'},
			{data: 'direccion'},
			{data: 'ciudad'},
			{data: 'region'},
			{data: 'precio'},
		]
	})
	
});