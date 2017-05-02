package hr.danijelpopic.github.presenter;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.NonNull;

import hr.danijelpopic.github.model.Owner;
import hr.danijelpopic.github.ui.fragment.UserDetailFragment;
import hr.danijelpopic.github.ui.view.UserDetailView;

public class UserDetailPresenterImpl implements UserDetailPresenter {

    private final UserDetailView mView;
    private Owner mOwner;

    public UserDetailPresenterImpl(@NonNull Owner owner,
            @NonNull UserDetailFragment fragment) {
        mView = checkNotNull(fragment, "fragment cannot be null!");
        mOwner = checkNotNull(owner, "owner cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void setupAll() {
        setOwnerData();
    }

    @Override
    public void setOwnerData() {
        mView.loadOwnerData(mOwner);
    }

    @Override
    public void showUserInBrowser() {
        mView.openInBrowser(mOwner.getHtmlUrl());
    }

}
