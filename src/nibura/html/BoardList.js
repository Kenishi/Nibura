function panelinit() {
	$(".group-content").css("display", "none"); // Set all groups to not show
	
	$(".group-header").click(function() {
		var contentEle =  $(this).parent().find(".group-content");
		var curState = contentEle.css("display");
		
		if(curState == "block") {
			contentEle.css('display', 'none');
		}
		else {
			contentEle.css('display', 'block');
		}
	});
}