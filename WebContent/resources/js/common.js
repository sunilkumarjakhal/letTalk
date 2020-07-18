	$(document).ready(
			function() {
				
				  $('#mainOuterDiv').height($(window).height());
				
				$("#searchF").click(function(){
										var searchValue = $("#username").val();
					//alert("clicked"+searchValue)
					
					 window.location.assign('search?queryName=' + searchValue)
				/* 	$.ajax({
						url : 'search?queryName=' + searchValue,
						type : 'GET',
						success : function(data) {
			//alert(data)
			//window.location.href('search?queryName=' + searchValue);
			
						},
						error : function(xhr, status, error) {
							alert("failure")
							console.log(xhr.responseText)

						}
					});*/
					});  
				
				
			});