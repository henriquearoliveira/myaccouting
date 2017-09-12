// Swipe do menu Slide
$(document).ready(function() {

	// on load
	hideDiv();
	
	var sizeWidth = $(window).width();

	// on resize
	$(window).resize(function() {
		hideDiv();
		reloadPage(sizeWidth);
	});

});

function hideDiv() {

	if ($(window).width() < 768) {

		slide();
	}
}

function slide() {
	$("#navBarHide")
			.swiperight(
					function() {
						$("#navBarHide")
								.removeClass('skin-blue sidebar-mini')
								.addClass(
										'skin-blue sidebar-mini sidebar-collapse sidebar-open');
					});
	$("#navBarHide").swipeleft(
			function() {
				$("#navBarHide").removeClass(
						'skin-blue sidebar-mini sidebar-collapse sidebar-open')
						.addClass('skin-blue sidebar-mini');
			});
}

function reloadPage(sizeWidth){
	
	setTimeout(function() {
		
		if ($(window).width() != sizeWidth){
			window.location.reload(true);
		}
		
	}, 2000);
	
}

$(".skin-blue.sidebar-mini").click(function(){
	$(this).removeClass('skin-blue sidebar-mini');
	$(this).addClass('skin-blue sidebar-mini sidebar-collapse sidebar-open');
});

$(".sidebar-collapse").click(function(){
	alert("teste");
	$(this).removeClass('loaded skin-blue sidebar-mini sidebar-open');
	$(this).addClass('skin-blue sidebar-mini');
});

/*$("#navBarHide").click(function(){
	$(this).removeClass('loaded skin-blue sidebar-mini sidebar-collapse sidebar-open').addClass(
	'skin-blue sidebar-mini loaded');
});*/

$(function() {
	$("#valorConversao").maskMoney({prefix:'R$ ', allowNegative: true, thousands:'.', decimal:',', affixesStay: false});
});

//Date picker
/*$('#dataHoraLancamento').datepicker({
	format: 'dd/mm/yyyy',
    forceParse: false,
	autoclose: true
}).datepicker("setDate", new Date());*/

$("#dataHoraLancamento").datepicker({ 
        format: 'yyyy-mm-dd',
        forceParse: false,
        autoclose: true
});

// Month And Year Listagem Lancamento
$('#monthYearListagem').datepicker({
	format: 'MM yyyy',
	startView: "months",
	minViewMode: "months",
    forceParse: false,
	autoclose: true
}).datepicker("setDate", new Date());


function readURL(input) {

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#myImg').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

$("#imgInput").change(function(){
    readURL(this);
    alteraUrl();
});

var imagem = null;

function alteraUrl(){
	imagem = $('#myImg').attr('src');
}

$('#myModal').on('shown.bs.modal', function (e) {
	alteraUrl();
	$('#imgModal').attr('src', imagem);
});

/*$('#monthYearListagemm').datepicker({
	format: 'MM yyyy',
	startView: "months",
	minViewMode: "months",
    forceParse: false,
	autoclose: true
});*/


