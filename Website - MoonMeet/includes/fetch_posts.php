<?php 
include("database_connection2.php");
              $posts_query = "SELECT * FROM posts ORDER BY post_id DESC LIMIT 60";
  foreach($connect->query($posts_query) as $row) {
    $post_content = $row['post'];
  echo "<div id='post' post_id='".$row['post_id']."' class='alert alert-info'><h4 style='font-family:sans-serif; font-size:23px'>
  <i data-toggle='tooltip' data-placement='left' title='".$row["by_user"] ."' style='font-size:30px' class='fa fa-user-circle'></i>
  <a data-toggle='tooltip' data-placement='top' title='".$row["by_user"] ." ' href='profile?user=" . $row["by_user"] . "'>". $row["by_user"]."</a>
  <i data-toggle='modal' data-target='#postOptions' class='fa fa-ellipsis-v' style='float:right;color:black;font-size:30px;cursor:pointer;padding-left:20px;padding-bottom:20px;'></i>
  <br><i style='margin-top:8px;' data-toggle='modal' data-target='#myModal' style='font-size:25px' class='fa fa-clock-o'></i>   ".time_ago($row['posted_on']) . "</h4>
  <div class='' style='background-color:'><h2 style='color:black'>" . $row['post'] ."</h2></div>
  <br>
  <button id='likebtn' disabled type='submit' class='btn btn-primary btn-block btn-lg'><i class='fa fa-heart'></i> Like</button>
  
  <br>
 

  </div>
  ";
}
/*

 <style>.blocks .btn-primary 
  {
      padding: 8px 72px;
      margin: 0 5px 5px;  
      border-radius: 15px;
  }</style>
  <div class='btn-group blocks' data-toggle='buttons'>
  <label class='btn btn-primary'>
  <i class='fa fa-heart'></i>
  </label>
  <label class='btn btn-primary'>
  <i class='fa fa-comment'></i>
  </label>
  <label class='btn btn-primary'>
  <i class='fa fa-share'></i>
  </label>
</div>

*/

 ?>