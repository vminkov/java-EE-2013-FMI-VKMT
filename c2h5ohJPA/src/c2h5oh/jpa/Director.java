package c2h5oh.jpa;

import javax.persistence.*;

@Entity
@Table(name = "T_DIRECTOR")
public class Director extends Employee {

	@Basic
	private String klatisikrakata;

	public void setKlatisikrakata(String param) {
		this.klatisikrakata = param;
	}

	public String getKlatisikrakata() {
		return klatisikrakata;
	}

}