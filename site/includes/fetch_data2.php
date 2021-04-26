<?php

$username = $_SESSION['username'];
$connect = new PDO("mysql:host=localhost;dbname=moon-meet;charset=utf8mb4", "root", "");
foreach($connect->query("SELECT * FROM login WHERE username = '$username'") as $row) {
  echo " <h5><strong> Username: ".$row["username"]." <br><br> Firstname: " . $row["firstname"]. " <br><br> Lastname: " . $row["lastname"]. "<br><br> Email: " . $row["email"]. "<br><br> Phone: ". $row['phone']. "<br><br> Birth date: ". $row['birthdate']. "<br> <br>Gender: ". $row['gender']. "<br><br> Bio: ". $row['bio']. "</h5>";
}
foreach($connect->query("SELECT COUNT(*) FROM posts WHERE by_user = '$username'") as $row) {
  echo "My Total Posts: ",$row["COUNT(*)"], "</strong> ";
}
?>