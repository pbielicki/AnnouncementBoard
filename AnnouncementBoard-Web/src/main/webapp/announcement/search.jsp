<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<s:include value="/head.jsp">
	<s:param name="title">
		<s:if test="%{queryEmpty == true}">
			<s:text name="page.search.title.all">
				<s:param>${fn:length(announcementList)}</s:param>
			</s:text>
		</s:if>
		<s:else>
			<s:text name="page.search.title.query">
				<s:param>${query}</s:param>
				<s:param>${fn:length(announcementList)}</s:param>
			</s:text>
		</s:else>
	</s:param>
</s:include>

<hr />

<s:set name="showLink" value="true" />
<c:forEach var="announcement" items="${announcementList}">
	<c:set var="an" value="${announcement}" scope="request" />

	<s:include value="announcementDetails.jsp" />
	<hr />
</c:forEach>

<s:include value="/foot.jsp" />
