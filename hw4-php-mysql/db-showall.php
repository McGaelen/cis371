<?php
$db = new mysqli('localhost', 'mcinteeg','mcinteeg','mcinteeg');
$query = 'SELECT * FROM friend';

if ($db->connect_error) {
    die('Can’t connect to db' . $db->connect_error);
} else {
    echo '<p>Connected successfully.</p>';
}

$result = $db->query($query);
echo '<p>Rows fetched:</p>';
printf('%s %s %s <br />', 'Name', 'Phone', 'Age');
while ($row = $result->fetch_assoc()) {
    printf('%s %s %d <br />', $row['name'], $row['phone'], $row['age']);
}

?>