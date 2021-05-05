<?php
session_start();
include("includes/database_connection2.php");
$username = $_SESSION['username'];
foreach($connect->query("SELECT * FROM login WHERE username = '$username'") as $row) {
$bio        = $row['bio'];
$firstname  = $row['firstname'];
$lastname   = $row["lastname"];
$email      = $row['email'];
$phone      = $row['phone'];
$gender     = $row['gender'];
$birthdate  = $row['birthdate'];
$balance    = $row['balance'];
$popularity = $row['popularity'];
$badge      = $row['badge'];
$profile_image  = $row['profile_image'];
}
if ($badge = "user") {
  $badge = '<span class="label label-primary"><i class="fa fa-user"></i> User</span>';
}
if ($profile_image == "") {
  $profile_image = "images/user_image.png";
}
if(isset($_SERVER['HTTPS']) && $_SERVER['HTTPS'] === 'on')   
$url = "https://";   
else  
$url = "http://";   
// Append the host(domain name, ip) to the URL.   
$urlh= $_SERVER['HTTP_HOST'];   

// Append the requested resource location to the URL   
$url.= $_SERVER['REQUEST_URI'];     
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=devise-width, initial-scale=1.0">
  <meta property="og:image" content="images/logo.png">
     <meta property="og:description" content="Moon Meet is an instant messaging application that gives you a good acces to connect with friends, make relationships, share stories etc..">
     <meta property="og:title" content="Moon Meet">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <meta name="keywords" content="moonmeet, Moonmeet, Moon meet, moon meet, Moon Meet, social media, new era of messaging, aziz becha, Aziz becha">
     <meta name="author" content="Aziz Becha">
     <meta http-equiv="content-type" content="text/html; charset=utf-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <link rel="stylesheet" href="css/profile.css">
  <title><?php echo $username, " - Profile" ?></title>
  <link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
     <link rel="stylesheet" href="css/bootstrap-3/bootstrap.css">
  <link href="css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
  <script src="js/script.js"></script>
</head>
<body>
<style>
html {
  overflow-x:hidden;
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
<section id="content" class="container">
    <!-- Begin .page-heading -->
    <div class="page-heading">
        <div class="media clearfix">
          <div class="media-left pr30">
            <a href="#">
              <img class="media-object mw150 img-responsive img-thumbnail img-circle" style="max-width:171.5px" src="<?php echo $profile_image; ?> " alt="...">
            </a>
            <center><h3><?php echo $badge; ?></h3>
            <h3><a class="btn btn-danger" href="add-profile-image.php">Change Image</a></h3></center>
          </div>                      
          <div class="media-body va-m">
          <div class="well ">
          <h2 class="media-heading">
            <?php  echo $firstname; echo " ", $lastname; ?> - Profile 
          </h2>
            <h2 class="media-heading"><?php echo "  @", $_SESSION['username']; ?>
             
            </h2><br>
            
            <h2 class="media-heading"><?php echo $bio; ?></h2>
            <!-- <h2><?php echo $popularity; ?> <i class="fa fa-star"></i></h2>-->
             <a class="btn btn-primary" href="https://www.facebook.com/sharer.php?u=<?php echo $urlh; ?>/profile?user=<?php echo $username; ?>">Share On Facebook</a>
            </div>
          </div>
        </div>
    </div>
    <script>document.addEventListener('contextmenu', event => event.preventDefault());
</script>
    <div class="row">
        <div class="col-md-4">
          <div class="panel">
            <div class="panel-heading">
              <span class="panel-icon">
                <i class="fa fa-star"></i>
              </span>
              <span class="panel-title"> User Popularity</span>
            </div>
            <div class="panel-body pn">
              <table class="table mbn tc-icon-1 tc-med-2 tc-bold-last">
                <thead>
                  <tr class="hidden">
                    <th class="mw30">#</th>
                    <th>First Name</th>
                    <th>Revenue</th>
                  </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                      <span class="fa fa-"><img src="images/logo.png" class="img-responsive" style="max-width:35px" alt=""></span>
                    </td>
                    <td style=""><?php echo $username; ?></td>
                    </tr>
                    <td>
                      <span style="font-size:30px" class="fa fa-user"></span>
                    </td>
                    <td style="text-transform: capitalize;"><?php echo $firstname, " ",$lastname; ?></td>
                    <tr>
                    <td>
                      <span style="font-size:30px" class="fa fa-envelope"></span>
                    </td>
                    <td style=""><?php echo $email; ?></td>
                    <tr>
                    <td>
                      <span style="font-size:30px" class="fa fa-phone"></span>
                    </td>
                    <td style=""><?php echo $phone; ?></td>
                    <tr>
                    <td>
                      <span style="font-size:30px" class="fa fa-transgender"></span>
                    </td>
                    <td style="text-transform: capitalize;"><?php echo $gender; ?></td>
                    <tr>
                    <td>
                      <span style="font-size:30px" class="fa fa-birthday-cake"></span>
                    </td>
                    <td style="text-transform: capitalize;"><?php echo $birthdate; ?></td>
                    <tr>
                    <td>
                      <span style="font-size:30px" class="fa fa-info-circle"></span>
                    </td>
                    <td style="text-transform: capitalize;"><?php echo $bio; ?></td>
                   <!-- <tr>
                    <td>
                      <span style="font-size:30px" class="fa fa-circle"></span>
                    </td>
                    <td style="text-transform: capitalize;"><?php //echo $balance; ?></td>
                    <tr>
                    <td>
                      <span style="font-size:30px" class="fa fa-star"></span>
                    </td>
                    <td style="text-transform: capitalize;"><?php //echo $popularity; ?></td>-->
                    <tr>
                  
                </tbody>
              </table>
              <center><a class="btn btn-primary" href="change">Change data</a></center>
            </div>
          </div></div>
          
        <div class="col-md-8">
          <div class="well">
          <div>
                   <?php 

                  		
if(isset($_POST['subpost']))
{

    $poster = $_SESSION['username'];
    $post = trim(filter_var($_POST["publication"],FILTER_SANITIZE_SPECIAL_CHARS));

    $q = "INSERT INTO `posts`(`by_user`, `post`) VALUES ('$poster','$post')";
    str_replace($post,"'","&apos;");
    str_replace($post, '"', "&quot;");
    $countLength = (strlen($post));
    if ($countLength >= "300") {
       echo "<div id='post-will-not-be-posted' class='alert alert-danger' role='alert'>
       <span class='close' onclick='hidepostwillnotbepublished();'>X</span>
       <strong>Max Characters Is 300 ! Post Will Not Be Published ...</strong>
     </div>";
    }
    elseif ($countLength <= "300") {

     $req = $connect->exec($q); {

       echo "<div class='alert alert-success' role='alert'>
       <strong>Your Post was submitted !</strong>
     </div>";
      }
     if (!$req) {
       echo "<div class='alert alert-danger' role='alert'>
       <strong>Your Post was not submitted ! Try Later ...</strong>
     </div>";
     }}
    }

                   ?>
                  
                    <form method="POST">
                        <div>
                          <?php //echo $msg ; ?>
                         </div>
                         <h3>Post</h3>
                         <textarea required placeholder="Share your ideas with others ..." style="border-radius:10px;" class="form-control emoji_act" name="publication" rows="6"></textarea>
                         <br>
                         <button type="submit" name="subpost" class="btn btn-primary btn-block btn-lg"><i class="fa fa-pencil"></i> <strong>Post</strong></button>
                    <br>
                   </form><br>
          <h3>Your Posts</h3>
              <div class="posts" style="word-wrap: break-word;">
              <?php 
  foreach($connect->query("SELECT post, by_user, posted_on FROM posts WHERE by_user = '$username' ORDER BY post_id DESC") as $row) {
  echo "<div class='alert alert-info'><h4 style='font-family:sans-serif; font-size:23px'><i style='font-size:30px' class='fa fa-user'></i> " . $row["by_user"] . "<br><i style='font-size:25px' class='fa fa-clock-o'></i>   ".time_ago($row['posted_on']) ."</h4><div class='' style='background-color:'><h2 style='color:black'>" . $row['post'] ."</h2></div></div>";}
 ?>
              </div>
          </div>
      </div>
  </section>
</body>
</html>
