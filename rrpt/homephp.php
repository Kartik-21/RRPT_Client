<!-- session -->
<?php
session_start();
 require_once 'config.php';
   $con = mysqli_connect("$host","$username","$password");
   mysqli_select_db($con,"$dbname");


 if(isset($_SESSION["email"]) && isset($_SESSION["password"])){

 
 	$q="select * from admin where a_email='$_SESSION[email]' and a_password='$_SESSION[password]'";
    		$i=mysqli_query($con,$q);

	  		if($r=mysqli_num_rows($i)==1){

		//	current page
				
	  	}else{
	   		header('Location: index.php?login_error=wrong');
	    		
 		}
 }else {

 	header('Location: index.php?login_error=error');
	    
 }

?>

<!-- deskboard page -->

<!DOCTYPE html>
<html>
<head>
	<title>login form</title>

	<script src="bootstrap/js/bootstrap.js"></script>
	<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css">
	<script src="js/jquery.js"></script>
	
</head>
<body>

	<?php  include 'headerphp.php'  ?>


	<?php include 'sidebarphp.php'?>

		
<div class="card" style="height: 180px; width: 230px; top: 20px; left: 40px; float: left;">

	<div class="card-body">
        <h1 align="center">1010</h1>
    </div>
    <div class="btn-primary">
        <h3 class="mb-0"><span style="text-align: center;">Total Books</span></h3>
    </div>
    
</div>

<div class="card" style="height: 180px; width: 230px; top: 20px; left: 60px; float: left;">

	<div class="card-body">
        <h1 align="center">1010</h1>
    </div>
    <div class="btn-primary">
        <h3 class="mb-0"><span style="text-align: center;">Total User</span></h3>
    </div>
    
</div>


<footer>


</footer>
		

	
</body>
</html>