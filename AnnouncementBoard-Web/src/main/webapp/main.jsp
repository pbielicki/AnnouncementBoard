<?xml version="1.0" encoding="UTF-8" ?>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:include value="/head.jsp">
	<s:param name="title"><s:text name="page.index.title" /></s:param>
</s:include>

<s:actionerror />
<s:form action="doLogIn" namespace="/admin">
  <s:textfield theme="bielu" key="input.username" name="username" />
  <s:password key="input.password" name="password" />
  <s:submit key="input.logIn" cssClass="submit" />
</s:form>

<s:url id="signUp" action="initSignUp" namespace="/admin" />
<a href="${signUp}"><s:text name="input.signUp" /></a>

<s:include value="/foot.jsp" />