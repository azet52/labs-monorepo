package io.azet.thingsmonitor.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.realm.implementation.RealmLineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.azet.thingsmonitor.R;
import io.azet.thingsmonitor.Record;
import io.azet.thingsmonitor.rest.WebServices;
import io.azet.thingsmonitor.rest.models.ChannelFeeds;
import io.azet.thingsmonitor.rest.onResponseListener;
import io.realm.Realm;
import io.realm.RealmResults;


public class ChartsFragment extends Fragment {

    String access = null;
    String channel = null;
    String apiKey = null;

    WebServices webServices;

    ArrayList<Float> values = new ArrayList<>();
    ArrayList<Float> numbers = new ArrayList<>();

    Calendar calendarStart;
    Calendar calendarEnd;

    String formatStart;
    String formatEnd;

    LineChart lineChart;
    Realm realm;
    TextView txtStart;
    TextView txtEnd;

    private OnFragmentInteractionListener mListener;

    public ChartsFragment() {
    }

    public static ChartsFragment newInstance() {
        ChartsFragment fragment = new ChartsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setAccess(String access) {
        this.access = access;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private void updateStart() {
        String format = "yyyy-MM-dd";
        String format2 = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        SimpleDateFormat sdf2 = new SimpleDateFormat(format2);

        formatStart = sdf.format(calendarStart.getTime());
        txtStart.setText(sdf2.format(calendarStart.getTime()));
    }

    private void updateEnd() {
        String format = "yyyy-MM-dd";
        String format2 = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        SimpleDateFormat sdf2 = new SimpleDateFormat(format2);

        formatEnd = sdf.format(calendarEnd.getTime());
        txtEnd.setText(sdf2.format(calendarEnd.getTime()));

        if (access.equals("private")) {
            webServices.getPrivateChannelRangeFeeds(new onResponseListener<ChannelFeeds>() {
                @Override
                public void onSuccess(ChannelFeeds channelFeeds) {
                    if (channelFeeds.getFeeds().size() > 0) {
                        for (int i = 0; i < channelFeeds.getFeeds().size(); i++) {
                            values.add(Float.valueOf(channelFeeds.getFeeds().get(i).getField1()));
                            numbers.add(Float.valueOf(channelFeeds.getFeeds().get(i).getEntryId()));
                        }
                        updateDatabase();
                    }
                }

                @Override
                public void onError() {
                }
            }, channel, apiKey, 1000, formatStart, formatEnd);
        } else {
            webServices.getPublicChannelRangeFeeds(new onResponseListener<ChannelFeeds>() {
                @Override
                public void onSuccess(ChannelFeeds channelFeeds) {
                    if (channelFeeds.getFeeds().size() > 0) {
                        for (int i = 0; i < channelFeeds.getFeeds().size(); i++) {
                            values.add(Float.valueOf(channelFeeds.getFeeds().get(i).getField1()));
                            numbers.add(Float.valueOf(channelFeeds.getFeeds().get(i).getEntryId()));
                        }
                        updateDatabase();
                    }
                }

                @Override
                public void onError() {
                }
            }, channel,  1000, formatStart, formatEnd);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charts, container, false);
        calendarStart = Calendar.getInstance();
        calendarEnd = Calendar.getInstance();

        lineChart = (LineChart) view.findViewById(R.id.chart);
        txtEnd = (TextView) view.findViewById(R.id.txtEnd);
        txtStart = (TextView) view.findViewById(R.id.txtStart);

        final DatePickerDialog.OnDateSetListener dateStart = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, monthOfYear);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStart();
            }

        };

        final DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, monthOfYear);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                webServices = WebServices.getInstance();
                updateEnd();

            }

        };

        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), dateStart, calendarStart
                        .get(Calendar.YEAR), calendarStart.get(Calendar.MONTH),
                        calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), dateEnd, calendarEnd
                        .get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH),
                        calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        return view;
    }

    private void updateDatabase() {
        Realm.init(getContext());
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (int i = 0; i < numbers.size(); i++) {
            realm.copyToRealm(new Record(values.get(i), numbers.get(i)));
        }
        realm.commitTransaction();
        RealmResults<Record> results = realm.where(Record.class).findAll();
        RealmLineDataSet<Record> dataSet = new RealmLineDataSet<Record>(results, "number", "value" );
        ArrayList<ILineDataSet> dataSetList = new ArrayList<ILineDataSet>();
        dataSetList.add(dataSet);
        LineData lineData = new LineData(dataSetList);
        lineChart.setData(lineData);
        lineChart.invalidate();

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
