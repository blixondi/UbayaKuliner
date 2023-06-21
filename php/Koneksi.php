<?php
class Koneksi 
{
    public $conn;
    // public $error = $this->conn->error;
    public function __construct($ip = "localhost", $username = "kenserver", $password = "kenserverdb42", $database = "uas_anmp")
    // ini dirubah saja kalo pengen pake ip, username, password, dan database yang kamu sudah set kalo memang beda
    {
        
        $this->conn = new mysqli($ip, $username, $password, $database);
    }

    public function prepare($sql)
    {
        # code...
        return $this->conn->prepare($sql);
    }

    public function query($sql)
    {
        # code...
        return $this->conn->query($sql);
    }

    public function __destruct()
    {
        $this->conn->close();
    }

    public function close()
    {
        $this->conn->close();
    }

    /**
     * Get the value of conn
     */ 
    public function getConn()
    {
        return $this->conn;
    }


}
