<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body style="">
<div>
Album has been removed!
</div>

<div >

<form method="post" action="${pageContext.request.contextPath}/upload.do" enctype="multipart/form-data">
   <input type="text" name="name" />
   <input type="file" name="file1" />
   <input type="submit" />
  </form>
</div>
</body>
</html>