


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


<!--editing form-->

<?php

 require_once 'config.php';
   $con = mysqli_connect("$host","$username","$password");
   mysqli_select_db($con,"$dbname");
   
   if(isset($_GET['n_id'])){
	   
		   $nid=$_GET['n_id'];
		   $q="select * from notification where noti_id='$nid'";
		  
		   $i=mysqli_query($con,$q);
		   
		   while($row=mysqli_fetch_assoc($i)){
			   
			   $name=$row['noti_name'];
			 }
		   
   }else{
	   
	   $name='';
	   
   } 
	

?>


<!-- add nofitication to server -->

<?php 

		//add the notification...

	 require_once 'config.php';
	$del='';
        $con = mysqli_connect("$host","$username","$password");
        mysqli_select_db($con,"$dbname");
		//mysqli_set_charset($con,'utf8');
     	if(isset($_POST['submit_noti'])){   
      
			$id=$_SESSION['id'];
			$post = $_POST['post'];
			//$nid=$_GET['n_id'];
				
		
			if(isset($_POST['nid'])){
				 $nid=$_POST['nid'];
				//$q1="update notification set noti_name='$post' where noti_id='$nid'";
				                
				if( mysqli_query($con,$q1)>0){      		
					$del='<div class="alert alert-success">Update sucessfully...</div>';
				   }else{
					$del='<div class="alert alert-danger">Try again...!</div>';
				   }
			}
			else{
        	   	$q = "INSERT INTO notification (noti_name,a_id) values('$post','$id')";
                                
                    if( mysqli_query($con,$q)>0){      		
                  		
                  		$del='<div class="alert alert-success">Insert sucessfully...</div>';
                	   }else{
                  		$del='<div class="alert alert-danger">Try again...!</div>';
                	   }
           }
		}mysqli_close($con);
             
?>

<!-- add nofitication form-->


<!DOCTYPE html>
<html>
<head>
	<title>add notification</title>
	<script src="js/jquery.js"></script>
	<script src="bootstrap/js/bootstrap.js"></script>
	<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css">
	<script src="//tinymce.cachefly.net/4.2/tinymce.min.js"></script>
	<script>
		tinymce.init({  mode : "textareas",
      
      force_br_newlines : false,
      force_p_newlines : false,
      forced_root_block : ''});
	</script>
	
	</head>
<body>
<?php  include 'headerphp.php'  ?>

<?php include 'sidebarphp.php'?>
		
<div class="container col-lg-8" style="background-color: ; float: right; right: 140px; top: 30px;">

	<?php echo $del; ?>

	<h2 align="center" >Add Post to the server</h2>

	<form role="form" class="form-horizontal" action="addnotiphp.php" method="post" enctype="multipart/form-data" name="upload">

	<br>
		<div class="form-group row">
			<label class="control-label col-sm-2" for="name">Post *</label>

			<div class="col-sm-10">

			<textarea name="post" style="height: 150px; width: 500px;"><?php echo $name;?></textarea>
			</div>
		</div>
		
		<div class="form-group row">
			<label class="control-label col-sm-2" for="submit"></label>	

			<div class="col-sm-10">
				
				<input type="submit" name="submit_noti" class="form-control btn-primary">
				<?php $response ?>
			</div>

		</div>


	</form>


</div>

</body>
</html>