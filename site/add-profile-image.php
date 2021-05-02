<?php
session_start();
if (!isset($_SESSION['user_id'])) {
	header("location: login");
}
$msg = "";
include("includes/database_connection2.php");
$id = $_SESSION['user_id'];
 if (isset($_POST['upload']) && $_FILES['image']['error']==0) {
	$allow_ext = array('png','PNG','jpg','JPG','jpeg','JPEG');
	$allow_type = array('image/png','image/gif','image/jpeg','image/bmp','image/tiff');
	$image_name = $_FILES['image']['name'];
	$image_type = getimagesize($_FILES['image']['tmp_name']);
	$image_name = explode('.',$image_name);
	$ext = end($image_name);
    $img = $_FILES['image']['name'];
	if(in_array($ext, $allow_ext) && in_array($image_type['mime'], $allow_type)){
    $newname = rand(111111111111,999999999999) . '.' . strtolower($ext);
		$size = filesize($_FILES['image']['tmp_name']);
		if ($size < "3145728") {
			$upload = move_uploaded_file($_FILES['image']['tmp_name'], "images/users/profile-image/".$newname);
			
			if ($upload) {
				$sql = "UPDATE login SET profile_image='images/users/profile-image/$newname' WHERE user_id='$id' ";
				$statement = $connect->prepare($sql);
				if($statement->execute())
				{
					$msg =  '<div class="alert alert-success"><strong>Image Uploaded !</strong></div>';
                }
                
				
			}
			
		} 
		elseif ($size > "3145728") {
			$msg =  '<div class="alert alert-danger"><strong>File Is Too Large ! Max is 3MB</strong></div>';
		}
	} else {
		$msg = '<div class="alert alert-danger"><strong>Error: Invalid File Type!</strong></div>';
	}
}

?>
<!DOCTYPE html> 
<html> 
<head>
<link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
<link rel="stylesheet" href="css/bootstrap-3/bootstrap.css"> 
<title>Uplaod Profile image</title> 
</head>
  <body>
  <div class="container-fluid">
    <div class="message">
	  <?php echo $msg; ?>
	</div>
    <div>
	<center>                  <script>document.addEventListener('contextmenu', event => event.preventDefault());
</script>
	<h2>Upload profile Image For Others To See It</h2>
	<img src="images/profile-example.png" style="width:50%" class="img img-responsive img-thumbnail" alt="" srcset="">
<br><br><br><br>
	<form method="POST" action="" enctype="multipart/form-data"> 
      <input class="" type="file" name="image" value="Upload image"/> 
        
      <div> <br>
          <button type="submit" class="btn btn-lg btn-primary" name="upload">Upload Image</button> 
        </div> 
  </form> 

	
	</center>
	</div>
  </div>
  <style>
  input[type=file]::-webkit-file-upload-button {
	background-color: #df4759;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
}
button.upload {
	background-color: #df4759;
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
}
  </style>
  <script>  
$(document).ready(function(){
 
 var is_session_expired = 'no';
    function check_session()
    {
      $.ajax({
        url:"check_session.php",
        method:"POST",
        success:function(data)
        {
          if(data == '1') {
            $('#loginModal').modal({
               backdrop: 'static',
               keyboard: false,
        });
      is_session_expired = 'yes';

       }
      }
    })
  }
 
 var count_interval = setInterval(function(){
        check_session();
  if(is_session_expired == 'yes')
  {
   clearInterval(count_interval);
  }
    }, 10000);
 
 $('#login_form').on('submit', function(event){
  event.preventDefault();
  $.ajax({
   url:"check_login.php",
   method:"POST",
   data:$(this).serialize(),
   success:function(data){
    if(data != '')
    {
     $('#error_message').html(data);
    }
    else
    {
     location.reload();
    }
   }
  });
 });

});  
</script>
<div class="modal fade" style="background-color:#87c5f5" id="loginModal" role="dialog">
    <div class="modal-dialog modal-sm">
  <div class="modal-content">
   <div class="modal-header">
    <h3 class="modal-title"><center>Session Expired ! <br> Please Login Again</center></h3>
   </div>
   <div class="modal-body">
    <form method="post" id="login_form">  
     <input type="text" name="email" placeholder="Enter Email" class="form-control" required /><br />  
     <input type="password" name="password" placeholder="Enter Password" class="form-control" required /><br />  
     <input type="submit" name="login" id="submit" class="btn btn-info btn-block" value="Login">  
    </form>
   </div>
  </div>
    </div>
</div>
</body> 
</html> 