package app.android.myapplication.com.project;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import app.android.myapplication.com.project.data.UserInfo;
import java.util.List;

import app.android.myapplication.com.project.data.GitHubRepo;
import app.android.myapplication.com.project.data.GitHubUser;
import app.android.myapplication.com.project.data.User;
import app.android.myapplication.com.project.databinding.MainScreenBinding;
import java.util.Observer;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func4;
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
          adapter.clearList();
          listData(username, secondUserName);
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

  public void listData(final String userName, String secondUserName) {
    final Observable<List<GitHubRepo>> fetchStarred1 = client.getStarredRepos(userName)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());

    final Observable<User> fetchUser2 = client.getUserInfo(userName)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());

    final Observable<List<GitHubRepo>> fetchStarred3 = client.getStarredRepos(secondUserName)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
    Observable<User> fetchUser4 = client.getUserInfo(secondUserName)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());


    Observable<List<GitHubRepo>> userInfoObservable = Observable.zip(fetchStarred1, fetchUser2,
        new Func2<List<GitHubRepo>, User, List<GitHubRepo>>() {
          @Override
          public List<GitHubRepo> call(List<GitHubRepo> gitHubRepos, User user) {
            
            for (int i = 0; i < gitHubRepos.size(); i++) {
              gitHubRepos.get(i).setUser(user);
            }
            return gitHubRepos;
          }
        });
    Observable<List<GitHubRepo>> mergedUser = Observable.zip(fetchStarred3, fetchUser4,
        new Func2<List<GitHubRepo>, User, List<GitHubRepo>>() {
          @Override
          public List<GitHubRepo> call(List<GitHubRepo> gitHubRepos, User user) {

            for (int i = 0; i < gitHubRepos.size(); i++) {
              gitHubRepos.get(i).setUser(user);
            }
            return gitHubRepos;
          }
        });
    Observable<List<GitHubRepo>> merged = userInfoObservable.mergeWith(mergedUser);

    merged.subscribe(new Subscriber<List<GitHubRepo>>()

    {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable throwable) {

      }

      @Override
      public void onNext(List<GitHubRepo> gitHubRepos) {
        adapter.addData(gitHubRepos);
      }
    });
  }

  private static <T> Observable.Operator<T, List<T>> flattenList() {
    return new Observable.Operator<T, List<T>>() {
      @Override
      public Subscriber<? super List<T>> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<List<T>>() {
          @Override
          public void onCompleted() {
            subscriber.onCompleted();
          }

          @Override
          public void onError(Throwable e) {
            subscriber.onError(e);
          }

          @Override
          public void onNext(List<T> contributors) {
            for (T c : contributors)
              subscriber.onNext(c);
          }
        };
      }
    };
  }
}

