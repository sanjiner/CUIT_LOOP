$(document).ready(function(e) {
	//$("#notice").on("click", function(event, showName) {
	//	$(this).find("ul").slideToggle();
	//	$(this).find("img:eq(2)").slideToggle();
	//});

	//$("#appcenter").on("click", function(event, showName) {
	//	$(this).find("ul").slideToggle();
	//	$(this).find("img:eq(2)").slideToggle();
	//});
	
	$(".container .items .item .optlabel").on("click", function(event, showName) {
		if($(this).siblings().find("ul").css('display')== "none"){
			$(".container .items .item .optlabel").siblings().find("ul").slideUp();
			$(this).siblings().find("ul").slideDown();
		}else{
			$(this).siblings().find("ul").slideUp();
		}
		
	});

	//$("#modifypwd").on("click", function(event, showName) {
	//	$(this).find("ul").slideToggle();
	//	$(this).find("img:eq(2)").slideToggle();
	//});
	//$("#sysstting").on("click", function(event, showName) {
	//	$(this).find("ul").slideToggle();
	//	$(this).find("img:eq(2)").slideToggle();
	//});
	//$("#myinfo").on("click", function(event, showName) {
	//	$(this).find("ul").slideToggle();
	//	$(this).find("img:eq(2)").slideToggle();
	//});
	//$("#msgmange").on("click", function(event, showName) {
	//	$(this).find("ul").slideToggle();
	//	$(this).find("img:eq(2)").slideToggle();

	//});
	//$("#xiangguan").on("click", function(event, showName) {
	//	$(this).find("ul").slideToggle();
	//	$(this).find("img:eq(2)").slideToggle();
	//});
	/*$("#AuthInfoManagement").on("click", function(event, showName) {
		$(this).find("ul").slideToggle();
		$(this).find("img:eq(2)").slideToggle();
	});*/
});
