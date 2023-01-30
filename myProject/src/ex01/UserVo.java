package ex01;

import java.sql.Date;

public class UserVo {
	private String id;
	private String pw;
	private String name;
	private String address;
	private String gender;
	private Date joindate;

	public UserVo() {
		super();
	}

	public UserVo(String id, String pw, String name, String address, String gender, Date joindate) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.address = address;
		this.gender = gender;
		this.joindate = joindate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getJoindate() {
		return joindate;
	}

	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}

	@Override
	public String toString() {
		return "UserVo [id=" + id + ", pw=" + pw + ", name=" + name + ", address=" + address + ", gender=" + gender
				+ ", joindate=" + joindate + "]";
	}

}
