package hr.danijelpopic.github.ui.fragment;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import hr.danijelpopic.github.R;
import hr.danijelpopic.github.model.Owner;
import hr.danijelpopic.github.model.Repository;
import hr.danijelpopic.github.presenter.RepositoryDetailPresenter;
import hr.danijelpopic.github.ui.screen.UserDetailActivity;
import hr.danijelpopic.github.ui.view.RepositoryDetailView;

public class RepositoryDetailFragment extends Fragment implements
        RepositoryDetailView {

    private RepositoryDetailPresenter mPresenter;

    // Views
    private LinearLayout llOwnerData;
    private ImageView ivAvatar;
    private TextView tvOwner;
    private TextView tvRepoData;
    private Button btnDetails;

    public RepositoryDetailFragment() {}

    public static RepositoryDetailFragment newInstance() {
        return new RepositoryDetailFragment();
    }

    @Override
    public void setPresenter(@NonNull RepositoryDetailPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.repository_detail_frag, container, false);

        llOwnerData = (LinearLayout) root.findViewById(R.id.llOwnerData);
        ivAvatar = (ImageView) root.findViewById(R.id.ivAvatar);
        tvOwner = (TextView) root.findViewById(R.id.tvOwner);
        tvRepoData = (TextView) root.findViewById(R.id.tvRepoData);
        btnDetails = (Button) root.findViewById(R.id.btnDetails);

        // ivAvatar OnClickListener - owner details
        llOwnerData.setOnClickListener(v -> {
            mPresenter.showOwnerDetails();
        });

        btnDetails.setOnClickListener(v -> {
            mPresenter.showRepositoryInBrowser();
        });

        mPresenter.setupAll();

        return root;
    }


    @Override
    public void loadRepositoryData(Repository r) {
        String text = "";
        text += r.getName() + (char)13 + (char)10;
        text += getString(R.string.watchers_rv) + r.getWatchersCount() + (char)13 + (char)10;
        text += getString(R.string.forks_rv) + r.getForksCount() + (char)13 + (char)10;
        text += getString(R.string.issues_rv) + r.getOpenIssuesCount() + (char)13 + (char)10;
        text += getString(R.string.language) + r.getLanguage() + (char)13 + (char)10;
        text += getString(R.string.date_created) + r.getCreatedAt() + (char)13 + (char)10;
        text += getString(R.string.date_updated) + r.getUpdatedAt() + (char)13 + (char)10;
        text += getString(R.string.size) + r.getSize() + (char)13 + (char)10;
        text += getString(R.string.score) + r.getScore() + (char)13 + (char)10;
        tvRepoData.setText(text);
    }

    @Override
    public void loadOwnerData(Owner owner) {
        // Set owner name
        tvOwner.setText(owner.getLogin());

        // Get image with Picasso
        Picasso.with(getActivity()).load(owner.getAvatarUrl()).into(ivAvatar);
    }

    @Override
    public void startUserActivity(Owner owner) {
        Intent intent = new Intent(getActivity(), UserDetailActivity.class);
        intent.putExtra("OWNER", owner);
        startActivity(intent);
        getActivity().finish(); // According to documentation we don't go back
    }

    @Override
    public void openInBrowser(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
