<?php 
include("includes/database_connection2.php");
$cmd = "DELETE FROM pinned_posts WHERE posted_on<=DATE_SUB(NOW(), INTERVAL 1 DAY)";
$req = $connect->exec($q);
?>