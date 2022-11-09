$('document').ready(function() { 
	/* handling form validation */
	$("#currency-form").validate({
		rules: {
			amount: {
				required: true,
			},
		},
		messages: {
			amount:{
			  required: ""
			 },			
		},
		submitHandler: submitForm	
	});	   
	/* Handling login functionality */
	function submitForm() {		
		var data = $("#currency-form").serialize();				
		$.ajax({				
			type : 'POST',
			url  : 'convert.php',
			dataType:'json',
			data : data,
			beforeSend: function(){	
				$("#convert").html('<span class="glyphicon glyphicon-transfer"></span> &nbsp; converting ...');
			},
			success : function(response){
				if(response.error == 1){
                    $("#converted_amount").html('<span class="form-group has-error">Error: Please select different currency</span>');
                    $("#convert").html('Convert');
				} else if(response.error == 2){
                    $("#converted_amount").html('<span class="form-group has-error">Error: Please enter a positive amount</span>');
                    $("#convert").html('Convert');
				} else if(response.error == 3){
                    $("#converted_amount").html('<span class="form-group has-error">Error: Please a number</span>');
                    $("#convert").html('Convert');
                } else if(response.rate){									
					$("#converted_amount").html("<strong>Converted Amount ("+response.to_Currency+"</strong>) : "+response.converted_amount);
					$("#converted_amount").show();
					$("#convert").html('Convert');
				} else {	
					$("#converted_amount").html("No Result");
                    $("#converted_amount").show();
				}
			}
		});
		return false;
	}   
});