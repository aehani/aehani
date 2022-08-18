

var report = function(result) {
    
    if(isNaN(result) === true)
    {
        document.getElementById("inputUserame").setCustomValidity("Please enter a number");
    } else if(result == "-1") {
        document.getElementById("inputUserame").setCustomValidity("Enter a postive Integer");
    }
    else {
        document.getElementById("inputUserame").setCustomValidity('       ');
        document.getElementById("result").innerHTML = result;
    }
   
}

document.getElementById("convert").onclick = function () {
    var f = document.getElementById("inputUserame").value; 
    calculate(f);  
};


var calculate = function (f) {
    var result; 
    if (isNaN(f) === true)
    {
        result = 'Please enter a number'; 
        report(result); 
    }
    else if(f > 0) 
    {
        result = f * 16;
        result = result.toFixed(2); 
        report(result);   
    }
    else if(f < 0) {
        result = -1;
        report(result); 
    }
};



var total = function(Answer) { 
    if(isNaN(Answer) === true) {
        document.getElementById("inputEmail").setCustomValidity('Please enter a number');
    } else if(Answer == "-1") { 

        document.getElementById("inputEmail").setCustomValidity('Enter a postive Integer');
    }
    
    else {
        document.getElementById("inputEmail").setCustomValidity('     ');
        document.getElementById("Answer").innerHTML = Answer;
    }
   
}



document.getElementById("Con").onclick = function () {
    var f1 = document.getElementById("inputEmail").value; 
    calculates(f1);  
};


var calculates = function (f1) { 
    var Answer = f1; 
    if (isNaN(f1) === true)
    {
        Answer = 'Please enter a number'; 
        total(Answer); 
    }
    else if(f1 > 0) 
    {
        Answer = f1 / 16; 
        Answer = Answer.toFixed(2); 
        total(Answer);   
    }
    else if(f1 < 0) {
        Answer = -1;
        total(Answer); 
    }

};







