<?php
	// 데이터베이스 접속
	$con = mysqli_connect("127.0.0.1", "root", "1q2w3e4r!@", "android_set");

	$result = mysqli_query($con, "SELECT * FROM USER");
	$response = array();

	while($row = mysqli_fetch_array($result)) {
		array_push($response, array("userName"=>$row[2]));
//		array_push($response, array("userID"=>$row[0], "userPassword"=>$row[1], "userName"=>$row[2], "userAge"=>$row[3]));
	}

	header('Content-type: application/json; charset=utf8'); 
//	echo json_encode(array($response));
	echo json_encode(array("response"=>$response), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
	mysqli_close($con);
?>
