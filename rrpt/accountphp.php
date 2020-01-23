
<!-- user login -->
<?php
	session_start();
	 require_once 'config.php';

    $con = mysqli_connect("$host","$username","$password");
    mysqli_select_db($con,"$dbname");

    if(isset($_POST['submit_logi']))
    {
    	if (!empty($_POST['email']) && !empty($_POST['password']))  {

    		$get_email=mysqli_real_escape_string($con,$_POST['email']);
    		
    		$get_password=mysqli_real_escape_string($con,$_POST['password']);

    		$q="select * from admin where a_email='$get_email' and a_password='$get_password'";

    		$i=mysqli_query($con,$q);


	    	if($r=mysqli_num_rows($i)==1){
                            
                    if($row=mysqli_fetch_assoc($i)){

                        $_SESSION['id']=$row['a_id'];
                    }

	    			$_SESSION['email']=$get_email;
	    			$_SESSION['password']=$get_password;

					header('Location: homephp.php');


	    		}else{

	    			header('Location: index.php?login_error=wrong');
	    		
	    		}
	    
    	}else {

    		header('Location: index.php?login_error=empty');
    		
    	}

    }else {
   
	header('Location: index.php?login_error=error');
    		
   }
?>