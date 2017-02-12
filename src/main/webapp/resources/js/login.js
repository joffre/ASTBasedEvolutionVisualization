/**
 * 
 */

//$("#butSignUp").on("click", function(){
//	$('#formSignIn').hide();
//	$('#formSignUp').show();	
//
//});
//$("#butSignUp").on("click", function(){
//	var textButton = $(this).text();
//	if(textButton == 'Sign up') {
//		$('#formSignIn').hide();
//		$('#formSignUp').show();
//		$(this).text('Sign in');
//	} else {
//		$('#formSignUp').hide();
//		$('#formSignIn').show();
//		$(this).text('Sign up');
//	}
//});

jQuery(document).ready(function(){
    jQuery('clientId').change(function(){
        var tmpVal = jQuery('#clientId').val();
        var tmphref = jQuery('#githubLink').attr('href');
        tmphref = tmphref+tmpVal;
        jQuery('#githubLink').attr('href',tmphref);
    });
});
