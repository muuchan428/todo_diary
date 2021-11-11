<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="actUsr" value="${ForwardConst.ACT_USR.getValue()}" />
<c:set var="actDia" value="${ForwardConst.ACT_DIA.getValue()}" />
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

        <h2>${date}</h2>
         <table id="diary_list">
            <tbody>
                <c:forEach var="diary" items="${diaries}" varStatus="status">
                <tr>
                <fmt:parseDate value="${diary.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <th class="time"><fmt:formatDate value="${createDay}" type="TIME" timeStyle="SHORT" /></th>

                    <th class="content"><pre><c:out value="${diary.content}" /></pre></th>
                    <th class="option"><a href="<c:url value='?action=${actDia}&command=${commEdt}&id=${diary.id}' />">EDIT</a></th>
                </tr>
                </c:forEach>
            </tbody>
        </table>

 </c:param>
</c:import>
