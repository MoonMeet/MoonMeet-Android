<?php 
include("includes/database_connection2.php");
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/bootstrap-4/bootstrap.min.css">
    <title>Document</title>
    <style>
    html, .container-fluid {
        background-color:#ecf0f3;
    }
    .blue {
        color: #193566;
    }
    .k-logo {
        box-shadow: 4px 4px 8px 0 rgba(0, 0, 0, 0.4), 6px 6px 20px 0 rgba(0, 0, 0, 0.25);
    }
    img:not(.k-logo) {
        /*  width:50%;*/
    }
    .card {
        padding-left:20px;
        padding-right:20px;
    }
    </style>
</head>
<body>
   <div class="container-fluid">
     <center><img src="images/k7logo" style="width:12em;height:12em;margin-top:15px" class="img rounded-circle img-responsive k-logo" alt="">
        <h1 class="blue">Ka7la Shop</h1>
        <h3 class="blue">Ka7la Shop is an online Tunisian store selling Game Codes - Gift Cards - Subscriptions - etc...</h3>
     <br>
     <div class="row">
     <!------------------------------>
        <div class="col-sm-4">
        <div class="card" style="width: 18rem;">
        <img style="width:80%;height:80%" src="images/valorant-points" class="img img-responsive card-top-image mx-auto d-block" alt="">

  <div class="card-body">
    <h5 class="card-title">Valorant Points</h5>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
    <a href="#" class="btn btn-primary">Go somewhere</a>
  </div>
</div>
        </div><br>
        <!------------------------------>
        <div class="col-sm-4">
        <div class="card" style="width: 18rem;">
        <img src="images/rp-logo" style="width:100%;height:100%" class="img img-responsive card-top-image mx-auto d-block" alt="">

  <div class="card-body">
    <h5 class="card-title">RP</h5>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
    <a href="#" class="btn btn-primary">Go somewhere</a>
  </div>
</div>
        </div><br>
        <!------------------------------>
        <div class="col-sm-4">
        <div class="card" style="width: 18rem;">
        <br>
        <img style="width:80%;height:80%" src="images/steam-card" class="img img-responsive card-top-image mx-auto d-block" alt="">

  <div class="card-body">
    <h5 class="card-title">Steam Cards</h5>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
    <a href="#" class="btn btn-primary">Go somewhere</a>
  </div>
</div>
        </div><br>
<!------------------------------>
<br>
<div class="col-sm-4">
<br>
        <div class="card" style="width: 18rem;">
        <img style="width:80%;height:80%" src="images/discord-nitro" class="img img-responsive card-top-image mx-auto d-block" alt="">

  <div class="card-body">
    <h5 class="card-title">Discord Nitro</h5>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
    <a href="#" class="btn btn-primary">Go somewhere</a>
  </div>
</div>
        </div><br>
<div class="col-sm-4">

        <div class="card" style="width: 18rem;">
        <br>
        <img style="width:40%;height:50%" src="images/nimo-logo" class="img img-responsive card-top-image mx-auto d-block" alt="">

  <div class="card-body">
    <h5 class="card-title">Nimo DM</h5>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
    <a href="#" class="btn btn-primary">Go somewhere</a>
  </div>
</div>
        </div><br>
<!------------------------------>
<div class="col-sm-4">

        <div class="card" style="width: 18rem;">
        <br>
        <img style="width:40%;height:50%" src="images/wild-rift-logo" class="img img-responsive card-top-image mx-auto d-block" alt="">

  <div class="card-body">
    <h5 class="card-title">Wild Rift</h5>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
    <a href="#" class="btn btn-primary">Go somewhere</a>
  </div>
</div>
        </div><br>
<!------------------------------>
        </div>
     </div>
     </center>
   </div>
</body>
</html>