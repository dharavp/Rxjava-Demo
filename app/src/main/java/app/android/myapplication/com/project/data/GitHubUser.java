package app.android.myapplication.com.project.data;

import java.util.List;

/**
 * Created by admin1234 on 7/24/17.
 */

public class GitHubUser {
  private List<GitHubRepo> gitHubRepo;
  private User user;
  //  private List<User> users;
  //
  //  public GitHubUser(List<GitHubRepo> gitHubRepo, List<User> users) {
  //      this.gitHubRepo = gitHubRepo;
  //      this.users = users;
  //  }

  public GitHubUser(List<GitHubRepo> gitHubRepo, User user) {
    this.gitHubRepo = gitHubRepo;
    this.user = user;
  }

  public List<GitHubRepo> getGitHubRepo() {
    return gitHubRepo;
  }

  public GitHubUser setGitHubRepo(List<GitHubRepo> gitHubRepo) {
    this.gitHubRepo = gitHubRepo;
    return this;
  }

  //public List<User> getUsers() {
  //    return users;
  //}
  //
  //public GitHubUser setUsers(List<User> users) {
  //    this.users = users;
  //    return this;
  //}
  public User getUser() {
    return user;
  }

  public GitHubUser setUser(User user) {
    this.user = user;
    return this;
  }
}
