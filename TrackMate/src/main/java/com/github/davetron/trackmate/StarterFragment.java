package com.github.davetron.trackmate;

import android.app.Activity;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StarterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StarterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StarterFragment extends Fragment {

    private static final String TAG = "StarterFrag";

    private int MARKS_PAUSE = 10;
    private int SET_PAUSE = 10;
    private int GO_PAUSE = 10;

    private OnFragmentInteractionListener mListener;
    private CountDownTimer countdown;

    public StarterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StarterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StarterFragment newInstance() {

        StarterFragment fragment = new StarterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_starter, container, false);

        final SeekBar marksSeekbar = (SeekBar) view.findViewById(R.id.marksSeekBar);
        final TextView marksValue = (TextView) view.findViewById(R.id.marksValue);

        //final SeekBar setSeekbar = (SeekBar) view.findViewById(R.id.SeekBar);
        final TextView setValue = (TextView) view.findViewById(R.id.marksValue);

        final SeekBar goSeekbar = (SeekBar) view.findViewById(R.id.marksSeekBar);
        final TextView goValue = (TextView) view.findViewById(R.id.marksValue);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        marksSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                marksValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Log.d(TAG, "About to start button setup");

        final Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            boolean running = false;

            public void onClick(View v) {

                if (running) {
                    countdown.cancel();
                    button.setText("Start");
                } else {
                    button.setText("Cancel");
                    final long duration = marksSeekbar.getProgress() * 1000;
                    //TODO add/subtract randomness to the duration
                    Log.d(TAG, "Starting countdown of duration  " + duration + " millisecs");
                    countdown = new CountDownTimer(duration, 100) {

                        @Override
                        public void onTick(long millisUntilFinished) {

                            int progress = (int) (duration - millisUntilFinished);
                            progressBar.setProgress(progress);
                            Log.d(TAG, "tick. " + progress);
                        }

                        @Override
                        public void onFinish() {
                            Log.d(TAG, "Finished!");
                            running = false;
                            button.setText("Start");

                            ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
                            //tone.startTone(ToneGenerator.TONE_PROP_BEEP, 200);
                            tone.startTone(ToneGenerator.TONE_PROP_ACK, 100);
                        }
                    };
                    countdown.start();
                    running = true;
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
