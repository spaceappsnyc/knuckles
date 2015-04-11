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

/**
 * Created by alice on 4/11/15.
 */
public class PlaceholderFragment extends Fragment{


    TextView startLabel;
    Button listenButton;
    PlaceholderFragmentListener listener;

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        startLabel = (TextView)rootView.findViewById(R.id.startlabel);
        listenButton = (Button)rootView.findViewById(R.id.listen_button);

        listenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("voice", "listening for command");
                listener.displaySpeechRecognizer();
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

    public void setText(String msg){
        startLabel.setText(msg);
    }

    public interface PlaceholderFragmentListener{

        public void displaySpeechRecognizer();
    }


}
