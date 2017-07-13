

import java.util.List;

public class human_users_birth implements Comparable<human_users_birth> {

	private String birthDate;
	private int human_user_id;
	private String human_user_name;
	private String email;
	private String face;
	// 0������ 1������
	private int type;
	// 0���������գ�1���������գ�-1�������ѹ�
	private int state;
	private int blessCount;
	// �������ն�����
	private int day;

	private List<String> sendUsers;

	private String mess;

	@Override
	public int compareTo(human_users_birth birth) {
		String MMdd = birth.getBirthDate().substring(5);
		String MMdd2 = this.getBirthDate().substring(5);
		return -MMdd.compareTo(MMdd2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		if (obj instanceof human_users_birth) {
			human_users_birth birth = (human_users_birth) obj;
			return (this.human_user_id == birth.human_user_id);
		}

		return super.equals(obj);
	}
	


	@Override
	public String toString() {
		return "[ user_id=" + human_user_id  +"]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getSendUsers() {
		return sendUsers;
	}

	public void setSendUsers(List<String> sendUsers) {
		this.sendUsers = sendUsers;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getBlessCount() {
		return blessCount;
	}

	public void setBlessCount(int blessCount) {
		this.blessCount = blessCount;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public int getHuman_user_id() {
		return human_user_id;
	}

	public void setHuman_user_id(int human_user_id) {
		this.human_user_id = human_user_id;
	}

	public String getHuman_user_name() {
		return human_user_name;
	}

	public void setHuman_user_name(String human_user_name) {
		this.human_user_name = human_user_name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
