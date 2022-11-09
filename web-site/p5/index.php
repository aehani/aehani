<?php 
include('header.php');
?>
<title>Currency conversion in PHP </title>
<script type="text/javascript" src="script/validation.min.js"></script>
<script type="text/javascript" src="script/ajax.js"></script>
<html class="no-js" lang="en">
   <nav>
        <ul>
           <li><a href ="/index.html">HOME </a></li>
           <li><a href ="/p1/index.html">Project 1 </a></li>
           <li><a href ="/p2/index.html">Project 2</a></li>
           <li><a href ="/p3/index.html">Project 3</a></li>
           <li><a href ="/p4/index.html">Project 4</a></li>
        </ul>
    </nav>
    <body> 
        
        <div class="container">

            
            <main class="s-home s-home--particles">


                <div id="particles-js" class="home-particles"></div>
    

                <div class="gradient-overlay"></div>

                <div class="home-content">

                    <div class="home-logo">
                        <a href="index-particles.html">
                        </a>
                    </div>

                    <div class="row home-content__main">

                        <div class="col-eight home-content__text pull-right">
                            <h3>Currency Converter</h3>

                            <form method="post" id="currency-form"> 		
                                <div class="form-group">
                                <label>From</label>
                                    <select name="from_currency">
                                        <option value="INR">Indian Rupee</option>
                                        <option value="USD" selected="1">US Dollar</option>
                                        <option value="AUD">Australian Dollar</option>
                                        <option value="EUR">Euro</option>
                                        <option value="EGP">Egyptian Pound</option>
                                        <option value="CNY">Chinese Yuan</option>
                                    </select>	
                                    &nbsp;<label>Amount</label>	
                                    <input type="text" placeholder="Currency" name="amount" id="amount" />			
                                    &nbsp;<label>To</label>
                                    <select name="to_currency">
                                        <option value="INR" selected="1">Indian Rupee</option>
                                        <option value="USD">US Dollar</option>
                                        <option value="AUD">Australian Dollar</option>
                                        <option value="EUR">Euro</option>
                                        <option value="EGP">Egyptian Pound</option>
                                        <option value="CNY">Chinese Yuan</option>
                                    </select>			
                                    &nbsp;&nbsp;<button type="submit" name="convert" id="convert" class="btn btn-default">Convert</button>	

                                </div>			
                            </form>	
                            <div id="converted_amount"></div>

                           <div style="margin:50px 0px 0px 0px;">

                           </div>		
                           
                        </div>  

                    </div> 

                    <div class="home-content__line"></div>

                </div> 

            </main> 
            
            <div class="row home-copyright">
                <span>Copyright Count 2018</span> 
                <span>Design by Ahsan Ehani - Courtesy of Theme</span>
            </div> <!-- end home-copyright -->


              <!-- preloader
            ================================================== -->
            <div id="preloader">
                <div id="loader">
                    <div class="line-scale-pulse-out">
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                    </div>
                </div>
            </div>

        </div>
        <script src="js/plugins.js"></script>
        <script src="js/polygons.js"></script>
        <script src="js/main.js"></script>
        <script src="js/theme.js"></script>
    </body>
</html>
<?php include('footer.php');?>


