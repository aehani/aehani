
$(document).ready(function() {
    
    $("#bntsubmit").click(function(){
        var name = $('#input-1').val(); 
        var gender = $('#gender').val(); 
        console.log(gender); 
        var pattern = /^[a-zA-Z]+$/
        console.log(name); 
        if(!name.match(pattern)) {
            document.getElementById("input-1").setCustomValidity("Please enter characters");
            setTimeout(myfunction, 1000);  
            
        } else if (gender !== "boy" || gender !== "girl"){
            document.getElementById("#gender").setCustomValidity("Please select a gender");
            
        }
            else {
                document.getElementById("input-1").setCustomValidity("     ");
                document.getElementById("myform").submit(); 

            } 
       

           
        
    });

});

function myfunction () {
    document.getElementById("input-1").setCustomValidity("     ");
    //document.getElementById("myform").reset();
    
}

