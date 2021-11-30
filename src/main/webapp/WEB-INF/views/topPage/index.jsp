<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actDia" value="${ForwardConst.ACT_DIA.getValue()}" />
<c:set var="actTsk" value="${ForwardConst.ACT_TSK.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">

        <h2>${login_user.name}'s page</h2>
        <div id="data">
        <span>Number of all</span>
        <div id="data_count">
            <div id="diary_count">

                <span>${diaries_count}</span><br/>
                <span class="count_title">Diaries</span>
            </div>
            <div id="task_count">
                <span>${tasks_count}</span><br/>
                <span class="count_title">Tasks</span>
            </div>
        </div>
        </div>
        <div id="today_data">
            <div id="today_diary">
            <span>Today's diary</span><br/>
                <c:choose>
                <c:when test="${fn:length(diaries) != 0}">
                     <table class="diary_list">
                        <tbody>
                            <c:forEach var="diary" items="${diaries}" varStatus="status">
                            <tr>
                                <fmt:parseDate value="${diary.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                                <th class="time"><span><fmt:formatDate value="${createDay}" pattern="HH:mm" /></span></th>
                                <th class="content"><pre><c:out value="${diary.content}" /></pre></th>
                                <th class="option"><a href="<c:url value='?action=${actDia}&command=${commEdt}&id=${diary.id}' />"><i class="far fa-edit"></i></a></th>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div id="no_diary">
                    <span>Nothing... Let's write a diary!</span>
                    <a href="<c:url value='?action=${actDia}&command=${commCrt}' />"><i class="fas fa-pen"></i></a>

                    </div>
                </c:otherwise>
                </c:choose>
            </div>
            <div id="today_task">
            <span>Today's completed task</span>
                <c:choose>
                   <c:when test="${fn:length(tasks) != 0}">
                   <table class="task_list">
                   <tbody>
                      <c:forEach var="task" items="${tasks}" varStatus="status">
                        <tr>
                            <th class="time">
                             <fmt:parseDate value="${task.finishedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="finishDay" type="date" />
                            <span> <fmt:formatDate value="${finishDay}" pattern="HH:mm" /></span></th>
                            <th class="content">${task.content}</th>
                            <th class="option"><a href="<c:url value='?action=${actTsk}&command=${commEdt}&id=${task.id}' />"><i class="far fa-edit"></i></a></th>
                        </tr>
                        </c:forEach>
                        </tbody>
                        </table>
                    </c:when>
                <c:otherwise>
                        <div id="no_task">
                            <span>Nothing... </span>
                        </div>
               </c:otherwise>
          </c:choose>
            </div>
        </div>
    </c:param>
</c:import>