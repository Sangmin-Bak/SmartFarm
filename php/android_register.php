<?php
	header('content-type: application/json; charset=utf8');
	
	// 데이터베이스 접속
	$con = mysqli_connect("127.0.0.1", "root", "1q2w3e4r!@", "android_set");
//	mysqli_query("SET NAMES UTF8");

	$userID = $_POST['userID'];
	$password = $_POST['password'];
	$name = $_POST['name'];
	$age = $_POST['age'];

	mysqli_query($con, "INSERT INTO USER(USERID, PASSWORD, NAME, AGE) VALUES ('$userID', '$password', '$name', '$age')");
	
	mysqli_close($con);
?>
