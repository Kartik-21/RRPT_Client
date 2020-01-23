<?php

require_once 'config.php';

$con = mysqli_connect("$host","$username","$password");
mysqli_select_db($con,"$dbname");

$email = $_GET['email'];

if($email!=""){

		//$sql = "SELECT book_title,book_image_url,book_pdf_url FROM book";
		
		$sql="select user_book.user_book_id, book.book_title, book.book_image_url,book.book_pdf_url from user_book,user,book where user_book.user_id=user.user_id and user_book.book_id=book.book_id and user.user_email='$email'";
		
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

}
mysqli_close($con)
?>

