<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="action" value="${ForwardConst.ACT_TSK.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DELETE.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>EDIT COMPLETED TASK</h2>

        <form id="form" method="POST" action="<c:url value='?action=${action}&command=${commUpd}' />">
<label for="${AttributeConst.DIA_CONTENT.getValue()}">CONTENT</label><br />
<input type="text" name="${AttributeConst.TSK_CONTENT.getValue()}"value="${task.content}"/>
<br /><br />
<input type="hidden" name="${AttributeConst.TSK_ID.getValue()}" value="${task.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">SAVE <i class="fas fa-check"></i></button>
        </form>

        <p id="delete_task">
            <a href="#" id="destroy" onclick="confirmDestroy();">DELETE <i class="far fa-trash-alt"></i></a>
        </p>
        <form method="POST"
            action="<c:url value='?action=${action}&command=${commDel}' />">
            <input type="hidden" name="${AttributeConst.TSK_ID.getValue()}" value="${task.id}" />
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
        </form>

        <script>
            function confirmDestroy() {
                if (confirm("このタスクを削除しますか？")) {
                    document.forms[1].submit();
                }
            }
        </script>

    </c:param>
</c:import>