<?php
$server =  "localhost";
$username =  "id20965189_anhnq";
$password =  "Quocanh2h2@";
$database =  "id20965189_mob403_db";
$con = new mysqli($server, $username, $password, $database);
if ($con->connect_error) {
    die("Error: " . $con->connect_error);
}
$sql="UPDATE MyGuests SET lastname='Name Updated' WHERE id=184";
if($con->query($sql)===TRUE){
     echo "Update thanh cong";
}
else{
     echo "Loi: ".$con->error;
}
?>