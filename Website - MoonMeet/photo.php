<?php 

include("includes/database_connection2.php");
$sql = "SELECT * FROM login WHERE username='azizvirus'";
foreach($connect->query($sql) as $row) {
    $image = $row['profile_image'];
}

    ?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
<script>document.addEventListener('contextmenu', event => event.preventDefault());
</script>
<img src="<?php echo $image ?>" alt="" srcset="">
</body>
</html>
