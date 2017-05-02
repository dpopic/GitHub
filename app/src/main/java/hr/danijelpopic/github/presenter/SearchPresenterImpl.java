package hr.danijelpopic.github.presenter;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.NonNull;

import java.util.List;

import hr.danijelpopic.github.data.RepositoryProviderAPI;
import hr.danijelpopic.github.model.Repository;
import hr.danijelpopic.github.ui.fragment.SearchFragment;
import hr.danijelpopic.github.ui.view.SearchView;

public class SearchPresenterImpl implements SearchPresenter {

    private final SearchView mSearchView;
    private final RepositoryProviderAPI mProvider;

    public SearchPresenterImpl(@NonNull RepositoryProviderAPI provider, @NonNull SearchView searchView) {
        mSearchView = checkNotNull(searchView, "searchView cannot be null!");
        mProvider = checkNotNull(provider, "provider cannot be null!");
        mSearchView.setPresenter(this);
        mProvider.setPresenter(this);
    }

    @Override
    public void getData(String query) {
        mProvider.getData(query);
    }

    @Override
    public void showData(List<Repository> items) {
        mSearchView.showData(items);
    }

    @Override
    public void showError(Throwable error) {
        mSearchView.showError(error);
    }

    @Override
    public void changeRepoOrder(SearchFragment.RepoOrderType orderType) {
        mSearchView.changeRepoOrder(orderType);
    }


}
