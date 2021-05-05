<?php 
session_start(); 
$text = rand(100000,999999); 
$_SESSION["vercode"] = $text; 
$height = 42; 
$width = 100;   
$image_p = imagecreate($width, $height); 
$blue = imagecolorallocate($image_p, 17, 53, 102); 
$white = imagecolorallocate($image_p, 255, 255, 255); 
$font_size = 110; 
imagestring($image_p, $font_size, 10, 15, $text, $white); 
imagejpeg($image_p, null, 80); 
?>