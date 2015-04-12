package me.valour.knucklescontrol;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.valour.knucklescontrol.views.HandView;

/**
 * Created by alice on 4/11/15.
 */
public class PlaceholderFragment extends Fragment{


    TextView mStartLabel;
    Button mListenButton;
    Button mLightsButton;
    HandView hand;
    PlaceholderFragmentListener listener;
    TextView mStatusView;

    public PlaceholderFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mListenButton = (Button)rootView.findViewById(R.id.listen_button);
        mLightsButton = (Button)rootView.findViewById(R.id.lights_button);
        mStatusView = (TextView) rootView.findViewById(R.id.status);

        hand = (HandView)rootView.findViewById(R.id.handview);

        mListenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("voice", "listening for command");
                listener.displaySpeechRecognizer();
            }
        });

        mLightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if(MainActivity.light_on) {
                // MainActivity.showLight();
                // } else {
                // MainActivity.hideLight();
                // }
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (PlaceholderFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void setText(String msg) {
        mStartLabel.setText(msg);
    }

    public interface PlaceholderFragmentListener {
        public void displaySpeechRecognizer();
    }

    public void registerTempChange(int delta){
        hand.tempChange = delta;
        hand.invalidate();

        if(delta==1){
            mStatusView.setBackgroundColor(getResources().getColor(R.color.red));
            mStatusView.setText("HEATING");
        } else if(delta==-1){
            mStatusView.setBackgroundColor(getResources().getColor(R.color.blue));
            mStatusView.setText("COOLING");
        }
    }

    public void registerFingerTemperatures(int index, float temp){
        hand.fingersTemp[index] = temp;
        hand.invalidate();
    }

}
