
$(document).ready(function(){

	//date picker			 
       $('#dob').Zebra_DatePicker();

	//mobile number validation
			$("#mobile_number").focusout(function(){
					var data = $("#mobile_number").val();
						var re = /^(\+91-|\+91|0)?\d{10}$/;
						var result;

						if(!data.match(re))
						{
						alert("Please Enter 10 digits Telephone Number");
						$("#mobile_number").focus();
					}
				});
				
		//username validation
		$("#username").change(function(){
				var username=$("#username").val();
				//alert(username)
				
					jQuery.ajax({
						type: "GET",
						data: {"username":username},
						url:"https://script.google.com/macros/s/AKfycbwkHKnGbDq0dgtIfNzposx5jlAthq0CQbfhlgVZRi7-OOb8NUQ/exec", 
						
						success:function(response)
						{
							if(response!='')
							{
								if(response=="username_already_taken")
								{
									alert("username already exist! please select another username!");
									$("#username").focus();
								}
							}
						}
					});
		});
		
		
		//compare password and confirm password
		$("#cpassword").blur(function(){
			var password=$("#password").val();
			var cpassword=$("#cpassword").val();
			if(password!=cpassword)
			{
				alert("Password and Confirm Password does not match!");
				$("#cpassword").focus();
			}
			
		});
	
});//end document ready



