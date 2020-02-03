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


<!-- delete notification from server -->
<?php

		
	  	require_once 'config.php';
        $con = mysqli_connect("$host","$username","$password");
        mysqli_select_db($con,"$dbname");
        $del='';
     if(isset($_GET['n_id']))
         {
			$q="delete from notification where noti_id='$_GET[n_id]'";
		
			$r=mysqli_query($con,$q);

			$del='<div class="alert alert-danger">Delete sucessfully....</div>';
		} 


?>

<!-- notification details page -->

<!DOCTYPE html>
<html>
	<head>
		<title>Admin page</title>
		<script src="bootstrap/js/bootstrap.js"></script>
		<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css">
		<script src="js/jquery.js"></script>
	</head>

	<body>


<?php  include 'headerphp.php'  ?>

<?php include 'sidebarphp.php'?>
	
<dir class="container-fluid col-lg-8" style="background-color: ; float: left; left: 20px; top: 20px;">

<?php echo $del; ?>
		
	<h2 align="center">Notificattion Details:</h2>

<br>
		<table class="table table-striped" border="1" width="70%">
			<thead>
				<tr>
					<th>Id</th>
					<th>Notification</th>
					<th>Date</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>

			</thead>

			<tbody>
				<?php
				require_once 'config.php';
				$con = mysqli_connect("$host","$username","$password");
				mysqli_select_db($con,"$dbname");

				$q="select * from notification order by noti_id desc";

				$r=mysqli_query($con,$q);

				while ($rows=mysqli_fetch_assoc($r)) {

					echo "<tr>

							<td>".$rows['noti_id']."</td>
							<td>".$rows['noti_name']."</td>
							<td>".$rows['date']."</td>
							<td><a href='#' class='btn btn-info btn-xs'>Edit</a></td>
							<td><a href='notidetailsphp.php?n_id=".$rows['noti_id']."' class='btn btn-danger btn-xs'>Delete</a></td>
						</tr>";
					}
				?>
			</tbody>
		</table>

		<?echo $del;?>
		</dir>
	</body>

</html>

