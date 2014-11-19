<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:url action="search" includeParams="none" id="url" />

<h2>
	<s:if test="#showLink != null">
		<a href="${url}?id=${an.id}">${an.category} - ${an.title}</a>
	</s:if>
	<s:else>${an.category} - ${an.title}</s:else>
	<br />
	<small style="font-size: 8pt;">${an.modificationDate.time}</small>
</h2>

<p>${an.description}</p>

<p>
	${an.location.address} <br />
	${an.location.city}
</p>
