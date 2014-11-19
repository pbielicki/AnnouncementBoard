<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:actionerror />
<s:form action="doSignUp" namespace="/admin"> 
	<s:textfield key="input.firstName" name="user.firstName" />
	<s:textfield key="input.lastName" name="user.lastName" />
	<s:textfield key="input.username" name="user.username" required="true" />
	<s:password key="input.password" name="password" required="true" />
	<s:password key="input.confirmPassword" name="confirmPassword" required="true" />
	<s:textfield key="input.email" name="user.email" required="true" />
	<s:textfield key="input.confirmEmail" name="confirmEmail" required="true" />
	<s:submit key="input.signUp" cssClass="submit" />
</s:form>
