<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="../common/head.jsp"%>
</head>
<body>
    <div id="main-wrapper" data-layout="vertical" data-navbarbg="skin5" data-sidebartype="full"
        data-sidebar-position="absolute" data-header-position="absolute" data-boxed-layout="full">
	<%@ include file="../common/header.jsp"%>
	<main class="container p-3 pb-5">
		<div class="card">
			<div class="card-header p-5">
				<h2>Board View</h2>
			</div>
			<div class="card-body">
				<form method="post">
					<div class="mb-3 mt-3">
						<label for="title" class="form-label">title</label> <input
							type="text" class="form-control" id="title"
							placeholder="Enter title" name="title" readonly
							value="${board.title}">
					</div>
					<div class="mb-3">
						<label for="content" class="form-label">content</label>
						<textarea class="form-control" id="content"
							placeholder="Enter content" name="content" rows="10" readonly>${board.content}</textarea>
					</div>
					<div class="mb-3">
						<label for="writer" class="form-label">writer</label> <input
							type="text" class="form-control" id="writer"
							placeholder="Enter writer" name="writer" value="${board.writer}"
							readonly>
					</div>
					<div class="mb-3">
						<p class="form-label">files</p>
						<c:forEach items="${board.attachs}" var="attach">
						<p><a href="${pageContext.request.contextPath }/download?${attach.queryString}">${attach.origin}</a></p>					
						</c:forEach>
						
					</div>
					<div class="text-center">
						<a href="modify?bno=${board.bno}&${cri.fullQueryString}"
							class="btn btn-warning">수정</a> <a
							href="remove?bno=${board.bno}&${cri.fullQueryString}"
							class="btn btn-danger btn-remove">삭제</a> <a
							href="list?${cri.fullQueryString }"
							class="btn btn-outline-primary">목록으로</a>
					</div>
					<div class="mb-3" id="replyArea">
						<p class="form-label mb-4 border-bottom pb-3">
							<i class="far fa-comment-dots"></i>replies
						</p>
						<div class="px-5 row mb-5">
							<textarea class="form-control mb-2" id="commentArea"
								placeholder="Enter comments" name="comment" rows="3"></textarea>
							<button type="button" class="btn btn-primary opacity-75">댓글
								작성</button>
						</div>
						<ul class="container replies">
							
						</ul>
					</div>
				</form>
			</div>
		</div>
	</main>
	</div>
	<%@ include file="../common/footer.jsp"%>
	<script>
	$(".btn-remove").click(function(){
		return confirm("정말 삭제하시겠습니까?");
	})

	let contextPath = '${pageContext.request.contextPath}';
	let replyPath = contextPath + "/reply";
	let bno = '${board.bno}';
	let writer = '${member.id}';
	
	showList();
	function showList(){
	$.ajax({
		url: replyPath,
		data: {bno:bno},
		success: list 
	})
	}
	
	// 댓글 목록	
	function list(replies){
	  /* let jsonStr = '[{"rno":1,"content":"댓글댓글","regDate":"2023-03-13 00:00:00","writer":"id1","bno":1142},{"rno":2,"content":"댓글댓글","regDate":"2023-03-13 00:00:00","writer":"id1","bno":1142},{"rno":4,"content":"댓글댓글","regDate":"2023-03-13 00:00:00","writer":"id1","bno":1142}]';
	  let replies = JSON.parse(jsonStr); */
	  console.log(replies);
	  let str="";
	  if(!replies.length){
		  str = `<li class="list-unstyled px-4">
	          <div class="mb-4 small text-center">
	           <p>댓글이 없습니다</p>
	          </div>
      		</li>`;
      		$(".replies").html(str);
      		return;

	  }
	  for(i in replies){
	    let r = replies[i];
// 	    console.log(r.rno, r.content, r.regDate, r.writer, r.bno);
		let isMine = writer === r.writer;
	    // 백틱으로 덮을 경우 EL 태그 앞에 무조건 역슬래쉬를 붙여줘야 동작한다.
	    str += `
	          <li class="list-unstyled px-4" data-rno="\${r.rno}">
	            <div class="row">
	              <a class="text-muted small mb-3 col text-decoration-none" href="">
	                <span class="fs-6 fw-bold">\${r.writer}</span>
	                <span class="mx-5">\${r.regDate}</span>
	              </a>
	            <div class="col text-end">`;
	    str +=   isMine ? '<a href="" class="text-danger"><i class="fas fa-times"></i></a>' : ''
	    str +=   `</div>
	            </div>
	            <div class="mb-4 small">
	              <p>\${r.content}</p>
	            </div>
	          </li>
	    `;
	    }
	  $(".replies").html(str);
	}
	
	// 해당 태그 내의 선택자를 활용하여 선택자 내의 버튼을 클릭하면 
	// 클릭했을 시의 동작을 콜백 함수의 내용대로 수행한다.
 	  $("#commentArea").next().click(function(){
// 	    console.log($("#commentArea").val());
	    // console.log($(this).end().val());
	    let content = $("#commentArea").val();
	    if(!writer) {
	    	alert("로그인 후 작성하세요");
	    	
	    	//
	    	location.href = contextPath + "/member/login?href=" + encodeURIComponent(location.pathname + location.search + '#replyArea');
	    	return;
	    }
	    else if(!content) {
	    	alert("댓글 내용을 입력하세요");
	    	return;
	    }
	    console.log({bno:bno, content:content, writer:writer});
	    $.ajax({
	    	url: replyPath,
	    	data : {bno:bno, content:content, writer:writer},
	    	method : "POST",
	    	success : function(data) {
	    		alert("댓글이 성공적으로 작성되었습니다");
	    		// 텍스트는 val()로 작용함.
	    		$("#commentArea").val("");
	    		showList();
	    	}
	    })
	  })

	  $(".replies").on("click", "li a.text-danger", function(){
		// 이벤트들은 
	    event.preventDefault();
		
		// 삭제하기 전에 한번 물어본다.
		if(!confirm("댓글을 삭제하시겠습니까?")) {
			return false;
		}
	    console.log($(this).closest("li").data("rno"));
	    let rno = $(this).closest("li").data("rno");
	    $.ajax({
	    	url: replyPath + "?rno=" + rno,
// 	    	data : {rno:rno},
	    	method : "DELETE",
	    	success : function(data) {
	    		if(data == 1){	
	    			// 그 전에 data란 값이 1이 나오는지 log를 통해서 확인을 해봐야한다.
		    		alert("댓글이 성공적으로 삭제되었습니다");
		    		showList();
	    		}
	    	}
	    })
	  });
	
</script>
</body>
</html>