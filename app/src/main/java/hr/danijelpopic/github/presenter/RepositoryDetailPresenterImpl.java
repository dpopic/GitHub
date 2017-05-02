package hr.danijelpopic.github.presenter;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.NonNull;

import hr.danijelpopic.github.model.Repository;
import hr.danijelpopic.github.ui.fragment.RepositoryDetailFragment;
import hr.danijelpopic.github.ui.view.RepositoryDetailView;

public class RepositoryDetailPresenterImpl implements
        RepositoryDetailPresenter {

    private final RepositoryDetailView mView;
    private Repository mRepository;

    public RepositoryDetailPresenterImpl(@NonNull Repository repository,
            @NonNull RepositoryDetailFragment fragment) {
        mView = checkNotNull(fragment, "fragment cannot be null!");
        mRepository = checkNotNull(repository, "repository cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void setupAll() {
        setRepositoryData();
        setOwnerData();
    }

    @Override
    public void setRepositoryData() {
        mView.loadRepositoryData(mRepository);
    }

    @Override
    public void setOwnerData() {
        mView.loadOwnerData(mRepository.getOwner());
    }

    @Override
    public void showOwnerDetails() {
        mView.startUserActivity(mRepository.getOwner());
    }

    @Override
    public void showRepositoryInBrowser() {
        mView.openInBrowser(mRepository.getHtmlUrl());
    }


}
