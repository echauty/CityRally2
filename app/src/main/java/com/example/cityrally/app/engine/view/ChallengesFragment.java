package com.example.cityrally.app.engine.view;

        import android.app.Activity;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ListView;
        import com.example.cityrally.app.MyActivity;
        import com.example.cityrally.app.R;
        import android.app.ActionBar;
        import com.example.cityrally.app.engine.manager.Manager;

        import java.util.ArrayList;
        //import java.util.List;

public class ChallengesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    public static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public ChallengesFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_challenges, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new ChallengesAdapter(Manager.game().getChallenges(), rootView.getContext());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mAdapter = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MyActivity) activity).onSectionAttached(2);
    }
}