package fri.uniza.sk.cookbookexample.model;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import fri.uniza.sk.cookbookexample.utilities.LoadData;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment {

    final public static String DATA_FRAGMENT_TAG = "dataFrag";
    private List<Recipe> recipes;

    public DataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);

        try {
            // Load Data from file
            InputStream openFile = getContext().getAssets().open("recepty.json");
            recipes = LoadData.loadRecepies(openFile);
            Log.d("CookBook", String.valueOf(recipes.size()));

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("CookBook", "Data read error");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return null;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }


}
