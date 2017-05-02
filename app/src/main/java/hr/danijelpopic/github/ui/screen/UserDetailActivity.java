package hr.danijelpopic.github.ui.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import hr.danijelpopic.github.R;
import hr.danijelpopic.github.model.Owner;
import hr.danijelpopic.github.presenter.UserDetailPresenter;
import hr.danijelpopic.github.presenter.UserDetailPresenterImpl;
import hr.danijelpopic.github.ui.fragment.UserDetailFragment;
import hr.danijelpopic.github.util.ActivityUtils;

public class UserDetailActivity extends AppCompatActivity {

    private UserDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail_act);

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        UserDetailFragment fragment =
                (UserDetailFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            // Create the fragment
            fragment = UserDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }

        // Get extras
        Intent intent = getIntent();
        Owner owner = (Owner) intent.getSerializableExtra("OWNER");

        // Create the presenter
        mPresenter = new UserDetailPresenterImpl(owner, fragment);
    }

}
