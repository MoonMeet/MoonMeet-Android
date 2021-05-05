<?php

//check_login.php

if(isset($_POST["login"]))
{
 $connect = new PDO("mysql:host=localhost;dbname=moon-meet", "root", "");

 session_start();

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
				
				$_SESSION['user_id'] = $row['user_id'];
				$_SESSION['username'] = $row['username'];
				$sub_query = "INSERT INTO login_details (user_id) VALUES ('".$row['user_id']."')";
				$statement = $connect->prepare($sub_query);
				$statement->execute();
				$_SESSION['login_details_id'] = $connect->lastInsertId();
				header('location: home');
		   	
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