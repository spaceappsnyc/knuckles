package me.valour.knucklescontrol.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.valour.knucklescontrol.R;
import me.valour.knucklescontrol.views.HandView;

/**
 * Created by alice on 4/11/15.
 */
public class PlaceholderFragment extends Fragment{


    Button mListenButton;
    Button mLightsButton;
    HandView hand;
    PlaceholderFragmentListener listener;
    TextView consolePrinter;

    public PlaceholderFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        hand = (HandView)rootView.findViewById(R.id.handview);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                listener.displaySpeechRecognizer();
                return false;
            }
        });

        consolePrinter = (TextView)rootView.findViewById(R.id.console_printer);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (PlaceholderFragmentListener) activity;
            listener.updatePreferences();
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
        consolePrinter.setText(msg);
    }

    public interface PlaceholderFragmentListener {
        public void displaySpeechRecognizer();
        public void updatePreferences();
    }

    public void registerTempChange(int delta){
        hand.tempChange = delta;
        hand.invalidate();

    }

    public void registerFingerTemperatures(int index, float temp){
        hand.fingersTemp[index] = temp;
        hand.invalidate();
    }

    public void toogleLights(){
        hand.lights = !hand.lights;
        hand.invalidate();
    }

    public boolean isLightOn(){
        return hand.lights;
    }

}
