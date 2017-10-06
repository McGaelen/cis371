<?php
$db = new mysqli('localhost', 'mcinteeg','mcinteeg','mcinteeg');
$query = 'SELECT * FROM friend';

if ($db->connect_error) {
    die('Can’t connect to db' . $db->connect_error);
}

$result = $db->query($query);
if ($db->error) {
    die($db->error);
}

printf('<table cellspacing="10"> <tr> <th>%s</th> <th>%s</th> <th>%s</th></tr>', 'Name', 'Phone', 'Age');
while ($row = $result->fetch_assoc()) {
    $phone = $row['phone'];
    if (strlen($phone) == 10) {
        $areacode = substr($phone, 0, 3);
        $secondpart = substr($phone, 3, 3);
        $thirdpart = substr($phone, 6, 4);
        $phone = '('.$areacode.') '.$secondpart.'-'.$thirdpart;
    } else {
        $secondpart = substr($phone, 0, 3);
        $thirdpart = substr($phone, 3, 4);
        $phone = $secondpart.'-'.$thirdpart;
    }
    printf('<tr> <td>%s</td> <td>%s</td> <td>%d</td></tr>', $row['name'], $phone, $row['age']);
}
echo '</table>';
?>