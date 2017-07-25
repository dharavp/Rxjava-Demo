package app.android.myapplication.com.project;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.android.myapplication.com.project.data.GitHubRepo;
import app.android.myapplication.com.project.data.GitHubUser;
import app.android.myapplication.com.project.databinding.ItemRecycleBinding;

/**
 * Created by admin1234 on 7/21/17.
 */

public class MyRecyclerViewDataAdapter
    extends RecyclerView.Adapter<MyRecyclerViewDataAdapter.MyRecyclerViewHolder> {
  ItemRecycleBinding itemRecyclerBinding;
  List<GitHubRepo> stringList = new ArrayList<>();

  public void addData(List<GitHubRepo> gitHubRepoList) {
    stringList.addAll(gitHubRepoList);
    notifyDataSetChanged();
  }

  public void clearList() {
    if (stringList != null && stringList.size() != 0) {
      stringList.clear();
    }
  }

  @Override
  public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    itemRecyclerBinding = DataBindingUtil.inflate(inflater, R.layout.item_recycler, parent, false);
    return new MyRecyclerViewHolder(itemRecyclerBinding);
  }

  @Override
  public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {
    holder.bindMyRecyclerView(stringList.get(position));
  }

  @Override
  public int getItemCount() {
    return stringList.size();
  }

  public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
    ItemRecycleBinding mBinding;

    public MyRecyclerViewHolder(ItemRecycleBinding itemView) {
      super(itemView.getRoot());
      this.mBinding = itemView;
    }

    public void bindMyRecyclerView(GitHubRepo gitHubRepo) {
      mBinding.textRepoName.setText(gitHubRepo.name);
      mBinding.textRepoDescription.setText(gitHubRepo.description);
      mBinding.textLanguage.setText("Language: " + gitHubRepo.language);
      mBinding.textStars.setText("Stars: " + gitHubRepo.stargazersCount);
      mBinding.name.setText(gitHubRepo.getUser().getLogin());
      mBinding.followers.setText(String.valueOf(gitHubRepo.getUser().getFollowers()));
      mBinding.following.setText(String.valueOf(gitHubRepo.getUser().getFollowing()));
      mBinding.repos.setText(String.valueOf(gitHubRepo.getUser().getPublic_repos()));
    }
  }
}
