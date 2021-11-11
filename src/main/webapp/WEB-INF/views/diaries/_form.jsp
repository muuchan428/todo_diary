<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<fmt:parseDate value="${diary.diaryDate}" pattern="yyyy-MM-dd" var="diaryDay" type="date" />
<label for="${AttributeConst.DIA_DATE.getValue()}">Date</label><br />
<input type="date" name="${AttributeConst.DIA_DATE.getValue()}" value="<fmt:formatDate value='${diaryDay}' pattern='yyyy-MM-dd' />" />
<br /><br />
<label for="${AttributeConst.DIA_CONTENT.getValue()}">CONTENT</label><br />
<textarea name="${AttributeConst.DIA_CONTENT.getValue()}" rows="10" cols="50">${diary.content}</textarea>
<br /><br />
<input type="hidden" name="${AttributeConst.DIA_ID.getValue()}" value="${diary.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">保存</button>