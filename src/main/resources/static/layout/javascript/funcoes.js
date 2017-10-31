$(function() {
	setTimeout(function() {
		$('#hideComponent').fadeOut('slow', function(){
			$(this).remove();
		});
	}, 5000);
});

/*$("body").on("change", '#hideComponent', function() { 
	alert("teste");
});

*/

/*$(document).ready(*/
$(function() {
	$("#tabela1").DataTable();
	/*$('#example2').DataTable({
		"paging" : true,
		"lengthChange" : false,
		"searching" : false,
		"ordering" : true,
		"info" : true,
		"autoWidth" : false
	});*/
});

// EXCLUIR WITHOUT RELOAD PAGE
/*$(document).ready(function(e) {
	$(".btn-danger").on("click", function(e) {  Não funfa = Forma Padrão.
	$("body").on("click", '.btn-danger', function() {
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
				
				$('#bodyteste').append(erroDiv); // VARIÁVEL LOCALIZADA EM OUTRO ARQUIVO -> erroDivMessage.js
			},

			error : function(e) {

				$('#bodyteste').append(erroDiv); // VARIÁVEL LOCALIZADA EM OUTRO ARQUIVO -> erroDivMessage.js

			}
		});
	});
});*/
/*var tr = null;
var li = null;
$(document).ready(function(e) {
	$(".btn-danger").on("click", function(e){
			tr = $(e.target).closest("tr");
			li = $(e.target).closest("li");;
		});
});*/

$(document).ready(function(e) {
	$("#botaoExcluirRegistro").on("click", function(e) {

		var id = $('.idValueModal').val();
		var liSelect = 'li' + id; // PEGA O VALOR QUE TÁ NO ID DE CADA LINHA
		var trSelect = 'tr' + id;
		
		var li = $("#"+liSelect); // ATRIBUI O SELETOR PARA UMA VARIÁVEL
		
		var tr = $("#"+trSelect);
		
		var endereco = "remover/";
		
		$.ajax({
			url : endereco + id,
			type : "DELETE",

			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},

			success : function() {

				tr.css("background-color", "#000000");
				tr.fadeIn(1000).fadeOut(500, function() {
					tr.remove();
				})
				
				li.css("backgroud-color","#000000");
				li.fadeIn(1000).fadeOut(500,function(){
					li.remove();
				})
				
				$('#bodyteste').append(erroDiv); // VARIÁVEL LOCALIZADA EM OUTRO ARQUIVO -> erroDivMessage.js
				
				$(function() {
					setTimeout(function() {
						$('#hideComponent').fadeOut('slow');
					}, 5000);
				});
			},

			error : function(e) {

				$('#bodyteste').append(erroDiv); // VARIÁVEL LOCALIZADA EM OUTRO ARQUIVO -> erroDivMessage.js

			}
		});
	});
});

$('#confirmaExclusaoModal').on('show.bs.modal', function (event) {
	var botao = $(event.relatedTarget);
	var id = botao.attr("id")
	/*var modal = $(this);*/
	$('.idValueModal').val(id);
});

$('#confirmaDepositoEmConta').on('show.bs.modal', function () {
	var valorData = $('#inputMonthYear').val();
	/*var modal = $(this);*/
	$('.valueModal').val(valorData);
});

$('#formDeposito').on('submit', function(e){
	
	var date = $('.valueModal').val();
	
	var endereco = "saldo?date=";
	
	var form = this;
	
	var saldo = null;
	
	e.preventDefault(e);
	
	$.ajax({
		
		url : endereco + date,
		type : "GET",

		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},

		success : function(data) {
			
			saldo = data;
			
			verificaValorDeposito(saldo, form);
		},

		error : function(e) {

			$('#bodyteste').append(erroDiv); // VARIÁVEL LOCALIZADA EM OUTRO ARQUIVO -> erroDivMessage.js

		}
	});
	
});

function verificaValorDeposito(saldo, form){
	
	var valorDepositoDigitado = $('#valorConversao').val().replace(/\./g, '').replace(/\,/g, '.');
	
	/*alert('digitado: ' + valorDepositoDigitado);
	alert('saldo: ' + saldo);
	*/
	
	var valorDigitado = Number(valorDepositoDigitado);
	
	if (saldo < valorDigitado) {
		
		$('#valorDigitadoIncorretoError').append(valorDigitadoIncorreto);
		/*$(valorDigitadoIncorreto).appendTo('#valorDigitadoIncorretoError').delay(1000).queue(function(){
			$(this).remove(); REMOVE RAPIDO DE MAIS
		});*/
		
		setTimeout(function() {
			$('#hideComponent').fadeOut('slow', function(){
				$(this).remove();
			});
		}, 2000);
		
		return;
		
	}
	
	form.submit();
	
}

/*$('body').on('appened', '#hideComponent', function(){
	alert('teste');
});*/

/* FAZ O PROCESSO DINAMICO DOS LANCAMENTOS, CARREGAMENTO DA TABELA */

$('#inputMonthYear').on("change", function(e){
	
	enviaLancamentos();
	
}).change();

function enviaLancamentos(){
	
	var urlTabela = '/lancamento/tabela';
	
	if($('#inputMonthYear').val() != ''){
		urlTabela = urlTabela + '?date=' + encodeURIComponent($('#inputMonthYear').val());
	}
	
	$("#ajax-loading").html(" " +
			" <div class='row'>" +
			" 	<div class='col-xs-12'>" +
			" 		<img src='/layout/imgs/loading-gif/spinner-loading5.gif' alt='loading...' />" +
			" 	</div>" +
			" </div");
	
	$("#tabelaBlock").load(urlTabela, function(){
		/*$.getScript("https://code.jquery.com/jquery-2.2.3.min.js");
		$.getScript("/layout/javascript/funcoes.js");*/
		// REMOVE O LOADING ASSIM QUE TERMINAR DE CARREGAR A(S) TABELA(S)
		$("#ajax-loading").remove();
	});
	
	$("#tabelaBlockMobile").load(urlTabela + '&mobile=mobile', function(){
		$("#ajax-loading").remove();
	});

	// TREINAMENTO.. RSRS
	/*$jq.when(
			$jq.getScript('/layout/javascript/jquery-2.2.3.min.js'),
			$jq.getScript('https://code.jquery.com/ui/1.11.4/jquery-ui.min.js'),
			$jq.getScript('/layout/bootstrap/js/bootstrap.min.js'),
			$jq.getScript('/layout/plugins/datatables/jquery.dataTables.min.js'),
			$jq.getScript('/layout/plugins/datatables/dataTables.bootstrap.min.js'),
		    $jq.Deferred(function( deferred ){
		    	$jq( deferred.resolve );
		    })
		).done(function(){

			$jq("#tabelaBlock").load(urlTeste);

		});
	
	
	$.ajax({
        
		type: "GET",
        url: urlTeste,
        datatype : "html",
        success: function(html) {
            console.log(html);
            alert('ddd');
             $("#tabelaBlock").load(html);
        },
		error : function(e) {
	
			alert('sdf');
	
		}
    });
	
	var urlSecond = '/lancamento/tabela';
	
	if($('#inputMonthYear').val() != ''){
		urlSecond = urlSecond + '/' + encodeURIComponent($('#inputMonthYear').val());
	}
	
	$.ajax({
	    url: '/lancamento/Tabela.html',
	    dataType: 'html',
	    success: function(html) {
	        var div = $('#tabelaBlock', $(html)).addClass('done');
	        $('#targetDiv').html(div);
	    }
	});*/
}

/* FAZ O PROCESSO DINAMICO, CARREGAMENTO DAS TABELAS VENCIDAS */
$('#inputDateVencidos').on("change", function(){
	
	enviaVencidos();
	
});

function enviaVencidos(){
	
	var urlTabela = '/lancamento/tabelaVencidos';
	
	if($('#inputDateVencidos').val() != ''){
		urlTabela = urlTabela + '?dataVencido=' + encodeURIComponent($('#inputDateVencidos').val());
	}
	
	$("#ajax-loading").html(" <img src='/layout/imgs/loading-gif/spinner-loading5.gif' alt='loading...' /> ");
	
	$("#tabelaVencidos").load(urlTabela, function(){
		
		// REMOVE O LOADING ASSIM QUE TERMINAR DE CARREGAR A(S) TABELA(S)
		$("#ajax-loading").remove();
	});
	
	$("#tabelaVencidosMobile").load(urlTabela + '&mobile=mobile', function(){
		$("#ajax-loading").remove();
	});
}





/* DECIDE SE NECESSÁRIO EXIBIR IS_PAGO -------- MOVIDO PARA ARQUIVO SEPARADO */
/*$(document).ready(function() {
	
	var idCategoria = $("#cat").val();
	
	desabilitaChecked(idCategoria);
	
});

$("#cat").on("change",function(){
	
	var idCategoria = $(this).val();
	
	desabilitaChecked(idCategoria);

});

function desabilitaChecked(idCategoria) {
	
	var endereco = "/categoria/json/";
	
	$.ajax({
		url : endereco + idCategoria,
		type : "GET",

		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json"); TIREI DEVIDO ESTAR RETORNANDO APENAS UMA STRING
		},

		success : function(data) {

			checked(data);
			
		},

		error : function(e) {

			$('#bodyteste').append(erroDiv); // VARIÁVEL LOCALIZADA EM OUTRO ARQUIVO -> erroDivMessage.js

		}
		
	});
}

function checked(resposta){
	
	alert("aqui");
	console.log(resposta);
	
	if (resposta == 'DESPESA') {
		$('#valorPago').show('slow');
	} else {
		$('#valorPago').hide('slow');
	}
}*/

/*function getValueSelected () {
	
	var valor = ('#conta : selected').text();
	
	alert(valor);
}*/

/*$(function(){
	var teste = $('#inputMonthYear').val();
	
		
		alert(teste);
		
});


function sendData() {

	alert('teste');
	
	$('#tabela1 .btn').click(function(){ // var id = $(this).attr("id");
	
	alert('haha');

});

var tr = $('#testee').closest("tr"); tr.css("background-color", "#000000");
tr.fadeIn(1000).fadeOut(200, function() { tr.remove(); }); };*/


