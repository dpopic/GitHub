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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import hr.danijelpopic.github.R;
import hr.danijelpopic.github.model.Owner;
import hr.danijelpopic.github.presenter.UserDetailPresenter;
import hr.danijelpopic.github.ui.view.UserDetailView;

public class UserDetailFragment extends Fragment implements UserDetailView {

    private UserDetailPresenter mPresenter;

    // Views
    private ImageView ivAvatar;
    private TextView tvOwner;
    private TextView tvOwnerData;
    private Button btnDetails;

    /**
     * Empty public constructor.
     */
    public UserDetailFragment() {
    }

    public static UserDetailFragment newInstance() {
        return new UserDetailFragment();
    }

    @Override
    public void setPresenter(@NonNull UserDetailPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.user_detail_frag, container,
                false);

        ivAvatar = (ImageView) root.findViewById(R.id.ivAvatar);
        tvOwner = (TextView) root.findViewById(R.id.tvOwner);
        tvOwnerData = (TextView) root.findViewById(R.id.tvOwnerData);
        btnDetails = (Button) root.findViewById(R.id.btnDetails);

        btnDetails.setOnClickListener(v -> {
            mPresenter.showUserInBrowser();
        });

        mPresenter.setupAll();

        return root;
    }

    @Override
    public void loadOwnerData(Owner o) {
        // Set owner name
        tvOwner.setText(o.getLogin());

        // Get image with Picasso
        Picasso.with(getActivity()).load(o.getAvatarUrl()).into(ivAvatar);

        String text = "";
        text += getString(R.string.id_tag) + o.getId() + (char) 13 + (char) 10;
        text += getString(R.string.type) + o.getType() + (char) 13 + (char) 10;

        tvOwnerData.setText(text);
    }

    @Override
    public void openInBrowser(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
