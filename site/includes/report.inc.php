<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "moon-meet";
$name = trim(filter_var($_POST["name"],FILTER_SANITIZE_SPECIAL_CHARS));
$email = trim(filter_var($_POST["email"],FILTER_SANITIZE_SPECIAL_CHARS));
$message = trim(filter_var($_POST["message"],FILTER_SANITIZE_SPECIAL_CHARS));

// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);
// Check connection
if (!$conn) {
  die("Connection failed: " . mysqli_connect_error());
}
$sql = "INSERT INTO reports (name, email, message)
VALUES ('$name', '$email', '$message')";

if (mysqli_query($conn, $sql)) {
  header("location: ../report.php?report_sent=true");
} else {
  header("location: ../report.php?report_sent=false");
}
mysqli_close($conn);
?>
