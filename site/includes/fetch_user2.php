<?php

//fetch_user.php

include('database_connection2.php');

session_start();
$user = "aziz";
$query = "SELECT * FROM login WHERE username LIKE %a%";

$statement = $connect->prepare($query);

$statement->execute();

$result = $statement->fetchAll();

$output = '
<table style="width:100%" class="table table-bordered table-striped table-hover">
	<tr class="info">
		<th width="90%">User</td>
		<th width="5%">Status</td>
		<th width="5%">Action</td>
	</tr>
';

foreach($result as $row)
{
	$status = '';
	$current_timestamp = strtotime(date("Y-m-d H:i:s") . '- 10 second');
	$current_timestamp = date('Y-m-d H:i:s', $current_timestamp);
	$user_last_activity = fetch_user_last_activity($row['user_id'], $connect);
	if($user_last_activity > $current_timestamp)
	{
		$status = '<span class="btn btn-success">Online</span>';
		//label label-success
	}
	else
	{
		$status = '<span class="btn btn-danger">Offline</span>';
	}
	$output .= '
	<tr>
		<td>'.$row['username'].fetch_is_type_status($row['user_id'], $connect).'      '.count_unseen_message($row['user_id'], $_SESSION['user_id'], $connect).'<br> <strong> Bio: '.$row['bio'].'<br> <strong>Gender: '.$row['gender'].'</td> 
		<td>'.$status.'</td>
		<td><button type="button" style="margin-left:2px; margin-top: 0px;" class="btn btn-primary  start_chat" data-touserid="'.$row['user_id'].'" data-tousername="'.$row['username'].'">Start Chat</button></td>
	</tr>
	';
}

$output .= '</table>';

echo $output;

?>