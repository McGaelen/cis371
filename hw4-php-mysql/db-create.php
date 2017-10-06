<?php

$db = new mysqli('localhost', 'mcinteeg','mcinteeg','mcinteeg');
$createFriendTable = "CREATE TABLE friend (
id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, 
name VARCHAR(30) NOT NULL,
phone VARCHAR(30) NOT NULL,
age INT(3)
)";

if ($db->connect_error) {
    die('Can’t connect to db' . $db->connect_error);
} else {
    echo '<p>Connected successfully.</p>';
}

$db->query('DROP TABLE if EXISTS friend');

$db->query($createFriendTable);

echo '<p>Table created.</p>';

?>