package app.android.myapplication.com.project.data;

/**
 * Created by bhavdip on 25/7/17.
 */

public class UserInfo {
  private User user;
  private User user2;

  public UserInfo(User user, User user2) {
    this.user = user;
    this.user2 = user2;
  }

  public User getUser() {
    return user;
  }

  public UserInfo setUser(User user) {
    this.user = user;
    return this;
  }

  public User getUser2() {
    return user2;
  }

  public UserInfo setUser2(User user2) {
    this.user2 = user2;
    return this;
  }
}
