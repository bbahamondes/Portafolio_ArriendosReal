$(document).ready(function(){

	var dt = $('#dtReservas').DataTable({
		ajax:{
			url: "http://40.117.177.9:8080/api/v1/reservas/all",
			dataSrc: ''
		},
		columns: [
			{data: 'idReserva'},
			{data: 'fechaEntrada'},
			{data: 'fechaSalida'},
		]	
	})
	
});