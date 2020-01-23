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

        //  current page
                
        }else{
            header('Location: index.php?login_error=wrong');
                
        }
 }else {

            header('Location: index.php?login_error=error');
        
 }

?>

<!-- compress Image -->


<?php

// Compress image
function compressImage($source, $destination, $quality) {

  $info = getimagesize($source);

  if ($info['mime'] == 'image/jpeg') 
    $image = imagecreatefromjpeg($source);

  elseif ($info['mime'] == 'image/gif') 
    $image = imagecreatefromgif($source);

  elseif ($info['mime'] == 'image/png') 
    $image = imagecreatefrompng($source);

  imagejpeg($image, $destination, $quality);

}

?>

<!-- book upload to the server  -->

<!DOCTYPE html>
<html>
<head>
	<title></title>
</head>
<body>


<div class="container">

  <div class="jumbotron">
    <h1>Admin Panel</h1> 
  </div>

<?php

        //echo '<pre>',print_r($_FILES),'</pre>';

        require_once 'config.php';
        $con = mysqli_connect("$host","$username","$password");
        mysqli_select_db($con,"$dbname");
         
        $id=$_SESSION['id'];
        $Name =trim($_POST['name']);
       
        $lang=trim($_POST['lang']); 	
        $year=trim($_POST['year']);
         if(empty($_POST['year']))
         {
           	$year='';	
         }

         $author=trim($_POST['author']);
         if(empty($_POST['author']))
         {
         	$author='';	
         }
                $pdfURL ='pdfs/';       //in android we alredy set the location of android in apires
                $imgURL='images/';

                $pdfMoveUrl='android/pdfs/';        //in this we have to set location to move the file
                $imgMoveUrl='android/images/';  

                $PdfInfo = pathinfo($_FILES['pdf']['name']);
                $PdfFileExtension = $PdfInfo['extension']; 
                $PdfFileURL = $pdfURL . $Name . '.' . $PdfFileExtension;     //it store in database 
                $PdfMoveFileUrl=$pdfMoveUrl. $Name . '.' . $PdfFileExtension;    //it store the actual file

                $ImageInfo=pathinfo($_FILES['img']['name']);
                $ImageFileExtension=$ImageInfo['extension'];    //finding a extension
                $ImageFileUrl=$imgURL.$Name.'.'.$ImageFileExtension;        //it store in database 
                $ImageMoveFileUrl=$imgMoveUrl.$Name.'.'.$ImageFileExtension;    //it store the actual file

               
        if($_SERVER['REQUEST_METHOD']=='POST'){

                if(isset($_POST['name']) and isset($_FILES['pdf']['name']) and isset($_FILES['img']['name'])){      
            
                         try{
                        			move_uploaded_file($_FILES['pdf']['tmp_name'],$PdfMoveFileUrl);  //name is assign after move
                        //            file_put_contents($ImagePath,base64_decode($ImageData));
                        		// Compress Image
								compressImage($_FILES['img']['tmp_name'],$ImageMoveFileUrl,60);

								//move_uploaded_file($_FILES['img']['tmp_name'],$ImageMoveFileUrl);   

                                    $InsertTableSQLQuery = "INSERT INTO book(book_title,book_image_url,book_pdf_url,book_lang,book_author,book_year,a_id) VALUES ('$Name','$ImageFileUrl','$PdfFileURL','$lang','$author','$year','$id') ";
                                
                                    if( mysqli_query($con,$InsertTableSQLQuery)>0){      		
                                  			  echo "<h2>uploaded..</h2>";
                                              header('Location: addbookphp.php');

                                	   }else{
                                  			   echo "<h2>error</h2>";
                                	   }
                                    }catch(Exception $e){} 
                               	   mysqli_close($con);
                 }
        
        }else{
        	
        	echo "<h1>connection error</h1>";
        }

 ?>

<a href="addbook_a.php" class="btn-primary" align="center"><h2>try again...</h2></a>

</div>
</body>
</html>

