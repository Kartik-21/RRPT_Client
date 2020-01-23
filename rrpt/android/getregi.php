<?php
	
	require_once 'config.php';
	
	$con = mysqli_connect("$host","$username","$password");
	
	mysqli_select_db($con,"$dbname");
	
	$email=$_POST['email'];
	$name=$_POST['name'];
	$gid=$_POST['id'];
	
	//$regex = '/^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,3})$/'; 
	
	
	if ($email!="") {
		
		$q1="select user_email from user where user_email='$email'";
		//$row = mysql_fetch_array($q1);
		$r1=mysqli_query($con,$q1);
		$row = mysqli_fetch_assoc($r1);
		if($row>0)
				{
					echo "Already register..";
					
				}else { 
					
					$query="INSERT INTO user(user_name,user_email,g_id) VALUES ('$name','$email','$gid')";
					if(mysqli_query($con,$query)>0)
					{
						echo "Registration Successful..!";		
					} else { 
					echo "error in regi";
					}    
		
				}    
	
	} else { 
	
		 echo "Please try again...";
	}    

	mysqli_close($con);
?>
