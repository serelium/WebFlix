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
    <script type="/resources/js/jquery.min.js"></script>
    <script type="/resources/js/moment.js"></script>
    <script type="/resources/js/combodate.min.js"></script>
<title>${movie.getTitle()}</title>
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
		<h3 style="color: white;">Number Of Movies With Filters:</h3>
		<form class="form-inline ml-auto" method="post" action="/count" style="display: inline;">
	    	<table>
			  <tr>
			    <td  align="left"><label style="color: white" for="ageGroup">Age Group:</label></th>
			    <td>
			    	<select class="form-control" id="ageGroup" name="ageGroup">
					  	<option selected value=''>-- Any Age Group --</option>
					    <option>18-20</option>
					    <option>20-25</option>
					    <option>25-30</option>
					    <option>30-35</option>
					    <option>35-40</option>
					    <option>40-45</option>
					    <option>45-50</option>
					    <option>55-60</option>
					    <option>65+</option>
				  	</select>
			  	</td> 
			  </tr>
			  <tr>
			    <td align="left"><label style="color: white" for="province">Province:</label></td>
			    <td> 
			    	<select class="form-control" id="province" name="province">
				  		<option selected value=''>-- Any Province --</option>
						<option value="AB">AB</option>
						<option value="BC">BC</option>
						<option value="MB">MB</option>
						<option value="NB">NB</option>
						<option value="NL">NL</option>
						<option value="NS">NS</option>
						<option value="ON">ON</option>
						<option value="PE">PE</option>
						<option value="QC">QC</option>
						<option value="SK">SK</option>
						<option value="NT">NT</option>
						<option value="NU">NU</option>
						<option value="YT">YT</option>
			  		</select>
			  		</td>
			  </tr>
			  <tr>
			    <td><label style="color: white" for="day">Day Of Week:</label></td>
			    <td>
				    <select class="form-control" id="day" name="day">
					  	<option selected value=''>-- Any Day --</option>
					    <option>Monday</option>
					    <option>Tuesday</option>
					    <option>Wednesday</option>
					    <option>Thursday</option>
					    <option>Friday</option>
					    <option>Saturday</option>
					    <option>Sunday</option>
				  	</select>
				</td>
			  </tr>
			  <tr>
			    <td><label style="color: white" for="month">Month:</label></td>
			    <td>
				    <select class="form-control" id="month" name="month">
						<option selected value=''>-- Any Month --</option>
					    <option value='1'>January</option>
					    <option value='2'>February</option>
					    <option value='3'>March</option>
					    <option value='4'>April</option>
					    <option value='5'>May</option>
					    <option value='6'>June</option>
					    <option value='7'>July</option>
					    <option value='8'>August</option>
					    <option value='9'>September</option>
					    <option value='10'>October</option>
					    <option value='11'>November</option>
					    <option value='12'>December</option>
				  	</select>
				</td>
			  </tr>
			</table>
			
			
			
			
	    	<!--  <div class="form-group">
			  <label style="color: white" for="sel1">Age Group:</label>
			  <select class="form-control" id="sel1">
			  	<option selected value=''>-- Any Age Group --</option>
			    <option>18=20</option>
			    <option>20-25</option>
			    <option>25-30</option>
			    <option>30-35</option>
			    <option>35-40</option>
			    <option>40-45</option>
			    <option>45-50</option>
			    <option>55-60</option>
			    <option>65+</option>
			  </select>
			</div>
			
	    	<div class="form-group">
			  <label style="color: white" for="sel1">Province:</label>
			  <select class="form-control" id="sel1">
			  	<option selected value=''>-- Any Province --</option>
				<option value="AB">AB</option>
				<option value="BC">BC</option>
				<option value="MB">MB</option>
				<option value="NB">NB</option>
				<option value="NL">NL</option>
				<option value="NS">NS</option>
				<option value="ON">ON</option>
				<option value="PE">PE</option>
				<option value="QC">QC</option>
				<option value="SK">SK</option>
				<option value="NT">NT</option>
				<option value="NU">NU</option>
				<option value="YT">YT</option>
			  </select>
			</div>
			
			<div class="form-group">
			  <label style="color: white" for="sel1">Day Of Week:</label>
			  <select class="form-control" id="sel1">
			  	<option selected value=''>-- Any Day --</option>
			    <option>Monday</option>
			    <option>Tuesday</option>
			    <option>Wednesday</option>
			    <option>Thursday</option>
			    <option>Friday</option>
			    <option>Saturday</option>
			    <option>Sunday</option>
			  </select>
			</div>
			
			<div class="form-group">
			  <label style="color: white" for="sel1">Month:</label>
			  <select class="form-control" id="sel1">
				<option selected value=''>-- Any Month --</option>
			    <option value='1'>January</option>
			    <option value='2'>February</option>
			    <option value='3'>March</option>
			    <option value='4'>April</option>
			    <option value='5'>May</option>
			    <option value='6'>June</option>
			    <option value='7'>July</option>
			    <option value='8'>August</option>
			    <option value='9'>September</option>
			    <option value='10'>October</option>
			    <option value='11'>November</option>
			    <option value='12'>December</option>
			  </select>
			</div> -->
			
			<br>
			<button type="submit" formaction="/count" class="btn btn-primary form-control">Find</button>
	     
	    </form>
	    
	</div>
	
</body>
</html>