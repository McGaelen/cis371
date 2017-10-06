<?php
$db = new mysqli('localhost', 'mcinteeg','mcinteeg','mcinteeg');
if ($db->connect_error) {
    die('Can’t connect to db' . $db->connect_error);
} else {
    echo '<p>Connected successfully.</p>';
}

$searchTerm = $_GET['friendName'];

$result = $db->query("SELECT * FROM friend WHERE name LIKE '%".$searchTerm."%'");
if ($db->error) {
    die($db->error);
}

echo '<p>Rows fetched:</p>';
printf('%s %s %s <br />', 'Name', 'Phone', 'Age');
while ($row = $result->fetch_assoc()) {
    printf('%s %s %d <br />', $row['name'], $row['phone'], $row['age']);
}

?>