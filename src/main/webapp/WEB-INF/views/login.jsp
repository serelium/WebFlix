<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="/resources/css/bootstrap.css">
	<link rel="icon" href="/resources/img/webflix-icon.png">
	<link rel="stylesheet" href="/resources/css/style.css">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>WebFlix</title>
</head>
<body>


	<div class="container fill">
	    <div class="row align-items-center h-100">
	        <div class="col-6 mx-auto">
	        <img src="/resources/img/webflix-logo-1.png" class="center">
	            <div class="jumbotron">
					<h3 class="text-center">Login</h3>
					<br>
					<font color="red">${errorMessage}</font>
					<br>
					<br>
					<form method="post">
					
						<div class="form-group">
							<input type="text" name="username" class="form-control" placeholder="Username">
						</div>
						
						<div class="form-group">
							<input type="password" name="password" class="form-control" placeholder="Password">
						</div>
						
						<button type="submit" class="btn btn-primary form-control">Login</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>