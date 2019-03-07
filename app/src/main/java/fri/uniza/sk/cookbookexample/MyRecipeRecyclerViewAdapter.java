package fri.uniza.sk.cookbookexample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fri.uniza.sk.cookbookexample.RecipesFragment.OnListFragmentInteractionListener;
import fri.uniza.sk.cookbookexample.model.Recipe;

import java.util.List;


public class MyRecipeRecyclerViewAdapter extends RecyclerView.Adapter<MyRecipeRecyclerViewAdapter.ViewHolder> {

    private final List<Recipe> recipes;
    private final OnListFragmentInteractionListener mListener;
    private Context context;
    private RecyclerView.LayoutManager layoutManager;

    public MyRecipeRecyclerViewAdapter(List<Recipe> items, OnListFragmentInteractionListener listener) {
        recipes = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        if (layoutManager.getClass().equals(LinearLayoutManager.class)) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_recipe_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_recipe_item_cardview, parent, false);
        }


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = recipes.get(position);

        holder.mImageView.setImageBitmap(recipes.get(position).getBitmapFromAsset(context));
        holder.mContentView.setText(recipes.get(position).title);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mContentView;
        public Recipe mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.smallImage);

            // Sets whether the View's Outline should be used to clip the contents of the View. (Used for rounded corners)
            mImageView.setClipToOutline(true);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        layoutManager = recyclerView.getLayoutManager();

    }
}
