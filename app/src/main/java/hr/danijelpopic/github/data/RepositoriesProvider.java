package hr.danijelpopic.github.data;

import static com.google.common.base.Preconditions.checkNotNull;

import hr.danijelpopic.github.model.RepositoriesResponse;
import hr.danijelpopic.github.network.GitHubService;
import hr.danijelpopic.github.network.ServiceFactory;
import hr.danijelpopic.github.presenter.SearchPresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Provides data for search presenter.
 */
public class RepositoriesProvider implements RepositoryProviderAPI {

    private SearchPresenter mPresenter;

    public RepositoriesProvider() {
    }

    @Override
    public void setPresenter(SearchPresenter presenter) {
        checkNotNull(presenter, "presenter cannot be null!");
        mPresenter = presenter;
    }

    @Override
    public void getData(String query) {
        GitHubService service = ServiceFactory.createRetrofitService(
                GitHubService.class, GitHubService.SERVICE_ENDPOINT);

        Observable<RepositoriesResponse> observable =
                service.searchRepositories(query);

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError);
    }

    @Override
    public void handleResponse(RepositoriesResponse repositoriesResponse) {
        mPresenter.showData(repositoriesResponse.getItems());
    }

    @Override
    public void handleError(Throwable error) {
        mPresenter.showError(error);
    }

}
