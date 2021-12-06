package service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.Data;

import org.apache.catalina.tribes.util.Arrays;

import dao.MemberDAO;
import dao.MemberDAOImpl;
import dto.Member;

public class MemberServiceImpl implements MemberService{
	MemberDAO mdao = new MemberDAOImpl();
	@Override
	public String insert(Member member) {
		String salt = saltmake(); //솔트생성
		String secretpw = sha256(member.getUserpw(),salt);
		member.setUserpw(secretpw); // 암호화된 비밀번호로 셋팅
		member.setSalt(salt);

		int cnt = mdao.insert(member);
		if(cnt>0) {
			return "추가완료";
		}else {
			return "추가실패";
		}
	}
	// salt를 랜덤하게 만들기
	public String saltmake() {
		String salt = null;;
		try {
			// 난수를 생성해준다
			SecureRandom sr =  SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[16]; //빈배열
			sr.nextBytes(bytes); // 랜덤한 값을 bytes에 만든다
			// byte데이터를 String형으로
			salt = new String(Base64.getEncoder().encode(bytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return salt;
	}
	
	
	//평문을 암호문으로 변경
	public String sha256(String userpw, String salt) { //salt:해킹방지
		StringBuffer sb = new StringBuffer();
		try {
			//SHA-256 : 단반향암호화(복호불가) 256bit(16진수 64자리) 
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(userpw.getBytes()); // 문자열을 바이트배열로 변경해서 전달
			md.update(salt.getBytes()); // 솔트를 추가
			byte[] data = md.digest(); // 암호화된 바이트배열(32바이트)
			System.out.println("암호화된 바이트 배열 : " + Arrays.toString(data));
			//16진수 문자열로 변경 sb변수에 추가
			for(byte b : data) {
				sb.append(String.format("%02x", b));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	public String update(Member member, String changepw) {
		String msg = null;
		Member dbmember = mdao.selectOne(member.getEmail());
		String dbpw = dbmember.getUserpw(); //db비밀번호(암호화되어있음)
		String dbsalt = dbmember.getSalt();
		String secretpw = sha256(member.getUserpw(), dbsalt); //입력받은비밀번호 암호화해서 db(기존)비번 일치확인
		if(!dbpw.equals(secretpw)) {
			msg = "비밀번호 불일치";
		}else { //변경권한생김
			// 변경할 비밀번호가 있다면
			if(!changepw.equals("")) {
				//솔트구하기
				String salt = saltmake();
				//새로운 암호비밀번호 얻기
				secretpw = sha256(changepw, salt);
				member.setSalt(salt);
				member.setUserpw(secretpw);
				int cnt = mdao.update(member);
				if(cnt>0) msg = "수정성공";
				else if(cnt<0) msg = "수정실패";
			}else { //변경할 비밀번호없다면 기존정보 다시셋팅
				member.setUserpw(dbpw);
				member.setSalt(dbsalt);
				int cnt = mdao.update(member);
				if(cnt>0) msg = "수정성공";
				else if(cnt<0) msg = "수정실패";
			}
		}
		return msg;
	}

	@Override
	public String delete(String email) {
		int cnt = mdao.delete(email);
		if(cnt>0) {
			return "삭제완료";
		}else {
			return "삭제실패";
		}
	}

	@Override
	public Member selectOne(String email) {
		return mdao.selectOne(email);
	}

	@Override
	public List<Member> selectList(String findkey, String findvalue) {
		Map<String, Object> map = new HashMap<>();
		map.put("findkey", findkey);
		map.put("findvalue", findvalue);
		return mdao.selectList(map);
	}
	@Override
	public Map<String,Object> login(String email, String userpw) {
		Map<String,Object> map = new HashMap<>();
		//0:로그인성공
		//1:이메일불일치
		//2:패스워드불일치
		String msg = null; //메세지
		String rcode =null; //결과코드값
		//한건조회
		Member member = mdao.selectOne(email);
		if(member==null) {
			msg="이메일불일치";
			rcode = "1";
		}else { //이메일존재할때
			String dbpw = member.getUserpw();
			String salt = member.getSalt();
			String secretpw = sha256(userpw,salt); //암호화된비밀번호만들기
			if(!dbpw.equals(secretpw)) {
				msg="비밀번호 불일치";
				rcode = "2";
			}else {
				msg="로그인성공";
				rcode = "0";
			}	
		}
		map.put("msg", msg);
		map.put("rcode",rcode);

		return map;
	}
	@Override
	public String update2(Member member) {
		Member dbmember = mdao.selectOne(member.getEmail()); 
		return null;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
