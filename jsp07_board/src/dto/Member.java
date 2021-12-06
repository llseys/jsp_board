package dto;

import java.sql.Date;

public class Member {
	private String email;
	private String userpw;
	private String salt;
	private String zipcode;
	private String addr;
	private String addrdetail;
	private String filename; // 업로드한 파일의 이름
	private Date regidate;
	
	public Member() {
	}

	public Member(String email, String userpw, String salt, String zipcode, String addr, String addrdetail,
			String filename, Date regidate) {
		this.email = email;
		this.userpw = userpw;
		this.salt = salt;
		this.zipcode = zipcode;
		this.addr = addr;
		this.addrdetail = addrdetail;
		this.filename = filename;
		this.regidate = regidate;
	}

	public Member(String email, String userpw, String zipcode, String addr, String addrdetail) {
		this.email = email;
		this.userpw = userpw;
		this.zipcode = zipcode;
		this.addr = addr;
		this.addrdetail = addrdetail;
	}

	public Member(String email, String userpw, String zipcode, String addr, String addrdetail,
			String filename) {
		this.email = email;
		this.userpw = userpw;
		this.zipcode = zipcode;
		this.addr = addr;
		this.addrdetail = addrdetail;
		this.filename = filename;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserpw() {
		return userpw;
	}

	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getAddrdetail() {
		return addrdetail;
	}

	public void setAddrdetail(String addrdetail) {
		this.addrdetail = addrdetail;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getRegidate() {
		return regidate;
	}

	public void setRegidate(Date regidate) {
		this.regidate = regidate;
	}

	@Override
	public String toString() {
		return "Member [email=" + email + ", userpw=" + userpw + ", salt=" + salt + ", zipcode=" + zipcode + ", addr="
				+ addr + ", addrdetail=" + addrdetail + ", filename=" + filename + ", regidate=" + regidate + "]";
	}
	
	
	

}
