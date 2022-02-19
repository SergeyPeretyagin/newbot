package Entity;

public class User implements Comparable<User>{
    @Override
    public String toString() {
        return "User2{" +
                "user_name='" + user_name + '\'' +
                ", spend_time='" + spend_time + '\'' +
                ", activities='" + activities + '\'' +
                '}';
    }

    private String user_name;
    private String spend_time;
    private String activities;

    public User(String user_name, String spend_time, String activities) {
        this.user_name = user_name;
        this.spend_time = spend_time;
        this.activities = activities;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSpend_time() {
        return spend_time;
    }

    public void setSpend_time(String spend_time) {
        this.spend_time = spend_time;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    @Override
    public int compareTo(User o) {
      return this.user_name.compareTo(o.getUser_name());
    }
}
