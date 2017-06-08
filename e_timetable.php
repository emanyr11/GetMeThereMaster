<?php
	$host= "localhost";
	$user = "id1523917_getmethere";
	$password = "getmethere";
	$db= "id1523917_getmethere";
	$sql = "SELECT * FROM e_timetable;";
	$conn = mysqli_connect($host, $user, $password, $db);
	
        $result = mysqli_query($conn,$sql);
		$response = array();
        while($row=mysqli_fetch_array($result))
	{
	array_push($response,array("Class Start"=>$row[1],"Class Finish"=>$row[2], "Location"=>$row[3],
        "Paper"=>$row[4]));
	}
	echo json_encode(array("server_response"=>$response));
	mysqli_close($conn);
?>