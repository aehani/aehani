<?php
 
   // echo $_POST['zipcode'];
    $zipcode =  $_POST['zipcode'];
   // echo $zipcode; 
    $zipcode1 = json_encode($zipcode); 
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
    $json1 = file_get_contents("https://api.betterdoctor.com/2016-03-01/doctors?specialty_uid=Family%20Practitioner%2Cfamily-medicine-geriatric-medicine&location={$lat1}%2C{$lng1}%2C15&skip=0&limit=100&user_key=cd77cc12cae34716e40cdc54c4a0706c"); 
    $arr1 = json_decode($json1, true);
    $meta = $arr1['meta']; 
    $docs = $arr1['data']; 
    $countdoc = 2;
    $countspec = 3; 
   /*echo '<div class="container"><div class="row"><div class="col-md-12"><div class="title"><h2>Blog</h2><p>Dantes remained confused and silent by this explanation of the <br> thoughts which had unconsciously</p></div><div id="blog-post" class="owl-carousel" ><div><div class="block"><img src="' .$docs[$countdoc]["profile"]["image_url"]. '" alt="" class="img-responsive"><div class="content"><h4> '. $docs[$countdoc]["profile"]["first_name"].''. $docs[$countdoc]["profile"]["last_name"].' '. $docs[$countdoc]["profile"]["title"].'  </h4>'; */
   echo'<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="title">
							<h2>Blog</h2>
							<p>Dantes remained confused and silent by this explanation of the <br> thoughts which had unconsciously</p>
						</div> 
                        <div id="blog-post" class="owl-carousel" style="opacity: 1; display: block;" >
							<div class="owl-wrapper-outer"><div class="owl-wrapper" style="width: 3800px; left: 0px; transition: all 800ms ease 0s; transform: translate3d(-380px, 0px, 0px);"><div class="owl-item" style="width: 380px;"><div>
								<div class="block">
									<img src="' .$docs[$countdoc]["profile"]["image_url"]. '" alt="" class="img-responsive">
									<div class="content">
										<h4>  '. $docs[$countdoc]["profile"]["first_name"].''. $docs[$countdoc]["profile"]["last_name"].' '. $docs[$countdoc]["profile"]["title"].' </h4>
										<small>By admin / Sept 18, 2014</small>
										<ul>
											<li>Specialized in '.$docs[$countspec]["specialties"][0]["name"].' </li>
                                            <li>Adress </li>
										</ul>
										
									</div>
				                </div>
				        </div> 
                        </div></div><div class="owl-item" style="width: 380px;"><div>
                            <div class="block">
                                <img src="' .$docs[$countdoc++]["profile"]["image_url"]. '" alt="" class="img-responsive">
                                <div class="content">
                                    <h4> '. $docs[$countdoc++]["profile"]["first_name"].''. $docs[$countdoc++]["profile"]["last_name"].' '. $docs[$countdoc++]["profile"]["title"].'</h4>
                                    <small>By admin / Sept 18, 2014</small>
                                    <ul>
                                        <li>Specialized in '.$docs[$countspec++]["specialties"][0]["name"].'</li> 
                                    </ul>
                                    <a href="blog.html" class="btn btn-read">Read More</a>

                                </div>
                            </div>
                        </div>
                        </div></div><div class="owl-item" style="width: 380px;"><div>
                            <div class="block">
                                <img src="' .$docs[$countdoc++]["profile"]["image_url"]. '" alt="" class="img-responsive">
                                <div class="content">
                                    <h4> '. $docs[$countdoc++]["profile"]["first_name"].''. $docs[$countdoc++]["profile"]["last_name"].' '. $docs[$countdoc++]["profile"]["title"].'</h4>
                                    <small>By admin / Sept 18, 2014</small>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus ex itaque repudiandae nihil qui debitis atque necessitatibus aliquam, consequuntur autem!
                                    </p>
                                    <a href="blog.html" class="btn btn-read">Read More</a>

                                </div>
                            </div>
                        </div>
                       </div></div><div class="owl-item" style="width: 380px;"><div>
                            <div class="block">
                                <img src="img/blog/blog-4.jpg" alt="" class="img-responsive">
                                <div class="content">
                                    <h4><a href="blog.html">Hey,This is a blog title</a></h4>
                                    <small>By admin / Sept 18, 2014</small>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus ex itaque repudiandae nihil qui debitis atque necessitatibus aliquam, consequuntur autem!
                                    </p>
                                    <a href="blog.html" class="btn btn-read">Read More</a>

                                </div>
                            </div>
                        </div>
                        </div></div><div class="owl-item" style="width: 380px;"><div>
                            <div class="block">
                                <img src="img/blog/blog-1.jpg" alt="" class="img-responsive">
                                <div class="content">
                                    <h4><a href="blog.html">Hey,This is a blog title</a></h4>
                                    <small>By admin / Sept 18, 2014</small>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusamus ex itaque repudiandae nihil qui debitis atque necessitatibus aliquam, consequuntur autem!
                                    </p>
                                    <a href="blog.html" class="btn btn-read">Read More</a>

                                </div>
                            </div>
                        </div></div></div></div>
							
				      <div class="owl-controls clickable"><div class="owl-pagination"><div class="owl-page active"><span class=""></span></div><div class="owl-page"><span class=""></span></div>
                      </div></div></div>
					</div>
				</div>
			</div> ';
?>






<?php 

    echo $zipcode =  $_POST['zipcode'];
    echo $doctype =  $_POST['doc'];
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