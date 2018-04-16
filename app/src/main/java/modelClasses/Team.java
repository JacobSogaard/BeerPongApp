package modelClasses;

public class Team {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!Team.class.isAssignableFrom(obj.getClass()))
            return false;

        final Team other = (Team) obj;
        if ((this.name == null) ? (other.getName() != null) : !this.name.equals(other.getName()))
            return false;

        return other.getName().equals(this.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
