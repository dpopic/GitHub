package hr.danijelpopic.github.ui.adapter;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import hr.danijelpopic.github.R;
import hr.danijelpopic.github.model.Repository;
import hr.danijelpopic.github.ui.fragment.SearchFragment;
import hr.danijelpopic.github.ui.listener.RepositoryItemListener;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private List<Repository> mItems;
    private RepositoryItemListener mItemListener;
    private SearchFragment.RepoOrderType currentOrderType;
    private Context ctx; // for piccasso

    public RepoAdapter(Context context, List<Repository> items,
            RepositoryItemListener itemListener) {
        super();
        setList(items);
        mItemListener = itemListener;
        currentOrderType = SearchFragment.RepoOrderType.STARS; // default
        ctx = context;
    }

    public void setList(List<Repository> items) {
        mItems = checkNotNull(items);
        sortList();
        notifyDataSetChanged();
    }

    public void setRepoOrderType(SearchFragment.RepoOrderType orderType){
        currentOrderType = orderType;
        sortList();
        notifyDataSetChanged();
    }

    public SearchFragment.RepoOrderType getOrderType(){
        return currentOrderType;
    }

    public void sortList(){
        Collections.sort(mItems, (obj1, obj2) -> {
            if(currentOrderType == SearchFragment.RepoOrderType.STARS){
                return obj2.getStargazersCount().compareTo(obj1.getStargazersCount());
            } else if(currentOrderType == SearchFragment.RepoOrderType.FORKS){
                return obj2.getForksCount().compareTo(obj1.getForksCount());
            } else if(currentOrderType == SearchFragment.RepoOrderType.UPDATED){
                return obj2.getUpdatedAt().compareToIgnoreCase(obj1.getUpdatedAt());
            } else {
                return 0;
            }
        });
    }

    @Override
    public RepoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup,
            int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.search_recycler_view, null);
        v.setLayoutParams(new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RepoAdapter.ViewHolder viewHolder, int i) {
        Repository repository = mItems.get(i);
        viewHolder.tvRepTitle.setText(repository.getName());
        viewHolder.tvOwner.setText(repository.getOwner().getLogin());
        viewHolder.tvWatchers.setText(String.valueOf(repository.getWatchersCount()));
        viewHolder.tvFork.setText(String.valueOf(repository.getForksCount()));
        viewHolder.tvIssue.setText(String.valueOf(repository.getOpenIssuesCount()));

        // Get image with Picasso
        Picasso.with(ctx).load(repository.getOwner().getAvatarUrl())
                .into(viewHolder.ivAvatar);

        // llOwnerData OnClickListener - owner details
        viewHolder.llOwnerData.setOnClickListener(v -> {
            mItemListener.onAvatarClick(repository.getOwner());
        });

        // llRepoData OnClickListener - repository details
        viewHolder.llRepoData.setOnClickListener(v -> {
            mItemListener.onRepoClick(repository);
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout llRepoData;
        public LinearLayout llOwnerData;
        public ImageView ivAvatar;
        public TextView tvRepTitle;
        public TextView tvOwner;
        public TextView tvWatchers;
        public TextView tvFork;
        public TextView tvIssue;

        public ViewHolder(View itemView) {
            super(itemView);
            llRepoData = (LinearLayout) itemView.findViewById(R.id.llRepoData);
            llOwnerData = (LinearLayout) itemView.findViewById(R.id.llOwnerData);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
            tvRepTitle = (TextView) itemView.findViewById(R.id.tvRepTitle);
            tvOwner = (TextView) itemView.findViewById(R.id.tvOwner);
            tvWatchers = (TextView) itemView.findViewById(R.id.tvWatchers);
            tvFork = (TextView) itemView.findViewById(R.id.tvFork);
            tvIssue = (TextView) itemView.findViewById(R.id.tvIssue);
        }
    }
}