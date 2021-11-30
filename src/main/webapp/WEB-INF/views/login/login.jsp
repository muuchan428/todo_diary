<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="actUsr" value="${ForwardConst.ACT_USR.getValue()}" />
<c:set var="command" value="${ForwardConst.CMD_LOGIN.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${loginError != null}">
            <div id="flush_error">
              <c:out value="${loginError}"></c:out>
            </div>
        </c:if>
        <h2>SIGN IN</h2>
        <div id="signIn_form">
        <form id="form" method="POST" action="<c:url value='/?action=${action}&command=${command}' />">
            <label for="${AttributeConst.USR_USR_ID.getValue()}">ID</label><br />
            <input type="text" name="${AttributeConst.USR_USR_ID.getValue()}" value="${userId}" />
            <br /><br />

            <label for="${AttributeConst.USR_PASS.getValue()}">PASSWORD</label><br />
            <input type="password" name="${AttributeConst.USR_PASS.getValue()}" />
            <br /><br />

            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            <button type="submit">ログイン</button>
        </form>
        </div>
       <hr>
    <div id="signIn">
      <a href="<c:url value='?action=${actUsr}&command=${commNew}'/>">SIGN UP <i class="fas fa-user-plus"></i></a>
        </div>
    </c:param>
</c:import>