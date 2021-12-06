package service;

import java.util.List;
import java.util.Map;

import dto.Member;

public interface MemberService {
	String insert(Member member);
	String update(Member member, String changepw);
	String update2(Member member);
	String delete(String email);
	Member selectOne(String email);
	List<Member> selectList(String findkey, String findvalue);
	Map<String,Object> login(String email, String userpw);
}
