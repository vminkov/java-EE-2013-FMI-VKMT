package c2h5oh.beans.roles;

public enum Role {
	WAITER("WAITER"),
	BARTENDER("BARTENDER"),
	DIRECTOR("DIRECTOR");
	
	private final String value;

	Role(String str) {
		this.value = str;
	}

	public static Role fromValue(String value) {
		if (value != null) {
			for (Role role : values()) {
				if (role.value.equals(value)) {
					return role;
				}
			}
		}

		return null;
	}

	public String toValue() {
		return value;
	}
}
