<?php
session_start();
include('includes/database_connection2.php');
if(!isset($_SESSION['user_id']))
{
  header("location: login");
}$msg = "";
$user = $_SESSION['username'];
$id = $_SESSION['user_id'];
$query = $connect->query("SELECT * FROM login WHERE user_id = '$id'");
while($row = $query->fetch()) {
 $profile_image = $row['profile_image'];
 $firstname = $row['firstname'];
 $lastname = $row['lastname'];
}
if ($profile_image == "") {
  $profile_image = "images/user_image.png";
}

?>
<!DOCTYPE html>
<html lang="en">
<head>
 <meta http-equiv="content-type" content="text/html; charset=utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <meta property="og:image" content="images/logo.png">
     <meta property="og:description" content="Moon Meet is an instant messaging application that gives you a good acces to connect with friends, make relationships, share stories etc..">
     <meta property="og:title" content="Moon Meet">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <meta name="keywords" content="moonmeet, Moonmeet, Moon meet, moon meet, Moon Meet, social media, new era of messaging, aziz becha, Aziz becha">
     <meta name="author" content="Aziz Becha">

 <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
   <link rel="stylesheet" href="css/bootstrap-3/bootstrap.css">

   <link rel="stylesheet" type="text/css" href="css/style2.css">
                <link rel="stylesheet" type="text/css" href="css/style3.css">
   <link rel="stylesheet" href="css/font-awesome-4.7.0/css/font-awesome.min.css">
     <script src="js/code.jquery.com/jquery-1.12.4.js"></script>
     <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
     <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
     <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
     <script src="https://cdn.rawgit.com/mervick/emojionearea/master/dist/emojionearea.min.js"></script>
  		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.js"></script>
      <link rel="stylesheet" href="https://cdn.rawgit.com/mervick/emojionearea/master/dist/emojionearea.min.css">
      <script type="text/javascript">$('textarea').emojiarea();</script>
               <title>Moon Meet</title>
                <link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
                <link rel="stylesheet" href="css/style1.css">

                <link rel="stylesheet" href="css/normalize.css">
                <link rel="stylesheet" href="css/style5.css">
                <style>
     #state{
        background: #ecf0f3;
    position: absolute;
    top: 0px;
    right: 0px;
    bottom: 0px;
    left: 0px;
    z-index:999999999999999;
    padding-bottom:50%;
    color: #193566;
    overflow: hidden;
     }
     section.loading-overlay{
        background: #ecf0f3;
    position: absolute;
    top: 0px;
    right: 0px;
    bottom: 0px;
    left: 0px;
    z-index:999999999999999;
    padding-bottom:50%;
    color: #193566;
    overflow: hidden;
     }

     html {
  background-color: #eceff4;
}
.btn-primary {
  background-color:#193566;
}
.panel-primary > .panel-heading {
  background-color:#193566
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
.alert, .sidenav {
  box-shadow: 0.1px 0.1px 0.1px 0.1px rgba(0, 0, 0, 0.1), 0 4px 4px 0 rgba(0, 0, 0, 0.1);
}
.sidenav {
  box-shadow: 0.1px 0.1px 0.1px 0.1px rgba(0.1, 0.1, 0.1, 0.1), 4px 4px 4px 4px rgba(0, 0, 0, 0.1);
}
 </style>
</head>
<script>
function play_sound() {
  var audio = new Audio('sounds/pop-sound-effect.mp3');
  audio.play();
}
</script>
<div id="top"></div>
<div style="background-color:#eceff4;" id="user_model_details"></div>
<script src="js/script.js"></script>
<header style="background-color:black; margin-bottom:7px">
<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">
  <div style="margin-left:-20px" class="navbar-header">
  <strong style="font-size:1.5em;color:#193566">Moon Meet</strong>
    <img class="img-navbar" src="images/logo" alt="logo.png" style="width:60px;margin-top:-6px">
    <a style="color:black" class="navbar-brand" href="#"></a>
    </div>

    <form class="navbar-form navbar-left" method="GET" action="search">
      <div class="form-group">
        <input type="text" class="form-control search-bar-navbar" name="query" placeholder="Search">
      </div>
      <button type="submit" class="btn btn-primary"><i class="fa fa-search"></i></button>
    </form>
    <ul class="nav navbar-nav navbar-right">
    <li class="dropdown-header"><div class="dropdown">

  <button onclick=" $('#myDropdown').toggle(); " id="dropdown-notif" class="dropbtn fa fa-bell"><span style="" class="label label-danger notif-number">4</span></button>
  <div id="myDropdown" class="dropdown-content">
    <script>

    </script>
    
    <a id="notif1">Welcome Back <?php echo $_SESSION['username']; ?> ! <span class="close" onclick="$('#notif1').slideUp();"><strong>X</strong></span></a>
    <a id="notif2">See latest Updates ! <span class="close" onclick="$('#notif2').slideUp();"><strong>X</strong></span></a>
    <a id="notif3">Read Rules Carefully ! <span class="close" onclick="$('#notif3').slideUp();"><strong>X</strong></span></a>
    <a id="notif4">New To Moon Meet ? Discover People Here ! <span class="close" onclick="$('#notif4').slideUp();"><strong>X</strong></span></a>
 
  </div>
  </div>
</li>
<div id="myProfile" style="margin-top:50px;" class="dropdown-content">
    <div class="well well-sm">
    <img src="<?php echo $profile_image ?>" class="img img-rounded" style="width:30px;height:30px;border-radius:50%" alt="" srcset=""><strong><?php echo " ", $firstname, " ", $lastname , " - " , $_SESSION['username']; ?></strong><br>

    <?php include("includes/fetch_data2.php"); ?>
    <br>
    <button onclick="location.replace('profile');" href="profile" class="btn btn-primary btn-block"><b><strong>See Full Profile</strong></b></button>
    </div>
  </div>

      <li><br><button class="btn btn-default" onclick=" $('#myProfile').toggle(); " id="dropdown-profile" style="background:Transparent;border:none;margin-top:-15px;">
      <img class="img profile-image" src="<?php echo $profile_image; ?>" style="width:40px;height:40px;margin-top:-10px;margin-right:5px;border-radius:50%;" alt="">
      <strong style="font-family: sans-serif">  My Profile <i style="font-size:12.5px;" class="fa fa-caret-down"></i></strong> </button></li>
      <li><form action="logout.php" method="POST"><button style="margin-top:5px;margin-right:5px;" class="btn btn-primary" type="submit" name="logout" ><span class="fa fa-sign-out"><strong style="font-family: sans-serif">   Logout</strong></span></button></form></li>
      <li><span class="opennav" style="font-size:30px;cursor:pointer; margin-top:10px" onclick="openNav()"><i class="fa fa-bars"></i></span></li>
    </ul>

    <ul style="float:none" class="nav navbar-nav ">
      <li class="active"><a href="#"><strong><i class="fa fa-home"></i> Home</strong></a></li>
      <li><a href="discover.php"><strong><i class="fa fa-user-plus"></i>  Discover People</strong></a></li>
      <li><a href="faq.php"><strong><i class="fa fa-question-circle"style="font-size:18px"></i> FAQ</strong></a></li>
      <li><a href="report.php"><strong><i class="fa fa-bug"></i> Report</strong></a></li>
      <li><a href="developers.php" style="margin-top:-1.5px;"><strong><i style="font-size:20px" class="fa fa-code"></i> Developers</strong></a></li>
      <li><a href="settings.php"><strong><i class="fa fa-cog" style="font-size:16px"></i> Settings</strong></a></li>
      <button type="submit" class="btn btn-primary up"><i class="fa fa-arrow-up"></i></i></button>
      <button style="margin-top:7px;" type="submit" class="btn btn-primary refresh"><i class="fa fa-refresh"></i></i></button>

    </ul>
  </div>
</nav>
</header>
<script>
$(document).ready(function(){
  checkConx();
  setInterval(function(){
		fetch_posts();
    fetch_stories();
    check_session();
    checkConx();
	}, 500);
fetch_posts();
fetch_stories();
checkConx();
check_session();
function fetch_posts()
	{
		$.ajax({
			url:"includes/fetch_posts.php",
			method:"POST",
			success:function(data){
				$('#posts').html(data);
			}
		})
	}
  function fetch_stories()
	{
		$.ajax({
			url:"includes/fetch_stories.php",
			method:"POST",
			success:function(data){
				$('.stories').html(data);
			}
		})
	}
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
 function checkConx(){
  if(navigator.onLine){
    $('#state').hide();
 } else {
    $('#state').show();
  $("*").css("overflow-x", "hidden");
  $("*").css("overflow-y", "hidden");

 }
}
});


</script>
<div class="alert alert-danger" id="state">
    <center>
    <h1 style="color: #193566;"><strong><i class="fa fa-exclamation-triangle"></i> Oups ! <i class="fa fa-exclamation-triangle"></i></strong></h1>
      <i style="color: #193566; font-size: 4em;" class="fa fa-wifi"></i>
    <strong>
        <h2 style="color: black;">Looks like there's no Internet Connection.</h2>
     <h2 style="color: black;">You are not connected to the internet.</h2>
     <h2 style="color: black;" >Please make sure that Wi-Fi Or Router is well going.</h2>
    </strong>
    </center>
</div>
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
<div id="mySidenav" class="sidenav" style="background-color:#193566;">
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()"><i class="fa fa-close"></i></a>
  <a class="mmeet" href="home.php"><i class="fa fa-moon-o"></i> <strong>Moon Meet</strong></a>
  <a class="user" style="margin-left: 25px; margin-top:-12px;" href="me.php"> @ <strong><?php echo $_SESSION['username']; ?></strong></a>

    <div style="padding:1px; background-color:white;margin-bottom:20px;"></div>
  <a href="me.php" style="margin-top:-10px;"><strong><i class="fa fa-user-circle"></i> My Profile</strong></a>
  <a href="discover.php"><strong> <i class="fa fa-search"></i> Discover People</strong></a>
  <a href="invite.php"><strong><i class="fa fa-user-plus"></i> Invite Friends</strong></a>
  <a href="announcements.php"><i class="fa fa-bullhorn"></i> <strong>Announcements</strong></a>
  <a href="settings.php"><strong><i class="fa fa-cog"></i> Settings</strong></a>
  <!--<a href="ka7lashop"><strong><i class="fa fa-gamepad"></i> Ka7la Shop</strong></a>-->
  <a href="report.php"><i class="fa fa-bug"></i> <strong>Report a problem</strong></a>
  <a href="developers.php"><i class="fa fa-code"></i> <strong>Developers</strong></a>
  <a href="rules.php"><i class="fa fa-gavel"></i> <strong>Rules</strong></a>
  <a href="privacypolicy.php"><i class="fa fa-file"></i> <strong>Privacy Policy</strong></a>
  <a href="terms.php"><i class="fa fa-file-word-o"></i> <strong>Terms & Conditions</strong></a>
  <a href="logout.php"><i class="fa fa-sign-out"></i> <strong>Logout</strong></a>
  <style>

/* #1e407b */
  </style>
</div>
<body style="background-color:#eceff4"><br><br><br>
</div>
<script src="js/ajax/msg.js"></script>
<div class="container-fluid">
<div class="col-sm-3">

  <div class="panel my-profile panel-primary">

    <div class="panel-heading">
   <i class="fa fa-user"></i>   <strong>My Profile</strong>
    </div>
    <div class="panel-body left">
<center><button type="button" class="openimage" data-toggle="modal" data-target="#profileImage"><img class="img img-rounded " style="width:65px; height:65px; border-radius:50%;" src="<?php echo $profile_image ?>" alt=""></button><br>
<button onclick="changeProfileImage();" class="changeImage" style="color:#193566;" data-toggle="modal" data-target="#changeImage"><i class="fa fa-camera"></i></button>
<div class="modal fade" id="profileImage" role="dialog">
    <div class="modal-dialog ">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><i class="fa fa-picture-o" aria-hidden="true"></i> <?php echo $_SESSION['username']; ?>'s Profile Image</h4>
        </div>
        <div class="modal-body">
        <img class="img img-rounded img-thumbnail" style="width:50%; height:60%;" src="<?php echo $profile_image ?>" alt="">
     <br> <br><a href="add-profile-image.php" class="btn btn-danger btn-block btn-lg"><strong>Change</strong></a>
      </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-close"></i> <strong>Close</strong></button>
        </div>
      </div>
    </div>
  </div>
  <!--------------------------->
</center>

  <?php include_once("includes/fetch_data.php");  ?>
           <center><a class="btn btn-primary btn-block" style="color:white" href="profile"><strong>Check full profile</strong></a></center>
    </div>
  </div>
  <div class="panel panel-primary card sticky-top">
    <div class="panel-heading">
<strong style="font-size:1.2em"><i style="font-size:24px;margin-right:3px" class="fa">&#xf10b;</i>  Moon Meet Mobile App</strong>
    </div>
   <div class="panel-body">
  <h4 style="text-align:center;color:#193566;"><strong><i class="fa fa-android"></i> You can also use Moon Meet on your phone by downloading the Mobile Application</strong></h4>
  <img class="img img-thumbnail" style="width:100%;" src="images/screenshots/3" alt="" srcset="">
  <a href="download.php" target="_blank" class="btn btn-primary btn-block btn-lg"><i class="fa fa-download"></i><strong> Download</strong></a>
   </div>
  </div>
<!--<iframe src="https://www.facebook.com/plugins/page.php?href=https%3A%2F%2Fwww.facebook.com%2Fmoonmeet.inc%2F&tabs=timeline&width=340&height=500&small_header=true&adapt_container_width=true&hide_cover=false&show_facepile=true&appId" width="340" height="500" style="border:none;overflow:hidden" scrolling="no" frameborder="0" allowfullscreen="true" allow="autoplay; clipboard-write; encrypted-media; picture-in-picture; web-share"></iframe>-->

 </div>
    <div class="col-sm-5">

    <div class="alert alert-info" id="welcome-1">
      <span class="close" onclick="$('#welcome-1').slideUp();">X</span>
    <strong>Welcome Back <?php echo $_SESSION['username']; ?> !
  </strong>
    <form style="float:right;" action="logout.php" method="POST">
    <button style="margin-top:-6px; margin-right: 15px" class="btn logout-navbar btn-primary" type="submit" name="logout"><strong><i class="fa fa-sign-out"></i> Logout</strong></button>
    </form>
   </div>
   <div id="welcome-2" class="alert alert-success">
    <span class="close" onclick="$('#welcome-2').slideUp();">X</span>
       <strong>Use Moon Meet to make relationships from the whole world, Chat, Post daily actions and more !</strong>
    </div>
       <div id="welcome-3" class="alert alert-danger">
       <span class="close" onclick="$('#welcome-3').slideUp();">X</span>
         <strong>Any illegal actions (spam, hate, bad words) or unrespect of <a href="rules" target="_blank" rel="noopener noreferrer">Rules</a> will risk on a permanent Ban !</strong>
       </div>
       <div class="panel panel-primary stories-main">
       <div class="panel-heading"  style="font-size:1.5em"><i class="fa fa-book"></i><strong> Stories</strong></div>
              <div class="panel-body" style="height:10%">
              <style>
                .stories ul li, .stories-main ul li {display: inline-block;}
              </style>
                <ul style="margin-left:-50px">
                <li style="display: inline-block;"><div data-toggle="modal" data-target="#storyAdd" style="cursor:pointer;border-radius:50%;width:49px;height:49px;border: #193566 solid 3px;margin-left:6px;"><img style="width:48px;height:46px;border-radius:50%;margin-left:-2px;margin-top:-1px" src="images/fa fa-plus" alt="Add Story">
                      <span style="font-size:9px;font-weight:500"><strong>Add Story</strong></span></div></li>
                  <li><div class="stories"></div></li>
                </ul>
</div>
            </div>

    <div class="panel panel-primary">
      <div class="panel-heading"  style="font-size:1.5em"><i class="fa fa-globe"></i><strong> News Feed</strong></div>
        <div class="panel-body">
          <div class="stories-line">
  <!---- Model ------>
<div class="modal modal-story-add fade" id="storyAdd" role="dialog">
    <div class="modal-dialog ">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><i class="fa fa-book"></i> Add Story</h4>
        </div>
        <div class="modal-body">
        <?php
        if (isset($_POST['add-story']) && $_FILES['image']['error']==0) {
          $msg = "";
          $allow_ext = array('png','PNG','jpg','JPG','jpeg','JPEG');
          $allow_type = array('image/png','image/gif','image/jpeg','image/bmp','image/tiff','image/jpg');
          $image_name = $_FILES['image']['name'];
          $image_type = getimagesize($_FILES['image']['tmp_name']);
          $image_name = explode('.',$image_name);
          $ext = end($image_name);
            $img = $_FILES['image']['name'];
          if(in_array($ext, $allow_ext) && in_array($image_type['mime'], $allow_type)){
            $newname = rand(111111111111,999999999999) . '.' . strtolower($ext);
            $size = filesize($_FILES['image']['tmp_name']);
            if ($size < "3145728") {
              $upload = move_uploaded_file($_FILES['image']['tmp_name'], "images/stories/".$newname);
              $imgname = "images/stories/".$newname;

              if ($upload) {
                $sql = "INSERT INTO stories (by_user, image) VALUES ('$user', 'images/stories/$newname' ) ";
                $statement = $connect->prepare($sql);
                if($statement->execute())
                {
                  //$msg =  '<div class="alert alert-success"><strong>Image Uploaded !</strong></div>';
                        }


              }
              elseif (!($statement->execute())) {$msg="error";}

            }
            elseif ($size > "3145728") {
              //$msg =  '<div class="alert alert-danger"><strong>File Is Too Large ! Max is 3MB</strong></div>';
            }
          } else {
            //$msg = '<div class="alert alert-danger"><strong>Error: Invalid File Type!</strong></div>';
          }
        }
        ?>
        <style>
            input[type=file]::-webkit-file-upload-button {
	background-color: #b92c28;
  border: none;
  color: white;
  padding: 15px 20px;
  text-align: center;
  text-decoration: none;
  font-size: 16px;
  border-radius: 3em;
}
        </style>
        	<form method="POST" enctype="multipart/form-data">
      <input class="" class="btn btn-danger" type="file" name="image" value="Upload image"/>

      <div> <br>
          <button type="submit" class="btn btn-lg btn-primary" name="add-story">Upload Story</button>
        </div>
  </form>
      </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-close"></i> <strong>Close</strong></button>
        </div>
      </div>
    </div>
  </div>
  <!-- End Model ------>
<style>
  .stories ul li {
    display: inline-block;

  }
  .stories ul {
    float:left;
  }
</style></div>
          <div class="row">
            <div class="col-sm-12">
              <div class="well well-lg well-info">
                <h4 style="color: #193566; font-weight: bold;"><i class="fa fa-user">  </i><?php echo "  ", $_SESSION['username']; ?> What's on your mind ? </h4>
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
                     <textarea required placeholder="Share your ideas with others ..." style="border-radius:10px;font-size: 44pt" class="form-control emoji_act" name="publication" rows="6"></textarea>
               <br>
               <button type="submit" name="subpost" class="btn btn-primary btn-block btn-lg"><i class="fa fa-pencil"></i> <strong>Post</strong></button>
                   </form><br>
                   <div class="posts" id="posts">

 <div id="postOptions" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Post Options</h4>
      </div>
      <div class="modal-body">
        <h4>If you think that this post really can dammage the platform or doesn't respect the rules, Report it now !</h4>
          <br>
        <a href="report.php" class="btn btn-danger btn-block btn-lg"><strong>Report</strong></a>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>

  <button class='btn btn-block btn-primary up'><i class='fa fa-arrow-up'></i></button>
                </div>
               </div>
              </div>
             </div>
		        </div>
          </div>
         </div>
    </div>

    <div class="col-sm-4">
   <div id="welcome-4" class="alert alert-info">
     <button alt="dismiss" class="close" onclick="$('#welcome-4').slideUp();"><strong>X</strong></button>
     <strong><?php echo $_SESSION['username']; ?></strong>, how are you today ?
     </div>

<div class="panel panel-primary">
<style>
ul.social-icons {
  margin: 0;
  padding: 0;
  width: 100%;
  text-align: center;
}

ul.social-icons > li {
  display: inline-block;
}

ul.social-icons > li > a {
  display: inline-block;
  font-size: 18px;
  line-height: 30px;
  width: 30px;
  height: 30px;
  border-radius: 36px;
  background-color: #163566;
  color: #fff;
  margin: 0 3px 3px 0;
}

ul.social-icons > li > a:hover {
  text-decoration: none;
  background-color: #163566;
}

</style>
      <div style="font-weight:bold" class="panel-heading">
        <i class="fa fa-share-alt"></i> &nbsp; Find us Out
      </div>
      <div class="panel-body">
          <center><ul class="social-icons">
  <li>
    <a target="_blank" href="https://facebook.com/moonmeet.inc" title="Facebook">
      <span class="icon fa fa-facebook"></span>
    </a>
  </li>
  <li>
    <a target="_blank" href="https://t.me/MoonMeet" title="Telegram">
      <span class="icon fa fa-telegram"></span>
    </a>
  </li>
  <li>
    <a target="_blank" href="https://github.com/AlucardTn/MoonMeet" title="GitHub">
      <span class="icon fa fa-github"></span>
    </a>
  </li>
</ul></center>
      </div>
</div>

        <div class="panel panel-primary">
         <div class="panel-heading"><i class="fa fa-comment"></i> <strong>Chats and Discussions</strong></div>
           <div class="panel-body">
<form class="form-content" method="GET" action="search">
      <div class="col-sm-9">
        <input type="text" class="form-control search-bar-navbar" name="query" placeholder="Search" required>
        </div>
      <div class="col-sm-3">
      <button type="submit" align="center" class="btn btn-primary"><i class="fa fa-search"></i></button>
      </div>

    </form><br>
   <!-- <input id="search-right" type="text" class="form-control search-bar-navbar" name="query" placeholder="Search" required>-->
           <div class="table-responsive table table-stripped ">
             <div id="user_details"></div>
           <form action="discover">
             <button align="center" type="submit" class="btn btn-primary btn-block btn-lg"><strong><i class="fa fa-user-plus"></i> Discover More</strong></button>
           </form>
           </div>

           </div>
        </div>
      </div>
  </div>
</div>

    </body>
    <footer>
      <div class="container-fluid">
       <center><h3>Made with <svg class="heart" viewBox="0 0 32 29.6">
  <path d="M23.6,0c-3.4,0-6.3,2.7-7.6,5.6C14.7,2.7,11.8,0,8.4,0C3.8,0,0,3.8,0,8.4c0,9.4,9.5,11.9,16,21.2
	c6.1-9.3,16-12.1,16-21.2C32,3.8,28.2,0,23.6,0z"/>
</svg>  in Tunisia</h3></center>
      </div>
      <!----- loading ------>
      <section style="overflow-x:hidden;background-color:#193566;color:#ecf0f3;right: 50%;
      width: 100%;
      height: 200%;
      z-index: 99999;" class="loading-overlay">

      <center>
      <div style="position: fixed; top:40%; right:38%;">
      <h1 style="color:white;font-size:3em;"><strong>Moon Meet Is Loading</strong></h1>
      <br>
      <div class="loader" id="loader-6">

          <span style="background-color:white;"></span>
          <span style="background-color:white;"></span>
          <span style="background-color:white;"></span>
          <span style="background-color:white;"></span>
        </div>
      </div>
      <br><br><br><br><br> <br><br><br><br><br> <br><br><br><br><br> <br><br><br><br><br> <br><br><br><br><br> <br><br><br><br><br> <br><br><br><br><br> <br><br><br><br><br> <br><br><br><br><br> <br><br><br><br><br> <br><br><br><br><br>
       </center>
     </section>
     </footer>
     <br>
     <div class="copyright" style="background-color: #ececec;width:100%;border-top:1px solid #000000">
       <div class="container">
       <h3 align="center">&copy; <script>document.write(new Date().getFullYear())</script> Moon Meet - All Rights Reserved.</h3>

       </div>
     </div>
</html>
