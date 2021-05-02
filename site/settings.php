<?php
session_start();
if (!isset($_SESSION['user_id'])) {
    header("location: login");
}
include("includes/database_connection2.php");
$message_general = "";
$id = $_SESSION['user_id'];
$username = $_SESSION['username'];
foreach($connect->query("SELECT * FROM login WHERE username = '$username'") as $row) {
    $firstname = $row['firstname'];
    $lastname = $row['lastname'];
    $email = $row['email'];
    $phone = $row['phone'];
    $birthdate = $row['birthdate'];
    $gender = $row['gender'];
    $bio = $row['bio']; 
    $balance = $row['balance'];
    $popularity = $row['popularity'];
  }
  if(isset($_POST["change"]))
  {
    $id = $_SESSION['user_id'];
    $firstname  = $_POST["firstname"];
    $lastname  = $_POST["lastname"];
      $email     = $_POST["email"];
      $phone  = $_POST["phone"];
      //$birthdate  = trim($_POST["birthdate"]);
      $gender  = $_POST["gender"];
      $bio  = $_POST["bio"];
    $password   = password_hash($_POST["password"], PASSWORD_DEFAULT);
    $connect->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $sql = "UPDATE login SET password='$password', email='$email', firstname ='$firstname', lastname ='$lastname', phone ='$phone', bio = '$bio', gender='$gender' WHERE user_id='$id'";
    $stmt = $connect->prepare($sql);
    if ($stmt->execute()) {
      $message_general = "<div class='alert alert-success'><strong>Your data was succesfully updated !</strong></div>";
    }
    else {
      $message_general = "<div class='alert alert-danger'><strong>There is an error ! Try later Or <a href='report'>Report Here</a></strong></div>";
    }
  }
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/bootstrap-4/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome-4.7.0/css/font-awesome.min.css">
    <title>Settings</title>
    <link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
    <style>
    * {
        scroll-behavior:smooth;
    }
    </style>
</head>
<body style="background-color:#e6e6e6">
<script>document.addEventListener('contextmenu', event => event.preventDefault());
</script>
    <div class="container-fluid">
       <div class="row">
       <div style="background-color:#b5b5b5;padding-bottom: 18px;" class="col-3 px-1 bg-dark position-fixed" id="sticky-sidebar">
       
       <div class="nav flex-column flex-nowrap vh-100 overflow-auto text-white p-2">
       <center><h2>Settings</h2></center>
       <a href="#general" class="btn btn-primary btn-lg btn-block"><strong><i class="fa fa-cog"></i> General</strong></a>
       <a href="#rules" class="btn btn-primary btn-lg btn-block"><strong><i class="fa fa-gavel"></i> Rules</strong></a>
       <a href="#privacypolicy" class="btn btn-primary btn-lg btn-block"><strong><i class="fa fa-file"></i> Privacy Policy</strong></a>
       <a href="#terms" class="btn btn-primary btn-lg btn-block"><strong><i class="fa fa-file-word-o"></i> Terms & Conditions</strong></a>
       <a href="#report" class="btn btn-danger btn-lg btn-block"><strong><i class="fa fa-warning"></i> Report</strong></a>

       <br><br><br><br><br><br><br><br>
       </div> </div>
       <div class="col-sm-9 offset-3" id="main">
       
       <br>
         <div class="alert alert-primary">
         <center><h1>Settings</h1></center>
         </div>
         <div class="alert alert-light" id="general">
        <h1><span class="badge badge-primary">General Settings</span></h1>
       
       <div class="alert alert-primary">
       <form method="POST" enctype="multipart/form-data">
            <h3>Firstname</h3>
          <input type="text" class="form-control" name="firstname" value="<?php echo $firstname; ?>" id="">
          <h3>Lastname</h3>
          <input type="text" class="form-control" name="lastname" value="<?php echo $lastname; ?>" id="">
          <h3>Email</h3>
          <input type="email" class="form-control" name="email" value="<?php echo $email; ?>" id="">
         
        <h3>Phone</h3>
          <input type="phone" class="form-control" name="phone" value="<?php echo $phone; ?>" id="">
          <div class="form-group">
            <label for="sel1"><h3>Gender</h3></label>
              <select class="form-control" value="<?php echo $gender; ?>" name="gender">
             <option>Male</option>
            <option>Female</option>
          </select>
         </div>
         <h3>Bio</h3>
          <input type="text" class="form-control" name="bio" value="<?php echo $bio; ?>" id="">

        <h3>Password</h3>
          <input required type="password" name="password" class="form-control">
          <br>
          <button type="reset" class="btn btn-primary btn-lg">Reset Fields</button> <button type="submit" name="change" class="btn btn-primary btn-lg">Change</button>
<br>
        </form>
       </div>
       </div>
       <!--<div class="alert alert-light" id="points">
        <h1><span class="badge badge-primary">Points</span></h1>
        <?php echo $message_general; ?>
       <div class="alert alert-primary">
       <h2>Points:</h2>
      <h1><i class="fa fa-circle"></i> <?php echo $balance ?></h1>
      <h2>Popularity: </h2>
      <h1><i class="fa fa-star"></i> <?php echo $popularity ?></h1>
       </div>
       </div>-->
       <div class="alert alert-light" id="rules">
        <h1><span class="badge badge-primary">Rules</span></h1>
        <?php echo $message_general; ?>
       <div class="alert alert-primary">
       <ul>
               <h2>

               <li><strong class="badge badge-danger">No Spam: </strong> Spamming will result to a permanent Ban !</li>
               <li><strong class="badge badge-danger">No Hate: </strong> We are here to make relationships, new friends, contacts and have fun !</li>
               <li><strong class="badge badge-danger">No bad words: </strong> Bad words, posts, actions will result to a permanent ban !</li>
               <li><strong class="badge badge-danger">No Traffic: </strong> Any traffic will be detected and followed by the actionner and will result to a criminal punishement!</li>
               <li><strong class="badge badge-danger">Be Friendly: </strong> Be friendly with others to be loved </li>
               <li><strong class="badge badge-danger">No Fake Accounts: </strong> Any fake account and accounts without identity will be already reported and banned !</li>
               <li><strong class="badge badge-danger">No Fake Informations: </strong> Any fake account / information will be already deleted !</li>
               <li><strong class="badge badge-danger">Find & be Found: </strong> Any user on this platform will found random persons and random persons will found him and anyone will be automaticly recommended for others ! (No one is choosen)</li>
               <li><strong class="badge badge-danger">Reports: </strong> Reporting bugs / problems / fake accounts / illegal actions is a responsibility for all the platform users so to enjoy a friendly environnemnt, every user must report every bug / problem / fake account / illegal action / bad words... By submitting the message <a href="report.php">Here</a></li>
             </h2>
           </ul>
       </div>
       </div>
       <div class="alert alert-light" id="privacypolicy">
        <h1><span class="badge badge-primary">Privacy Policy</span></h1>
       <div class="alert alert-primary">
       </center>
<h3>Aziz Becha & Rayen Mark built the Moon Meet app & site as a Free app & Site. This SERVICE is provided by Rayen Mark & Aziz Becha at no cost and is intended for use as is.</h3>
<h3>This page is used to inform visitors regarding my policies with the collection, use, and disclosure of Personal Information if anyone decided to use my Service.</h3>
<h3>If you choose to use my Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that you add here are not used to anything like tracking or these stuffs. I will not use or share your information with anyone except as described in this Privacy Policy.</h3>
<h3>The terms used in this Privacy Policy have the same meanings as in our <a href="terms.php">Terms and Conditions</a>, which is accessible at Moon Meet unless otherwise defined in this Privacy Policy.</h3>
<h2><strong>Information Collection and Use:</strong></h2>  
<h3>For a better experience, while using our Service, I may require you to provide us with certain personally identifiable information, including but not limited to We only collect username, firstname, lastname, or any public information. The information that I request will be retained on your device and is not collected by me in any way.</h3>
<h3>The app / site does use third party services that may collect information used to identify you.</h3>
<h2><strong>Reference to third party service used by the app / site are below: </strong></h2>
<ul>
<h3> <strong> - Firebase.   Google Play Services.</strong></h3>
<h3><strong> - MySQL</strong></h3></ul>
<h2><strong>Log Data</strong></h2>
<h3>I want to inform you that whenever you use my Service, in a case of an error in the app I collect data and information (through third party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app / site when utilizing my Service, the time and date of your use of the Service, and other statistics.</h3>
<h2><strong>Cookies</strong></h2>    
<h3>Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device's internal memory.</h3>
<h3>This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.</h3>    
<h2><strong>Service Providers:</strong></h2>    
<h3>I may employ third-party companies and individuals due to the following reasons:</h3>
<ul>
<li><h3>
To facilitate our Service;To provide the Service on our behalf;To perform Service-related services; or To assist us in analyzing how our Service is used.
</h3></li>
<li>
<h3>I want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.
</h3>
</li>
</ul>
<h2><strong>Security:</strong></h2>
<h3>I value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and I cannot guarantee its absolute security.</h3>
<h2><strong>Children’s Privacy:</strong></h2>   
<h3>These Services do not address anyone under the age of 13. I do not knowingly collect personally identifiable information from children under 13. In a case I discover that a child under 13 has provided me with personal information, I immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact me so that I will be able to take necessary actions.
</h3> 
<h2><strong>Changes to This Privacy Policy:</strong></h2>
<h3>I may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new Privacy Policy on this page.</h3>
  <h3>
This policy is effective as of 23-11-2020</h3>
<h2><strong>Contact Us:</strong></h2>
<h3>If you have any questions or suggestions about my Privacy Policy, do not hesitate to contact me at <a href="mailto:aziz07becha@gmail.com">aziz07becha@gmail.com</a> or <a href="mailto:Rayensbai2@gmail.com">Rayensbai2@gmail.com</a></h3>
<h3>Or report <a href="report.php">Here</a> or message <a href="message.php">here</a>.</h3>
       </div>
       </div>
       <div class="alert alert-light" id="terms">
        <h1><span class="badge badge-primary">Terms & Conditions</span></h1>
       <div class="alert alert-primary">
       <center><h2><strong>Terms & Conditions</strong></h2></center>
          <h3>By downloading or using the app, these terms will automatically apply to you – you should make sure therefore that you read them carefully before using the app. You’re not allowed to copy, or modify the app, any part of the app, or our trademarks in any way. You’re not allowed to attempt to extract the source code of the app, and you also shouldn’t try to translate the app into other languages, or make derivative versions. The app itself, and all the trade marks, copyright, database rights and other intellectual property rights related to it, still belong to Rayen Mark.
</h3>
<h3>Aziz Becha & Rayen Mark are committed to ensuring that the app / site are as useful and efficient as possible. For that reason, we reserve the right to make changes to the app / site or to charge for its services, at any time and for any reason. We will never charge you for the app / site or its services without making it very clear.</h3>
 <h3>The Moon Meet app / site stores and processes personal data that you have provided to us, in order to provide my Service. It’s your responsibility to keep your phone and access to the app secure. We therefore recommend that you do not jailbreak or root your phone, which is the process of removing software restrictions and limitations imposed by the official operating system of your device. It could make your phone vulnerable to malware/viruses/malicious programs, compromise your phone’s security features and it could mean that the miz app won’t work properly or at all.</h3>     
   <h3>You should be aware that there are certain things that Aziz Becha & Rayen Mark will not take responsibility for. Certain functions of the app will require the app to have an active internet connection. The connection can be Wi-Fi, or provided by your mobile network provider, but Aziz Becha & Rayen Mark cannot take responsibility for the app not working at full functionality if you don’t have access to Wi-Fi, and you don’t have any of your data allowance left.

If you’re using the app outside of an area with Wi-Fi, you should remember that your terms of the agreement with your mobile network provider will still apply. As a result, you may be charged by your mobile provider for the cost of data for the duration of the connection while accessing the app, or other third party charges. In using the app, you’re accepting responsibility for any such charges, including roaming data charges if you use the app outside of your home territory (i.e. region or country) without turning off data roaming. If you are not the bill payer for the device on which you’re using the app, please be aware that we assume that you have received permission from the bill payer for using the app.</h3>
   <h3>Along the same lines, Aziz Becha & Rayen Mark cannot always take responsibility for the way you use the app i.e. You need to make sure that your device stays charged – if it runs out of battery and you can’t turn it on to avail the Service, Rayen Mark cannot accept responsibility.
</h3>
<h3>With respect to Aziz Becha &  Rayen Mark’s responsibility for your use of the app / site, when you’re using the app, it’s important to bear in mind that although we endeavour to ensure that it is updated and correct at all times, we do rely on third parties to provide information to us so that we can make it available to you. Aziz Becha & Rayen Mark accepts no liability for any loss, direct or indirect, you experience as a result of relying wholly on this functionality of the app.
</h3>
<h3>At some point, we may wish to update the site / app. The app is currently available on Android – the requirements for system(and for any additional systems we decide to extend the availability of the app to) may change, and you’ll need to download the updates if you want to keep using the app. Aziz Becha & Rayen Mark does not promise that it will always update the app / site so that it is relevant to you and/or works with the Android version that you have installed on your device. However, you promise to always accept updates to the application when offered to you, We may also wish to stop providing the app, and may terminate use of it at any time without giving notice of termination to you. Unless we tell you otherwise, upon any termination, (a) the rights and licenses granted to you in these terms will end; (b) you must stop using the app, and (if needed) delete it from your device.</h3>
<h3>Reporting bugs / problems / unrespect of <a href="rules.php">Rules</a> is a responsibilyty for all th platform users because reporting these things will help very much to upgrade the platform and enjoy a friendly environnement.</h3>
<h2><strong>Changes to This Terms and Conditions:</strong></h2>
<h3>I may update our Terms and Conditions from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new Terms and Conditions on this page.
</h3>
<h3>These terms and conditions are effective as of 23-11-2020.</h3>

   <h3>Also you must read carefully the <a href="rules.php">Rules</a> And respect them to have a good environnement around you and other users.</h3>   
      <h3>Read The <strong>Privacy Policy <a href="privacypolicy.php">Here</a>.</strong></h3>

       </div>
       </div>
       
       <div class="alert alert-light" id="report">
        <h1><span class="badge badge-primary">Report</span></h1>
       <div class="alert alert-primary">
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
       <center><h3>© Moon Meet <script>document.write(new Date().getFullYear())</script></h3></center>
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