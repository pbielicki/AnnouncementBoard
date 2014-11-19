<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<s:actionerror />
<table width="100%">
 <tr>
  <td valign="middle">
	<s:form action="create" method="post" enctype="multipart/form-data">
		<tr><td>
		<fieldset>
		<table width="100%">

		<s:textfield key="input.title" name="announcement.title" required="true" cssStyle="width: 100%;" labelposition="top" />

		<s:select key="input.category"
				headerKey="" headerValue="%{getText('input.select')}"
		       	name="announcement.category"
	      	   	list="categories"
		       	required="true"
		        labelposition="top"
		/>
	
		<s:textarea key="input.description" name="announcement.description" required="true" cols="80" rows="10"  labelposition="top" />
		<s:textfield key="input.city" name="announcement.location.city" required="true" maxLength="50" id="city"  labelposition="top" />
		<s:textfield key="input.address" name="announcement.location.address" maxLength="255" cssStyle="width: 100%;" id="address"  labelposition="top" />
		<s:textfield key="input.district" name="announcement.location.district" maxLength="100"  labelposition="top" />
		<s:textfield key="input.postalCode" name="announcement.location.postalCode" maxLength="10" id="postalCode"  labelposition="top" />

		<tr><td colspan="2">
			<a href="#" onclick="javascript:showAddress(); return false;"><s:text name="input.updateMapPosition" /></a>
		</td></tr>

		</table>
		</fieldset>
		</td></tr>

		<tr><td colspan="2">
			<fieldset>
				<s:text name="input.image.maxSize" />
				<table width="100%">
				<s:file key="input.image" name="upload0" />
				<s:file key="input.image" name="upload1" />
				<s:file key="input.image" name="upload2" />
				<s:file key="input.image" name="upload3" />
				<s:file key="input.image" name="upload4" />
				</table>
			</fieldset>
		</td></tr>

		<s:hidden name="announcement.location.latitude" id="latitude" />
		<s:hidden name="announcement.location.longitude" id="longitude" />
		<s:hidden name="announcement.location.countryCode" id="countryCode" />
		<s:hidden name="announcement.images" />
		<s:submit key="input.create" cssClass="submit" />

	</s:form>
  </td>
  <td style="width: 100%;" align="center" valign="top">
	<fieldset>
		<s:fielderror theme="bielu">
			<s:param>announcement.location.longitude</s:param>
		</s:fielderror>

		<br />
		<div id="mapDiv" style="width: 100%px; height: 400px; border-style: solid;">
		</div>
	</fieldset>
  </td>
 </tr>
</table>

<c:set var="gMapKey">ABQIAAAANI4esZ9gnHeEwt_lF9gwzRS-KPsi5zJk0vs6kHl1jWdGhvVmGhTDSLFGR_pA1zRQaATnn49B0p61HA</c:set> 
<c:set var="googleURL">http://www.google.com/jsapi</c:set>

<script type="text/javascript" src="${googleURL}?key=${gMapKey}"></script>
<script type="text/javascript" src="js/initForm.js"></script>