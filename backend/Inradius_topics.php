<?php
$connection = new mysqli("mysql.hostinger.in","u446913642_ecell","helloworld","u446913642_table");
if($connection->connect_error){
	die("Could not connect to database");
}

$table = 'Inradius_Topics';
$action = 'read';

if (isset($_GET['action'])) {
	$action = $_GET['action'];
}

if ($action == 'read') {
	$sql = "SELECT * FROM $table";
	$result = $connection->query($sql);
	$report = array();

	while ($row = $result->fetch_assoc()) {
		array_push($report, $row);
	}

	$res['report'] = $report;
}

if ($action == 'fetch') {
	$id = $_GET['id'];

	$sql = "SELECT * FROM $table WHERE id='$id'";
	$result = $connection->query($sql);
	$report = array();

	while ($row = $result->fetch_assoc()) {
		array_push($report, $row);
	}

	$res['report'] = $report;
}

if ($action == 'create') {
	$id = $_POST['id'];
	$topic = $_POST['topic'];
	$desc = $_POST['desc'];
	$maker = $_POST['maker'];

	$sql = "INSERT INTO $table VALUES ('$id','$topic','$desc',1,1,'$maker')";
	$result = $connection->query($sql);
	
	if($result){
		$res['message'] = "Product added successfully!";
	}else{
		$res['error'] = true;
		$res['message'] = "Could not insert product";
	}
}
/*
if ($action == 'update') {
	$id = $_POST['id'];
	$item = $_POST['item'];
	$price = $_POST['price'];
	$quantity = $_POST['quantity'];
	$minimum = $_POST['minimum'];

	$sql = "UPDATE $table SET item = '$item', price = '$price', quantity = '$quantity', minimum = '$minimum' WHERE id = '$id'";
	$result = $connection->query($sql);
	
	if($result){
		$res['message'] = "Product updated successfully!";
	}else{
		$res['error'] = true;
		$res['message'] = "Could not update product";
	}
}

if ($action == 'delete') {
	$id = $_POST['id'];

	$sql = "DELETE FROM $table WHERE id = '$id'";
	$result = $connection->query($sql);
	
	if($result){
		$res['message'] = "Product deleted successfully!";
	}else{
		$res['error'] = true;
		$res['message'] = "Could not delete product";
	}
}*/

$connection->close();

header("Content-type: application/json");
echo json_encode($res);
die();
?>