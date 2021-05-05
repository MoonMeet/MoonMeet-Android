<?php

//check_code.php

session_start();

$code = $_POST['code'];

if($code == $_SESSION['captcha_code'])
{
 echo 'success';
}

?>
