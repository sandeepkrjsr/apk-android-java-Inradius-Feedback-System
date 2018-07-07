<?php
$connection = new mysqli("mysql.hostinger.in","u446913642_ecell","helloworld","u446913642_table");
if($connection->connect_error){
	die("Could not connect to database");
}

$table = 'Inradius_Subscriptions';
$action = 'read';

if (isset($_GET['action'])) {
	$action = $_GET['action'];
}

if ($action == 'fetch') {
	$emp_id = $_GET['emp_id'];

	$sql = "SELECT t.* FROM Inradius_Topics t JOIN $table s ON t.id=s.topic_id WHERE s.emp_id='$emp_id'";
	//$sql = "SELECT * FROM Inradius_Topics t JOIN $table s ON t.id=s.topic_id WHERE s.emp_id='$emp_id'";
	$result = $connection->query($sql);
	$report = array();

	while ($row = $result->fetch_assoc()) {
		array_push($report, $row);
	}

	$res['report'] = $report;
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

if ($action == 'create') {
	$sub_id = $_POST['sub_id'];
	$emp_id = $_POST['emp_id'];
	$topic_id = $_POST['topic_id'];

	$sql = "INSERT INTO $table VALUES ('$sub_id','$emp_id','$topic_id')";
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