package hr.danijelpopic.github.ui.view;

import android.view.Menu;

import java.util.List;

import hr.danijelpopic.github.model.Repository;
import hr.danijelpopic.github.presenter.SearchPresenter;
import hr.danijelpopic.github.ui.fragment.SearchFragment;

public interface SearchView extends BaseView<SearchPresenter> {

    void showOrderPopUpMenu();

    void setSearchView(Menu menu);

    void showError(Throwable error);

    void showData(List<Repository> items);

    void setToolbarTitle(String title);

    void setToolbarSubTitle(String subTitle);

    void changeRepoOrder(SearchFragment.RepoOrderType orderType);
}
