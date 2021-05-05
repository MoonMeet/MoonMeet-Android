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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/bootstrap-3/bootstrap.css">
    <link rel="stylesheet" href="css/font-awesome-4.7.0/css/font-awesome.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400&display=swap" rel="stylesheet">
<link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
        <link rel="stylesheet" href="css/style.css">
    <title>Login to Moon Meet</title>
    <style>
    body {
        color: #999;
		background: #f5f5f5;
	}
	.form-control {
		box-shadow: none;
		border-color: #ddd;
	}
	.form-control:focus {
		border-color: #163566; 
	}
	.login-form {
        width: 350px;
		margin: 0 auto;
		padding: 30px 0;
        margin-top:3%
	}
    .login-form form {
        color: #434343;
		border-radius: 1px;
    	margin-bottom: 15px;
        background: #fff;
		border: 1px solid #0000;
        box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.3);
        padding: 30px;
	}
	.login-form h4 {
		text-align: center;
		font-size: 22px;
        margin-bottom: 20px;
	}
    .login-form .avatar img {
        
		margin: 0 auto 30px;
        justify-content: center;
		width: 170px;
		height: 170px;
		border-radius: 50%;
		z-index: 9;
		padding: 15px;
		box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);
	}
    .login-form .avatar i {
        font-size: 62px;
    }
    .login-form .form-group {
        margin-bottom: 20px;
    }
	.login-form .form-control, .login-form .btn {
		min-height: 40px;
		border-radius: 2px; 
        transition: all 0.5s;
	}
	.login-form .close {
        position: absolute;
		top: 15px;
		right: 15px;
	}
	.login-form .btn {
		
		border: none;
		line-height: normal;
	}
	.login-form .btn:hover, .login-form .btn:focus {
		/*background: #42ae68;*/
	}
    .login-form .checkbox-inline {
        float: left;
    }
    .login-form input[type="checkbox"] {
        margin-top: 2px;
    }
    .login-form .forgot-link {
        float: right;
    }
    .login-form .small {
        font-size: 13px;
    }
    .login-form a {
        color: #163566;
    }
</style>
</head>
<body>
<div class="login-form"><br>
    <form method="post">
    <?php echo $message; ?>
		<div class="avatar"><img class="img img-responsive" src="images/logo.png" alt=""></div>
    	<h4 class="modal-title">Login to Moon Meet</h4>
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Username, E-mail or Phone" name="username" required="required">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" placeholder="Password" name="password" required="required">
        </div>
		<div class="form-group" style="margin-right:40px">
            <div class="col-sm-9" style="margin-left:-15px">
			   <input type="text" class="form-control" placeholder="Verification Code" name="vercode" required="required">
			</div>
			<div class="col-sm-3">
              <img src="captcha.php" alt="" srcset="">		
			</div>
        </div><br><br><br>
		<button type="submit" name="login" class="btn btn-primary btn-block btn-lg"><i class="fa fa-sign-in"></i> Login</button>
                     
    </form>			
    <div class="text-center small">Don&apos;t have an account ?<a href="register.php"> Create an account Here.</a></div>
</div>
</body>
</html>

