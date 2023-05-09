<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">

<head>
	<jsp:include page="../common/head.jsp"></jsp:include>
	<style>
	.board-list .white-box {height: 200px}
	</style>
</head>

<body>
    <!-- ============================================================== -->
    <!-- Preloader - style you can find in spinners.css -->
    <!-- ============================================================== -->
    <div class="preloader">
        <div class="lds-ripple">
            <div class="lds-pos"></div>
            <div class="lds-pos"></div>
        </div>
    </div>
    <!-- ============================================================== -->
    <!-- Main wrapper - style you can find in pages.scss -->
    <!-- ============================================================== -->
    <div id="main-wrapper" data-layout="vertical" data-navbarbg="skin5" data-sidebartype="full"
        data-sidebar-position="absolute" data-header-position="absolute" data-boxed-layout="full">
        
		<jsp:include page="../common/header.jsp"></jsp:include>
        <!-- ============================================================== -->
        <!-- Left Sidebar - style you can find in sidebar.scss  -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- End Left Sidebar - style you can find in sidebar.scss  -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Page wrapper  -->
        <!-- ============================================================== -->
        <div class="page-wrapper">
            <!-- ============================================================== -->
            <!-- Container fluid  -->
            <!-- ============================================================== -->
            <div class="container p-3">
                <!-- ============================================================== -->
                <!-- RECENT SALES -->
                <!-- ============================================================== -->
                <div class="row board-list">   
                
                <c:forEach items="${boards }" var="board">
                    	<div class="col-12 col-sm-4 col-lg-3"> 
                    		<a href="view?bno=${board.bno}&${page.cri.fullQueryString}">
                        	<div class="card white-box p-0 pt-3 text-white"> 
                        		<img alt="" src="https://t1.daumcdn.net/daumtop_chanel/op/20200723055344399.png">
                        		<div class="d-flex justify-content-between align-items-center bg-secondary p-1">
									<h5 class="text-truncate">${board.title }</h5>
									<h5 class="small text-truncate">${board.writer }</h5>
								</div>
                        	</div>
                        	</a>
                    	</div>
				</c:forEach>
            
                </div>
			</div>
            <!-- ============================================================== -->
            <!-- End Container fluid  -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- footer -->
            <!-- ============================================================== -->
            
            <!-- ============================================================== -->
            <!-- End footer -->
            <!-- ============================================================== -->
        </div>
        <jsp:include page="../common/footer.jsp"></jsp:include>
        <!-- ============================================================== -->
        <!-- End Page wrapper  -->
        <!-- ============================================================== -->
    </div>
    <!-- ============================================================== -->
    <!-- End Wrapper -->
    <!-- ============================================================== -->
   
</body>

</html>