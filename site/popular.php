<?php 
include("includes/database_connection2.php");
session_start();
if(!isset($_SESSION['user_id'])) {
    header("location: login");
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/bootstrap-3/bootstrap.css">
    <title>Popular - Moon Meet</title>
</head>
<body>
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
    <div class="container-fluid">
        <div>
        <?php 
              $posts_query = "SELECT * FROM login ORDER BY popularity DESC LIMIT 10";
  foreach($connect->query($posts_query) as $row) {
  echo "<div class='alert alert-success'><h4 style='font-family:sans-serif; font-size:23px'>
  <i data-toggle='tooltip' data-placement='left' title='".$row["username"] ."' style='font-size:30px' class='fa fa-user-circle'></i>
  <a style='color:black' data-toggle='tooltip' data-placement='left' title='".$row["username"] ." ' href='profile?user= " . $row["username"] . "'><strong>". $row["username"]."</strong></a><br>
  <div class='' style='background-color:'><h2 style='color:black'><i style='color:#337ab7' class='fa fa-star'></i> " . $row['popularity'] ."</h2></div>

  <div class='' style='background-color:'><h2 style='color:black'><i style='color:#337ab7' class='fa fa-info'></i> " . $row['bio'] ."</h2></div></div>";

}
 ?>
        </div>
    </div>                  <script>document.addEventListener('contextmenu', event => event.preventDefault());
</script>
</body>
</html>