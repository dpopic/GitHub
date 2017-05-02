package hr.danijelpopic.github.ui.listener;

import hr.danijelpopic.github.model.Owner;
import hr.danijelpopic.github.model.Repository;

public interface RepositoryItemListener {

    void onAvatarClick(Owner owner);

    void onRepoClick(Repository repository);
}
