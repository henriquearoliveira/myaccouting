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

$(function() {
	$("#valorConversao").maskMoney({prefix:'R$ ', allowNegative: true, thousands:'.', decimal:',', affixesStay: false});
});

//Date picker
$('#dataHoraLancamento').datepicker({
	format: 'dd/mm/yyyy',
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

/*$('#monthYearListagemm').datepicker({
	format: 'MM yyyy',
	startView: "months",
	minViewMode: "months",
    forceParse: false,
	autoclose: true
});*/


