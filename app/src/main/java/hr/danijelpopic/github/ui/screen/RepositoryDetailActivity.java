package hr.danijelpopic.github.ui.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import hr.danijelpopic.github.R;
import hr.danijelpopic.github.model.Repository;
import hr.danijelpopic.github.presenter.RepositoryDetailPresenter;
import hr.danijelpopic.github.presenter.RepositoryDetailPresenterImpl;
import hr.danijelpopic.github.ui.fragment.RepositoryDetailFragment;
import hr.danijelpopic.github.util.ActivityUtils;

public class RepositoryDetailActivity extends AppCompatActivity {

    private RepositoryDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repository_detail_act);

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RepositoryDetailFragment fragment = (RepositoryDetailFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            // Create the fragment
            fragment = RepositoryDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }

        // Get extras
        Intent intent = getIntent();
        Repository repository = (Repository) intent.getSerializableExtra(
                "REPOSITORY");

        // Create the presenter
        mPresenter = new RepositoryDetailPresenterImpl(repository, fragment);
    }


}
