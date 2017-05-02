package hr.danijelpopic.github.ui.fragment;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hr.danijelpopic.github.R;
import hr.danijelpopic.github.model.Owner;
import hr.danijelpopic.github.model.Repository;
import hr.danijelpopic.github.presenter.SearchPresenter;
import hr.danijelpopic.github.ui.adapter.RepoAdapter;
import hr.danijelpopic.github.ui.listener.RepositoryItemListener;
import hr.danijelpopic.github.ui.screen.RepositoryDetailActivity;
import hr.danijelpopic.github.ui.screen.UserDetailActivity;
import hr.danijelpopic.github.ui.view.SearchView;

public class SearchFragment extends Fragment implements SearchView {

    private SearchPresenter mPresenter;
    private RepoAdapter mListAdapter;

    public SearchFragment() {}

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void setPresenter(@NonNull SearchPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set RecyclerView adapter
        mListAdapter = new RepoAdapter(getActivity(),
                new ArrayList<Repository>(0), mItemListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_frag, container, false);

        // set RecyclerView
        RecyclerView rvRepositories = (RecyclerView) root.findViewById(
                R.id.rvRepositories);
        rvRepositories.setAdapter(mListAdapter);
        rvRepositories.setLayoutManager(new LinearLayoutManager(getActivity()));

        // enable menu
        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                showOrderPopUpMenu(); // show filter menu
                break;
        }
        return true;
    }

    @Override
    public void setSearchView(Menu menu) {
        MenuItem searchViewMenuItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView
                searchView =
                (android.support.v7.widget.SearchView) searchViewMenuItem
                        .getActionView();
        int searchImgId = android.support.v7.appcompat.R.id.search_button;
        ImageView v = (ImageView) searchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.ic_search);

        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(
                new android.support.v7.widget.SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        query = query.trim();
                        setToolbarTitle(getString(R.string.results_for) + query);
                        setToolbarSubTitle(getString(R.string.order_by)
                                + mListAdapter.getOrderType().toString().toLowerCase());

                        mPresenter.getData(query);

                        // hide searchView
                        searchView.setQuery("", false);
                        searchView.setIconified(true);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
    }

    @Override
    public void setToolbarTitle(String title){
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    @Override
    public void setToolbarSubTitle(String subTitle){
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subTitle);
    }

    @Override
    public void showError(Throwable error) {
        Toast.makeText(getActivity(), error.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showData(List<Repository> items) {
        mListAdapter.setList(items);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_act_menu, menu);
        setSearchView(menu);
    }

    @Override
    public void showOrderPopUpMenu() {
        PopupMenu popup = new PopupMenu(getContext(),
                getActivity().findViewById(R.id.action_filter));
        popup.getMenuInflater().inflate(R.menu.filter_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.filter_stars:
                            mPresenter.changeRepoOrder(RepoOrderType.STARS);
                            break;
                        case R.id.filter_forks:
                            mPresenter.changeRepoOrder(RepoOrderType.FORKS);
                            break;
                        default:
                            mPresenter.changeRepoOrder(RepoOrderType.UPDATED);
                            break;
                    }
                    return true;
                });

        popup.show();
    }

    @Override
    public void changeRepoOrder(RepoOrderType orderType){
        mListAdapter.setRepoOrderType(orderType);
        setToolbarSubTitle(getString(R.string.order_by) + orderType.toString().toLowerCase());
    }

    /**
     * Listener for clicks on repositories in the RecyclerView.
     */
    RepositoryItemListener mItemListener = new RepositoryItemListener() {
        @Override
        public void onAvatarClick(Owner owner) {
            // mPresenter.openOwnerDetails(owner);
            Intent intent = new Intent(getActivity(), UserDetailActivity.class);
            intent.putExtra("OWNER", owner);
            startActivity(intent);
        }

        @Override
        public void onRepoClick(Repository repository) {
            // mPresenter.openRepositoryDetails(repository);
            Intent intent = new Intent(getActivity(), RepositoryDetailActivity.class);
            intent.putExtra("REPOSITORY", repository);
            startActivity(intent);
        }
    };

    /**
     * Used with the filter spinner in the tasks list.
     */
    public enum RepoOrderType {
        /**
         * Order by number of stars.
         */
        STARS,

        /**
         * Order by number of forks.
         */
        FORKS,

        /**
         * Order by number of updates.
         */
        UPDATED
    }

}

