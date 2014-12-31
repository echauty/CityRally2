package com.example.cityrally.app.engine.controller;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.cityrally.app.R;
import com.example.cityrally.app.directions.route.Route;
import com.example.cityrally.app.directions.route.Routing;
import com.example.cityrally.app.directions.route.RoutingListener;
import com.example.cityrally.app.engine.model.Challenge;
import com.example.cityrally.app.engine.model.Game;
import com.example.cityrally.app.engine.model.GameListener;
import com.example.cityrally.app.engine.controller.location.SimpleGeofence;
import com.example.cityrally.app.engine.view.ChallengesFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Collection;

public class ChallengesController extends RecyclerView.Adapter<ChallengesController.ViewHolder> implements RoutingListener{

    private Challenge[] mChallenges;

    public LatLng start;
    public LatLng end;


    @Override
    public void onRoutingFailure() {

    }

    @Override
    public void onRoutingStart() {

    }


    @Override
    public void onRoutingSuccess(PolylineOptions mPolyOptions, Route route) {
        Log.e("route","route");

        PolylineOptions polyoptions = new PolylineOptions();
        polyoptions.color(Color.BLUE);
        polyoptions.width(10);
        polyoptions.addAll(mPolyOptions.getPoints());
        Controller.getLocation().getMap().addPolyline(polyoptions);

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(start);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
        Controller.getLocation().getMap().addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(end);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        Controller.getLocation().getMap().addMarker(options);
    }


    public void executeRoute(LatLng start, LatLng end){
        this.start = start;
        this.end = end;
        Routing routing = new Routing(Routing.TravelMode.WALKING);
        routing.registerListener(this);
        routing.execute(start, end);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView mTextView;
        public Button mExploreButton;
        public CardView mCard;
        public TextView mTitleView;
        public Button mSolveButton;
        public ImageView mSolvedView;
        public ImageView mImageView;


        public ViewHolder(View v) {
            super(v);
            mSolveButton = (Button) v.findViewById(R.id.solveButton);
            mCard = (CardView) v.findViewById(R.id.cardView);
            mTitleView = (TextView) v.findViewById(R.id.title);
            mExploreButton = (Button) v.findViewById(R.id.exploreButton);
            mTextView = (TextView) v.findViewById(R.id.textView);
            mSolvedView = (ImageView) v.findViewById(R.id.solvedImage);
            mImageView = (ImageView) v.findViewById(R.id.image);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChallengesController(Collection<Challenge> challenges, Context context) {
        mChallenges = challenges.toArray(new Challenge[challenges.size()]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mChallenges.length;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChallengesController.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int index) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Challenge challenge = mChallenges[index];

        holder.mTitleView.setText(challenge.getTitle());
        holder.mTextView.setText(challenge.getText());

        if (challenge.isUnlocked()) {
            holder.mCard.setVisibility(View.VISIBLE);
            holder.mSolveButton.setEnabled(true);
            if (!challenge.isSolved()) {
                if (!challenge.isInPosition()){
                    holder.mSolveButton.setVisibility(View.INVISIBLE);
                }
                else{
                    holder.mSolveButton.setVisibility(View.VISIBLE);
                }
                holder.mSolvedView.setVisibility(View.INVISIBLE);
            } else {
                holder.mSolvedView.setVisibility(View.VISIBLE);
            }
        } else {
            holder.mCard.setVisibility(View.GONE);
        }

        if (challenge.isSolved()) {
            Log.e("solved", challenge.toString());
            holder.mSolvedView.setVisibility((View.VISIBLE));
            //we verify the next challenge is unlocked
            //for 3 challenges
            /*
            Log.e("position"," "+ position);
            if (position >= 2) {
                Challenge nextChallenge = mChallenges[position - 1];
                if (!nextChallenge.isUnlocked()) {
                    nextChallenge.setUnlocked(true);
                    Controller.game().saveChallenges();
                    Log.e("unlocked",nextChallenge.toString());
                }
            }
            */
        }
        if (!challenge.isSolved())
            Log.e("not solved", challenge.toString());

        holder.mExploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating services for geofences
                if (challenge.getId().equals("1")) {
                    Controller.getLocation().startTest1();
                }
                if (challenge.getId().equals("2")) {
                    Controller.getLocation().startTest2();
                }
                if (challenge.getId().equals("3")) {
                    Controller.getLocation().startTest3();
                }
                if (challenge.getId().equals("4")) {
                    Controller.getLocation().startTest4();
                }

                SimpleGeofence geofence = challenge.getGeofence();
                Controller.getLocation().moveCamera(geofence.getLatitude(), geofence.getLongitude());
                Log.e("check", "lastlocation");

                try {
                    Thread.sleep(1000);

                            executeRoute(new LatLng(Controller.getLocation().getmLastLocation().getLatitude(), Controller.getLocation().getmLastLocation().getLongitude()), new LatLng(geofence.getLatitude(), geofence.getLongitude()));

                        Log.e("execute", "lastlocation");
                    //}
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                Controller.getActivity().getNavigationDrawerFragment().selectItem(0);
            }
        });

        holder.mSolveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game game = challenge.getGame();
                Log.e("onClick", challenge.toString());
                if (game != null) {
                    game.start(new GameListener() {
                        @Override
                        public void onResult(boolean success) {
                            Log.e("result", "resultat");
                            if (!challenge.isSolved() && success) {
                                challenge.setSolved(true);
                                Integer id1 = Integer.parseInt(challenge.getId());

                                //
                                Toast.makeText(Controller.getActivity(), "New clue available :)", Toast.LENGTH_LONG).show();

                                //we reveal the new clue
                                //revealClue(id1);

                                //we unlock the next challenge
                                Controller.getGame().unlockNextChallenge(id1);
                                //Controller.getGame().saveChallenges();
                                Controller.getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ChallengesFragment.mAdapter != null) {
                                            ChallengesFragment.mAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
/*
    public void revealClue(Integer nbClue){
        TextView tv;
        switch(nbClue){
            case 1 :
                tv = (TextView) Controller.getmMysteryFragment().getActivity().findViewById(R.id.clue1);
                tv.setText(R.string.clue1);
                break;
            case 2:
                tv = (TextView) Controller.getmMysteryFragment().getActivity().findViewById(R.id.clue2);
                tv.setText(R.string.clue2);
                break;
            case 3:
                tv = (TextView) Controller.getmMysteryFragment().getActivity().findViewById(R.id.clue3);
                tv.setText(R.string.clue3);
                break;
            case 4:
                tv = (TextView) Controller.getmMysteryFragment().getActivity().findViewById(R.id.clue4);
                tv.setText(R.string.clue4);
                break;
            default:
                break;
        }

    }
    */
}