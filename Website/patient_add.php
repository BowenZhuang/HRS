<?php
$con = mysql_connect("localhost","root","Conestoga1", "pInfo");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_query("Use pInfo", $con);

$sql="INSERT INTO pLog (pname, dob, phone, bCalled, bSentSMS)
VALUES
('$_POST[name]','$_POST[dob]','$_POST[phone]','false', 'false')";

if (!mysql_query($sql,$con))
  {
  die('Error: ' . mysql_error());
  }
echo "1 record added";
echo "<hr>";
echo "<strong><a href='add.htm'>Back to Add Page</a></strong>";

mysql_close($con);
?>