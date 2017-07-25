package app.android.myapplication.com.project;

import java.util.List;

import app.android.myapplication.com.project.data.GitHubRepo;
import app.android.myapplication.com.project.data.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by admin1234 on 7/21/17.
 */

public interface GitHubService {
    @GET("users/{user}/starred")
    Observable<List<GitHubRepo>> getStarredRepositories(@Path("user") String userName);

    @GET("users/{user}")
    Observable<User> getUserInfo(@Path("user") String userName);
}
