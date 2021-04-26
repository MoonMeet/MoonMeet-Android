<?php 
include("database_connection2.php");
function fetch_user_typing_status($user_id, $connect)
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
			echo ' - <strong><em><span class="text-muted">  Typing...</span></em></strong>';
			$is_typing = '<strong><em><span class="text-muted">  Typing...</span></em></strong>';
		}
	}
	//return $output;
}

?>