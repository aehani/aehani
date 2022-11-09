$(document).ready(function () {
     $('#list').html(localStorage.getItem('ListItems'));
});
var oriVal; 
var ids; 
var num = 1; 
$('')
$('#convert').on('click', function(e) {
    e.preventDefault(); 
    var text = $('input:text').val();
    if(text !== "") 
    {
        document.getElementById("myInput").value = "";
        $('#list').append('<li id = "item'+num+'">'+ text + "<button id='remove' class= 'hvr-pulse'>X</button><hr></li>"); 
        num++; 
        localStorage.setItem('ListItems', $('#list').html()); 
    }
    document.getElementById("myInput").focus();

    
});
$("#list").on('dblclick', 'li', function () {
    oriVal = $(this).text();
    $(this).text("");
    $("<input class='sti' type='text'>").appendTo(this).focus();
    $('.sti').css('height','40px'); 
    $('.sti').css('bottom', '5px'); 
    $('.sti').css('width', '250px');    
    oriVal = oriVal.slice(0,-1); 
    ids = $(this).attr('id'); 
});

$("#list").on('focusout', 'li > input', function () {
    var $this = $(this);
    if($this.val() !== "")
    {
        $this.parent().text($this.val() || oriVal);
        console.log(ids); 
        console.log($this.val()); 
        $(document.getElementById(ids)).append("<button id='remove'>X</button><hr></li>"); 
        $this.remove(); // Don't just hide, remove the element.
        localStorage.setItem('ListItems', $('#list').html()); 
    }
    else 
    {
        //$this.val(oriVal); 
        $this.parent().text($this.val() || oriVal);
        console.log(ids); 
        console.log($this.val()); 
        $(document.getElementById(ids)).append("<button id='remove'>X</button><hr></li>"); 
        $this.remove(); // Don't just hide, remove the element.
        localStorage.setItem('ListItems', $('#list').html()); 
    }

});


//remove function
$(document).on('click', '#remove', function () {
  $(this).parent().remove();
  localStorage.setItem('ListItems', $('#list').html());     //update local storage with removed items
});

$("#list").on('click', 'li', function () {   
    var $this = $(this);               // Cache the element in a jQuery object
    var select = $this.hasClass('select');     // Is item selected

    if (select === true) {           // Check if item is selected
      $this.removeClass("select");
    } else {                           // Otherwise indicate it is 
        $this.addClass("select");
        localStorage.setItem('ListItems', $('#list').html());
    }                                 // End of else option
  });   












$('#myInput').on('focus', function() {
    this.value=''; 
});
$('#myInput').on('keypress',function(e) {
    if(e.which == 13) {
        e.preventDefault(); 
        var text = $('input:text').val();
        if(text !== "")
            {
                this.value=''; 
                $('#list').append('<li id = "item'+num+'">'+ text + "<button id='remove' class= 'hvr-pulse'>X</button><hr></li>"); 
                num++; 
            }

       
    }
});




