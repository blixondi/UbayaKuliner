<?php 
include_once("../Koneksi.php");

$koneksi = new Koneksi();
if ($koneksi->getConn()->connect_error) {
    return ["result" => "error", "message" => "unable to connect"];
}

$username = $_POST['username'];
$password = $_POST['password'];
// echo json_encode($username);
$sql = "select * from users where username='$username' and password='$password'";

$result = $koneksi->query($sql);
$user = $result->fetch_assoc();
if (sizeof($user) != 0){
    // $user['status'] = "OK";
    echo json_encode($user);
} else {
    // $error = $koneksi->conn->error;
    echo json_encode(["status" => "ERROR", "msg" => "Error: no user found"]);
}

// $result = $success->fetch_assoc();
// if ($status === TRUE) {
    // echo json_encode(["status" => "OK", "value" => $result]);
// } else {
    // $error = $koneksi->conn->error;
    // echo json_encode(["status" => "ERROR", "msg" => "Error: $error, with syntax $sql"]);
// }


?>