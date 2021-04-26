<?php 
include('includes/database_connection2.php');
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=*, initial-scale=1.0">
  <meta property="og:image" content="images/logo.png">
     <meta property="og:description" content="Moon Meet is an instant messaging application that gives you a good acces to connect with friends, make relationships, share stories etc..">
     <meta property="og:title" content="Moon Meet">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <meta name="keywords" content="moonmeet, Moonmeet, Moon meet, moon meet, Moon Meet, social media, new era of messaging, aziz becha, Aziz becha">
     <meta name="author" content="Aziz Becha">
     <meta http-equiv="content-type" content="text/html; charset=utf-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Aziz Becha - Moon Meet</title>
  <link rel="stylesheet" href="css/bootstrap-3/bootstrap.min.css">
<link rel="stylesheet" href="css/profile.css">
<link rel="stylesheet" href="css/font-awesome-4.7.0/css/font-awesome.min.css">
</head>
<body>
<?php

foreach($connect->query("SELECT * FROM login WHERE username = 'azizvirus'") as $row) {
$bio = $row['bio'];
$popularity = $row['popularity'];
}
?>                  <script>document.addEventListener('contextmenu', event => event.preventDefault());
</script>
<section style="margin-top:-40px" id="content" class="container">
    <!-- Begin .page-heading -->
    <div class="page-heading">
      <br>
        <div class="media clearfix">
          <div class="media-left pr30">
            <a href="#">
              <img class="media-object mw150 img-responsive img-thumbnail" style="max-width:200px" src="images/dev.aziz.jpg" alt="...">
            </a>
          </div>                      
          <div class="media-body va-m"><br>
            <h2 class="media-heading">Aziz Becha </h2><h3><span class="label label-danger"> Admin <i class="fa fa-shield"></i> </span> <span style="margin-left:5px" class="label label-success"> Developer <i style="font-size:22px" class="fa fa-code"></i> </span> <span style="margin-left:5px" class="label label-primary"><i class="fa fa-user"></i> User</span>
            </h3>
            <p class="lead"><strong>CO. Founder, Chairman, Developer, Admin At Moon Meet</strong></p>
           <!-- <h2 style="color:black"><strong><?php echo $popularity; ?> <i class="fa fa-star"></i></strong></h2>-->
            <div class="media-links">
              <ul class="list-inline list-unstyled">
                <li>
                  <a href="#" title="facebook link">
                    <span class="fa fa-facebook-square fs35 text-primary"></span>
                  </a>
                </li>
                <li>
                  <a href="#" title="twitter link">
                    <span class="fa fa-twitter-square fs35 text-info"></span>
                  </a>
                </li>
                <li>
                  <a href="#" title="google plus link">
                    <span class="fa fa-google-plus-square fs35 text-danger"></span>
                  </a>
                </li>
                <li class="hidden">
                  <a href="#" title="behance link">
                    <span class="fa fa-behance-square fs35 text-primary"></span>
                  </a>
                </li>
                <li class="hidden">
                  <a href="#" title="pinterest link">
                    <span class="fa fa-pinterest-square fs35 text-danger-light"></span>
                  </a>
                </li>
                <li class="hidden">
                  <a href="#" title="linkedin link">
                    <span class="fa fa-linkedin-square fs35 text-info"></span>
                  </a>
                </li>
                <li class="hidden">
                  <a href="#" title="github link">
                    <span class="fa fa-github-square fs35 text-dark"></span>
                  </a>
                </li>
                <li class="">
                  <a href="#" title="phone link">
                    <span class="fa fa-phone-square fs35 text-system"></span>
                  </a>
                </li>
                <li>
                  <a href="#" title="email link">
                    <span class="fa fa-envelope-square fs35 text-muted"></span>
                  </a>
                </li>
                <li class="hidden">
                  <a href="#" title="external link">
                    <span class="fa fa-external-link-square fs35 text-muted"></span>
                  </a>
                </li>
              </ul>
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
              <span class="panel-title"> You can find me on</span>
            </div>
            <div class="panel-body pn">
              <table class="table mbn tc-icon-1 tc-med-2 tc-bold-last">
                <thead>

                </thead>
                <tbody>

                <tr>
                    <td>
                      <span><img style="width:30px;" src="images/logo" alt=""></span>
                    </td>
                    <td><strong>AzizVirus</strong></td>
                    <td>
                      
                  </tr>
                  <tr>
                    <td>
                      <span style="font-size:30px" class="fa fa-github"></span>
                    </td>
                    <td><strong>AzizVirus</strong></td>
                    <td>
                      
                  </tr>
                  <tr>
                    <td>
                      <span style="font-size:30px" class="fa fa-linkedin"></span>
                    </td>
                    <td><strong>Aziz Becha</strong></td>
                    
                  <tr>
                    <td>
                      <span style="font-size:30px"class="fa fa-facebook"></span>
                    </td>
                    <td><strong>Aziz Bécha</strong></td>
<tr>
                    <td>
                      <span style="font-size:30px"class="fa fa-telegram"></span>
                    </td>
                    <td><strong>azizbecha</strong></td>
                    </tr>
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
          <div class="well well-lg">
          <div class="row">
            <div class="col-sm-6">
               <img class="img-responsive img-thumbnail" src="images/dev.aziz" alt="">
            </div>
            <div class="col-sm-6">
            <img class="img-responsive img-thumbnail" src="images/dev.aziz2" alt="">
            </div>         
            </div></div>
          </body>
</html>
<?php include("admin/private/log.php"); ?>