<?php
$server =  "localhost";
$username =  "id20965189_anhnq";
$password =  "Quocanh2h2@";
$database =  "id20965189_mob403_db";
$con = new mysqli($server, $username, $password, $database);
if ($con->connect_error) {
     die("Error: " . $con->connect_error);
}
$sql = "SELECT id, firstname, lastname, email FROM MyGuests";
$result = $con->query($sql);
if ($result->num_rows > 0) //neu co du lieu
{
     while ($row = $result->fetch_assoc()) { //doc tung dong du lieu
          echo "id: " . $row["id"] . "- First: " . $row["firstname"]
               . "- Last: " . $row["lastname"] . "Email: " . $row["email"] . "</br>";
     }
} else {
     echo "Khong co du lieu ";
}
?>