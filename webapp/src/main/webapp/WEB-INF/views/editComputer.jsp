<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> <spring:message code="label.home"></spring:message> </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computerId}
                    </div>
                    <h1><spring:message code="label.editComputer"></spring:message></h1>

                    <form action="edit" method="POST">
                        <input type="hidden" value="${computer.computerId}" id="computerId" name = "computerId"/> <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="label.computerName"></spring:message></label>
                                <input type="text" value="${computer.computerName}" class="form-control" id="computerName" placeholder="<spring:message code="label.computerName"></spring:message>" name="computerName">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="label.introducedDate"></spring:message></label>
                                <input type="date" value="${computer.introductionDate}" class="form-control" id="introduced" placeholder="dd-mm-yyyy" name="introduced">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="label.discontinuedDate"></spring:message></label>
                                <input type="date" value="${computer.discontinuedDate}" class="form-control" id="discontinued" placeholder="dd-mm-yyyy" name ="discontinued">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="label.company"></spring:message></label>
                                <select class="form-control" id="companyId" name="companyId">
                                    <c:forEach var="company" items="${listCompanies}">
                                		<option value="${company.id}">${company.name}</option>
									</c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value=<spring:message code = "label.edit"></spring:message> class="btn btn-primary">
                            or
                            <a href="/cdb/dashboard?page=1" class="btn btn-default"><spring:message code="label.cancel"></spring:message></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>