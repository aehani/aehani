<?php
require_once './php/db_connect.php';
?>
<!DOCTYPE html>
<html lang="en">

<head>

	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Favorite Baby Names</title>
	<meta name="description" content="Cardio is a free one page template made exclusively for Codrops by Luka Cvetinovic" />
	<meta name="keywords" content="html template, css, free, one page, gym, fitness, web design" />
	<meta name="author" content="Luka Cvetinovic for Codrops" />
	<!-- Favicons (created with http://realfavicongenerator.net/)-->
	<link rel="apple-touch-icon" sizes="57x57" href="img/favicons/apple-touch-icon-57x57.png">
	<link rel="apple-touch-icon" sizes="60x60" href="img/favicons/apple-touch-icon-60x60.png">
	<link rel="icon" type="image/png" href="img/favicons/favicon-32x32.png" sizes="32x32">
	<link rel="icon" type="image/png" href="img/favicons/favicon-16x16.png" sizes="16x16">
	<link rel="manifest" href="img/favicons/manifest.json">
	<link rel="shortcut icon" href="img/favicons/favicon.ico">
	<meta name="msapplication-TileColor" content="#00a8ff">
	<meta name="msapplication-config" content="img/favicons/browserconfig.xml">
	<meta name="theme-color" content="#ffffff">
	<!-- Normalize -->
	<link rel="stylesheet" type="text/css" href="css/normalize.css">
	<!-- Bootstrap -->
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<!-- Owl -->
	<link rel="stylesheet" type="text/css" href="css/owl.css">
	<!-- Animate.css -->
	<link rel="stylesheet" type="text/css" href="css/animate.css">
	<!-- Font Awesome -->
	<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.1.0/css/font-awesome.min.css">
	<!-- Elegant Icons -->
	<link rel="stylesheet" type="text/css" href="fonts/eleganticons/et-icons.css">
	<!-- Main style -->
	<link rel="stylesheet" type="text/css" href="css/cardio.css">
    <link rel="stylesheet" type="text/css" href="css/p6.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"> 
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="css/demo.css">
    <link rel="stylesheet" type="text/css" href="css/set2.css">
</head>

<body>
	<div class="preloader">
		<img src="img/loader.gif" alt="Preloader image">
	</div>
	<nav class="navbar">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><img src="img/logo.png" data-active-url="img/logo-active.png" alt=""></a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right main-nav">
					<li><a href="http://lamp.cse.fau.edu/~aehani2016/" target="_blank">Home</a></li>
					<li><a href="#services" class="btn btn-blue">Add A Name</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
	<header id="intro">
		<div class="container">
			<div class="table">
				<div class="header-text">
					<div class="row">
						<div class="col-md-12 text-center">
							<h1 class="white typed">What's Your Favorite Baby Name? _______</h1>
							<span class="typed-cursor">|</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	<section id="services" class="section section-padded">
		<div class="container">
             <div class="container">
              </div>
            <?php
                // Create table with two columns: id and value
                $createStmt = 'CREATE TABLE `Boy` (' . PHP_EOL
                . '  `id` int(10) NOT NULL AUTO_INCREMENT,' . PHP_EOL
                . '  `Name` varchar(128) DEFAULT NULL,' . PHP_EOL
                . '  `Vote` int(11) DEFAULT NULL,' . PHP_EOL
                . '  PRIMARY KEY (`id`)' . PHP_EOL
                . ') ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;';
            
                // Create table with two columns: id and value
                $createStmt2 = 'CREATE TABLE `Girl` (' . PHP_EOL
                . '  `id` int(10) NOT NULL AUTO_INCREMENT,' . PHP_EOL
                . '  `Name` varchar(128) DEFAULT NULL,' . PHP_EOL
                . '  `Vote` int(11) DEFAULT NULL,' . PHP_EOL
                . '  PRIMARY KEY (`id`)' . PHP_EOL
                . ') ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;';
                ?>

                <?php
                // Add two rows to the table
                error_reporting(0);

                if($_POST['gender'] === 'Boy') {
                    $result = $db->query("SELECT Name FROM Boy WHERE Name = '{$_POST['fav_name']}'");
                    if($result->num_rows == 0) {
                        $insertStmt = "INSERT INTO Boy (id, Name, Vote) VALUES (null, '{$_POST['fav_name']}', '1')";// row not found, do stuff...
                        echo'<script> console.log('{$_POST['fav_name']};')</script>';
                    } else {

                        $updatestmt = $db->query("UPDATE Boy SET Vote = Vote + 1 WHERE Name = '{$_POST['fav_name']}'"); 
                    } 

                } else {
                    if($_POST['fav_name'] != "")
                    {
                        $result = $db->query("SELECT Name FROM Girl WHERE Name = '{$_POST['fav_name']}'");
                        if($result->num_rows == 0) {
                            $insertStmt = "INSERT INTO Girl (id, Name, Vote) VALUES (null, '{$_POST['fav_name']}', '1')";// row not found, do stuff...
                        } else {

                            $updatestmt = $db->query("UPDATE Girl SET Vote = Vote + 1 WHERE Name = '{$_POST['fav_name']}'"); 
                        } 
                    }


                }


                ?>
                <?php
                if($db->query($insertStmt)) {
                    echo '      ' . PHP_EOL;
                } else {
                    echo '       ' . PHP_EOL;
                }
                ?>

    
                <div id = "features">
                  <div id = "all">
                    <div id = "row">
                <?php

                // Get the rows from the table
                $selectStmt = 'SELECT Name, Vote FROM `Boy` ORDER BY Vote DESC limit 10;';
                ?>
                      <div id="title">
                        
                <?php
                $result = $db->query($selectStmt);
                $count = 1; 
                if($result->num_rows > 0) {
                    echo '        <div id = "table">' . PHP_EOL;
                    echo '        <h3>Popular Baby Boy Names</h3>' . PHP_EOL;
                    echo '        <table><tr><th>Rank</th><th>Name</th><th>Vote</th></tr>' . PHP_EOL;
                    while($row = $result->fetch_assoc()) {
                        echo '          <tr><td> ' .$count++ . '</td><td>' . $row["Name"] . '</td><td>' . $row["Vote"] .  '</td></tr>' . PHP_EOL;
                    }
                    echo '     </table></div>' . PHP_EOL;
                } else {
                    echo '      ' . PHP_EOL;
                }
                ?>
                <?php

                // Get the rows from the table
                $selectStmt = 'SELECT Name, Vote FROM `Girl` ORDER BY Vote DESC limit 10;';
                ?>

                        
                <?php
                $result = $db->query($selectStmt);
                $count = 1; 
                if($result->num_rows > 0) {
                    echo '        <div id = "table">' . PHP_EOL;
                    echo '        <h3>Popular Baby Girl Names</h3>' . PHP_EOL;
                    echo '        <table><tbody id = "new"><tr><th>Rank</th><th>Name</th><th>Vote</th></tr>' . PHP_EOL;
                    while($row = $result->fetch_assoc()) {
                        echo '          <tr><td> ' .$count++ . '</td><td>' . $row["Name"] . '</td><td>' . $row["Vote"] .  '</td></tr>' . PHP_EOL;
                    }
                    echo '     </tbody></table></div>' . PHP_EOL;
                } else {
                    echo '      ' . PHP_EOL;
                }
                ?>
                      </div>

              </div>            
            </div>
            </div>
		</div>
        <div>
            <form id = "myform" method="post" > 		
                <div id = "frm">
                <label>Gender</label>
                    <select id = "gender" name="gender" required>
                        <option value="" selected="1" disabled>Select</option>
                        <option  value="Boy">Boy</option>
                        <option  value="Girl">Girl</option>
                    </select>
                    <section class="content">
				        <span class="input input--nao">
					   <input class="input__field input__field--nao" type="text" id="input-1" name="fav_name" required minlength="2" maxlength="10" />
					   <label class="input__label input__label--nao" for="input-1">
						&nbsp;<span class="input__label-content input__label-content--nao">Name</span>
					   </label>
					   <svg class="graphic graphic--nao" width="300%" height="100%" viewBox="0 0 1200 60" preserveAspectRatio="none">
						  <path d="M0,56.5c0,0,298.666,0,399.333,0C448.336,56.5,513.994,46,597,46c77.327,0,135,10.5,200.999,10.5c95.996,0,402.001,0,402.001,0"/>
					   </svg>
				       </span>
                    </section>
                    <!--&nbsp;<label>Name</label>	
                        <input type="text" placeholder="name" name="fav_name" id="fav_name" required minlength="2" maxlength="10" /> -->	
                   <button type="submit" name="bntsubmit" id="bntsubmit" class="btn btn-default">Submit</button> &nbsp;
                    
                    

                </div>
            </form>
         </div>
		<div class="cut cut-bottom"></div>
	</section>
	<footer>
		<div class="container">
			<div class="row">
			</div>
			<div class="row bottom-footer text-center-mobile">
				<div class="col-sm-8">
					<p>&copy; 2019 All Rights Reserved. Created by Ahsan Ehani Courtesy of Bootsrap theme </p>
				</div>
			</div>
		</div>
	</footer>
	<!-- Holder for mobile navigation -->
	<div class="mobile-nav">
		<ul>
		</ul>
		<a href="#" class="close-link"><i class="arrow_up"></i></a>
	</div>
	<!-- Scripts -->
	<script src="js/jquery-1.11.1.min.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/wow.min.js"></script>
	<script src="js/typewriter.js"></script>
	<script src="js/jquery.onepagenav.js"></script>
	<script src="js/main.js"></script>
    <script src = "./php/p6.js"></script> 
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script>
        if ( window.history.replaceState ) {
          window.history.replaceState( null, null, window.location.href );
        }
    </script>
</body>

</html>
