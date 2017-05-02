package hr.danijelpopic.github.ui.view;

import hr.danijelpopic.github.model.Owner;
import hr.danijelpopic.github.presenter.UserDetailPresenter;

public interface UserDetailView extends BaseView<UserDetailPresenter> {

    void loadOwnerData(Owner owner);

    void openInBrowser(String url);
}
