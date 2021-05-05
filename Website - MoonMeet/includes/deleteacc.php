<?php
session_start();
include('database_connection2.php');
//include('database_connection2.php');
if(!isset($_SESSION['username']))
{
  header("location: ../login.php");
  
}
if(!isset($_SESSION['user_id']))
{
  header("location: ../login.php");
  
}
$uid = $_SESSION["user_id"];
//DELETE FROM login WHERE user_id = '$uid'"
$query = "DELETE FROM login WHERE user_id = '$uid' ";
$statement = $connect->prepare($query);

$statement->execute();
session_destroy();
header("location: ../login.php?accountDeleted=true");
?>