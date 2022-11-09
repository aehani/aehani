<?php
$zipcode = $_POST['zipcode1']; 
$doctype = $_POST['doc']; 
$e1=$_REQUEST['email1'];
$e2=$_REQUEST['email2'];
$output="Email verification failed, please try again!";
$input = "I am here"; 
//echo "<script type='text/javascript'>alert('$e1');</script>";
//echo "<script type='text/javascript'>alert('$e2');</script>";
if((filter_var($e1, FILTER_VALIDATE_EMAIL))&&(filter_var($e2, FILTER_VALIDATE_EMAIL))&&($e1==$e2))
{   
    //echo "<li>".$zipcode. "</li><li>" .$doctype. "</li><li>" .$e1."</li><li>".$e2. "</li><li>"; 
    $zipcode2 = json_encode($zipcode); 
    //echo "<li>".$zipcode2. "</li><li>" .$doctype1. "</li><li>";
    $json = file_get_contents("https://www.zipcodeapi.com/rest/15sinzW7g6ezPdZFYZDBKFP5sogdirb5aYeNPogXAj9pDr1gj4s6wSbTrwfGYZYx/info.json/{$zipcode}/degrees");
    $arr = json_decode($json);
    json_encode( $arr );
    //echo $json  ."<br />"; 
    //echo $arr->lat."<br />"; 
    //echo $arr->lng."<br />"; 
    $lat = $arr->lat; 
    $lng = $arr->lng; 
    $lng1 = json_encode($lng); 
    $lat1 = json_encode($lat); 
    if($doctype == "pcp")
    {
         $json1 = file_get_contents("https://api.betterdoctor.com/2016-03-01/doctors?specialty_uid=Family%20Practitioner%2Cfamily-medicine-geriatric-medicine&location={$lat1}%2C{$lng1}%2C15&skip=0&limit=100&user_key=cd77cc12cae34716e40cdc54c4a0706c");
    }
    else {
        $json1 = file_get_contents("https://api.betterdoctor.com/2016-03-01/doctors?specialty_uid={$doctype}&location={$lat1}%2C{$lng1}%2C15&skip=0&limit=100&user_key=cd77cc12cae34716e40cdc54c4a0706c"); 
        
    }
    $arr1 = json_decode($json1, true);
    //echo $json1  ."<br />"; 
    $meta = $arr1['meta']; 
    $docs = $arr1['data']; 
    if($meta['total'] != 0) {
    $countdoc = 0; 
    $count = 0;
    $count1 = 1; 
    $count2 = 2;
    $count3 = 3;
    $count4 = 4;
    $count5 = 5;
    $st = "FL"; 
    $address = $docs[$count]['practices'][0]['visit_address']['street'];
    $zip = $docs[$count]['practices'][0]['visit_address']['zip'];
    $ad = explode(" ", $address); 
    $ad1 = $docs[$count1]['practices'][0]['visit_address']['street'];
    $zip1 = $docs[$count1]['practices'][0]['visit_address']['zip'];
    $add = explode(" ", $ad1); 
    $ad2 = $docs[$count2]['practices'][0]['visit_address']['street'];
    $zip2 = $docs[$count2]['practices'][0]['visit_address']['zip'];
    $addr = explode(" ", $ad2);
    $ad3 = $docs[$count3]['practices'][0]['visit_address']['street'];
    $zip3 = $docs[$count3]['practices'][0]['visit_address']['zip'];
    $addre = explode(" ", $ad3);
    $ad4 = $docs[$count4]['practices'][0]['visit_address']['street'];
    $zip4 = $docs[$count4]['practices'][0]['visit_address']['zip'];
    $ads = explode(" ", $ad4); 
    $ad5 = $docs[$count5]['practices'][0]['visit_address']['street'];
    $zip5 = $docs[$count5]['practices'][0]['visit_address']['zip'];
    $adds = explode(" ", $ad5); 
    error_reporting(0);
    echo'<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="title">
							<h2>Doctors</h2>
						</div> 
                        <div id="blog-post" class="owl-carousel" style="opacity: 1; display: block;" >
							<div class="owl-wrapper-outer">
                            <div class="owl-item" style="width: 380px;"><div>
								<div class="block">
									<img src="' .$docs[$countdoc]["profile"]["image_url"]. '" alt="" class="img-responsive">
									<div class="content">
										<h4>  '. $docs[$countdoc]["profile"]["first_name"].' '. $docs[$countdoc]["profile"]["last_name"].' '. $docs[$countdoc]["profile"]["title"].' </h4>
                                        
										<ul>
											<li><strong>Specialty:</strong> '.$docs[$countdoc]["specialties"][0]["name"].' </li>
                                            <li><strong>Address:</strong><a target="_blank" href = "http://maps.google.com/maps?q='.$ad[0].'+'.$ad[1].'+'.$ad[2].'+'.$ad[3].'+'.$ad[4].'+'.$ad[5].''.$st.'+'.$zip.'+'.$docs[$countdoc]["profile"]["slug"].'">'.$docs[$countdoc]['practices'][0]['visit_address']['street'].', '.$docs[$countdoc]['practices'][0]['visit_address']['city'].', ' .$docs[$countdoc]['practices'][0]['visit_address']['state'].' '.$docs[$countdoc]['practices'][0]['visit_address']['zip'].'</a></li>
                                            
                                           <li> <strong>Phone Number:</strong> '.$docs[$countdoc++]['practices'][0]['phones'][0]['number'].'</li>
										</ul>
                                         <a href="blog.html" class="btn btn-read">Read More</a>
				                </div>
				        </div> 
                        </div></div>
                        <div class="owl-item" style="width: 380px;"><div>
                            <div class="block">
                                <img src="' .$docs[$countdoc]["profile"]["image_url"]. '" alt="" class="img-responsive">
                                <div class="content">
                                    <h4> '. $docs[$countdoc]["profile"]["first_name"].' '. $docs[$countdoc]["profile"]["last_name"].' '. $docs[$countdoc]["profile"]["title"].'</h4>
                                            <ul>
											<li><strong>Specialty:</strong> '.$docs[$countdoc]["specialties"][0]["name"].' </li>
                                           <li><strong>Address:</strong><a target="_blank" href = "http://maps.google.com/maps?q='.$add[0].'+'.$add[1].'+'.$add[2].'+'.$add[3].'+'.$add[4].'+'.$add[5].''.$st.'+'.$zip1.'+'.$docs[$countdoc]["profile"]["slug"].'"> '.$docs[$countdoc]['practices'][0]['visit_address']['street'].', '.$docs[$countdoc]['practices'][0]['visit_address']['city'].', ' .$docs[$countdoc]['practices'][0]['visit_address']['state'].' '.$docs[$countdoc]['practices'][0]['visit_address']['zip'].'</a></li>
                                           <li> <strong>Phone Number:</strong> '.$docs[$countdoc++]['practices'][0]['phones'][0]['number'].'</li>
										</ul>
                                    <a href="blog.html" class="btn btn-read">Read More</a>

                                </div>
                            </div>
                        </div>
                        </div>
                        <div class="owl-item" style="width: 380px;">
                        <div>
                            <div class="block">
                                <img src="' .$docs[$countdoc]["profile"]["image_url"]. '" alt="" class="img-responsive">
                                <div class="content">
                                    <h4> '. $docs[$countdoc]["profile"]["first_name"].' '. $docs[$countdoc]["profile"]["last_name"].' '. $docs[$countdoc]["profile"]["title"].'</h4>
                                    <ul>
											<li><strong>Specialty:</strong> '.$docs[$countdoc]["specialties"][0]["name"].' </li>
                                           <li><strong>Address:</strong><a target="_blank" href = "http://maps.google.com/maps?q='.$addr[0].'+'.$addr[1].'+'.$addr[2].'+'.$addr[3].'+'.$addr[4].'+'.$addr[5].''.$st.'+'.$zip2.'+'.$docs[$countdoc]["profile"]["slug"].'"> '.$docs[$countdoc]['practices'][0]['visit_address']['street'].', '.$docs[$countdoc]['practices'][0]['visit_address']['city'].', ' .$docs[$countdoc]['practices'][0]['visit_address']['state'].' '.$docs[$countdoc]['practices'][0]['visit_address']['zip'].'</a></li>
                                            
                                            <li><strong>Phone Number:</strong> '.$docs[$countdoc++]['practices'][0]['phones'][0]['number'].'</li>
										</ul>
                                         <a href="blog.html" class="btn btn-read">Read More</a>
                                </div>
                            </div>
                        </div>
                       </div></div>
                        <div class="owl-item" style="width: 380px;">
                        <div>
                            <div class="block">
                                <img src="' .$docs[$countdoc++]["profile"]["image_url"]. '" alt="" class="img-responsive">
                                <div class="content">
                                    <h4> '. $docs[$countdoc++]["profile"]["first_name"].' '. $docs[$countdoc]["profile"]["last_name"].' '. $docs[$countdoc]["profile"]["title"].'</h4>
                                    <ul>
											<li><strong>Specialty:</strong> '.$docs[$countdoc]["specialties"][0]["name"].' </li>
                                             <li><strong>Address:</strong><a target="_blank" href = "http://maps.google.com/maps?q='.$addre[0].'+'.$addre[1].'+'.$addre[2].'+'.$addre[3].'+'.$addre[4].'+'.$addre[5].''.$st.'+'.$zip3.'+'.$docs[$countdoc]["profile"]["slug"].'"> '.$docs[$countdoc]['practices'][0]['visit_address']['street'].', '.$docs[$countdoc]['practices'][0]['visit_address']['city'].', ' .$docs[$countdoc]['practices'][0]['visit_address']['state'].' '.$docs[$countdoc]['practices'][0]['visit_address']['zip'].'</a></li>
                                            
                                            <li><strong>Phone Number:</strong> '.$docs[$countdoc++]['practices'][0]['phones'][0]['number'].'</li>
										</ul>
                                         <a href="blog.html" class="btn btn-read">Read More</a>
                                </div>
                            </div>
                        </div>
                        </div>
                       <div class="owl-item" style="width: 380px;">
                        <div>
                            <div class="block">
                                <img src="' .$docs[$countdoc]["profile"]["image_url"]. '" alt="" class="img-responsive">
                                <div class="content">
                                    <h4> '. $docs[$countdoc]["profile"]["first_name"].' '. $docs[$countdoc]["profile"]["last_name"].' '. $docs[$countdoc]["profile"]["title"].'</h4>
                                    <ul>
											<li><strong>Specialty:</strong> '.$docs[$countdoc]["specialties"][0]["name"].' </li>
                                          <li><strong>Address:</strong><a target="_blank" href = "http://maps.google.com/maps?q='.$ads[0].'+'.$ads[1].'+'.$ads[2].'+'.$ads[3].'+'.$ads[4].'+'.$ads[5].''.$st.'+'.$zip4.'+'.$docs[$countdoc]["profile"]["slug"].'"> '.$docs[$countdoc]['practices'][0]['visit_address']['street'].', '.$docs[$countdoc]['practices'][0]['visit_address']['city'].', ' .$docs[$countdoc]['practices'][0]['visit_address']['state'].' '.$docs[$countdoc]['practices'][0]['visit_address']['zip'].'</a></li>
                                          
                                            <li> <strong>Phone Number:</strong> '.$docs[$countdoc++]['practices'][0]['phones'][0]['number'].'</li>
										</ul>
                                         <a href="blog.html" class="btn btn-read">Read More</a>
                                </div>
                            </div>
                        </div>
                       </div>
                       <div class="owl-item" style="width: 380px;">
                        <div>
                            <div class="block">
                                <img src="' .$docs[$countdoc]["profile"]["image_url"]. '" alt="" class="img-responsive">
                                <div class="content">
                                    <h4> '. $docs[$countdoc]["profile"]["first_name"].' '. $docs[$countdoc]["profile"]["last_name"].' '. $docs[$countdoc]["profile"]["title"].'</h4>
                                    <ul>
											<li><strong>Specialty:</strong> '.$docs[$countdoc]["specialties"][0]["name"].' </li>
                                          <li><strong>Address:</strong><a target="_blank" href = "http://maps.google.com/maps?q='.$adds[0].'+'.$adds[1].'+'.$adds[2].'+'.$adds[3].'+'.$adds[4].'+'.$adds[5].''.$st.'+'.$zip5.'+'.$docs[$countdoc]["profile"]["slug"].'"> '.$docs[$countdoc]['practices'][0]['visit_address']['street'].', '.$docs[$countdoc]['practices'][0]['visit_address']['city'].', ' .$docs[$countdoc]['practices'][0]['visit_address']['state'].' '.$docs[$countdoc]['practices'][0]['visit_address']['zip'].'</a></li>
                                            <li><strong>Phone Number:</strong> '.$docs[$countdoc++]['practices'][0] ['phones'][0]['number'].'</li>
										</ul>
                                         <a href="blog.html" class="btn btn-read">Read More</a>
                                </div>
                            </div>
                        </div>
                       </div>        
                </div>
					</div>
				</div>
			</div> ';
    } else {
        echo'<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="title">
							<h2>Doctors</h2>
                            <p><strong>No geriatric general health practitioners found in this zip code. <br> Please reload the page and enter another zip code.</p> 
						</div> 
                    </div>
                </div>
              </div>'; 
    }
  
    ?>
<script>
    $('html, body').animate({scrollTop: $("#blog").offset().top}, 500); 
</script>
<?php
    
    
    $masteremail=$_REQUEST['email2'];
    $q1_val = $_POST['q1'];
    $q2_val=$_POST['q2'];
    $q3_val=$_POST['q3'];
    $q4_val=$_POST['q4'];
    $msg="1. Which of the following symptoms are you experiencing? \n" . $q1_val . 
     "\r\n2. Which of the following do you closely identify to?\n " . $q2_val . 
     "\r\n3. Which of the following symptoms are you experiencing? \n" . $q3_val .
     "\r\n4. Which of the following symptoms are you experiencing? \n" . $q4_val;
    $headers ="From: ReferU \r\n";
    $headers .= 'MIME-Version: 1.0' . "\r\n";
    $headers .= "Content-type: text/html; charset=iso-8859-1\r\n";
    mail($masteremail, "Your Questionnaire Reponse",$msg, $headers);
}
else{
     //echo($output);
    echo "<script type='text/javascript'>alert('$output');</script>";
    //header("Location: index.html");
}
?>


