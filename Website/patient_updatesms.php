<?php
if(empty($_GET[id])){
  echo "invalid input";
}
else {
  $con = mysql_connect("localhost","root","Conestoga1", "pInfo");
  if (!$con)
    {
    die('Could not connect: ' . mysql_error());
    }

  mysql_query("Use pInfo", $con);
  $sql = "UPDATE pLog SET bSentSMS = $_GET[sms] where id=$_GET[id]";

  if (!mysql_query($sql,$con))
    {
    die('Error: ' . mysql_error());
    }
  echo "1 record updated";
  mysql_close($con);
}
?>