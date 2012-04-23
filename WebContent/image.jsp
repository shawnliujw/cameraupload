<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<!DOCTYPE html>

<html>

<head>

	<title>PhotoSwipe</title>

	<meta name="author" content="Ste Brennan - Code Computerlove - http://www.codecomputerlove.com/" />

	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />

	<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link href="${pageContext.request.contextPath}/swipe/styles.css" type="text/css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/swipe/photoswipe.css" type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/jquery/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/swipe/klass.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/swipe/code.photoswipe.jquery-3.0.4.min.js"></script>
	

	<script type="text/javascript">
	(function(window, $, PhotoSwipe){
		
		$(document).ready(function(){
			
			var options = {};
			$("#Gallery a").photoSwipe(options);
		
		});
		
		
	}(window, window.jQuery, window.Code.PhotoSwipe));
	
	</script>

</head>

<body>
<header style="text-align: center;">
<h1>Welcome to my photo album</h1>
</header>

<div id="MainContent">

	<div id="Gallery" class="gallery">
		
	 	<c:forEach items="${filePaths}" var="filePath" varStatus="status">
			<c:if test="${status.index%3 ==0}">
				<ul>
			</c:if>
			<li><a href="${filePath}"><img src="${filePath}" alt="Image ${status.index}" /></a></li>
				<c:if test="${status.index%3 ==2}">
				</ul>
			</c:if>
		</c:forEach>
		
		<%--
		 <li><a href="images/full/002.jpg"><img src="images/thumb/002.jpg" alt="Image 002" /></a></li>
		<li><a href="images/full/003.jpg"><img src="images/thumb/003.jpg" alt="Image 003" /></a></li>
		<li><a href="images/full/004.jpg"><img src="images/thumb/004.jpg" alt="Image 004" /></a></li>
		<li><a href="images/full/005.jpg"><img src="images/thumb/005.jpg" alt="Image 005" /></a></li>
		<li><a href="images/full/006.jpg"><img src="images/thumb/006.jpg" alt="Image 006" /></a></li>
		<li><a href="images/full/007.jpg"><img src="images/thumb/007.jpg" alt="Image 007" /></a></li>
		<li><a href="images/full/008.jpg"><img src="images/thumb/008.jpg" alt="Image 008" /></a></li>
		<li><a href="images/full/009.jpg"><img src="images/thumb/009.jpg" alt="Image 009" /></a></li>
		<li><a href="images/full/010.jpg"><img src="images/thumb/010.jpg" alt="Image 010" /></a></li>
		<li><a href="images/full/011.jpg"><img src="images/thumb/011.jpg" alt="Image 011" /></a></li>
		<li><a href="images/full/012.jpg"><img src="images/thumb/012.jpg" alt="Image 012" /></a></li>
		<li><a href="images/full/013.jpg"><img src="images/thumb/013.jpg" alt="Image 013" /></a></li>
		<li><a href="images/full/014.jpg"><img src="images/thumb/014.jpg" alt="Image 014" /></a></li>
		<li><a href="images/full/015.jpg"><img src="images/thumb/015.jpg" alt="Image 015" /></a></li>
		<li><a href="images/full/016.jpg"><img src="images/thumb/016.jpg" alt="Image 016" /></a></li>
		<li><a href="images/full/017.jpg"><img src="images/thumb/017.jpg" alt="Image 017" /></a></li>
		<li><a href="images/full/018.jpg"><img src="images/thumb/018.jpg" alt="Image 018" /></a></li>  --%>
		
	</div>
	
</div>	

	
<div id="Footer">
	Develop By Shawn  Mpupa
	
</div>

</body>

</html>


