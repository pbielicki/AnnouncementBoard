<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<jsp:include page="head.jsp">
	<jsp:param name="title" value="Search" />
</jsp:include>

<s:form action="rss" method="get">
	<s:textfield label="Search" name="query" />
	<s:submit cssClass="submit" />
</s:form>

<s:include value="/foot.jsp" />