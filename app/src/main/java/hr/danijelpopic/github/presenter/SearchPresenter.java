package hr.danijelpopic.github.presenter;

import java.util.List;

import hr.danijelpopic.github.model.Repository;
import hr.danijelpopic.github.ui.fragment.SearchFragment;

public interface SearchPresenter {

    void getData(String query);

    void showData(List<Repository> items);

    void showError(Throwable error);

    void changeRepoOrder(SearchFragment.RepoOrderType orderType);
}
