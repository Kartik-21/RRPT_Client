<?php

session_start();
?>
<?php

 require_once 'config.php';
  $con = mysqli_connect("$host","$username","$password");
    mysqli_select_db($con,"$dbname");

echo "hello";
 if(isset($_SESSION['email']) && isset($_SESSION['password'])){

 
 	$q="select * from user where user_email='$_SESSION[email]' and user_password='$_SESSION[password]'";

    		$i=mysqli_query($con,$q);

	  		if($r=mysqli_num_rows($i)==1){

	  			echo $_SESSION['email'];
	  			echo $_SESSION['password'];
	  			echo $_SESSION['id'];

			//	header('Location: homephp.php');
				
	  	}else{
	   		//header('Location: index.php?login_error=wrong');
	    		
 		}
 }else{

 	echo "does not set";
 }

?>
