<?php 
session_start();
include("includes/database_connection2.php");
if (!isset($_SESSION['user_id'])) {
    header("location: login");
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/bootstrap-4/bootstrap.css">
    <link rel="stylesheet" href="css/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
    <title>Announcements - Moon Meet</title>
</head>
<body>
 <style>
 html {
     overflow-x: hidden;
 }
 .divider {
    background-color: #DADADA;
    padding:1.7px;
 }
 </style>
 <br>
    <div class="container-fluid">
    <center><h2 style="color: #193566;font-size:2.8em;"><i class="fa fa-bullhorn"></i> Announcements <i class="fa fa-bullhorn"></i></h2></center>
    <br>
    <?php 
              $announcements_query = "SELECT * FROM announcements ORDER BY announcement_id DESC";
  foreach($connect->query($announcements_query) as $row) {
  echo '
  <div class="alert alert-light" style="background-color:#ecf0f3;box-shadow: 3px 3px 3px 3px rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
          <h2 style="color:#193566"><img src="images/logo.png" class="img img-responsive" style="width:100px;height:100px;" alt="">'.$row['title'].'</h2>
          <h3 style="color:#193566;margin-top:-16px;">'.time_ago($row['date']).'</h3>
          <div class="divider">
          </div>
           <h3 style="margin-top:10px;">'.$row['content'].'</h3>
          <center><img src="images/screenshots/2.jpg" class="img img-responsive img-thumbnail" style="width:18%;margin-top:10px;" alt=""></center>
          <br>
          <div class="divider">
          </div>
          <h4 style="margin-top:10px">By <span style="color:#193566">Moon Meet Inc.</span> For more informations visit <span style="color:#193566">moonmeet.org</span></h4>
          </div>
  ';
}
 ?>
       </div>
    </div>
</body>
</html>