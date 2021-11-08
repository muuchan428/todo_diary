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
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>LOG IN</h2>
        <form method="POST" action="<c:url value='/?action=${action}&command=${command}' />">
            <label for="${AttributeConst.USR_USR_ID.getValue()}">ユーザーID</label><br />
            <input type="text" name="${AttributeConst.USR_USR_ID.getValue()}" value="${userId}" />
            <br /><br />

            <label for="${AttributeConst.USR_PASS.getValue()}">パスワード</label><br />
            <input type="password" name="${AttributeConst.USR_PASS.getValue()}" />
            <br /><br />

            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            <button type="submit">ログイン</button>
        </form>
        <p>―――――――――――――――――
    <div id="signIn">
        <h2><a href="<c:url value='?action=${actUsr}&command=${commNew}'/>">SIGN IN</a></h2>
        </div>
    </c:param>
</c:import>