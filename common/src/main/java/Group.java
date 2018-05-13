import java.util.List;

public class Group {
    private int id;
    private String name;
    private String info;
    private int accountIdAdmin;
    private List<Integer> membersId;

    public Group(int id, String name, String info, int accountIdAdmin, List<Integer> membersId) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.accountIdAdmin = accountIdAdmin;
        this.membersId = membersId;
    }

    public Group() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getAccountIdAdmin() {
        return accountIdAdmin;
    }

    public void setAccountIdAdmin(int accountIdAdmin) {
        this.accountIdAdmin = accountIdAdmin;
    }

    public List<Integer> getMembersId() {
        return membersId;
    }

    public void setMembersId(List<Integer> membersId) {
        this.membersId = membersId;
    }

    @Override
    public String toString() {
        return "Group \"" + name + "\". Id: " + id + ", info: " + info + ", accountIdAdmin: " + accountIdAdmin +
                ", members id: " + membersId + ".";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Group)) {
            return false;
        }
        Group group = (Group) obj;
        return id == group.id && name.equals(group.name) && info.equals(group.info) && accountIdAdmin == group.accountIdAdmin && membersId.equals(group.membersId);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        result = 31 * result + name.hashCode();
        result = 31 * result + info.hashCode();
        result = 31 * result + accountIdAdmin;
        return 31 * result + membersId.hashCode();
    }
}