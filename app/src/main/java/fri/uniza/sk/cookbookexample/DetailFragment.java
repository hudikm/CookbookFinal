package fri.uniza.sk.cookbookexample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import fri.uniza.sk.cookbookexample.model.DataFragment;


public class DetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "recipePosition";
    private int position;
    private DataFragment dataFragment;

    public DetailFragment() {
        // Required empty public constructor
    }


    public static DetailFragment newInstance(int position) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM1);
        }
        dataFragment = ((MainActivity) getActivity()).getDataFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_recipe, container, false);
        initData(view);

        return view;
    }

    private void initData(View view) {
        ((ImageView) view.findViewById(R.id.recipeImage)).setImageBitmap(dataFragment.getRecipes().get(position).getBitmapFromAsset(getContext()));
        ((TextView) view.findViewById(R.id.detailRecipe)).setText(dataFragment.getRecipes().get(position).detail);
        ListView listView = (ListView) view.findViewById(R.id.ingredientsList);
        listView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dataFragment.getRecipes().get(position).ingredients));
        setListViewHeightBasedOnChildren(listView);
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = dataFragment.getRecipes().get(position).ingredients.size() * 100;
//
//        listView.setLayoutParams(params);
//        listView.requestLayout();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
