<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actAuth" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commOut" value="${ForwardConst.CMD_LOGOUT.getValue()}" />
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
    <title><c:out value="todo diary" /></title>
    <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
    <link rel="stylesheet" href="<c:url value='/css/style.css?v=2' />">
</head>
<body>
   <div id="header">
    <div id="wrapper">

            <div id="header_logo">
                     <h1>TODO DIARY</h1>
            </div>

        <div id="header_menu">
            <details>
                <summary>
                    <span class="open">MENU</span>
                    <span class="close">CLOSE</span>
                </summary>
                <div id="menu_content">
                    <a href="<c:url value='?action=${actTop}&command=${commIdx}' />">HOME</a>
                    <li>NEW DIARY</li>
                    <li>CALENDAR</li>
                    <li>SETTING</li>
                   <a href="<c:url value='?action=${actAuth}&command=${commOut}' />">LOG OUT</a>
                </div>

            </details>

            </div>
        </div>
        <div id="content_parent">
        <div id="content">${param.content}</div>
        </div>
        <div id="footer"></div>
    </div>
</body>
</html>