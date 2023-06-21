<?php 
include_once("../Koneksi.php");

$koneksi = new Koneksi();
if ($koneksi->getConn()->connect_error) {
    return ["result" => "error", "message" => "unable to connect"];
}

$username = $_POST['username'];
$first_name = $_POST['first_name'];
$last_name = $_POST['last_name'];
$password = $_POST['password'];

$sql = "INSERT INTO users (username, first_name, last_name, password) VALUES ('$username', '$first_name', '$last_name', '$password')";

if ($koneksi->query($sql) === TRUE) {
    echo json_encode(["status" => "OK", "msg" => "Successfully register user"]);
} else {
    $error = $koneksi->conn->error;
    echo json_encode(["status" => "ERROR", "msg" => "Error: $error, with syntax $sql"]);
}

// echo json_encode($result->fetch_assoc());

?>