<!-- session  -->
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


<!-- book editing form -->
<?php

	  	require_once 'config.php';
        $con = mysqli_connect("$host","$username","$password");
        mysqli_select_db($con,"$dbname");
        
     if(isset($_GET['b_id']))
         {
			$q="select * from book where book_id='$_GET[b_id]'";
		
			$r=mysqli_query($con,$q);

			while ($rows =mysqli_fetch_assoc($r)) {

				$book_title=$rows['book_title'];
				//$book_image_url=$rows['book_image_url'];
				$book_lang=$rows['book_lang'];
				$book_year=$rows['book_year'];
				$book_author=$rows['book_author'];
				
			}

         }
         else{

				$book_title='';
				//$book_image_url='';
				$book_lang='';
				$book_year='';
				$book_author='';				
         }
?>


<!-- adding book page -->


<!DOCTYPE html>
<html>
<head>
	<title>add books</title>
	<script src="js/jquery.js"></script>
	<script src="bootstrap/js/bootstrap.js"></script>
	<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css">
</head>
<body>
	<?php  include 'headerphp.php'  ?>
	<?php include 'sidebarphp.php'?>

<div class="container col-lg-8" style="background-color: ; float: right; right: 140px; top: 30px;">

	
	<h2 align="center" >Add book to the server</h2>
	<form role="form" class="form-horizontal" action="bookuploadphp.php" method="post" enctype="multipart/form-data" name="upload">

	<br>
		<div class="form-group row">
			<label class="control-label col-sm-2" for="name">Title *</label>

			<div class="col-sm-6">

				<input type="text" name="name" value="<?php echo $book_title; ?>" required class="form-control"  required>		

			</div>
		</div>
		<div class="form-group row">
			<label class="control-label col-sm-2" for="lang">Language *</label>



			<div class="col-sm-6">

				<select class="form-control" name="lang" required>
				<option value="">Select Language</option>
					
					
					<?php

						require_once 'config.php';
				        $con = mysqli_connect("$host","$username","$password");
				        mysqli_select_db($con,"$dbname");
				        $q="select * from lang";
				        $i=mysqli_query($con,$q);

				        while ($row =mysqli_fetch_assoc($i)) {

				        	if($book_lang==$row['lang_name'])
				        	{
				        		$selected='selected';
				        	}
				        	else{

				        		$selected='';
				        	}

				        	echo '<option value="'.$row['lang_name'].'" '.$selected.' >'.$row['lang_name'].'</option>';

				        	}
					?>					
				</select>


			
			</div>
		</div>
	<div class="form-group row">
			<label class="control-label col-sm-2"  for="year">Year</label>

			<div class="col-sm-6">

				<input type="text" name="year" value="<?php echo $book_year; ?> " class="form-control">		

			</div>
		</div>
		<div class="form-group row">
			<label class="control-label col-sm-2" for="name">Author</label>

			<div class="col-sm-6">

				<input type="text" name="author" value="<?php echo$book_author; ?>" class="form-control">		

			</div>
		</div>

		<div class="form-group row">
			<label class="control-label col-sm-2" >Image *</label>	

			<div class="col-sm-6">
				
				<input type="file" name="img"  class="form-control" accept="Image/*"    />
				
			</div>

		</div>

		<div class="form-group row">
			<label class="control-label col-sm-2" >Pdf *</label>	

			<div class="col-sm-6">
				<input type="file" name="pdf" class="form-control" accept="application/pdf" multiple  />
				</div>

		</div>

		<div class="form-group row">
			<label class="control-label col-sm-2" for="submit"></label>	

			<div class="col-sm-6">
				
				<input type="submit" name="submit" class="form-control btn-primary">
			</div>

		</div>


	</form>


</div>

</body>
</html>

