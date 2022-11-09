
$(document).ready(function() {
    
    $("#submit").click(function(){
     var name = $('#fav_name').val(); 
     console.log(name);
        if(isNaN(name) === false) {
            document.getElementById("fav_name").setCustomValidity("Please enter a name"); 
            //document.getElementById("myform").reset();

        } else if(name === " ") {
            document.getElementById("fav_name").setCustomValidity("Please enter a name");
           // document.getElementById("myform").reset();
           
        } else {
            var name = " "; 
           
        }
        //location.reload(true);
    });

});


