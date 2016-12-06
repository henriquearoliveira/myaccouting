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
