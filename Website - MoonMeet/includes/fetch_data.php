<?php
$username = $_SESSION['username'];
$connect = new PDO("mysql:host=localhost;dbname=moon-meet;charset=utf8mb4", "root", "");

foreach($connect->query("SELECT username, firstname, lastname, email, phone, birthdate, gender, bio FROM login WHERE username = '$username'") as $row) {
  echo "<h4>Username: ".$row["username"]." <br> Firstname: " . $row["firstname"]. " <br> Lastname: " . $row["lastname"]. "<br> Email: " . $row["email"]. "<br> Phone: ". $row['phone']. "<br> Birth date: ". $row['birthdate']. "<br> Gender: ". $row['gender']. "<br> Bio: ". $row['bio']." </h4>";  
}

?>