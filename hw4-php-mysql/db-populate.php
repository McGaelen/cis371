<?php
$db = new mysqli('localhost', 'mcinteeg','mcinteeg','mcinteeg');
$csvFile = $_FILES['csvFile'];

if ($csvFile['error'] !== 0) {
    die('error loading csv file.');
}

$uploadFolder = './uploads/' . $csvFile['name'];

if (is_uploaded_file($csvFile['tmp_name'])) {
    move_uploaded_file ($csvFile['tmp_name'], $uploadFolder);
}

$file = fopen($uploadFolder, 'r');
$counter = 0;
while ($row = fgetcsv($file)) {
    $db->query('INSERT INTO friend ( name, phone, age ) VALUES ( $row[0], $row[2], $row[3]);');
    if ($db->error) {
        die($db->error);
    }
    $counter++;
}

echo 'Records entered: ' . $counter;
?>