package hr.danijelpopic.github.data;

import hr.danijelpopic.github.model.RepositoriesResponse;
import hr.danijelpopic.github.presenter.SearchPresenter;

public interface RepositoryProviderAPI {

    void setPresenter(SearchPresenter presenter);

    void getData(String query);

    void handleResponse(RepositoriesResponse repositoriesResponse);

    void handleError(Throwable error);
}
