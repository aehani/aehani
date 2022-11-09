
// function to calculate the result of the survey
function tabulateAnswers() {
  //Type of Specialists    
  var cardiologist = 0;
  var neurologist = 0;
  var gastroenterology = 0;
  var primaryCareDoctor = 0;
  var check=0;
    var category = [0,0,0,0];
    var count = 0
       if($('input[name=q1]:checked').length<=0)
        {
            check+=1;
        }
        if($('input[name=q2]:checked').length<=0)
        {
            check+=1;
        }
        if($('input[name=q3]:checked').length<=0)
        {
            check+=1;
        }
        if($('input[name=q4]:checked').length<=0)
        {
            check+=1;
        }
    if(check>=1)
        {
         alert("you have not answered all questions");   
        }
    else{
  // get a list of the radio inputs on the page
  var choices = document.getElementsByTagName('input');
        
    
    
    
  // loop through all the radio inputs
  for (i=0; i<choices.length; i++) {
    // if the radio is checked..
    if (choices[i].checked) {
      // add 1 to that choice's score
      if (choices[i].title == 'c1') {
        cardiologist = cardiologist + 1;
          category[0]+=1;
      }
      if (choices[i].title == 'c2') {
        neurologist = neurologist + 1;
          category[1]+=1;
      }
      if (choices[i].title == 'c3') {
        gastroenterology = gastroenterology + 1;
          category[2]+=1;
      }
      if (choices[i].title == 'c4') {
        primaryCareDoctor = primaryCareDoctor + 1;
          category[3]+=1;
      }
    }
  }
    
    
  // Math.max returns the highest value amongst parameters sent.    
  var maxscore = Math.max(cardiologist,neurologist,gastroenterology,primaryCareDoctor);
  var specialist; 
    for (var  j = 0; j < 4; j++)
        {
            if (category[j] == maxscore)
                {
                    count++;
                }
            
        }
    if (count >=2 )
        {
            alert("PCP, there exists two max");
            specialist = "pcp"; 
            
        }
  
  else {
      var answerbox = document.getElementById('answer');
      
  if (cardiologist == maxscore) {
    //answerbox.innerHTML = "Cardiologist";
      alert("Cardiologist");
      specialist = "Cardiologist"; 
  }
  if (neurologist == maxscore) {
    //answerbox.innerHTML = "Neurologist";
     alert("Neurologist");
     specialist = "Neurologist"; 
  }
  if (gastroenterology == maxscore) {
    //answerbox.innerHTML = "Gastroenterology";
      alert("Gastroenterologist");
      specialist = "Gastroenterologist"; 
  }
       }
  /*if (primaryCareDoctor == maxscore) {
    //answerbox.innerHTML = "Based on your responses, we cannot determine a specialist, therefore we will refer you to a Primary Care Doctor";
      alert("Based on your responses, we cannot determine a specialist, therefore we will refer you to a Primary Care Doctor");
  }*/

}
}
/*
// program the reset button
function resetAnswer() {
  var answerbox = document.getElementById('answer');
  answerbox.innerHTML = "Your result will show up here!";
} */