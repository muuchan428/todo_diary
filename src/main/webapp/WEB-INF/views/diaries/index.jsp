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

        <h2>DIARY</h2>
        <div id="dates_list">
        <c:forEach var="date" items="${dates}" varStatus="status">
        <a href="<c:url value='?action=${actDia}&command=${commShow}&date=${date}' />"><c:out value="${date}"/></a>
        <p class="space"></p>
        </c:forEach>
        </div>
            <div id="pager">
            （全 ${dates_count} 件）<br />
            <ul id="pagination">

                <c:forEach var="i" begin="1" end="${((dates_count - 1) / maxRow) + 1}" step="1">
                 <c:choose>
                    <c:when test="${i == page}">
                        <li><a class="active" href=""><span><c:out value="${i}" /></span></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<c:url value='?action=${actDia}&command=${commIdx}&page=${i}' />"><span><c:out value="${i}" /></span></a></li>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>

                </ul>
             </div>
 </c:param>
</c:import>
