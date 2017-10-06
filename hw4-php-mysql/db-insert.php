<?php
$db = new mysqli('localhost', 'mcinteeg','mcinteeg','mcinteeg');

if ($db->connect_error) {
    die('Can’t connect to db' . $db->connect_error);
} else {
    echo '<p>Connected successfully.</p>';
}

$name = $_POST['friendName'];
$phone = $_POST['friendPhone'];
$age = $_POST['friendAge'];

$db->query("INSERT INTO friend ( name, phone, age ) VALUES ( '".$name."','".$phone."','".$age."' )");
if ($db->error) {
    die($db->error);
}

echo 'Friend added with following attributes: '.$name.' '.$phone.' '.$age;

?>