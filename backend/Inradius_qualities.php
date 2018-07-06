<?php
$connection = new mysqli("mysql.hostinger.in","u446913642_ecell","helloworld","u446913642_table");
if($connection->connect_error){
	die("Could not connect to database");
}

$table = 'Inradius_Qualities';
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

	$sql = "SELECT * FROM $table WHERE topic_id='$id'";
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
	$measure = $_POST['measure'];
	
	for ($i=0; $i < sizeof($measure); $i++) { 
		$sql = "INSERT INTO $table VALUES ('$id','$topic','$measure[$i]',0,0)";
		$result = $connection->query($sql);
	}
	
	if($result){
		$res['message'] = "Product added successfully!";
	}else{
		$res['error'] = true;
		$res['message'] = "Could not insert product";
	}
}

if ($action == 'update') {
	$id = $_POST['id'];
	$measure = $_POST['measure'];
	$points = $_POST['points'];

	for ($i=0; $i < sizeof($points); $i++) { 
		$sql = "UPDATE $table SET points = points + '$points[$i]', total = total + '5' WHERE topic_id = '$id' AND measure = '$measure[$i]'";
		$result = $connection->query($sql);
	}
	
	if($result){
		$res['message'] = "Product updated successfully!";
	}else{
		$res['error'] = true;
		$res['message'] = "Could not update product";
	}
}
/*
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