
$(document).ready(function() {
    
    $("#send").click(function(){
        var name = $('#name').val(); 
        var zipcode = $('#zipcode1').val(); 
        console.log(zipcode); 
        var pattern = /^[a-zA-Z]+$/
        var pattern1 = /^[0-9]*$/
        //alert(zipcode); 
        console.log(name); 
        if(!name.match(pattern)) {
            document.getElementById("name").setCustomValidity("Please enter characters");
            setTimeout(myfunction, 1000);  
            
        } else if (!zipcode.match(pattern1)){
            //alert(zipcode); 
            document.getElementById("zipcode1").setCustomValidity("Please enter valid zip code");
            setTimeout(myfunction, 1000);  
        }
            else {
            
                document.getElementById("name").setCustomValidity("     ");
                document.getElementById("zipcode1").setCustomValidity("     ");
                $.post("referU.php", {name: name, zipcode: zipcode},
                function(data) {
                    $('#blog').html(data);
                    $('#myform')[0].reset();
                });

            } 
       

           
        
    });

});

function myfunction () {
    document.getElementById("name").setCustomValidity("     ");
    document.getElementById("zipcode1").setCustomValidity("     ");
    //document.getElementById("myform").reset();
    
}




























