<?php
include_once("includes/database_connection2.php");
session_start();

$message = '';

if(isset($_GET['accountDeleted']))
{
	if ($_GET["accountDeleted"] == "true") {
		$message = '<div class="alert alert-danger">Account Deleted !</div>';
	}
}
if(isset($_GET['loggedIn']))
{
	if ($_GET["loggedIn"] == "false") {
		$message = '<div class="alert alert-danger">You are not logged in !</div>';
	}
}

if(isset($_SESSION['user_id']))
{
	header('location:home');
}
if(isset($_POST['login']))
{

	$timestamp = date('d/m/Y h:i:s');
    $browser = $_SERVER['HTTP_USER_AGENT'];
	$logfile = 'admin/private/login-attempts.txt';
	$fp = fopen($logfile, 'a+');
	fwrite($fp, '['. $_POST["username"].'] Tried a Login Attempt At '. $timestamp .' By browser '.$browser." \n");
	fclose($fp);
	$query = "SELECT * FROM login WHERE username = :username OR email = :username OR phone = :username";
	$statement = $connect->prepare($query);
	$statement->execute(
		array(
			':username' => $_POST["username"]
		)
	);
	$count = $statement->rowCount();
	if($count > 0)
	{
		$result = $statement->fetchAll();
		foreach($result as $row)
		{
			if(password_verify($_POST["password"], $row["password"]))
			{
				if ($_POST["vercode"] != $_SESSION["vercode"] OR $_SESSION["vercode"]=='') {
					$message = '<div class="alert alert-danger"><strong>Error Verification Code !</strong></div>';
				}
				else {
				$_SESSION['user_id'] = $row['user_id'];
				$_SESSION['username'] = $row['username'];
				$sub_query = "INSERT INTO login_details (user_id) VALUES ('".$row['user_id']."')";
				$statement = $connect->prepare($sub_query);
				$statement->execute();
				$_SESSION['login_details_id'] = $connect->lastInsertId();
				header('location: home');
		   	}
        }
			else
			{
				$message = '<div class="alert alert-danger"><strong>Wrong Password</strong></div>';
			}
		}
	}
	elseif($count < 0)
	{
        $message = '<div class="alert alert-danger"><strong>Wrong Username</strong></div>';
    }
	else
	{
		$message = '<div class="alert alert-danger"><strong>Error ! Your inputs are not correct</strong></div>';
	}
}


?>
<!DOCTYPE html>
    <head>
        <title>Login to Moon Meet</title>
		<link rel="stylesheet" href="css/bootstrap-3/bootstrap.min.css">
		<link rel="stylesheet" href="css/buttons.css">
		<link rel="stylesheet" href="css/font-awesome-4.7.0/css/font-awesome.min.css">
		<meta property="og:image" content="images/logo.png">
     <meta property="og:description" content="Moon Meet is an instant messaging application that gives you a good acces to connect with friends, make relationships, share stories etc..">
     <meta property="og:title" content="Moon Meet">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <meta name="keywords" content="moonmeet, Moonmeet, Moon meet, moon meet, Moon Meet, social media, new era of messaging, aziz becha, Aziz becha">
     <meta name="author" content="Aziz Becha">
     <meta http-equiv="content-type" content="text/html; charset=utf-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
		<script>//document.addEventListener('contextmenu', event => event.preventDefault());</script>
		  <link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
    </head>
    <body>
        <div class="container">
			<center><img src="images/logo" class="img-responsive" style="max-width:200px" alt="" srcset=""></center>
			<div class="panel panel-primary">
  				<div class="panel-heading"><strong>Login to Moon Meet</strong></div>
				<div class="panel-body">
					<h4 align="center" class="text-danger"><?php echo $message; ?></h4>
	               <div class="demo-form">
				   <form method="post" enctype="multipart/form-data">
						<div class="form-group form-group-lg">
							<h4><strong>Enter Username, Email or Phone</strong></h4>
							<input autofocus type="text" name="username" class="form-control" placeholder="Enter Username, Email or Phone" required />
						</div>
						<div class="form-group form-group-lg">
						<h4><strong>Enter Password</strong></h4>
							<input type="password" name="password" placeholder="Enter Password" class="form-control" required/>
						</div>
						<div class="form-group">

            <h4><strong>Verification Code: </strong><img src="captcha.php" ></h4>

            <input type="text" name="vercode" class="form-control" placeholder="Verfication Code" required="required">
        </div>

						<div class="form-group">
							<button type="submit" name="login" data-sitekey="reCAPTCHA_site_key" data-callback='onSubmit' data-action='submit' class="btn btn-primary btn-lg btn-block g-recaptcha"><strong><i class="fa fa-sign-in"></i> Login</strong></button>
						</div>
					</form>
				   </div>
				   <h4>Forgot Your Password ? <a href="recover">Click Here</a></h4>
					<h4>Don't have an account ?</h4><a class="btn btn-primary btn" style="color:white" href="register"><strong>Register</strong></a>
				</div>
			</div>
		</div>
    </body>
</html>

