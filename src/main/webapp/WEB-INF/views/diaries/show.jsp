<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="actUsr" value="${ForwardConst.ACT_USR.getValue()}" />
<c:set var="actDia" value="${ForwardConst.ACT_DIA.getValue()}" />
<c:set var="actTsk" value="${ForwardConst.ACT_DIA.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>${month}</h2>
        <div id="history">
        <c:forEach var="date" items="${dates}" varStatus="status">
        <p class="space"></p>
        <details>
        <summary>
        <fmt:parseDate value="${date}" pattern="yyyy-MM-dd" var="createDate" type="date" />
    <span><fmt:formatDate value="${createDate}" pattern="MM-dd"/></span>

        </summary>

         <table class="diary_list">
            <tbody>
                <c:forEach var="diary" items="${diaries}" varStatus="status">


                    <c:if test="${fn:contains(diary.diaryDate,date) == true}">
                <tr>
                    <fmt:parseDate value="${diary.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <th class="time"><span><fmt:formatDate value="${createDay}" pattern="MM/dd HH:mm" /></span></th>

                    <th class="content"><pre><c:out value="${diary.content}" /></pre></th>
                    <th class="option"><a href="<c:url value='?action=${actDia}&command=${commEdt}&id=${diary.id}' />"><i class="far fa-edit"></i></a></th>
                </tr>
                </c:if>
                </c:forEach>
                        <c:forEach var="task" items="${tasks}" varStatus="status">
                            <c:if test="${fn:contains(task.finishedAt,date) == true}">

                                <tr>

                                    <th class="time">
                                     <fmt:parseDate value="${task.finishedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="finishDay" type="date" />
                                    <span> <fmt:formatDate value="${finishDay}" pattern="MM/dd HH:mm" /></span></th>

                                    <th class="content">${task.content}</th>
                                    <th class="option"><a href="<c:url value='?action=${actTsk}&command=${commEdt}&id=${task.id}' />"><i class="far fa-edit"></i></a></th>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>


        </details>
        </c:forEach>
        </div>
 </c:param>
</c:import>
