<?php

include('database_connection2.php');

session_start();
$chat_message = trim(filter_var($_POST["chat_message"],FILTER_SANITIZE_SPECIAL_CHARS));
str_replace($chat_message,"'","&apos;");
str_replace($chat_message,'"',"&quot;");
$data = array(
	':to_user_id'		=>	$_POST['to_user_id'],
	':from_user_id'		=>	$_SESSION['user_id'],
	':chat_message'		=>	$chat_message,
	':status'			=>	'1'
);

$query = "
INSERT INTO chat_message 
(to_user_id, from_user_id, chat_message, status) 
VALUES (:to_user_id, :from_user_id, :chat_message, :status)
";

$statement = $connect->prepare($query);

if($statement->execute($data))
{
	echo fetch_user_chat_history($_SESSION['user_id'], $_POST['to_user_id'], $connect);
}

?>