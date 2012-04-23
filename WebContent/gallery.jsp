<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

	<title>PhotoSwipe</title>

	<meta name="author" content="Ste Brennan - Code Computerlove - http://www.codecomputerlove.com/" />

	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />

	<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

	<link href="${pageContext.request.contextPath}/gallery/gallery.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery/jquery.mobile-1.1.0.min.css" />
	<link href="${pageContext.request.contextPath}/css/main.css"rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.mobile-1.1.0.min.js"></script>
	<script type="text/javascript" src="gallery/gallery.js"></script>
	<script type="text/javascript" src="gallery/gallery.touch.js"></script>
	

	<script type="text/javascript">
	 $(document).bind("pageinit",function() { 
	        $('#galleryDiv').swipeGallery({autoHeight: true});  

	     });

	
	</script>

</head>

<body>




<div data-role="page" data-add-back-btn="true" id="Gallery1" class="gallery-page">

	<div data-role="header">
	</div>

	<div data-role="content">	
		
		<ul class="gallery" id="galleryDiv">
		
			<li><img src="images/thumb/001.jpg" alt="Image 001" /></li>
			<li><img src="images/thumb/002.jpg" alt="Image 002" /></li>
			<li><img src="images/thumb/003.jpg" alt="Image 003" /></li>
			<li><img src="images/thumb/004.jpg" alt="Image 004" /></li>
			<li><img src="images/thumb/005.jpg" alt="Image 005" /></li>
			<li><img src="images/thumb/006.jpg" alt="Image 006" /></li>
			<li><img src="images/thumb/007.jpg" alt="Image 007" /></li>
			
		</ul>
		
	</div>
	
	<div data-role="footer">
	</div>
	
</div>



</body>

</html>


