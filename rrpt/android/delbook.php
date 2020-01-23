<?php
	
	require_once 'config.php';
	
	$con = mysqli_connect("$host","$username","$password");
	
	mysqli_select_db($con,"$dbname");
	
	$id=$_POST['id'];
	
	//echo $id;
	
	if($id!="")
	{
		
		$q="delete from user_book where user_book_id='$id'";
		$i=mysqli_query($con,$q);

			if($i>0){
				echo "Removed...";
			}
	}
	mysqli_close($con);
?>