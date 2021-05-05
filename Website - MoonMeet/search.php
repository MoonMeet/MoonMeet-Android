<?php 
session_start();
include("includes/database_connection2.php");
$user_id = $_SESSION['user_id'];
if (!isset($user_id)){
    header('location: login.php');
}
if (isset($_GET['query'])) {
  $query = $_GET['query'];
  str_replace($query, "'", "");
  str_replace($query, '"', "")

  if (empty($query)) {
    header("location: home");
  }

?>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" href="css/bootstrap-3/bootstrap.css">
<link rel="stylesheet" href="css/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Discover People - Moon Meet</title>
    <style>
       html {
  background-color: #eceff4;
}

.btn-primary {
  background-color:#193566;
  border-color:#193566;
}
.panel-primary > .panel-heading {
  background-color:#193566;
}
.panel-primary > .panel-heading {
color: #fff;
background-color: #193566;
border-color: #193566;
}
.panel-primary {
border-color: #193566;
}
.dropbtn, span.opennav {
background-color:#193566;

}
.btn-primary:hover,
.btn-primary:focus,
.btn-primary:active
{background-color:  #234b90;}
.dropbtn:hover {background-color:  #234b90;}
span.opennav {background-color:  #234b90;}
.alert {
  box-shadow: 0.1px 0.1px 0.1px 0.1px rgba(0, 0, 0, 0.1), 0 6px 6px 0 rgba(0, 0, 0, 0.2);

}
h1 {
  color:#193566;
  text-align:center;
  font-weight:bold;
}

    </style>
</head>
<body>
    <div class="container">
    <strong><h1>Search People</h1></strong>
        <div class="col-sm-12">
<?php 
$sql = "SELECT * FROM login WHERE username LIKE '%$query%'";
$statement = $connect->prepare($sql);
$statement->execute();
$result = $statement->fetchAll();

foreach($result as $row) {
    $profile_image = $row['profile_image'];
    if ($profile_image == "") {
      $profile_image = "images/user_image.png";
    }
      echo "
      <div class='col-sm-6'>
      <div class='well well-sm' style=''>
      <center><div class='row'>
      <img align='center' style='width:60px;height:60px;' src='".$profile_image."' class='img-circle img-thumbnail'>
      <br><h3 style='text-transform: capitalize;margin-top:10px'>",$row['firstname'], " ",$row['lastname'],"</h3><a href='profile.php?user=".$row['username']."'><h3 style='margin-top:-5px'>@",$row['username'],"</h3></a>
      <h3> Bio: ",$row['bio']," <br> Joined at ".$row['join_date']."</h3>
      <br><a class='btn btn-primary btn-lg' style='text-decoration:none; color:white;' href='profile?user=".$row['username']."'>See profile</a></div></center></div>
      </div>
      "; }
      if (!$result) {
        echo '
          <div class="alert alert-danger">
          <h1>Sorry There Is No Results !</h1>
          </div>
        ';
      }
      ?>
     </div>
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
<?php } 
if (!isset($_GET["query"])) {
  header("location: home");
} 
 ?>