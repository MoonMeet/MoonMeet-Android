<?php 
$id = $_SESSION['user_id'];
$query = $connect->query("SELECT * FROM login WHERE user_id = '$id'");
while($row = $query->fetch()) {
        echo $row['balance'];
        
        }

?>