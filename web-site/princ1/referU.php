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
    $json1 = file_get_contents("https://api.betterdoctor.com/2016-03-01/doctors?specialty_uid=Family%20Practitioner%2Cfamily-medicine-geriatric-medicine&location={$lat1}%2C{$lng1}%2C30&skip=0&limit=100&user_key=cd77cc12cae34716e40cdc54c4a0706c");






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
                <div class="owl-wrapper-outer">
                    <div class="owl-item" style="width: 380px;">
                        <div>
                            <div class="block">
                                <img src="' .$docs[$countdoc]["profile"]["image_url"]. '" alt="" class="img-responsive">
                                <div class="content">
                                    <h4>  '. $docs[$countdoc]["profile"]["first_name"].' '. $docs[$countdoc]["profile"]["last_name"].' '. $docs[$countdoc]["profile"]["title"].' </h4>
                                    <ul>
                                        <li><strong>Specialty:</strong> '.$docs[$countspec]["specialties"][0]["name"].' </li>
                                        <li><strong>Address:</strong> '.$docs[$countdoc]['practices'][0]['visit_address']['street'].', '.$docs[$countdoc]['practices'][0]['visit_address']['city'].', ' .$docs[$countdoc]['practices'][0]['visit_address']['state'].' '.$docs[$countdoc]['practices'][0]['visit_address']['zip'].' </li>
                                        <li> <strong>Phone Number:</strong> '.$docs[$countspec++]['practices'][0]['phones'][0]['number'].'</li>
                                    </ul>
                                    <a href="blog.html" class="btn btn-read">Read More</a>
                                </div>
                            </div> 
                        </div>
                    </div>
                    <div class="owl-item" style="width: 380px;">
                        <div>
                            <div class="block">
                                <img src="' .$docs[$countdoc++]["profile"]["image_url"]. '" alt="" class="img-responsive">
                                <div class="content">
                                    <h4> '. $docs[$countdoc++]["profile"]["first_name"].' '. $docs[$countdoc++]["profile"]["last_name"].' '. $docs[$countdoc++]["profile"]["title"].'</h4>
                                    <ul>
                                        <li><strong>Specialty:</strong> '.$docs[$countspec++]["specialties"][0]["name"].' </li>
                                        <li><strong>Address:</strong> '.$docs[$countdoc++]['practices'][0]['visit_address']['street'].', '.$docs[$countdoc++]['practices'][0]['visit_address']['city'].', ' .$docs[$countdoc++]['practices'][0]['visit_address']['state'].' '.$docs[$countdoc++]['practices'][0]['visit_address']['zip'].' </li>
                                        <li> <strong>Phone Number:</strong> '.$docs[$countspec++]['practices'][0]['phones'][0]['number'].'</li>
                                    </ul>
                                    <a href="blog.html" class="btn btn-read">Read More</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="owl-item" style="width: 380px;">
                        <div>
                            <div class="block">
                                <img src="' .$docs[$countdoc++]["profile"]["image_url"]. '" alt="" class="img-responsive">
                                <div class="content">
                                    <h4> '. $docs[$countdoc++]["profile"]["first_name"].' '. $docs[$countdoc++]["profile"]["last_name"].' '. $docs[$countdoc++]["profile"]["title"].'</h4>
                                    <ul>
                                        <li><strong>Specialty:</strong> '.$docs[$countspec]["specialties"][0]["name"].' </li>
                                        <li><strong>Address:</strong> '.$docs[$countdoc]['practices'][0]['visit_address']['street'].', '.$docs[$countdoc]['practices'][0]['visit_address']['city'].', ' .$docs[$countdoc]['practices'][0]['visit_address']['state'].' '.$docs[$countdoc]['practices'][0]['visit_address']['zip'].' </li>
                                        <li> <strong>Phone Number:</strong> '.$docs[$countspec++]['practices'][0]['phones'][0]['number'].'</li>
                                    </ul>
                                    <a href="blog.html" class="btn btn-read">Read More</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="owl-item" style="width: 380px;">
                    <div>
                        <div class="block">
                            <img src="' .$docs[$countdoc++]["profile"]["image_url"]. '" alt="" class="img-responsive">
                            <div class="content">
                                <h4> '. $docs[$countdoc++]["profile"]["first_name"].' '. $docs[$countdoc++]["profile"]["last_name"].' '. $docs[$countdoc++]["profile"]["title"].'</h4>
                                <ul>
                                    <li><strong>Specialty:</strong> '.$docs[$countspec]["specialties"][0]["name"].' </li>
                                    <li><strong>Address:</strong> '.$docs[$countdoc]['practices'][0]['visit_address']['street'].', '.$docs[$countdoc]['practices'][0]['visit_address']['city'].', ' .$docs[$countdoc]['practices'][0]['visit_address']['state'].' '.$docs[$countdoc]['practices'][0]['visit_address']['zip'].' </li>
                                    <li> <strong>Phone Number:</strong> '.$docs[$countspec++]['practices'][0]['phones'][0]['number'].'</li>
                                </ul>
                                <a href="blog.html" class="btn btn-read">Read More</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="owl-item" style="width: 380px;">
                    <div>
                        <div class="block">
                            <img src="' .$docs[$countdoc++]["profile"]["image_url"]. '" alt="" class="img-responsive">
                            <div class="content">
                                <h4> '. $docs[$countdoc++]["profile"]["first_name"].' '. $docs[$countdoc++]["profile"]["last_name"].' '. $docs[$countdoc++]["profile"]["title"].'</h4>
                                <ul>
                                    <li><strong>Specialty:</strong> '.$docs[$countspec]["specialties"][0]["name"].' </li>
                                    <li><strong>Address:</strong> '.$docs[$countdoc]['practices'][0]['visit_address']['street'].', '.$docs[$countdoc]['practices'][0]['visit_address']['city'].', ' .$docs[$countdoc]['practices'][0]['visit_address']['state'].' '.$docs[$countdoc]['practices'][0]['visit_address']['zip'].' </li>        
                                    <li> <strong>Phone Number:</strong> '.$docs[$countspec++]['practices'][0]['phones'][0]['number'].'</li>
                                </ul>
                                <a href="blog.html" class="btn btn-read">Read More</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="owl-item" style="width: 380px;">
                    <div>
                        <div class="block">
                            <img src="' .$docs[$countdoc++]["profile"]["image_url"]. '" alt="" class="img-responsive">
                            <div class="content">
                                <h4> '. $docs[$countdoc++]["profile"]["first_name"].' '. $docs[$countdoc++]["profile"]["last_name"].' '. $docs[$countdoc++]["profile"]["title"].'</h4>
                                <ul>
                                    <li><strong>Specialty:</strong> '.$docs[$countspec]["specialties"][0]["name"].' </li>
                                    <li><strong>Address:</strong> '.$docs[$countdoc]['practices'][0]['visit_address']['street'].', '.$docs[$countdoc]['practices'][0]['visit_address']['city'].', ' .$docs[$countdoc]['practices'][0]['visit_address']['state'].' '.$docs[$countdoc]['practices'][0]['visit_address']['zip'].' </li>
                                    <li> <strong>Phone Number:</strong> '.$docs[$countspec++]['practices'][0]['phones'][0]['number'].'</li>
                                </ul>
                                <a href="blog.html" class="btn btn-read">Read More</a>
                            </div>
                        </div>
                    </div>
                </div>        
            </div>
        </div>
    </div>
</div> 
 ';
?>




