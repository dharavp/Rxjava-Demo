package app.android.myapplication.com.project;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

import app.android.myapplication.com.project.data.GitHubRepo;
import app.android.myapplication.com.project.data.GitHubUser;
import app.android.myapplication.com.project.data.User;
import app.android.myapplication.com.project.databinding.MainScreenBinding;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    MainScreenBinding activityMainBinding;
    MyRecyclerViewDataAdapter adapter;
    private Subscription subscription;
    private String userName;
    private GitHubClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        adapter = new MyRecyclerViewDataAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        activityMainBinding.chatRecycleData.setLayoutManager(linearLayoutManager);
        activityMainBinding.chatRecycleData.setAdapter(adapter);
        client = GitHubClient.getInstance();
        activityMainBinding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = activityMainBinding.editTextUsername.getText().toString();
                String secondUserName = activityMainBinding.editUsername.getText().toString();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(secondUserName)) {
                    listData(username,secondUserName);
                    //   listData(secondUserName);
                }
            }
        });
    }

    private void getStarredRepos(String username) {
//        subscription = GitHubClient.getInstance()
//                .getStarredRepos(username)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<GitHubRepo>>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.d(TAG, "In onCompleted()");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        Log.d(TAG, "In onError()");
//                    }
//
//                    @Override
//                    public void onNext(List<GitHubRepo> gitHubRepos) {
//                        Log.d(TAG, "In onNext()");
//                        adapter.addData(gitHubRepos);
//                    }
//                });

    }

    public void listData(String userName,String secondUserName) {
        Observable<List<GitHubRepo>> fetchStarred1 = client.getStarredRepos(userName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        Observable<User> fetchStarred2 = client.getUserInfo(userName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        Observable<List<GitHubRepo>> fetchStarred3 = client.getStarredRepos(secondUserName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        Observable<User> fetchStarred4 = client.getUserInfo(secondUserName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        Observable<List<GitHubRepo>> mergedRepo = Observable.merge(fetchStarred1, fetchStarred3);
        Observable<User> mergedUser = Observable.merge(fetchStarred2, fetchStarred4);
        Observable<GitHubUser> zip = Observable.zip(mergedRepo, mergedUser, new Func2<List<GitHubRepo>, User, GitHubUser>() {
            @Override
            public GitHubUser call(List<GitHubRepo> gitHubRepos, User user) {
                return new GitHubUser(gitHubRepos, user);
            }
        });
        zip.subscribe(new Subscriber<GitHubUser>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(GitHubUser gitHubUser) {
                adapter.addData(gitHubUser);
            }
        });
    }

}

