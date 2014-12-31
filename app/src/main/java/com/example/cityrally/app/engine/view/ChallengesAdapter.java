package com.example.cityrally.app.engine.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.example.cityrally.app.R;
import com.example.cityrally.app.engine.games.Game;
import com.example.cityrally.app.engine.games.GameResultListener;
import com.example.cityrally.app.engine.manager.location.SimpleGeofence;
import com.example.cityrally.app.engine.view.Challenge;
import com.example.cityrally.app.engine.manager.Manager;

import java.util.Collection;

public class ChallengesAdapter extends RecyclerView.Adapter<ChallengesAdapter.ViewHolder> {

    private Challenge[] mChallenges;
    private int lastPosition = -1;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout mContainer;
        public ImageView mImageView;
        public TextView mTitleView;
        public TextView mTextView;
        public Button mExploreButton;
        public Button mSolveButton;
        public ImageView mSolvedView;

        public ViewHolder(View v) {
            super(v);

            mContainer = (LinearLayout) v.findViewById(R.id.card_relative_layout);
            mImageView = (ImageView) v.findViewById(R.id.info_image);
            mTitleView = (TextView) v.findViewById(R.id.info_title);
            mTextView = (TextView) v.findViewById(R.id.info_text);
            mExploreButton = (Button) v.findViewById(R.id.exploreButton);
            mSolveButton = (Button) v.findViewById(R.id.solveButton);
            mSolvedView = (ImageView) v.findViewById(R.id.info_solved);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChallengesAdapter(Collection<Challenge> challenges, Context context) {
        mChallenges = challenges.toArray(new Challenge[challenges.size()]);
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChallengesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_challenges, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...

        //TextView textView = (TextView) v.findViewById(R.id.info_text);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);
        final Challenge challenge = mChallenges[position];

        holder.mTitleView.setText(challenge.getTitle());
        holder.mTextView.setText(challenge.getText());

        if (challenge.isUnlocked()) {
            holder.mContainer.setVisibility(View.VISIBLE);
            holder.mSolveButton.setEnabled(true);
            if (!challenge.isSolved()) {
                if (!challenge.isInPosition()){
                    holder.mSolveButton.setVisibility(View.INVISIBLE);
                }
                else{
                    holder.mSolveButton.setVisibility(View.VISIBLE);
                    holder.mSolveButton.setTextColor(Manager.activity().getResources().getColor(R.color.cardview_shadow_start_color));
                }
                holder.mSolvedView.setVisibility(View.INVISIBLE);
            } else {
                holder.mSolveButton.setTextColor(Manager.activity().getResources().getColor(android.R.color.holo_green_light));
                holder.mSolvedView.setVisibility(View.VISIBLE);
            }
        } else {
            holder.mContainer.setVisibility(View.GONE);
        }

<<<<<<< HEAD
=======
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
                    Manager.game().saveChallenges();
                    Log.e("unlocked",nextChallenge.toString());
                }
            }
            */
        }
        if (!challenge.isSolved())
            Log.e("not solved", challenge.toString());

>>>>>>> all challenges working
        holder.mExploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating services for geofences
                if (challenge.getId().equals("1")) {
                    Manager.game().getGameWithId("photo").onStart();
                }
                if (challenge.getId().equals("2")) {
                    Manager.location().startScenario2();
                }
                if (challenge.getId().equals("3")) {
                    Manager.location().startScenario3();
                }
                if (challenge.getId().equals("4")) {
                    Manager.location().startScenario4();
                }

                SimpleGeofence geofence = challenge.getGeofence();
                Manager.location().addMarker(challenge.getId(), Manager.activity().getText(challenge.getTitle()).toString(), geofence.getLatitude(), geofence.getLongitude());
                Manager.location().moveCamera(geofence.getLatitude(), geofence.getLongitude());

                Manager.activity().getNavigationDrawerFragment().selectItem(0);
            }
        });

        holder.mSolveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(Manager.activity());
                dialog.setCancelable(false);
                dialog.setMessage(challenge.getChallenge());
                dialog.setPositiveButton(R.string.start, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Game game = challenge.getGame();
                        Log.e("onClick", challenge.toString());
                        if (game != null) {
                            game.start(new GameResultListener() {
                                @Override
                                public void onResult(boolean success) {
                                    if (success && !challenge.isSolved()) {
                                        challenge.setSolved(true);
<<<<<<< HEAD
=======
                                        Integer id1 = Integer.parseInt(challenge.getId());
                                        if (id1 <= 3){
                                           Challenge challenge2 = Manager.game().getChallengesMap().get(String.valueOf(id1+1));
                                           challenge2.setUnlocked(true);
                                        }
                                        //int id1 = challenge.getId();
>>>>>>> all challenges working
                                        Manager.game().saveChallenges();

                                        Manager.activity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (ChallengesFragment.mAdapter != null) {
                                                    ChallengesFragment.mAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        });
                                    }
                                    Log.e("result", "" + success);
                                }
                            });
                        }
                    }
                });
                dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
                dialog.show();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mChallenges.length;
    }
}