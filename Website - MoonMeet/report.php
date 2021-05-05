<?php
session_start();
include('includes/database_connection2.php');
if(!isset($_SESSION['user_id']))
{
  header("location: login.php");
  
}?>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" href="css/bootstrap-4/bootstrap.min.css">
<link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
<script src="js/bootstrap-4/bootstrap.min.js"></script>
  <link rel="stylesheet" href="css/style1.css">
  <link rel="stylesheet" href="css/buttons.css">
  <link rel="stylesheet" href="css/font-awesome-4.7.0/css/font-awesome.min.css">
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Report</title>
</head>
<body style="background-color:skyblue">
<script>document.addEventListener('contextmenu', event => event.preventDefault());
</script>
  <div class="container">
                    <div class="logo">
                      <center><img style="width:190px" src="images/logo.png" alt="" srcset=""></center>
                    </div>
                    <?php
                if (isset($_GET['report_sent'])) {

                  if ($_GET['report_sent'] == 'true') {
                    echo '<div class="alert alert-primary alert-dismissible fade show text-center"><button type="button" class="close" data-dismiss="alert">&times;</button>Report sent ! Thank you for your help to make our platform better <3 </div>';
                        }
                        else if ($_GET['report_sent'] == 'false') {
                          echo '<div class="alert alert-danger text-center"><button type="button" class="close" data-dismiss="alert">&times;</button>There is an error ! Try later ... </div>';
                              }
                    } ?>
    <div class="col-sm-12">
      <form action="includes/report.inc.php" method="POST">
      <h4>Your name</h4>
          <input class="form-control" type="text" name="name" id=""><br>
          <h4>Your Email</h4>
          <input class="form-control" type="email" name="email" id=""><br>
          <h4>Your Message</h4>
           <textarea class="form-control" name="message" id="" cols="30" rows="5"></textarea>
           <br>
          <button  class="btn btn-primary btn-lg" type="submit">Send report</button><br>
       </form>
    </div>
  </div>
    <div class="copyright">
    <?php //include_once("includes/footer2.php"); ?>
    </div>
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
