package fri.uniza.sk.cookbookexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import fri.uniza.sk.cookbookexample.model.DataFragment;
import fri.uniza.sk.cookbookexample.model.Recipe;

public class MainActivity extends AppCompatActivity implements RecipesFragment.OnListFragmentInteractionListener {

    private View detailFragmentContainer = null;

    private DetailFragment detailFragment = null;
    private int curentPosition = -1;

    private DataFragment dataFragment = null;

    public DataFragment getDataFragment() {
        if (dataFragment == null) {
            dataFragment = (DataFragment) getSupportFragmentManager().findFragmentByTag(DataFragment.DATA_FRAGMENT_TAG);
            if (dataFragment == null) {
                dataFragment = new DataFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(dataFragment, DataFragment.DATA_FRAGMENT_TAG);
                fragmentTransaction.commit();
            }
        }
        return dataFragment;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find or create retain DataFragment
        // DataFragment holds data of all recipes
        getDataFragment();

        detailFragmentContainer = findViewById(R.id.detailFragment);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.listFrameLayout, RecipesFragment.newInstance());
        fragmentTransaction.commit();

        Fragment detailFragment = getSupportFragmentManager().findFragmentByTag("detailFragment");
        //Remove old unused detail fragment
        if (detailFragment != null)
            getSupportFragmentManager().beginTransaction().remove(detailFragment).commit();


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int position = savedInstanceState.getInt("detailPosition", -1);
        if (position > -1) {
            createFragment(position); // When changing screen orientation, we can't reuse existing fragment because we use different layout and fragment container
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (curentPosition > -1) {
            outState.putInt("detailPosition", curentPosition);
        }
    }

    @Override
    public void onListFragmentInteraction(Recipe item, int position) {
        createFragment(position);
    }

    private void createFragment(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DetailFragment detailFragment = DetailFragment.newInstance(position);
        curentPosition = position;

        // Test the display orientation
        if (detailFragmentContainer == null) { // Portrait orientation

            fragmentTransaction.replace(R.id.listFrameLayout, detailFragment, "detailFragment");
            fragmentTransaction.addToBackStack("detailFragment");
            fragmentTransaction.commit();

        } else { // Landscape orientation

            fragmentTransaction.replace(R.id.detailFragment, detailFragment, "detailFragment");
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

}
