<?php 
include("database_connection2.php");
$story_query = "SELECT * FROM stories where posted_on >= DATE_SUB(NOW(),INTERVAL 24 HOUR) ORDER BY story_id DESC";
                      foreach($connect->query($story_query) as $row) {
                      echo '<li style="display: inline-block;margin-left:5px"><div style="cursor:pointer;border-radius:50%;width:50px;height:50px;border: #193566 solid 3px;margin-left:7px;"><img style="width:44px;height:43px;border-radius:50%" src="'.$row['image'].'" alt="">
                      <span style="font-size:10.8px;font-weight:bold;">'.substr($row["by_user"], 0, 6).'..</span></div></li>';
                        }
 ?>