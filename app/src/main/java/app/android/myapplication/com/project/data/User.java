package app.android.myapplication.com.project.data;

/**
 * Created by admin1234 on 7/24/17.
 */

public class User {
    private int followers;
    private int following;
    private int public_repos;
    private String login;

    public int getFollowers() {
        return followers;
    }

    public User setFollowers(int followers) {
        this.followers = followers;
        return this;
    }

    public int getFollowing() {
        return following;
    }

    public User setFollowing(int following) {
        this.following = following;
        return this;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public User setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }
}
