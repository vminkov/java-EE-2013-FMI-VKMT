package c2h5oh.jpa;

import javax.persistence.*;

@Entity
@Table(name = "T_ENTITY1")
public class Entity1 {

	@Id
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}