<?php 
if($_POST["action"] == "like")
 {
  $query = "
  SELECT * FROM likes 
  WHERE for_post = '".$_POST["post_id"]."' 
  AND by_user = '".$_SESSION["user_id"]."'
  ";
  $statement = $connect->prepare($query);
  $statement->execute();

  $total_row = $statement->rowCount();

  if($total_row > 0)
  {
   echo 'You have already like this post';
  }
  else
  {
   $insert_query = "
   INSERT INTO tbl_like 
   (user_id, post_id) 
   VALUES ('".$_SESSION["user_id"]."', '".$_POST["post_id"]."')
   ";

   $statement = $connect->prepare($insert_query);

   $statement->execute();

   $notification_query = "
   SELECT user_id, post_content FROM tbl_samples_post 
   WHERE post_id = '".$_POST["post_id"]."'
   ";

   $statement = $connect->prepare($notification_query);

   $statement->execute();

   $notification_result = $statement->fetchAll();

   foreach($notification_result as $notification_row)
   {
    $notification_text = '
    <b>' . Get_user_name($connect, $_SESSION["user_id"]) . '</b> has like your post - "'.strip_tags(substr($notification_row["post_content"], 0, 30)).'..."
    ';

    $insert_query = "
    INSERT INTO tbl_notification 
     (notification_receiver_id, notification_text, read_notification) 
     VALUES ('".$notification_row['user_id']."', '".$notification_text."', 'no')
    ";

    $statement = $connect->prepare($insert_query);
    $statement->execute();
   }

   echo 'Like';
  }

 }
?>