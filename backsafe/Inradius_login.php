<?php
	
	if($_SERVER['REQUEST_METHOD']=='GET'){

		$email = $_GET['email'];
		$pass = $_GET['pass'];

		$hostname = "mysql.hostinger.in";
		$username = "u446913642_ecell";
		$password = "helloworld";
		$database = "u446913642_table";

		$con = mysqli_connect($hostname,$username,$password,$database);
		$sql = "SELECT id,name,email,pass,level FROM Inradius_Employees WHERE email='".$email."' AND pass='".$pass."'";
		$r = mysqli_query($con,$sql);
		$res = mysqli_fetch_array($r);
		$result = array();

		array_push($result,array(
			"id"=>$res['id'],
			"name"=>$res['name'],
			"email"=>$res['email'],
			"pass"=>$res['pass'],
			"level"=>$res['level']
			)
		);

		echo json_encode(array("result"=>$result));
		mysqli_close($con);
	}
?>