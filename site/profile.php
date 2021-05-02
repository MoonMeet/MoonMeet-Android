<?php
session_start();
if(isset($_SERVER['HTTPS']) && $_SERVER['HTTPS'] === 'on')
$url = "https://";
else
$url = "http://";
// Append the host(domain name, ip) to the URL.
$urlh= $_SERVER['HTTP_HOST'];

// Append the requested resource location to the URL
$url.= $_SERVER['REQUEST_URI'];
include('includes/database_connection2.php');
if(!isset($_SESSION['user_id']))
{
  header("location: login.php");

}
if (isset($_GET['user'])) {
  $user  = $_GET["user"];
  if ($user == $_SESSION['username']) {
    header("location: me");
  }
  if ($user == "azizvirus") {
    header("location: azizbecha");
  }
  if(!isset($_SESSION['user_id']))
{
  header("location: login.php");
}

$req = $connect->query("SELECT * FROM login WHERE username = '$user'");
$resultat = $req->fetch();

if (!$resultat){
header('location: 404'); } else {

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
  <title><?php echo $user, " - Profile"; ?></title>
  <link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
     <link rel="stylesheet" href="css/bootstrap-3/bootstrap.css">
  <link href="css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.js"></script>
  <link rel="stylesheet" href="code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="https://cdn.rawgit.com/mervick/emojionearea/master/dist/emojionearea.min.css">
  <script src="https://cdn.rawgit.com/mervick/emojionearea/master/dist/emojionearea.min.js"></script>
   <script src="js/bootstrap-3/bootstrap.min.js"></script>
   <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
   <style>
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
.alert {
  box-shadow: 0.1px 0.1px 0.1px 0.1px rgba(0, 0, 0, 0.1), 0 6px 6px 0 rgba(0, 0, 0, 0.2);

}
   </style>
<!--<script src="js/ajax/msg2.js"></script>-->
</head>
<body>
<?php
$username = $_SESSION['username'];
foreach($connect->query("SELECT * FROM login WHERE username = '$user'") as $row) {
  $joined_at  = $row['join_date'];
$bio        = $row['bio'];
$firstname  = $row['firstname'];
$lastname   = $row["lastname"];
$email      = $row['email'];
$phone      = $row['phone'];
$gender     = $row['gender'];
$birthdate  = $row['birthdate'];
$badge      = $row['badge'];
$popularity = $row['popularity'];
$profile_image = $row['profile_image'];

}
if ($badge = "user") {
  $badge = '<span class="label label-primary"><i class="fa fa-user"></i> User</span>';
}
if ($profile_image == "") {
  $profile_image = "images/user_image.png";
}
?>
<section id="content" class="container">
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
    <!-- Begin .page-heading -->
    <div class="page-heading">
        <div class="media clearfix">
          <div class="media-left pr30">
            <a href="#">
              <img class="media-object mw150 img-responsive img-thumbnail img-circle" style="max-width:171.5px" src="<?php echo $profile_image; ?>" alt="...">
            </a>
           <center><h3> <?php echo $badge; ?></h3></center>
          </div>
          <div class="media-body va-m">
          <div class="well ">
          <h2 style="text-transform: capitalize;" class="media-heading">
            <?php  echo $firstname; echo " ", $lastname; ?> - Profile
          </h2>
            <h2 class="media-heading"><?php echo "  @", $user; ?>
            <script>document.addEventListener('contextmenu', event => event.preventDefault());
</script>
            </h2>
            <h2>About:</h2>
            <h2 style="text-transform: capitalize;" class="media-heading"><?php echo $bio; ?></h2><br>
           <!-- <div id="user_details"></div>
            <div id="user_model_details"></div>
            <button type="button" style="margin-left:2px; margin-top: 0px;" class="btn btn-primary  start_chat" data-touserid="'.$row['user_id'].'" data-tousername="'.$row['username'].'">Start Chat</button></td>
                <h2 class="media-heading"><?php echo $popularity; ?> <i class="fa fa-star"></i></h2>-->
            </div>

            <a href="report.php" class="btn btn-danger"><strong><i class="fa fa-warning"></i> Report User</strong></a>
            <button onclick="shareonfb();" class="btn btn-primary"><strong><i class="fa fa-facebook"></i> Share On Facebook <i class="fa fa-share-alt"></i></strong></button>
            <button class="btn btn-info" onclick="shareontwitter();">Share On Twitter</button>
<script>
   function shareonfb(){
       var url = "https://facebook.com/sharer/sharer.php?u=localhost/moonmeet/profile?user=<?php echo $user; ?>";
       window.open(url, '', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=300,width=600');
       return false;
  }
  function shareontwitter(){
    var url = 'https://twitter.com/intent/tweet?url=localhost/moonmeet/profile?user=<?php echo $user; ?>';
    TwitterWindow = window.open(url, 'TwitterWindow',width=600,height=300);
    return false;
 }
</script>
           </div>
          </div>
         </div>

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
                    <td style=""><?php echo $user; ?></td>
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
                   <!-- <td>
                      <span style="font-size:30px" class="fa fa-phone"></span>
                    </td>
                    <td style=""><?php //echo $phone; ?></td>-->
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
                    <tr>

                </tbody>
              </table>

            </div>
          </div>
          <div class="panel">
            <div class="panel-heading">
              <span class="panel-icon">
                <i class="fa fa-trophy"></i>
              </span>
              <span class="panel-title"> My Skills</span>
            </div>
            <div class="panel-body pb5">
              <span class="label label-warning mr5 mb10 ib lh15">Default</span>
              <span class="label label-primary mr5 mb10 ib lh15">Primary</span>
              <span class="label label-info mr5 mb10 ib lh15">Success</span>
              <span class="label label-success mr5 mb10 ib lh15">Info</span>
              <span class="label label-alert mr5 mb10 ib lh15">Warning</span>
              <span class="label label-system mr5 mb10 ib lh15">Danger</span>
              <span class="label label-info mr5 mb10 ib lh15">Success</span>
              <span class="label label-success mr5 mb10 ib lh15">Ui Design</span>
              <span class="label label-primary mr5 mb10 ib lh15">Primary</span>

            </div>
          </div>
          <div class="panel">
            <div class="panel-heading">
              <span class="panel-icon">
                <i class="fa fa-pencil"></i>
              </span>
              <span class="panel-title">About Me</span>
            </div>
            <div class="panel-body pb5">

              <h6>Experience</h6>

              <h4>Facebook Internship</h4>
              <p class="text-muted"> University of Missouri, Columbia
                <br> Student Health Center, June 2010 - 2012
              </p>

              <hr class="short br-lighter">

              <h6>Education</h6>

              <h4>Bachelor of Science, PhD</h4>
              <p class="text-muted"> University of Missouri, Columbia
                <br> Student Health Center, June 2010 through Aug 2011
              </p>

              <hr class="short br-lighter">

              <h6>Accomplishments</h6>

              <h4>Successful Business</h4>
              <p class="text-muted pb10"> University of Missouri, Columbia
                <br> Student Health Center, June 2010 through Aug 2011
              </p>

            </div>
          </div>
        </div>
        <div class="col-md-8">
          <div class="well">
          <div>

          <h3><?php echo $user ?>'s Posts</h3>
              <div class="posts" style="word-wrap: break-word;">
              <?php
              $query = "SELECT * FROM posts WHERE by_user = '$user' ORDER BY post_id DESC";
              $stmt = $connect->prepare($query);
              $stmt->execute();
              $data = $stmt->fetchAll();
              if (!$data) {
              echo '<div class="alert alert-info" style=""><center><h1 style="color: #193566;"><strong>'.$user.' dont have any post !</h1></strong></center></div>';
              }
  foreach($connect->query($query) as $row) {
  echo "<div class='alert alert-info'><h4 style='font-family:sans-serif; font-size:23px'><i style='font-size:30px' class='fa fa-user'></i> " . $row["by_user"] . "<br><i style='font-size:25px' class='fa fa-clock-o'></i>   ".$row['posted_on'] ."</h4><div class='' style=''><h2 style='color:black'>" . $row['post'] ."</h2></div></div>";}
 ?>
              </div>
          </div>

      </div>
  </section>
</body>
  </html><?php }}  if (!isset($_GET['user'])) { ?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=devise-width, initial-scale=1.0">
  <link rel="stylesheet" href="css/profile.css">
  <title><?php echo $_SESSION['username'], " - Profile"; ?></title>
  <link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
  <link rel="stylesheet" href="css/bootstrap-3/bootstrap.css">
  <link href="css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.js"></script>
  <link rel="stylesheet" href="code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="https://cdn.rawgit.com/mervick/emojionearea/master/dist/emojionearea.min.css">
  <script src="https://cdn.rawgit.com/mervick/emojionearea/master/dist/emojionearea.min.js"></script>
   <script src="js/bootstrap-3/bootstrap.min.js"></script>
   <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
   <style>
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
.alert {
  box-shadow: 0.1px 0.1px 0.1px 0.1px rgba(0, 0, 0, 0.1), 0 6px 6px 0 rgba(0, 0, 0, 0.2);

}
   </style>
</head>
<body>
<?php
$username = $_SESSION['username'];
foreach($connect->query("SELECT * FROM login WHERE username = '$username'") as $row) {
$bio           = $row['bio'];
$firstname     = $row['firstname'];
$lastname      = $row["lastname"];
$email         = $row['email'];
$phone         = $row['phone'];
$gender        = $row['gender'];
$birthdate     = $row['birthdate'];
$badge         = $row['badge'];
$popularity    = $row["popularity"];
$profile_image = $row['profile_image'];
}
if ($badge = "user") {
  $badge = '<span class="label label-primary"><i class="fa fa-user"></i> User</span>';
}
if ($profile_image == "") {
  $profile_image = "images/user_image.png";
}
?>
<section id="content" class="container">
    <!-- Begin .page-heading -->
    <div class="page-heading">
        <div class="media clearfix">
          <div class="media-left pr30">
            <a href="#">
              <img class="media-object mw150 img-responsive img-thumbnail img-circle" style="max-width:171.5px" src="<?php echo $profile_image; ?> " alt="...">
            </a>
            <center><h3> <?php echo $badge; ?></h3>
            <h3><a class="btn btn-danger" href="add-profile-image.php">Change Image</a></h3>
            </center>
          </div>
          <div class="media-body va-m">
          <div class="well well-lg">
          <h2 class="media-heading">
            <?php  echo $firstname; echo " ", $lastname; ?> - Profile
          </h2>
            <h2 class="media-heading"><?php echo "  @", $_SESSION['username']; ?>

            </h2><br>

            <h2 class="media-heading"><?php echo $bio; ?></h2>
          <!--  <h2><?php echo $popularity; ?> <i class="fa fa-star"></i></h2> -->
            <a class="btn btn-primary" target="_blank" href="https://www.facebook.com/sharer.php?u=<?php echo $urlh; ?>/profile?user=<?php echo $username; ?>">Share On Facebook</a>

            </div>
          </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-4">
          <div class="panel">
            <div class="panel-heading">
              <span class="panel-icon">
                <i class="fa fa-star"></i>
              </span>
              <span class="panel-title"> User Informatons</span>
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
                      <span class="fa fa-"><img src="images/logo" class="img-responsive" style="max-width:35px" alt=""></span>
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
                    <tr>
                  <td>
                      <span style="font-size:30px" class="fa fa-clock-o"></span>
                    </td>
                    <td style="text-transform: capitalize;"><?php echo $row['join_date']; ?></td>
                    <tr>
                </tbody>
              </table>
              <form action="settings" method="post">
              <center><button class="btn btn-primary">Change data</button></center>
              </form>
            </div>
            </div></div>

        <div class="col-md-8">
          <div class="well">
          <div>           <?php
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
                     <textarea required placeholder="Share your ideas with others ..." style="border-radius:10px" class="form-control emoji_act" name="publication" rows="6"></textarea>
                         <br>
                         <button type="submit" name="subpost" class="btn btn-primary btn-block btn-lg"><i class="fa fa-pencil"></i> <strong>Post</strong></button>
                    <br>
                   </form><br>
          <h3>Your Posts</h3>
              <div class="posts" style="word-wrap: break-word;">
              <?php
              $query = "SELECT * FROM posts WHERE by_user = '$username' ORDER BY post_id DESC";
              $stmt = $connect->prepare($query);
              $stmt->execute();
              $data = $stmt->fetchAll();
              if (!$data) {
              echo '<div class="alert alert-info" style=""><center><h1 style="color: #193566;"><strong>You dont have any post !</h1></strong></center></div>';
              }
  foreach($connect->query($query) as $row) {
  echo "<div class='alert alert-info'><h4 style='font-family:sans-serif; font-size:23px'><i style='font-size:30px' class='fa fa-user'></i> " . $row["by_user"] . "<br><i style='font-size:25px;margin-top:5px' class='fa fa-clock-o'></i>   ".time_ago($row['posted_on']) ."</h4><div class='' style='background-color:'><h2 style='color:black'>" . $row['post'] ."</h2></div></div>";}
 ?>
              </div>
          </div>
      </div>
  </section>
</body>
</html><?php }  ?>
