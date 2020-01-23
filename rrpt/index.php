
<!-- login cheack -->

<?php 
session_start();

	 require_once 'config.php';
	 $login_err='';

	 if(isset($_GET['login_error']))
	 {

	 		if($_GET['login_error']=='empty')
	 		{
	 			$login_err='<div class="alert alert-danger">Email or password was empty!</div>';
	 		}elseif ($_GET['login_error']=='wrong') {

	 			$login_err='<div class="alert alert-danger">Email or password was wrong!</div>';
	 		}elseif ($_GET['login_error']=='error') {

	 			$login_err='<div class="alert alert-danger">Please Login first..!</div>';
	 		}
	 }



?>

<!-- login form  -->

<!DOCTYPE html>
<html>
	<head>
		<title>Admin page</title>
		<script src="bootstrap/js/bootstrap.js"></script>
		<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css">
		<script src="js/jquery.js"></script>
	</head>

	<body>
<div class="container">

	<div class="container">
		
			<?php echo $login_err; ?>
	</div>

		<div class="jumbotron">
			<h1 align="center" class="btn-primary">Welcome to RRPT</h1>

	
<div class="container">

				<form role="form" class="form-horizontal" name="login Form" action="accountphp.php" method="post" >

					<h2 style="text-align: center;">Login here</h2><br><br>
					<div class="form-group row">
						<label class="control-label col-sm-1" for="email">Email:</label>
						<div class="col-sm-5">
							<input type="text" name="email" class="form-control"  id="email" placeholder="Enter email:" >
						</div>
					</div>

					<div class="form-group row">
						<label class="control-label col-sm-1" for="password">Password:</label>
						<div class="col-sm-5">
							<input type="Password" name="password"  class="form-control" id="password" placeholder="Enter password:" >
						</div>
					</div>
				
					<div class="form-group row">
						<label class="control-label col-sm-2 " for="submit"></label>
						<div class="col-sm-3">
							<input type="submit" name="submit_logi" id="submit" class="form-control">
						</div>
					</div>
					
				</form>		 

			</div>
		</div>

	</div>
	
	</body>

</html>