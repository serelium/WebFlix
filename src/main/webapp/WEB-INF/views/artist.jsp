<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="/resources/css/bootstrap.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">
	<link rel="stylesheet" href="/resources/css/style.css">
	<link rel="icon" href="/resources/img/webflix-icon.png">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>${artist.getName()}</title>
</head>
<body>
	<nav class="navbar navbar-expand-lg lighten-2">
	
	  <img src="/resources/img/webflix-logo-1.png" class="left" height="10%" width="10%">
	  <!--<span class="navbar-toggler-icon"></span>-->
	  <span class="glyphicon glyphicon-search"></span>
	
	  <div class="collapse navbar-collapse" id="navbarSupportedContent">
	    <form class="form-inline ml-auto" action="/search">
	    
	      <div class="md-form my-0" style="border:1px solid white">
	      	<i class="fa fa-search ml-1" style="color:#FFF;"></i>
	        <input class="form-control mr-sm-2 transparent-input" type="text" name="searchQuery" placeholder="Search" style="color:white">
	      </div>
	     
	    </form>
	    
	    <form method="post" class="ml-2">
			<button type="submit" class="btn btn-primary form-control">Logout</button>
	    </form>
	  </div>
	</nav>
	
	<div class="container fill mt-5">	
	<h3 style="color: white;">${artist.getName()}</h3>
		<p style="color: white;">
			Birthday: <span style="color: #999999">${artist.getBirthDate()}</span><br>
			Place of birth: <span style="color: #999999"> ${artist.getBirthPlace()}</span><br>
			Biography: <span style="color: #999999">${artist.getBiography()}</span><br>
		</p>
	</div>
</body>
</html>