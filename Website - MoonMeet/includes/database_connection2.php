<?php

$connect = new PDO("mysql:host=localhost;dbname=moon-meet;charset=utf8mb4", "root", "");

date_default_timezone_set('Africa/Tunis');

function fetch_user_last_activity($user_id, $connect)
{
	$query = "SELECT * FROM login_details WHERE user_id = '$user_id' ORDER BY last_activity DESC LIMIT 1";
	$statement = $connect->prepare($query);
	$statement->execute();
	$result = $statement->fetchAll();
	foreach($result as $row)
	{
		return $row['last_activity'];
	}
}

function time_ago($timestamp){
  
    date_default_timezone_set("Africa/Tunis");         
    $time_ago        = strtotime($timestamp);
    $current_time    = time();
    $time_difference = $current_time - $time_ago;
    $seconds         = $time_difference;
    
    $minutes = round($seconds / 60); // value 60 is seconds  
    $hours   = round($seconds / 3600); //value 3600 is 60 minutes * 60 sec  
    $days    = round($seconds / 86400); //86400 = 24 * 60 * 60;  
    $weeks   = round($seconds / 604800); // 7*24*60*60;  
    $months  = round($seconds / 2629440); //((365+365+365+365+366)/5/12)*24*60*60  
    $years   = round($seconds / 31553280); //(365+365+365+365+366)/5 * 24 * 60 * 60
                  
    if ($seconds <= 60){
  
      return "Just Now.";
  
    } else if ($minutes <= 60){
  
      if ($minutes == 1){
  
        return "One minute ago.";
  
      } else {
  
        return "$minutes minutes ago.";
  
      }
  
    } else if ($hours <= 24){
  
      if ($hours == 1){
  
        return "One hour ago.";
  
      } else {
  
        return "$hours hours ago.";
  
      }
  
    } else if ($days <= 7){
  
      if ($days == 1){
  
        return "Yesterday.";
  
      } else {
  
        return "$days days ago.";
  
      }
  
    } else if ($weeks <= 4.3){
  
      if ($weeks == 1){
  
        return "A week ago.";
  
      } else {
  
        return "$weeks weeks ago.";
  
      }
  
    } else if ($months <= 12){
  
      if ($months == 1){
  
        return "One month ago.";
  
      } else {
  
        return "$months months ago.";
  
      }
  
    } else {
      
      if ($years == 1){
  
        return "One year ago.";
  
      } else {
  
        return "$years years ago.";
  
      }
    }
  }

  function fetch_user_chat_history($from_user_id, $to_user_id, $connect)
  {
	  $query = "SELECT * FROM chat_message 
	  WHERE (from_user_id = '".$from_user_id."' 
	  AND to_user_id = '".$to_user_id."') 
	  OR (from_user_id = '".$to_user_id."' 
	  AND to_user_id = '".$from_user_id."') 
	  ORDER BY timestamp DESC";
  
	  $q = "SELECT * FROM chat_message 
	  WHERE (from_user_id = '".$from_user_id."' 
	  AND to_user_id = '".$to_user_id."') 
	  OR (from_user_id = '".$to_user_id."' 
	  AND to_user_id = '".$from_user_id."')";
  
	  $statement = $connect->prepare($query);
	  $statement->execute();
	  $result = $statement->fetchAll();
	  $output = '<ul style="" class="list-unstyled">';
	  $stmt = $connect->prepare($q);
	  $stmt->execute();
	  $data = $stmt->fetchAll();
	  if (!$data) {
	  echo '<div class="alert alert-info" style=""><center><h3 style="color: #193566;"><strong>No Messages Yet ! <br> Start typing and your messages will show here.</h3><img src="images/logo" style="max-width:180px;"></strong></center></div>';
	  }
	  foreach($result as $row)
	  {
		  $user_name = '';
		  $dynamic_background = '';
		  $chat_message = '';
		  echo '<div id="typing_status"></div>';
		  if($row["from_user_id"] == $from_user_id)
		  {
			  if($row["status"] == '2')
			  {
				  $chat_message = '<b style="color:white">This message has been removed</b>';
				  $user_name = '<b style="color:gray" class="text-success">You</b>';
			  }
			  else
			  {
				  $chat_message = '<b style="color:white">'.$row['chat_message'].'</b>';
				  $user_name = '<div align="left"><button type="button" class="btn btn-danger btn-xs remove_chat" id="'.$row['chat_message_id'].'"><i class="fa fa-trash"></i></button>&nbsp;<b style="color:gray">You</b></div>';
			  }
			  
  //<b class="text-success">You</b>
			  $dynamic_background = 'background-color:#193566;border-top-left-radius: 18px ;border-top-right-radius: 18px; border-bottom-left-radius: 18px ; ';
		  }
		  else
		  {
			  if($row["status"] == '2')
			  {
				  $chat_message = '<b style="color:white">This message has been removed</b>';
			  }
			  else
			  {
				  $chat_message = $row["chat_message"];
			  }
			  $user_name = '<strong style="color:#193566; text-transform:capitalize; ">'.get_user_name($row['from_user_id'], $connect).'</strong>';
			  $dynamic_background = 'background-color:#8C92AC;border-top-left-radius: 18px ;border-top-right-radius: 18px; border-bottom-right-radius: 18px ; ';
		  }
		  $output .= '
		  <li style="border-bottom:1px dotted #ccc;padding-top:8px; padding-left:8px; margin-top:10px; padding-right:8px;'.$dynamic_background.'">
			  <p>'.$user_name.' <br>'.$chat_message.'<br>
				  <div align="right">
					  - <strong style="color:white">'.time_ago($row['timestamp']).'</strong>
				  </div>
			  </p>
		  </li>
		  ';
	  }
	  $output .= '</ul>';
	  $query = "UPDATE chat_message 
	  SET status = '0' 
	  WHERE from_user_id = '".$to_user_id."' 
	  AND to_user_id = '".$from_user_id."' 
	  AND status = '1'
	  ";
	  $statement = $connect->prepare($query);
	  $statement->execute();
	  return $output;
  }
function get_user_name($user_id, $connect)
{
	$query = "SELECT * FROM login WHERE user_id = '$user_id'";
	$statement = $connect->prepare($query);
	$statement->execute();
	$result = $statement->fetchAll();
	foreach($result as $row)
	{
		return $row['username'];
	}
}
function get_profile_image($user_id, $connect)
{
	$query = "SELECT * FROM login WHERE user_id = '$user_id'";
	$statement = $connect->prepare($query);
	$statement->execute();
	$result = $statement->fetchAll();
	foreach($result as $rw)
	{
		return $rw['profile_image'];
	}
}

function count_unseen_message($from_user_id, $to_user_id, $connect)
{
	$query = "
	SELECT * FROM chat_message 
	WHERE from_user_id = '$from_user_id' 
	AND to_user_id = '$to_user_id' 
	AND status = '1'
	";
	$statement = $connect->prepare($query);
	$statement->execute();
	$count = $statement->rowCount();
	$output = '';
	if($count > 0)
	{
		$output = '<span class="label label-success label-lg">'.$count.'</span>';
	}
	return $output;
}
$is_typing  = "";
function fetch_is_type_status($user_id, $connect)
{
	$query = "
	SELECT is_type FROM login_details 
	WHERE user_id = '".$user_id."' 
	ORDER BY last_activity DESC 
	LIMIT 1
	";	
	$statement = $connect->prepare($query);
	$statement->execute();
	$result = $statement->fetchAll();
	$output = '';
	foreach($result as $row)
	{
		if($row["is_type"] == 'yes')
		{
			$output = ' - <strong><em><span class="text-muted">  Typing...</span></em></strong>';
			$is_typing = '<strong><em><span class="text-muted">  Typing...</span></em></strong>';
		}
	}
	return $output;
}
////////////////////////////////////////////////////
function fetch_group_chat_history($connect)
{
	$query = "
	SELECT * FROM chat_message 
	WHERE to_user_id = '0'  
	ORDER BY timestamp DESC
	";

	$statement = $connect->prepare($query);

	$statement->execute();

	$result = $statement->fetchAll();

	$output = '<ul class="list-unstyled">';
	foreach($result as $row)
	{
		$user_name = '';
		$dynamic_background = '';
		$chat_message = '';
		if($row["from_user_id"] == $_SESSION["user_id"])
		{
			if($row["status"] == '2')
			{
				$chat_message = '<em>This message has been removed</em>';
				$user_name = '<b class="text-success">You</b>';
			}
			else
			{
				$chat_message = $row["chat_message"];
				$user_name = '<button type="button" class="btn btn-danger remove_chat" id="'.$row['chat_message_id'].'">x</button>&nbsp;<b class="text-success">You</b>';
			}
			
			$dynamic_background = 'background-color:#ffe6e6;';
		}
		else
		{
			if($row["status"] == '2')
			{
				$chat_message = '<em>This message has been removed</em>';
			}
			else
			{
				$chat_message = $row["chat_message"];
			}
			$user_name = '<b class="text-danger">'.get_user_name($row['from_user_id'], $connect).'</b>';
			$dynamic_background = 'background-color:#ffffe6;';
		}

		$output .= '

		<li style="border-bottom:1px dotted #ccc;padding-top:5px; padding-left:5px; padding-right:5px;'.$dynamic_background.'">
			<p>'.$user_name.' - '.$chat_message.' 
				<div align="right">
					- <small>'.time_ago($row['timestamp']).'</small>
				</div>
			</p>
		</li>
		';
	}
	$output .= '</ul>';
	return $output;
}


?>