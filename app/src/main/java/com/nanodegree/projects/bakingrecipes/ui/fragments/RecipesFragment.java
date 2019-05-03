package com.nanodegree.projects.bakingrecipes.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.nanodegree.projects.bakingrecipes.R;
import com.nanodegree.projects.bakingrecipes.utils.GlobalApplication;
import com.nanodegree.projects.bakingrecipes.utils.Prefs;
import com.nanodegree.projects.bakingrecipes.adapters.RecipesAdapter;
import com.nanodegree.projects.bakingrecipes.webapi.RecipesApiCallback;
import com.nanodegree.projects.bakingrecipes.webapi.RecipesSingleton;
import com.nanodegree.projects.bakingrecipes.listeners.OnRecipeClickListener;
import com.nanodegree.projects.bakingrecipes.models.Recipe;
import com.nanodegree.projects.bakingrecipes.listeners.ItemClickListener;
import com.nanodegree.projects.bakingrecipes.utils.NetworkUtils;
import com.nanodegree.projects.bakingrecipes.utils.RecyclerSpacingItemDecoration;
import com.nanodegree.projects.bakingrecipes.widget.RecipeWidgetService;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * REcipe Fragment that is shown in the Home Screen
 */
public class RecipesFragment extends Fragment {

    @BindView(R.id.recipes_list_recyclerview)
    RecyclerView recipesListRecyclerView;

    @BindView(R.id.noDataContainer)
    LinearLayout linearLayout;

    private GlobalApplication globalApplication;
    private static String RECIPES_KEY = "recipes";

    private OnRecipeClickListener onRecipeClickListener;
    private Unbinder unbinder;
    private List<Recipe> recipeList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewRoot = inflater.inflate(R.layout.fragment_recipes, container, false);
        unbinder = ButterKnife.bind(this, viewRoot);

        linearLayout.setVisibility(View.VISIBLE);

        recipesListRecyclerView.setVisibility(View.GONE);
        recipesListRecyclerView.setHasFixedSize(true);

        boolean isTabletLayout = getResources().getBoolean(R.bool.isTabletLayout);
        if (isTabletLayout) {
            recipesListRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));
        } else {
            recipesListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }

        recipesListRecyclerView.addItemDecoration(new RecyclerSpacingItemDecoration((int) getResources().getDimension(R.dimen.medium_padding)));
        recipesListRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());

        return viewRoot;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        globalApplication = (GlobalApplication) getActivity().getApplication();

        globalApplication.setIdleState(false);


        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPES_KEY)) {
            recipeList = savedInstanceState.getParcelableArrayList(RECIPES_KEY);

            recipesListRecyclerView.setAdapter(new RecipesAdapter(getActivity().getApplicationContext(), recipeList, new ItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    onRecipeClickListener.onRecipeSelected(recipeList.get(position));
                }
            }));
            setVisibility();
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeClickListener) {
            onRecipeClickListener = (OnRecipeClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onRecipeClickListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //  unbind the view n DestroyView
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (recipeList != null && !recipeList.isEmpty())
            outState.putParcelableArrayList(RECIPES_KEY, (ArrayList<? extends Parcelable>) recipeList);
    }

    public RecipesFragment() {}

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (recipeList == null) {
                getRecipesfromAPI();
            }
        }
    };

    private void getRecipesfromAPI() {
        if (NetworkUtils.isNetworkAvailable(getActivity().getApplicationContext())) {
            RecipesSingleton.getInstance().getRecipesFromApiCall(new RecipesApiCallback<List<Recipe>>() {
                @Override
                public void onResponse(final List<Recipe> result) {
                    if (result != null) {
                        recipeList = result;
                        recipesListRecyclerView.setAdapter(new RecipesAdapter(getActivity().getApplicationContext(), recipeList, new ItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                onRecipeClickListener.onRecipeSelected(recipeList.get(position));
                            }
                        }));
                        if (Prefs.GetRecipeFromPreference(getActivity().getApplicationContext()) == null) {
                            RecipeWidgetService.updateWidget(getActivity(), recipeList.get(0));
                        }
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.unable_to_load_data), Toast.LENGTH_SHORT).show();
                    }
                    setVisibility();
                }

                @Override
                public void onCancel() {
                    setVisibility();
                }

            });
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_network_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void setVisibility() {

        if (recipeList != null && recipeList.size() > 0) {
            recipesListRecyclerView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        } else {
            recipesListRecyclerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        globalApplication.setIdleState(true);
    }
}
