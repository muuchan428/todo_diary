<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="actUsr" value="${ForwardConst.ACT_USR.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
          <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

        <h2>USER SETTING</h2>
            <p>
                <a href="<c:url value='?action=${actUsr}&command=${commEdt}'/>">EDIT</a>
            </p>
        <table>
            <tbody>
                <tr>
                    <th>NAME</th>
                    <td><c:out value="${user.name}"/></td>
                </tr>
                <tr>
                    <th>ID</th>
                    <td><c:out value="${user.userId}"/></td>
                </tr>
                <tr>
                    <th>最終更新</th>
                    <fmt:parseDate value="${user.updatedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </tbody>
        </table>

    </c:param>
</c:import>