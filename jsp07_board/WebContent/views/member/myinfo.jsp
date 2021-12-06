<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../include/include.jsp" %>
    <%@ include file="../include/msg.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function goPopup(){
		// 주소검색을 수행할 팝업 페이지를 호출합니다.
		// 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
		var pop = window.open("${path}/views/member/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
		
		// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
	    //var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
	}
	function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		document.frmMyinfo.zipcode.value = zipNo;
		document.frmMyinfo.addr.value = roadAddrPart1;
		document.frmMyinfo.addrdetail.value = addrDetail;
	}
	function modiCheck() {
		var userpw = frmMyinfo.userpw.value;
		if(userpw==''){
			alert('기존비밀번호를 입력해주세요')
			frmMyinfo.userpw.focus();
		}else{
			frmMyinfo.submit();
		}
	}

</script>
</head>
<body>
	<h2>회원정보</h2>
	
	<form name="frmMyinfo" action="${path}/member/modify" method="post" enctype="multipart/form-data">
		<table border="1">
		<tr>
			<th>이메일</th>
			<td>  <input type="text" name="email" value="${member.email}" readonly>   </td>
		</tr>
		<tr>
			<th>기존비밀번호</th>
			<td>  <input type="password" name="userpw" value="" >   </td>
		</tr>
		
		<tr>
			<th>변경비밀번호</th>
			<td>  <input type="password" name="changepw" value="" >   </td>
		</tr>
		<tr>
			<th>주소</th>
			<td> 	
				<!-- api를 이용해서 주소찾기 -->
				<input type="number" name="zipcode" value="${member.zipcode}" size="5"> 
				<button type="button" onclick="goPopup()">찾기</button>
				<hr>
				<input type="text" name="addr" value="${member.addr}" size="30"> <br>
				<input type="text" name="addrdetail"  value="${member.addrdetail}"size="30"> 
			</td>
		</tr>
		<tr>
			<th>사진</th>
			<td> 
				<!-- saveImg : server.xml 에서 Context 추가 -->
				<div><img alt="" src="/saveImg/${member.filename}" width="200"> </div> 
				<div>
					<a href="${path}/file/filedown?filename=${member.filename}"> 다운로드 </a> <hr>
					삭제<input type="checkbox" name="filedel">
					<input type="text" name="filename" value="${member.filename}" readonly> <br>
				</div>
				<hr>
				<input type="file" name="file">
				
			</td>
		</tr>
		<tr>
			<th>등록일자</th>
			<td><input type="text" name="regidate" value="${member.regidate}"> </td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button type="button" onclick="modiCheck()">수정하기</button>
				<button>취소</button>
			</td>
		</tr>
	</table>
	</form>

</body>
</html>