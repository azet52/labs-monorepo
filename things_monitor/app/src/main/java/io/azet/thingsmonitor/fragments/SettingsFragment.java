package io.azet.thingsmonitor.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;

import org.apache.commons.lang3.math.NumberUtils;

import io.azet.thingsmonitor.R;

public class SettingsFragment extends Fragment {

    private static final String DOWNLOAD_UPDATE = "azet.io.settings.download.update";
    private static final String NOTIFICATION_UPDATE = "azet.io.settings.notifcation.update";

    private OnFragmentInteractionListener mListener;

    RadioGroup rgAccess;
    ImageView imgChannel;
    EditText editChannel;
    LinearLayout llApiKey;
    EditText editReadKey;
    Spinner spinnerField;
    EditText editThreshold;
    Spinner spinnerAction;
    Switch swEnabled;

    String access = "public";


    public SettingsFragment() {
        // Required empty public constructor
    }


    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();

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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        rgAccess = (RadioGroup) view.findViewById(R.id.rgAccess);
        llApiKey = (LinearLayout) view.findViewById(R.id.llApiKey);
        editReadKey = (EditText) view.findViewById(R.id.editReadKey);
        imgChannel = (ImageView) view.findViewById(R.id.imgChannel);
        editChannel = (EditText) view.findViewById(R.id.editChannel);
        spinnerField = (Spinner)view.findViewById(R.id.spinnerField);
        editThreshold = (EditText)view.findViewById(R.id.editThreshold);
        spinnerAction = (Spinner)view.findViewById(R.id.spinnerAction);
        swEnabled = (Switch) view.findViewById(R.id.swEnabled);

        rgAccess.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbPublic) {
                    access = "public";
                    llApiKey.setVisibility(View.GONE);
                } else {
                    access = "private";
                    llApiKey.setVisibility(View.VISIBLE);
                }
            }
        });

        editChannel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    onOutfocus();
                } else {
                    editChannel.setText("");
                }
            }
        });

        editReadKey.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    onOutfocus();
                } else {
                    editReadKey.setText("");
                }
            }
        });

        editThreshold.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    editThreshold.setText("");
                }
            }
        });

        swEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    String field = spinnerField.getSelectedItem().toString();
                    Float threshold = null;
                    String action = spinnerAction.getSelectedItem().toString();
                    if (NumberUtils.isCreatable(editThreshold.getText().toString())) {
                        threshold = NumberUtils.toFloat(editThreshold.getText().toString());
                    }
                    if (!field.equals("None") && !action.equals("None") && threshold != null) {
                        onNotificationChange(true, field, threshold, action);
                    } else {
                        compoundButton.setChecked(false);
                    }
                } else {
                    onNotificationChange(false, "", 0.0f, "");
                }
            }
        });

        return view;
    }

    public void onOutfocus() {
        String channel = editChannel.getText().toString();
        if (channel.length() > 4) {
            Intent settings = new Intent();
            settings.setAction(DOWNLOAD_UPDATE);
            settings.putExtra("access", access);
            settings.putExtra("channel", channel);
            imgChannel.setImageDrawable(getResources().getDrawable(R.drawable.checked));
            String apiKey = editChannel.getText().toString();
            if (editReadKey.getVisibility() == View.VISIBLE && editReadKey.getText().toString().length() == 16) {
                settings.putExtra("apiKey", apiKey);
            }
            getActivity().sendBroadcast(settings);
        }

    }

    public void onNotificationChange(boolean enabled, String field, Float threshold, String action) {
        Intent settings = new Intent();
        settings.setAction(NOTIFICATION_UPDATE);
        settings.putExtra("enabled", enabled);
        settings.putExtra("field", field);
        settings.putExtra("threshold", (float)threshold);
        settings.putExtra("action", action);
        getActivity().sendBroadcast(settings);
    }

    public void onButtonPressed(Uri uri) { }

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
