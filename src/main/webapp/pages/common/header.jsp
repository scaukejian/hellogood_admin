<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}/"/>

<script type="text/javascript" src="<c:url value='/js/common/jquery-1.11.2.min.js'/>"></script>

<script type="text/javascript" src="<c:url value='/js/common/jquery.lazyload.min.js'/>"></script>
<script src="<c:url value='/js/common/jquery-latest.pack.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/common/jquery.prompt.js?v001'/>" type="text/javascript"></script>
<script src="<c:url value='/js/common/jquery-migrate-1.2.1.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/login/jquery.cookie.js'/>" type="text/javascript" ></script>

<script src="<c:url value='/js/koala/Koala.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/koala/Validate.js'/>" type="text/javascript"></script>

<!-- hellogood ui js -->
<script src="<c:url value='/js/ui/bootstrap-datepicker.js?v02'/>" type="text/javascript"></script>
<script src="<c:url value='/js/ui/bootstrap.min.js'/>" type="text/javascript"></script>

<!--[if lte IE 6]>
<script src="<c:url value='/js/ui/DD_belatedPNG.js'/>" type="text/javascript"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('div, ul, img, li, input , a , i, span');
    </script>
<![endif]-->
<script src="<c:url value='/js/ui/jquery.treeview.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/ui/main.js'/>" type="text/javascript"></script>
<!-- common css -->
<link href="<c:url value='/css/koala-common.css'/>" rel="stylesheet" type="text/css" />
<%--<link href="<c:url value='/css/DT_bootstrap.css'/>" rel="stylesheet" type="text/css" />--%>
<%--<link href="../../css/bootstrap.min.css" rel="stylesheet" type="text/css" />--%>
<link href="<c:url value='/css/Series.css?v003'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/Style.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/datepicker.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/css/font-icon.css'/>" rel="stylesheet" type="text/css" />

<link href="<c:url value='/css/auth-common.css'/>" rel="stylesheet" type="text/css" />

<script src="<c:url value='/js/common/common.js'/>" type="text/javascript"></script>
<!-- set namespace -->
<script src="<c:url value='/js/common/setns.js'/>" type="text/javascript"></script>
<!-- prompt -->

<script src="<c:url value='/js/common/grid.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/common/page.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/common/util.js?v007'/>" type="text/javascript"></script>
<script src="<c:url value='/js/common/msgBox.js?v002'/>" type="text/javascript"></script>

<script src="<c:url value='/js/common/jquery.poshytip.min.js'/>" type="text/javascript"></script>
<link href="<c:url value='/css/poshytip/tip-yellowsimple.css'/>" rel="stylesheet" type="text/css" />

<%-- <link href="/css/font-family-global.css" rel="stylesheet" type="text/css" />
 --%>
<!-- init env -->
<script type="text/javascript">
<!--
var rootPath = "${pageContext.request.contextPath}";
//-->
</script>
<script src="<c:url value='/js/common/jsInit.js'/>" type="text/javascript"></script>
<!-- js 加上时间戳 -->
<fmt:setBundle basename="systemConfig"/>
<fmt:message key="ts" var="ts"/>
