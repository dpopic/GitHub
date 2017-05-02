package hr.danijelpopic.github.ui.screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import hr.danijelpopic.github.R;
import hr.danijelpopic.github.data.RepositoriesProvider;
import hr.danijelpopic.github.presenter.SearchPresenter;
import hr.danijelpopic.github.presenter.SearchPresenterImpl;
import hr.danijelpopic.github.ui.fragment.SearchFragment;
import hr.danijelpopic.github.util.ActivityUtils;

public class SearchActivity extends AppCompatActivity {

    private SearchPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_act);

        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setSubtitle(getString(R.string.github_subtitle));
        setSupportActionBar(toolbar);

        SearchFragment fragment = (SearchFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            // Create the fragment
            fragment = SearchFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.contentFrame);
        }

        // Create the presenter
        mPresenter = new SearchPresenterImpl(new RepositoriesProvider(), fragment);

    }

}


