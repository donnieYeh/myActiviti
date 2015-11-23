<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<img src="workFlow!showDiagram.do?procDefId=<s:property value='#procDefId'/>" style="position: absolute;">
	<div
		style="position: relative;
		border: 3px solid red;
		margin:0px;
		top:<s:property value='#taskCoordinate.y-3'/>px;
		left:<s:property value='#taskCoordinate.x-3'/>px ;
		width:<s:property value='#taskCoordinate.width'/>px ;
		height:<s:property value='#taskCoordinate.height'/>px ;
		border-radius:15px; 
		"
	></div>
</body>
</html>