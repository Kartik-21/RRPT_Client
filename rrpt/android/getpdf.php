<?php

require_once 'config.php';

$con = mysqli_connect("$host","$username","$password");
mysqli_select_db($con,"$dbname");


	//random result
		//$sql = "SELECT * FROM book order by rand()";
		$sql = "SELECT * FROM book order by book_id desc";
	
	$result = mysqli_query($con,$sql);
	
		if(mysqli_num_rows($result) > 0)
		{
		while( $row = mysqli_fetch_assoc($result)) 
		{	
	
		$output['data'][] = $row;
	
		}
	
		print(json_encode($output));
		}
		else
		{
			echo"";
		}



?>

