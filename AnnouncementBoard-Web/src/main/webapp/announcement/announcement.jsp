<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<s:include value="/head.jsp">
	<s:param name="title">${announcement.category} - ${announcement.title} (${announcement.location.city})</s:param>
</s:include>

<c:set var="an" value="${announcement}" scope="request" />
<s:include value="announcementDetails.jsp" />

<s:include value="/foot.jsp" />
