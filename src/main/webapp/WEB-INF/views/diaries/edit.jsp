<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="action" value="${ForwardConst.ACT_DIA.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commDel" value="${ForwardConst.CMD_DESTROY.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>EDIT DIARY</h2>

        <form method="POST" action="<c:url value='?action=${action}&command=${commUpd}' />">
            <c:import url="_form.jsp" />
        </form>
        <p>
            <a href="#" id="destroy" onclick="confirmDestroy();">DELETE</a>
        </p>
        <form method="POST"
            action="<c:url value='?action=${action}&command=${commDel}' />">
            <input type="hidden" name="${AttributeConst.DIA_ID.getValue()}" value="${diary.id}" />
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
        </form>

        <script>
            function confirmDestroy() {
                if (confirm("この日記を削除しますか？")) {
                    document.forms[1].submit();
                }
            }
        </script>

    </c:param>
</c:import>