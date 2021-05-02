<?php

include('includes/database_connection2.php');

session_start();

$message = '';

if(isset($_SESSION['user_id']))
{
	header('location:home');
}

if(isset($_POST["register"]))
{
	$firstname = trim(filter_var($_POST["firstname"],FILTER_SANITIZE_SPECIAL_CHARS));
	$lastname = trim(filter_var($_POST["lastname"],FILTER_SANITIZE_SPECIAL_CHARS));
	$email = trim(filter_var($_POST["email"],FILTER_SANITIZE_SPECIAL_CHARS));
	$phone = trim(filter_var($_POST["phone"],FILTER_SANITIZE_SPECIAL_CHARS));
	$birthdate = trim(filter_var($_POST["birthdate"],FILTER_SANITIZE_SPECIAL_CHARS));
	$gender = trim(filter_var($_POST["gender"],FILTER_SANITIZE_SPECIAL_CHARS));
	$bio = trim(filter_var($_POST["bio"],FILTER_SANITIZE_SPECIAL_CHARS));
	$username = trim(filter_var($_POST["username"],FILTER_SANITIZE_SPECIAL_CHARS));
	$password = trim(filter_var($_POST["password"],FILTER_SANITIZE_SPECIAL_CHARS));
	str_replace($firstname,"'","&apos;");
    str_replace($firstname, '"', "&quot;");
	//----------------------------------
	str_replace($lastname,"'","&apos;");
    str_replace($lastname, '"', "&quot;");
	//----------------------------------
	str_replace($email,"'","&apos;");
    str_replace($email, '"', "&quot;");
	//----------------------------------
	str_replace($phone,"'","&apos;");
    str_replace($phone, '"', "&quot;");
	//----------------------------------
	str_replace($birthdate,"'","&apos;");
    str_replace($birthdate, '"', "&quot;");
	//----------------------------------
	str_replace($gender,"'","&apos;");
    str_replace($gender, '"', "&quot;");
	//----------------------------------
	str_replace($bio,"'","&apos;");
    str_replace($bio, '"', "&quot;");
	//----------------------------------
	str_replace($username,"'","&apos;");
    str_replace($username, '"', "&quot;");
	//----------------------------------
	str_replace($password,"'","&apos;");
    str_replace($password, '"', "&quot;");
	//----------------------------------

	$check_query = "
	SELECT * FROM login
	WHERE username = :username
	";
	$statement = $connect->prepare($check_query);
	$check_data = array(
		':username'		=>	$username
	);
	if($statement->execute($check_data))
	{
		if($statement->rowCount() > 0)
		{
			$message .= "<div class='alert alert-danger'><label>Username already taken</label></div>";
		}
		else
		{
			if(empty($username))
			{
				$message .= "<div class='alert alert-danger'><label>Username is required</label></div>";
			}
			if(empty($password))
			{
				$message .= "<div class='alert alert-danger'><label>Password is required</label></div>";
			}
			else
			{
				if($password != $_POST['confirm_password'])
				{
					$message .= "<div class='alert alert-danger'><label>Password not match</label></div>";
				}
			}
			if($message == '')
			{
				$data = array(
                    ':lastname' 		=>	$lastname,
                    ':firstname'		=>	$firstname,
					':email'	    	=>	$email,
					':phone' 		    =>	$phone,
					':gender' 		    =>	$gender,
					':birthdate' 		=>	$birthdate,
					':bio' 		        =>	$bio,
                    ':username'		    =>	$username,
					':password'		    =>	password_hash($password, PASSWORD_DEFAULT)
				);

				$query = "
				INSERT INTO login
				(username, password, firstname, lastname, email, phone, gender, birthdate, bio)
				VALUES (:username, :password, :firstname, :lastname, :email, :phone, :gender, :birthdate, :bio)
				";
				$statement = $connect->prepare($query);
				if($statement->execute($data))
				{
					header("location: login.php");
				}
			}
		}
	}
}

?>

<html lang="en">
    <head>
        <title>Register On Moon Meet and Join Us</title>
		<meta property="og:image" content="images/logo.png">
     <meta property="og:description" content="Moon Meet is an instant messaging application that gives you a good acces to connect with friends, make relationships, share stories etc..">
     <meta property="og:title" content="Moon Meet">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <meta name="keywords" content="moonmeet, Moonmeet, Moon meet, moon meet, Moon Meet, social media, new era of messaging, aziz becha, Aziz becha">
     <meta name="author" content="Aziz Becha">
     <meta http-equiv="content-type" content="text/html; charset=utf-8">
     <meta http-equiv="X-UA-Compatible" content="IE=edge">
		<link href="css/bootstrap-4/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="js/bootstrap-4/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>document.addEventListener('contextmenu', event => event.preventDefault());
</script>
		  <link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
    </head>
    <body>

	<form method="post">
		<div class="container">
		<div class="card">
		<div class="card-body">
		<center><img src="images/logo.png" class="img-responsive" style="max-width:200px; margin-top:-50px;" alt="" srcset="">
		<h3>Moon Meet</h3></center><br>
		   <div class="form-content">
		   <span><?php echo $message; ?></span>
                    <div class="row">
                        <div class="col-md-6">

                            <div class="form-group">
							<label for="fname">First Name *</label>
                                <input type="text" name="firstname" class="form-control" placeholder="Firstname" value="" required>
                            </div>
                            <div class="form-group">
							<label for="lname">Last Name *</label>
                                <input type="text" name="lastname" class="form-control" placeholder="Lastname" value="" required>
                            </div>
							<div class="form-group">
							<label for="email">Email *</label>
                                <input type="email" name="email" class="form-control" placeholder="Email" value="" required>
                            </div>
							<div class="form-group">
							<label for="phone">Phone *</label>
                                <input type="number" name="phone" class="form-control" placeholder="Phone" value="" required>
                            </div>
                        </div>
                        <div class="col-md-6">
						<div class="form-group">
							<label for="birthdate">Birth date *</label>
                                <input type="date" name="birthdate" class="form-control" placeholder="Birth date" value="" required>
                            </div>
							<div class="form-group">
							<label for="username">Username *</label>
                                <input type="text" name="username" class="form-control" placeholder="Username" value="" required>
                            </div>
							<div class="form-group">
							<label for="username">Password *</label>
                                <input type="password" name="password" class="form-control" placeholder="Password" value="" required>
                            </div>
							<div class="form-group">
							<label for="password">Confirm password *</label>
                                <input type="password" name="confirm_password" class="form-control" placeholder="Confirm password" value="" required>
                            </div>
                        </div>
                     </div>

		   </div>
		   <div class="col-sm-12">
		   <div class="form-group">
            <label for="sel1">Gender: *</label>
              <select class="form-control" name="gender">
             <option>Male</option>
            <option>Female</option>
          </select>
         </div>
		 <div class="form-group">
							<label for="username">Bio *</label>

                                <input type="text" name="bio" class="form-control" placeholder="Tell us something about you" value="" required>
                            </div>
		 <div class="form-group">
		 <h6 class="alert alert-danger">By Clicking Here, You Accept Our <a href="terms.php">Terms & Conditions</a></h6>
							<input type="submit" name="register" class="btn btn-primary" value="Register" />
						</div>
		   </div>
		   </form>
		   </div></div>
      </body>
</html>

