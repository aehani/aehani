<?php
$e1=$_REQUEST['email1'];
$e2=$_REQUEST['email2'];
$output="Email verification failed, please try again!";
if((filter_var($e1, FILTER_VALIDATE_EMAIL))&&(filter_var($e2, FILTER_VALIDATE_EMAIL))&&($e1==$e2))
{
$masteremail=$_REQUEST['email2'];
$q1_val = $_POST['q1'];
$q2_val=$_POST['q2'];
$q3_val=$_POST['q3'];
$q4_val=$_POST['q4'];
$msg="1. Which of the following symptoms are you experiencing? \n" . $q1_val . 
     "\r\n2. Which of the following do you closely identify to?\n " . $q2_val . 
     "\r\n3. Which of the following symptoms are you experiencing? \n" . $q3_val .
     "\r\n4. Which of the following symptoms are you experiencing? \n" . $q4_val;
$header="From: ReferU \r\n";
$headers .= 'MIME-Version: 1.0' . "\r\n";
$headers .= "Content-type: text/html; charset=iso-8859-1\r\n";
mail($masteremail, "Your Questionnaire Reponse",$msg, $header);
header("Location: index.php");
}
else{
    //echo($output);
    echo "<script type='text/javascript'>alert('$output');</script>";
    //header("Location: index.html");
    
}
?>
