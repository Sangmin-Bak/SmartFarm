<?php
	header('content-type: text/html; charset=utf-8');
	
	// 데이터베이스 접속
	$con = mysqli_connect("127.0.0.1", "root", "1q2w3e4r!@", "android_set");
//	mysqli_query("SET NAMES UTF8");

	$userID = $_POST['userID'];
	$password = $_POST['password'];

	$statement = mysqli_prepare($con, "SELECT * FROM USER WHERE USERID = ? AND PASSWORD = ?");
	mysqli_stmt_bind_param($statement, "ss", $userID, $password);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userID, $password, $name, $age);

	$response = array();
	$response["success"] = false;

	while(mysqli_stmt_fetch($statement)) {
		$response["success"] = true;
		$response["USERID"] = $userID;
		$response["PASSWORD"] = $password;
		$response["NAME"] = $name;
		$response["AGE"] = $age;
	}

	echo json_encode($response);

?>
