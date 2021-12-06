package dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MBConn {
	public static SqlSession getSession() {
		String resource = "mybatis/mybatisConfig.xml";
		SqlSession session = null;
		try {
			// 마이바티스 환경 xml파일
			InputStream is = Resources.getResourceAsStream(resource);
			// 팩토리 만들기 (세션을 만들수있는 객체)
			SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(is);
			session = sf.openSession();
			System.out.println("세션생성 성공");
			System.out.println("--------------------------------------");
		} catch (IOException e) {
			System.out.println("mb환경파일 읽기 실패");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return session;
	}
}
