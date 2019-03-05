<?php	
	$data = json_decode(file_get_contents("php://input"));
	include('config.php');
	$db = new DB();
	$sql = "SELECT idapplication, app_name, cuid, email_distribution, logs_path, arima, description FROM `application` ORDER BY app_name";
	$data = $db->qryData($sql);
	echo json_encode($data);
?>