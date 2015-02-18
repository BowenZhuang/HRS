<?php

if(empty($_POST[name]) || empty($_POST[dob]) || empty($_POST[phone])){
  echo "invalid input";
}
else {
  $con = mysql_connect("localhost","root","Conestoga1", "pInfo");
  if (!$con) {
    die('Could not connect: ' . mysql_error());
  }

  mysql_query("Use pInfo", $con);

  $sql="INSERT INTO pLog (pname, dob, phone, bCalled, bSentSMS)
  VALUES ('$_POST[name]','$_POST[dob]','$_POST[phone]',0,0)";

  if (!mysql_query($sql,$con)) {
    die('Error: ' . mysql_error());
  }

  mysql_close($con);

  echo "1 record added";
  echo "<hr>";
  echo "<strong><a href='add.html'>Back to Add Page</a></strong>";
}
?>