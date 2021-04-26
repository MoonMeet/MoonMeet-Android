<?php  
 
//check_session.php
 
session_start();

if(isset($_SESSION["username"]))
{
 echo '0';
}
else
{
 echo '1';
}

?>
