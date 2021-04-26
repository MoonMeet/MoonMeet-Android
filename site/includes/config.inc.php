<?php

    $dbServerName = "localhost";
    $dbUsername = "root";
    $dbPassword = "";
    $databaseName = "moon-meet";

    $db = mysqli_connect($dbServerName, $dbUsername, $dbPassword, $databaseName);

    if (!$db) {
        die("Connection Failed ! " . mysqli_connect_errno());
    }
?>