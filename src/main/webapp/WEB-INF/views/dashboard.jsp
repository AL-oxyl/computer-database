<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="label.home"></spring:message></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard?page=1"> <spring:message code="label.home"></spring:message> </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                 ${numberComputer} <spring:message code="label.computerFound"></spring:message><c:if test="${testNumber}">s</c:if> 
                 <spring:message code="label.found"></spring:message><c:if test="${testNumber}">s</c:if> 
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder=<spring:message code = "label.searchName"></spring:message> />
                        <input type="submit" id="searchsubmit" value=<spring:message code = "label.filterByName"></spring:message>
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="add"><spring:message code="label.addComputer"></spring:message></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="label.edit"></spring:message></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            <spring:message code="label.computerName"></spring:message>
                        </th>
                        <th>
                            <spring:message code="label.introducedDate"></spring:message>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <spring:message code="label.discontinuedDate"></spring:message>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <spring:message code="label.company"></spring:message>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                  	<c:forEach var ="computer" items="${computerList}"> 
                   		<tr>
                        	<td class="editMode"><input type="checkbox" name="cb" class="cb" value="${computer.computerId}"></td>
                        	<td><a href="/cdb/edit?id=${computer.computerId}" onclick="">${computer.computerName}</a></td>
                        	<td>${computer.introductionDate}</td>
                        	<td>${computer.discontinuedDate}</td>
                        	<td>${computer.manufacturer}</td>
                    	</tr>
                    </c:forEach>      
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
            	<c:if test="${testLeft}">
                	<li>
                    	<a href="dashboard?page=1" aria-label="Previous">
                      	<span aria-hidden="hidden">&laquo;</span>
                  		</a>
              		</li>
              	</c:if>	
              	<c:forEach var ="numberPage" items="${pages}"> 
              		<li><a href="dashboard?page=${numberPage}">${numberPage}</a></li>
              	</c:forEach>
              	<c:if test="${testRight}">
              		<li>
                		<a href="dashboard?page=${numberPage}" aria-label="Next">
                    		<span aria-hidden="true">&raquo;</span>
                		</a>
            		</li>
            	</c:if>	
        	</ul>
		</div>
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default" name="limit">10</button>
            <button type="button" class="btn btn-default" name="limit">50</button>
            <button type="button" class="btn btn-default" name="limit">100</button>
        </div>

    </footer>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/dashboard.js"></script>
</body>
</html>
