<?php
// Initialize the session
session_start();
 
// Unset all of the session variables
$_SESSION = array();
 
// Destroy the session.
session_destroy();
session_unset();
// Redirect to login page
header("location: ../login.php?loggedout=true");
exit;

?>