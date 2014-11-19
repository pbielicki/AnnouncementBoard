<?xml version="1.0" encoding="UTF-8" ?>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:include value="/head.jsp">
	<s:param name="title"><s:text name="page.signUp.title" /></s:param>
</s:include>

<s:include value="userForm.jsp" />

<s:include value="/foot.jsp" />