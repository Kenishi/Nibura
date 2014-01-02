
/*-------------------- EXPANDABLE PANELS ----------------------*/
var panelspeed = 500; //panel animate speed in milliseconds
var totalpanels = 0;	// Total number of panels. Set dynamically at init
var accordian = false; //set panels to behave like an accordian, with one panel only ever open at once      
 
        var panelheight = new Array();
        var iconheight = parseInt($('.open-close-icon').css('height'));
 
//Initialise collapsible panels
function panelinit() {
	enumberateGroups();
	totalpanels = $(".group").length;
	
    for(var i = 0 ; i <= totalpanels; i++) {
        panelheight[i] = parseInt($('#g-'+i).find('.group-content').css('height'));
        $('#g-'+i).find('.group-content').css('margin-top', -panelheight[i]);
    }
}
 
function enumberateGroups() {
	var groups = $(".group");
	for(var i=0; i<=groups.length; i++) {
		$(groups[i]).attr("id", ("g-" + i));
	}
}

$('.group-header').click(function() {           
    var obj = $(this).next();
    var objid = parseInt($(this).parent().attr('ID').substr(3,2));  
    currentpanel = objid;
    if (accordian == true) {
        resetpanels();
    }
     
    if (parseInt(obj.css('margin-top')) <= (panelheight[objid]*-1)) {
        obj.clearQueue();
        obj.stop();
        obj.prev().find('.open-close-icon').css('background-position', '0px -'+iconheight+'px');
        obj.animate({'margin-top':0}, panelspeed);
    } else {
        obj.clearQueue();
        obj.stop();
        obj.prev().find('.open-close-icon').css('background-position', '0px 0px');
        obj.animate({'margin-top':(panelheight[objid]*-1)}, panelspeed); 
    }
});
 
function resetpanels() {
    for (var i=1; i<=totalpanels; i++) {
        if (currentpanel != i) {
            $('#g-'+i).find('.open-close-icon').css('background-position', '0px 0px');
            $('#g-'+i).find('.group-content').animate({'margin-top':-panelheight[i]}, panelspeed);
        }
    }
}