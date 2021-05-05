<?php 
include("assets/libs/phpqrcode/qrlib.php");
$lien='https://www.243tech.com';
QRcode::png($lien, 'image-qrcode.png');
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/bootstrap-4/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
    <title>Download Moon Meet Mobile Application</title>
</head>
<style>
    h1,h2 {
        color: white;
    }
    .download {
        padding-left:4.5em;
        padding-right:4.5em;
        
    }
    .blue,h1,h2  :not(a) {
        color: #193566;
    }
    img {
        box-shadow: 1px 1px 1px 1px rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
    }
</style>
<body>
   
    <div style="background-color:#eceff4;"
         class="container-fluid">
         <br>
        <h1 style="text-align:center;"><i class="fa fa-download"></i> Download Moon Meet !</h1>
        <br>
        <h2 style="text-align:center;color: #193566;"><i class="fa fa-android blue"></i> Download Moon Meet Mobile Version and stay with your contacts & friends anywhere.</h2>
       <br><br>
  <div class="row">
  <div style="color:white" class="col-sm-6">
         <center>
         <i class="fa fa-download blue" style="font-size:15em"></i><br>
         <a href="" class="btn btn-success btn-lg download" style="margin-bottom:8px;color:white"><strong>Download</strong></a>
         <br><hr style="background-color:white">
         </center>
    </div>
    <div style="color:white" class="col-sm-6">
         <center>
         <img src="image-qrcode" class="img img-responsive border img-thumbnail" style="width:27%;height:27%" alt="" srcset="">
   <br><br>
     <h3 style="color: #193566;"><strong>Scan this QR Code and the download will start automatically</strong></h3>
         <hr style="background-color:white">
         </center>
    </div>
    <div class="col-sm-6">
    
    </div>
  </div>

       <br>
       <div class="row">
          <div class="col-sm">
<center><img src="images/screenshots/1.jpg" class="img img-responsive rounded" style="width:50%;margin-bottom:1em;" alt=""></center>          </div>
          <div class="col-sm">
          <center><img src="images/screenshots/3.jpg" class="img img-responsive rounded" style="width:50%;margin-bottom:1em" alt="">

</center>           </div>
           <div class="col-sm">
           <center><img src="images/screenshots/2.jpg" class="img img-responsive rounded" style="width:50%;margin-bottom:1em" alt="">

</center>           </div>
       </div>
       
    </div>
</body>
</html>