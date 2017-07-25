package app.android.myapplication.com.project;

import com.android.annotations.NonNull;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import app.android.myapplication.com.project.data.GitHubRepo;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by admin1234 on 7/21/17.
 */

public class PostClass {
    private static final String GITHUB_BASE_URL = "https://api.github.com/";

    private static PostClass instance;
    private GitHubService gitHubService;

    private PostClass() {
        final Gson gson =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(GITHUB_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        gitHubService = retrofit.create(GitHubService.class);
    }

    public static PostClass getInstance() {
        if (instance == null) {
            instance = new PostClass();
        }
        return instance;
    }

    public Observable<List<GitHubRepo>> getStarredRepos(@NonNull String userName) {
        return gitHubService.getStarredRepositories(userName);
    }
}
