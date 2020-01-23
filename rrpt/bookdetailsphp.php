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

		//current page
				
	  	}else{
	   		header('Location: index.php?login_error=wrong');
	    		
 		}
 }else {

 	header('Location: index.php?login_error=error');
	    
 }

?>

<!-- delete book from server -->
<?php

	  	require_once 'config.php';
        $con = mysqli_connect("$host","$username","$password");
        mysqli_select_db($con,"$dbname");
        $del='';
     if(isset($_GET['b_id']))
         {
			$q="delete from book where book_id='$_GET[b_id]'";
		
			if(mysqli_query($con,$q)>0){

				$del='<div class="alert alert-danger">Delete sucessfully....</div>';
			}

		} 


?>

<!-- book details form -->

<!DOCTYPE html>
<html>
	<head>
		<title>book details</title>
		<script src="bootstrap/js/bootstrap.js"></script>
		<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css">
		<script src="js/jquery.js"></script>
	</head>

	<body>


<?php  include 'headerphp.php'  ?>

<?php include 'sidebarphp.php'?>
	
<dir class="container-fluid col-lg-8" style="background-color:; float: left; left: 20px; top: 20px; width: auto">

		<?php echo $del;?>
	<h2 align="center">Books Details:</h2>

<br>
		<table class="table table-striped" border="1" width="70%">
			<thead>
				<tr class="btn-primary">
					<th>Id</th>
					<th>Title</th>
					<th>Image_url</th>
					<th>Pdf_url</th>
					<th>Lang</th>
					<th>Year</th>
					<th>Author</th>
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

				$q="select * from book order by book_id desc";

				$r=mysqli_query($con,$q);

				while ($rows=mysqli_fetch_assoc($r)) {

					echo "<tr>

							<td>".$rows['book_id']."</td>
							<td>".$rows['book_title']."</td>
							<td>".$rows['book_image_url']."</td>
							<td>".$rows['book_pdf_url']."</td>
							<td>".$rows['book_lang']."</td>
							<td>".$rows['book_year']."</td>
							<td>".$rows['book_author']."</td>
							<td>".$rows['date']."</td>
							<td><a href='addbookphp.php?b_id=".$rows['book_id']."' class='btn btn-info btn-xs'>Edit</a></td>
							<td><a href='bookdetailsphp.php?b_id=".$rows['book_id']."' class='btn btn-danger btn-xs'>Delete</a></td>
						</tr>";
					}
				?>
			</tbody>
		</table>
		</dir>
	</body>

</html>

