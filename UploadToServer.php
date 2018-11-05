<?php
	$file_path = "/var/www/html/uploads/";
	$file_path = $file_path . basename( $_FILES['fileToUpload']['name']);
	if(move_uploaded_file($_FILES['fileToUpload']['tmp_name'], $file_path)) {
		echo "success";
	} else{
		echo "fail";
	}
?>
