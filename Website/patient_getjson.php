<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

$con = mysql_connect("localhost","root","Conestoga1", "pInfo");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_query("Use pInfo", $con);

$result = mysql_query("SELECT * FROM pLog where bCalled=0 LIMIT 3"); 
$loop=0;
$outp = '[';
while($row = mysql_fetch_array($result)) {
    if ($loop > 0) {$outp .= ",";}
    $outp .= '{"id":"'      . $row["id"]      . '",';
    $outp .= '"pname":"'    . $row["pname"]   . '",';
    $outp .= '"dob":"'      . $row["dob"]     . '",';
    $outp .= '"phone":"'    . $row["phone"]   . '",';
    $outp .= '"bCalled":"'  . $row["bCalled"] . '",';
    $outp .= '"bSentSMS":"' . $row["bSentSMS"]. '"}';
    $loop = $loop + 1;
    if($loop >= 20) break;
}
$outp .="]";

mysql_close($con);

echo($outp);
mysql_close($con);
?>