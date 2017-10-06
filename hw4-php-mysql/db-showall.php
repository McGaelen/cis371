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
printf('%20s %12s %4d\n', 'Name', 'Phone', 'Age');
while ($row = $result->fetch_assoc()) {
    printf('%20s %12s %4d\n', $row['name'], $row['phone'], $row['age']);
}

?>