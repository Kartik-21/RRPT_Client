<?php
	
	require_once 'config.php';
	
	$con = mysqli_connect("$host","$username","$password");
	
	mysqli_select_db($con,"$dbname");
	
	$email=$_POST['email'];
	$bid=$_POST['id'];
	
	//echo $email;
	//echo $bid;
	//echo "added...";

	
	if($email!="")
	{		
		$q="select user_id from user where user_email='$email'";
		$i=mysqli_query($con,$q);
		while($row=mysqli_fetch_assoc($i)){
		$u_id=$row['user_id'];		
		}
	}
	
		
		$q2="select * from user_book where user_id='$u_id' and book_id='$bid'";
		$row=mysqli_query($con,$q2);
		
		if(mysqli_fetch_row($row)>0){
			
			echo "Already Added...";
		
		}else{
			
			$q1="insert into user_book(user_id,book_id) values('$u_id','$bid')";
			if(mysqli_query($con,$q1)>0)
			{
				echo "Added..";
			}
			else{
				echo "not Added..";
			}		
		}
	
	mysqli_close($con);
?>