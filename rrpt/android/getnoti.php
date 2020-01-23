<?php

require_once 'config.php';

$con = mysqli_connect("$host","$username","$password");
mysqli_select_db($con,"$dbname");

		$sql = "SELECT * FROM notification order by noti_id desc";
		
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
			echo "";
		}
	

?>

