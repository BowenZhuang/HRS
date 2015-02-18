<?php
if(empty($_GET[id])){
  echo "invalid input";
}
else {
  $con = mysql_connect("localhost","root","Conestoga1", "pInfo");
  if (!$con) {
    die('Could not connect: ' . mysql_error());
  }

  mysql_query("Use pInfo", $con);
  $sql = "DELETE FROM pLog where id=$_GET[id]";

  if (!mysql_query($sql,$con)) {
    die('Error: ' . mysql_error());
  }
  echo "1 record deleted";
  mysql_close($con);
}
?>