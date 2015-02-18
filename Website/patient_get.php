<?php
$con = mysql_connect("localhost","root","Conestoga1", "pInfo");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

mysql_query("Use pInfo", $con);

$result = mysql_query("SELECT * FROM pLog"); //where bCalled=0

echo "<table border='1'>
<tr>
<th>Id</th>
<th>name</th>
<th>dob</th>
<th>phone</th>
<th>bCalled</th>
<th>bSentSMS</th>
</tr>";

while($row = mysql_fetch_array($result))
  {
  echo "<tr>";
  echo "<td>" . $row['id'] . "</td>";
  echo "<td>" . $row['pname'] . "</td>";
  echo "<td>" . $row['dob'] . "</td>";
  echo "<td>" . $row['phone'] . "</td>";
  echo "<td>" . $row['bCalled'] . "</td>";
  echo "<td>" . $row['bSentSMS'] . "</td>";
  echo "</tr>";
  }
echo "</table>";
echo "<hr>";
echo "<strong><a href='insertTest.htm'>Back to Insert Page</a></strong>";
mysql_close($con);
?>