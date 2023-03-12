package io.azet.thingsmonitor.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.azet.thingsmonitor.R;


public class DashboardFragment extends Fragment {

    private static final String DOWNLOAD_UPDATE = "azet.io.settings.download.update";
    private static final String NEW_FEED = "azet.io.feed.update";
    private static final String BAD_CHANNEL = "azet.io.feed.error";

    int currentField = 0;
    Float minimum;
    Float average;
    Float maximum;
    Float[] values = new Float[8];
    String[] names = new String[8];

    ArrayList<Float[]> allFeeds = new ArrayList<>();

    ImageView img;
    TextView txtFieldName;
    TextView txtMin;
    TextView txtMinUnit;
    TextView txtMax;
    TextView txtMaxUnit;
    TextView txtAvg;
    TextView txtAvgUnit;
    TextView txtValue;
    TextView txtUnit;
    TextView txtDate;
    TextView txtAlert;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(NEW_FEED)) {
                Log.d("Dashboard", "new feed received");
                txtAlert.setVisibility(View.GONE);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                txtDate.setText(sdf.format(new Date()));
                parseFeeds(intent);
                if(values[currentField] != null) {
                    txtValue.setText(String.valueOf(values[currentField]));
                }
                parseNames(intent);
                if (names[currentField] != null) {
                    txtFieldName.setText(names[currentField].toUpperCase());
                    if (names[currentField].equals("temperature")) {
                        img.setVisibility(View.VISIBLE);
                        img.setImageDrawable(getResources().getDrawable(R.drawable.thermometer));
                        setUnits("°C");
                    } else if (names[currentField].equals("humidity")) {
                        img.setVisibility(View.VISIBLE);
                        img.setImageDrawable(getResources().getDrawable(R.drawable.hygrometer));
                        setUnits("%");
                    } else if (names[currentField].equals("air pressure")) {
                        img.setVisibility(View.VISIBLE);
                        img.setImageDrawable(getResources().getDrawable(R.drawable.gauge));
                        setUnits("hPa");
                    } else {
                        img.setVisibility(View.GONE);
                        setUnits("");
                    }
                }
                parseAtributes();
            } else if (action.equals(BAD_CHANNEL)) {
                txtAlert.setText("Wrong channel settings!");
                txtAlert.setVisibility(View.VISIBLE);
            } else if (action.equals(DOWNLOAD_UPDATE)) {
                currentField = 0;
                minimum = null;
                average = null;
                maximum = null;
                values = new Float[8];
                allFeeds.clear();
            }
        }
    };


    private void setUnits(String unit) {
        txtUnit.setText(unit);
        txtAvgUnit.setText(unit);
        txtMaxUnit.setText(unit);
        txtMinUnit.setText(unit);
    }

    private void parseAtributes() {
        if (names[currentField] != null && values[currentField] != null) {
            minimum = values[currentField];
            txtMin.setText(String.valueOf(minimum));
            average = values[currentField];
            txtMax.setText(String.valueOf(minimum));
            maximum = values[currentField];
            int size = 1;
            for (Float[] floats : allFeeds) {
                if (floats[currentField] != null && minimum > floats[currentField]) {
                    minimum = floats[currentField];
                    txtMin.setText(String.valueOf(minimum));
                }
                if (floats[currentField] != null && maximum < floats[currentField]) {
                    maximum = floats[currentField];
                    txtMax.setText(String.valueOf(maximum));

                }
                if (floats[currentField] != null) {
                    average = (average*size + floats[currentField])/(++size);
                    txtAvg.setText(String.valueOf(average));

                }
            }
        }
    }

    private void parseNames(Intent intent) {
        for (int i = 0; i< 8; i++) {
            if (intent.getExtras().get("nameField" + String.valueOf(i+1)) != null) {
                names[i] = (intent.getExtras().getString("nameField"+String.valueOf(i+1)));
            } else {
                names[i] = null;
            }
        }
        allFeeds.add(values);
    }


    private void parseFeeds(Intent intent) {
        for (int i = 0; i< 8; i++) {
            if (intent.getExtras().get("field" + String.valueOf(i+1)) != null) {
                values[i] = Float.valueOf(intent.getExtras().getString("field"+String.valueOf(i+1)));
            } else {
                values[i] = null;
            }
        }
        allFeeds.add(values);
    }

    private OnFragmentInteractionListener mListener;

    public DashboardFragment() { }

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DOWNLOAD_UPDATE);
        filter.addAction(NEW_FEED);
        filter.addAction(BAD_CHANNEL);


        getActivity().registerReceiver(receiver, filter);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 8; i++) {
                    if (names[i] != null) {
                        currentField = i;
                        break;
                    }
                }
            }
        });
        img = (ImageView) view.findViewById(R.id.img);
        txtFieldName = (TextView) view.findViewById(R.id.txtFieldName);
        txtMin = (TextView) view.findViewById(R.id.txtMin);
        txtMinUnit = (TextView) view.findViewById(R.id.txtMinUnit);
        txtMax = (TextView) view.findViewById(R.id.txtMax);
        txtMaxUnit = (TextView) view.findViewById(R.id.txtMaxUnit);
        txtAvg = (TextView) view.findViewById(R.id.txtAvg);
        txtAvgUnit = (TextView) view.findViewById(R.id.txtAvgUnit);
        txtValue = (TextView) view.findViewById(R.id.txtValue);
        txtUnit = (TextView) view.findViewById(R.id.txtUnit);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        txtAlert = (TextView) view.findViewById(R.id.txtAlert);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
