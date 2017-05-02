package hr.danijelpopic.github.ui.view;

import hr.danijelpopic.github.model.Owner;
import hr.danijelpopic.github.model.Repository;
import hr.danijelpopic.github.presenter.RepositoryDetailPresenter;

public interface RepositoryDetailView extends
        BaseView<RepositoryDetailPresenter> {

    void loadRepositoryData(Repository r);

    void loadOwnerData(Owner owner);

    void startUserActivity(Owner owner);

    void openInBrowser(String url);
}
