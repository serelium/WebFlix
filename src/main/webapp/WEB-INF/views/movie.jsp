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
	
	
	<c:forEach items="${movie.getProductionCountries()}" var="country" varStatus="status">
		<c:choose>
			<c:when test="${status.first}">
				<c:set var = "countries" value = "${country.getName()}"/>
  			</c:when>
			<c:otherwise>
  				<c:set var = "countries" value = "${countries}, ${country.getName()}"/>
  			</c:otherwise>
		</c:choose>
	</c:forEach>
	
	<c:forEach items="${movie.getGenres()}" var="genre" varStatus="status">
		<c:choose>
			<c:when test="${status.first}">
				<c:set var = "genres" value = "${genre.getName()}"/>
  			</c:when>
			<c:otherwise>
  				<c:set var = "genres" value = "${genres}, ${genre.getName()}"/>
  			</c:otherwise>
		</c:choose>
	</c:forEach>
	
	<c:forEach items="${movie.getArtists()}" var="movieRole" varStatus="status">
		<c:choose>
			<c:when test="${status.first}">
				<c:if test="${movieRole.getType().getTypeName() ==  'Actor'}">
					<c:set var = "actors">
						<a href="/artist/${movieRole.getArtist().getId()}" style="color: #999999;">${movieRole.getArtist().getName()} (${movieRole.getCharacterName()})</a>
					</c:set>
				</c:if>
				<c:if test="${movieRole.getType().getTypeName() ==  'Director'}">
					<c:set var = "director">
						<a href="/artist/${movieRole.getArtist().getId()}" style="color: #999999;">${movieRole.getArtist().getName()}</a>
					</c:set>
				</c:if>
  			</c:when>
			<c:otherwise>
  				<c:if test="${movieRole.getType().getTypeName() ==  'Actor'}">
  					<c:set var = "actors">
  						${actors}, <a href="/artist/${movieRole.getArtist().getId()}" style="color: #999999;">${movieRole.getArtist().getName()} (${movieRole.getCharacterName()})</a>
  					</c:set>
				</c:if>
				<c:if test="${movieRole.getType().getTypeName() ==  'Director'}">
					<c:set var = "director">
						<a href="/artist/${movieRole.getArtist().getId()}" style="color: #999999;">${movieRole.getArtist().getName()}</a>
					</c:set>
				</c:if>
  			</c:otherwise>
		</c:choose>
	</c:forEach>
	
	<c:forEach items="${movie.getScriptwriters()}" var="scriptwriter" varStatus="status">
		<c:choose>
			<c:when test="${status.first}">
				<c:set var = "scriptwriters" value = "${scriptwriter.getName()}"/>
  			</c:when>
			<c:otherwise>
  				<c:set var = "scriptwriters" value = "${scriptwriters}, ${scriptwriter.getName()}"/>
  			</c:otherwise>
		</c:choose>
	</c:forEach>
	
	<c:forEach items="${suggestedMovies}" var="suggestedMovie" varStatus="status">
		<c:choose>
			<c:when test="${status.first}">
				<c:set var = "suggestedMovie" value = "${suggestedMovie.getTitle()}"/>
  			</c:when>
			<c:otherwise>
  				<c:set var = "scriptwritersStr" value = "${scriptwriters}, ${scriptwriter.getName()}"/>
  			</c:otherwise>
		</c:choose>
	</c:forEach>
	
	<div class="container fill mt-5">
		<h3 style="color: white;">${movie.getTitle()}</h3>
		<p style="color: white;">
			Year of release: <span style="color: #999999">${movie.getYearOfRelease()}</span><br>
			Production Countries: <span style="color: #999999">${countries}</span><br>
			Origin language: <span style="color: #999999">${movie.getOriginLanguage().getName()}</span><br>
			Length: <span style="color: #999999">${movie.getLength()} minutes</span><br>
			Genres: <span style="color: #999999">${genres}</span><br>
			Director: <span style="color: #999999">${director}</span><br>
			Cast: <span style="color: #999999">${actors}</span><br>
			Scriptwriters: <span style="color: #999999">${scriptwriters}</span><br>
			<br>
			Synopsis: <span style="color: #999999">${movie.getSynopsis()}</span>
		</p>
		 <form method="post" class="ml-0" style="width: 7%;">
			<button type="submit" class="btn btn-primary form-control">Rent</button>
	    </form>
	    <br>
	    <br>
	    <br>
	    <h3 style="color: white;">You Might Also Like</h3>
	    <p style="color: white;">
	    	<c:forEach items="${suggestedMovies}" var="suggestedMovie" varStatus="status">
	    		<a href="/movie/${suggestedMovie.getId()}" style="color: white;">${suggestedMovie.getTitle()} (${suggestedMovie.getYearOfRelease()})</a><br>
	    	</c:forEach>
	    </p>
	    
	</div>
	
</body>
</html>