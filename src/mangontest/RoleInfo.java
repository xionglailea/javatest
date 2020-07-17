package mangontest;

public class RoleInfo {
  public long roleId;
  public String roleName;
  public int sex;

  public RoleInfo(long roleId, String roleName, int sex) {
    this.roleId = roleId;
    this.roleName = roleName;
    this.sex = sex;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("roleId:").append(roleId).append(";roleName:").append(roleName)
            .append(";sex:").append(sex);
    return sb.toString();
  }
}
