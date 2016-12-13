$(function() {
	setTimeout(function() {
		$('#hideComponent').fadeOut('slow');
	}, 7000);
});

$(function() {
	$('#tabela1').DataTable();
	$('#example2').DataTable({
		"paging" : true,
		"lengthChange" : false,
		"searching" : false,
		"ordering" : true,
		"info" : true,
		"autoWidth" : false
	});
});

// EXCLUIR WITHOUT RELOAD PAGE
$(document).ready(function() {
	$(".btn-danger").click(function(e) {
		var id = $(this).attr("id");

		var endereco = "remover/";
		
		// teste
		$.ajax({
			url : endereco + id,
			type : "DELETE",

			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},

			success : function() {

				var tr = $(e.target).closest("tr");
				tr.css("background-color", "#000000");
				tr.fadeIn(1000).fadeOut(200, function() {
					tr.remove();
				})
				
				var li = $(e.target).closest("li");;
				li.css("backgroud-color","#000000");
				li.fadeIn(1000).fadeOut(200,function(){
					li.remove();
				})
			},

			error : function(e) {

				$('#bodyteste').append(erroDiv); // VARIÁVEL LOCALIZADA EM OUTRO ARQUIVO -> erroDivMessage.js

			}
		});
	});
});

$('#inputMonthYear').on("change", function(){
	
	var teste = $(this).val();
	
	
});

/*$(function(){
	var teste = $('#inputMonthYear').val();
	
		
		alert(teste);
		
});*/
/*
 * function sendData() {
 * 
 * alert('teste');
 * 
 * $('#tabela1 .btn').click(function(){ // var id = $(this).attr("id");
 * 
 * alert('haha');
 * 
 * });
 * 
 * var tr = $('#testee').closest("tr"); tr.css("background-color", "#000000");
 * tr.fadeIn(1000).fadeOut(200, function() { tr.remove(); }); };
 */

