<%--
  Created by IntelliJ IDEA.
  User: Miao
  Date: 2026/9/11
  Time: 08:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div style='text-align:center;'>
	<%-- 首页 --%>
	<a class='btn btn-info ${p.pageNumber == 1 ? "disabled" : ""}'
	   <c:if test="${p.pageNumber != 1}">href="${param.url}?pageNumber=1${param.param}"</c:if>
	   style="${p.pageNumber == 1 ? 'pointer-events: none; background-color: #eee; border-color: #ddd;' : ''}">首页</a>

	<%-- 上一页 --%>
	<a class='btn btn-info ${p.pageNumber == 1 ? "disabled" : ""}'
	   <c:if test="${p.pageNumber != 1}">href="${param.url}?pageNumber=${p.pageNumber - 1}${param.param}"</c:if>
	   style="${p.pageNumber == 1 ? 'pointer-events: none; background-color: #eee; border-color: #ddd;' : ''}">上一页</a>

	<h3 style='display:inline;'>[${p.pageNumber}/${p.totalPage}]</h3>
	<h3 style='display:inline;'>[${p.totalCount}]</h3>

	<%-- 下一页 --%>
	<a class='btn btn-info ${p.totalPage == 0 || p.pageNumber == p.totalPage ? "disabled" : ""}'
	   <c:if test="${p.pageNumber != p.totalPage}">href="${param.url}?pageNumber=${p.pageNumber + 1}${param.param}"</c:if>
	   style="${p.totalPage == 0 || p.pageNumber == p.totalPage ? 'pointer-events: none; background-color: #eee; border-color: #ddd;' : ''}">下一页</a>

	<%-- 尾页 --%>
	<a class='btn btn-info ${p.totalPage == 0 || p.pageNumber == p.totalPage ? "disabled" : ""}'
	   <c:if test="${p.pageNumber != p.totalPage}">href="${param.url}?pageNumber=${p.totalPage}${param.param}"</c:if>
	   style="${p.totalPage == 0 || p.pageNumber == p.totalPage ? 'pointer-events: none; background-color: #eee; border-color: #ddd;' : ''}">尾页</a>

	<input type='text' class='form-control' style='display:inline;width:60px;' value='' id="txtJump"/>
	<a class='btn btn-info' href='javascript:void(0);' onclick='go()'>GO</a>

	<script type="text/javascript">
		function go() {
			var pageNumber = $("#txtJump").val().trim()
			if (!isNaN(pageNumber) && pageNumber !== "") {
				pageNumber = parseInt(pageNumber)
				if (pageNumber < 1) {
					pageNumber = 1
				}
				if (pageNumber > ${p.totalPage}) {
					pageNumber = ${p.totalPage}
				}
				location.href = "${param.url}?pageNumber=" + pageNumber + "${param.param}"
			} else {
				alert("请输入正确的页码！")
			}
		}
	</script>
</div>