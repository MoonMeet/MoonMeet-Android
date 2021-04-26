<?php

//fetch_user.php

include('database_connection2.php');

session_start();

$query = "
SELECT * FROM login
WHERE user_id != '".$_SESSION['user_id']."' 
ORDER BY user_id DESC";

$statement = $connect->prepare($query);

$statement->execute();

$result = $statement->fetchAll();

$output = '
<table style="width:100%;background-color:#eceff4;" class="table table-bordered table-striped table-hover">
	<br>
';

foreach($result as $row)
{
	$profile_pic = $row["profile_image"];
	if ($profile_pic == "") {
		$profile_pic = "images/user_image.png";

	}
	$status = '';
	$status2 = '';
	$current_timestamp = strtotime(date("Y-m-d H:i:s") . '- 10 second');
	$current_timestamp = date('Y-m-d H:i:s', $current_timestamp);
	$user_last_activity = fetch_user_last_activity($row['user_id'], $connect);
	if($user_last_activity > $current_timestamp)
	{
		$status = '<span class="btn btn-success"><strong>Online</strong></span>';
		$status2 = '<i style="color:green;margin-top:25px;margin-left:-14px;position:absolute;" class="fa fa-circle"></i>';
	}
	else
	{
		$status = '<span class="btn btn-danger"><strong>Offline</strong></span>';
		$status2 = '<i style="color:#F00004;margin-top:25px;margin-left:-14px;position:absolute;" class="fa fa-circle"></i>';
	}
	$output .= '
	<tr>
		<td><img style="width:35px;height:35px;border-radius:50%;margin-right:3px;" src="'.$profile_pic.'">'.$status2.'<strong>'.$row['username'].fetch_is_type_status($row['user_id'], $connect).'      '.count_unseen_message($row['user_id'], $_SESSION['user_id'], $connect).'</strong><br> <strong> Bio: '.$row['bio'].'<br> <strong>Last Online: '.time_ago($user_last_activity).'</td> 
		<td>'.$status.'</td>
		<td><button type="button" style="margin-left:2px; margin-top: 0px;" onclick="" class="btn btn-primary  start_chat" data-touserid="'.$row['user_id'].'" data-tousername="'.$row['username'].'"><i class="fa fa-comment"> </i> <strong> Start Chat</strong> </button></td>
	</tr>
	';
}

$output .= '</table>';

echo $output;

?>