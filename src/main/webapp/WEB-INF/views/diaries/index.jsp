<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="constants.ForwardConst" %>


<c:set var="actUsr" value="${ForwardConst.ACT_USR.getValue()}" />
<c:set var="actDia" value="${ForwardConst.ACT_DIA.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>DIARY</h2>
         <c:choose>
            <c:when test="${fn:length(months) != 0}">
                <div id="dates_list">

                    <c:forEach var="month" items="${months}" varStatus="status">
                        <a href="<c:url value='?action=${actDia}&command=${commShow}&date=${month}' />"><c:out value="${month}"/></a>
                        <p class="space"></p>
                    </c:forEach>
                </div>
                <div id="pager">
                    <ul id="pagination">

                        <c:forEach var="i" begin="1" end="${((months_count - 1) / maxRow) + 1}" step="1">
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
                </c:when>
                <c:otherwise>
                <div id="no_history">
                <span>No history</span>
                </div>
                </c:otherwise>
            </c:choose>


 </c:param>
</c:import>
