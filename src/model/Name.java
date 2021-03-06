package model;

/**
 * A wrapper for Strings;
 * for use in searching
 * for a Person by name
 * in an ArrayList
 */
public class Name {
	
	private final String name;
	
	public Name(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof Name) {
			Name other = (Name) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
		if (obj instanceof Person) {
			Person other = (Person) obj;
			if (name == null) {
				if (other.getName() != null)
					return false;
			} else if (!name.equals(other.getName()))
				return false;
			return true;
		}
		return false;
	}
	
	

}
