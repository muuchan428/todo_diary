<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actUsr" value="${ForwardConst.ACT_USR.getValue()}" />
<c:set var="actDia" value="${ForwardConst.ACT_DIA.getValue()}" />
<c:set var="actAuth" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commOut" value="${ForwardConst.CMD_LOGOUT.getValue()}" />
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
    <title><c:out value="todo diary" /></title>
    <link rel="stylesheet" href="<c:url value='https://use.fontawesome.com/releases/v5.6.1/css/all.css'/>" >
    <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
    <link rel="stylesheet" href="<c:url value='/css/style.css?' />">
</head>
<body>
   <div id="header">
    <div id="wrapper">

            <div id="header_logo">

                     <h1>
                        <c:choose>
                            <c:when test="${sessionScope.login_user != null}"><a href="<c:url value='?action=${actTop}&command=${commIdx}' />">TODO DIARY</a></c:when>
                            <c:otherwise><a href="<c:url value='?action=${actAuth}&command=${commIdx}' />">TODO DIARY</a></c:otherwise>
                     </c:choose>
                     </h1>
            </div>

        </div>
       </div>

        <div id="content_parent">

    <div id="todo">
        <c:if test="${sessionScope.login_user != null}">
            <div id="addTodo">
                <p>
                    <input type="text"  placeholder="Input new task!" id="content" name="${AttributeConst.TSK_CONTENT.getValue()}">
                </p>
                <button type="button" id="send_addTodo"><i class="fas fa-plus"></i></button>
            </div>
            <div id="todo_flash"></div>
            <div id="todo_list">
                <ul id="todos"></ul>
            </div>
        </c:if>
    </div>


        <div id="contents">
            <div id="flash">
            <c:if test="${flash != null}">
                <div id="flash_success">
                    <c:out value="${flash}"></c:out>
                </div>
            </c:if>
            </div>
            ${param.content}
        </div>

   <c:if test="${sessionScope.login_user != null}">
        <div id="menu">


                <div id="menu_content">
                <span>MENU</span>
                <ul>
                    <li><a href="<c:url value='?action=${actTop}&command=${commIdx}' />"><i class="fas fa-home"></i>HOME</a></li>
                    <li><a href="<c:url value='?action=${actDia}&command=${commCrt}' />"><i class="fas fa-pen"></i>WRITE DIARY</a></li>
                    <li><a href="<c:url value='?action=${actDia}&command=${commIdx}' />"><i class="fas fa-book"></i>HISTORY</a></li>
                    <li><a href="<c:url value='?action=${actUsr}&command=${commShow}' />"><i class="fas fa-user-cog"></i>SETTING</a></li>
                  <li> <a href="<c:url value='?action=${actAuth}&command=${commOut}' />"><i class="fas fa-sign-out-alt"></i>SIGN OUT</a></li>
            </ul>
                </div>


            </div>
            </c:if>

    </div>
   <div id="footer"></div>

<script type="text/javascript" src=<c:url value='/js/todo_list.js' />></script>
</body>
</html>