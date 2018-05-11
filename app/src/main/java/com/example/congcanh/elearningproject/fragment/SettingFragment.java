package com.example.congcanh.elearningproject.fragment;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.congcanh.elearningproject.R;
import com.example.congcanh.elearningproject.activity.LoginActivity;
import com.example.congcanh.elearningproject.activity.MainActivity;
import com.example.congcanh.elearningproject.base.BaseFragment;
import com.example.congcanh.elearningproject.base.BaseView;
import com.example.congcanh.elearningproject.helper.AlarmReceiver;
import com.example.congcanh.elearningproject.model.CircleTransform;
import com.example.congcanh.elearningproject.model.MyConstants;
import com.example.congcanh.elearningproject.mvp.SettingFragmentVP;
import com.example.congcanh.elearningproject.presenter.SettingFragmentPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment implements SettingFragmentVP.View, BaseView {
    private SettingFragmentPresenter presenter;
    private ListView listView;
    private AppCompatImageView profileImage;
    private TextView userName, time;
    private ArrayAdapter adapter;
    private LinearLayout signOutLinearLayout, pickupPracticeTimeLinearLayout;
    private Spinner spnCategory;
    String[] arrayString = new String[]{"Thiết lập mục tiêu", "Thiết lập nhắc nhở", "Liên hệ với nhớm phát triển",
            "Đăng xuất"};

    Integer[] levelList = new Integer[]{1,2,3,4,5,6};
    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);
    private Switch switchButton;


    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static SettingFragment inst;
    private TextView alarmTextView;
    int totalLevel=0;


    private CheckBox chkSunday;
    private CheckBox chkMonday;
    private CheckBox chkTuesday;
    private CheckBox chkWednesday;
    private CheckBox chkThursday;
    private CheckBox chkFriday;
    private CheckBox chkSaturday;

    public static SettingFragment instance() {
        return inst;
    }
    public SettingFragment() {
        // Required empty public constructor
    }
    public static SettingFragment newInstance() {
        return new SettingFragment();
    }


    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spnCategory = (Spinner) rootView.findViewById(R.id.spnCategory);

        List<String> list = new ArrayList<>();
        list.add("1 ");
        list.add("2 ");
        list.add("3 ");
        list.add("4 ");
        list.add("5 ");
        list.add("6 ");

        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCategory.setAdapter(adapter);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                    totalLevel=levelList[i];
                Toast.makeText(getContext(),levelList[i].toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //listView = (ListView)rootView.findViewById(R.id.lstView_setting);
        profileImage = rootView.findViewById(R.id.profile_image);
        userName = (TextView)rootView.findViewById(R.id.user_name);
        signOutLinearLayout = (LinearLayout)rootView.findViewById(R.id.ln_sign_out);
        pickupPracticeTimeLinearLayout = (LinearLayout)rootView.findViewById(R.id.ln_setup_time);
        time = (TextView) rootView.findViewById(R.id.tw_practice_time);

        chkSunday = (CheckBox) rootView.findViewById(R.id.alarm_details_repeat_sunday);
        chkMonday = (CheckBox) rootView.findViewById(R.id.alarm_details_repeat_monday);
        chkTuesday = (CheckBox) rootView.findViewById(R.id.alarm_details_repeat_tuesday);
        chkWednesday = (CheckBox) rootView.findViewById(R.id.alarm_details_repeat_wednesday);
        chkThursday = (CheckBox) rootView.findViewById(R.id.alarm_details_repeat_thursday);
        chkFriday = (CheckBox) rootView.findViewById(R.id.alarm_details_repeat_friday);
        chkSaturday = (CheckBox) rootView.findViewById(R.id.alarm_details_repeat_saturday);
        //tui nghĩ nếu có thể ông nên lưu state của switchButton lại và load nó lên mõi khi vào đây.
        //

        alarmManager = (AlarmManager) this.getActivity().getSystemService(ALARM_SERVICE);


        presenter = new SettingFragmentPresenter();
        presenter.attachView(this);
        setProfileImage();
        setUserName();

        time.setText(timeHour + ":" + timeMinute);

        signOutLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.logout();

                ((MainActivity)getActivity()).startActivity(new Intent(getActivity(),LoginActivity.class));
                ((MainActivity)getActivity()).finish();
            }
        });

        pickupPracticeTimeLinearLayout.setOnClickListener(listener1);

        alarmManager = (AlarmManager) view.getContext().getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(view.getContext(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(view.getContext(), 0, myIntent, 0);

        //attachListView();


        switchButton =(Switch) rootView.findViewById(R.id.switch_practice_reminder);
        switchButton.setChecked(false);
        if (switchButton.isChecked()) {
            Toast.makeText(getContext(), "Alarm is working", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), "Alarm Stopped", Toast.LENGTH_SHORT).show();
            alarmManager.cancel(pendingIntent);
        }
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    Toast.makeText(getContext(), "Turn on Alarm", Toast.LENGTH_SHORT).show();

                    createAlarm();

                } else {
                    Toast.makeText(getContext(), "Turn off Alarm", Toast.LENGTH_SHORT).show();
                    alarmManager.cancel(pendingIntent);
                }
            }
        });
        setCheckbox();

    }

    public void setCheckbox()
    {
        chkSunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    chkSunday.setChecked(true);
                }
                else
                {
                    chkSunday.setChecked(false);
                }
                alarmManager.cancel(pendingIntent);
                createAlarm();
            }
        });

        chkMonday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    chkMonday.setChecked(true);
                }
                else
                {
                    chkMonday.setChecked(false);
                }
                alarmManager.cancel(pendingIntent);
                createAlarm();
            }
        });
        chkTuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    chkTuesday.setChecked(true);
                }
                else
                    chkTuesday.setChecked(false);
                alarmManager.cancel(pendingIntent);
                createAlarm();
            }
        });

        chkWednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    chkWednesday.setChecked(true);
                }
                else
                {
                    chkWednesday.setChecked(false);
                }
                alarmManager.cancel(pendingIntent);
                createAlarm();
            }
        });
        chkThursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    chkThursday.setChecked(true);
                }
                else
                {
                    chkThursday.setChecked(false);
                }
                alarmManager.cancel(pendingIntent);
                createAlarm();
            }
        });
        chkFriday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    chkFriday.setChecked(true);
                }
                else
                {
                    chkFriday.setChecked(false);
                }
                alarmManager.cancel(pendingIntent);
                createAlarm();
            }
        });
        chkSaturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    chkSaturday.setChecked(true);
                }
                else
                {
                    chkSaturday.setChecked(false);
                }
                alarmManager.cancel(pendingIntent);
                createAlarm();
            }
        });

    }
    @Override
    protected int getLayout() {
        return R.layout.fragment_setting;
    }

    //Thiet lap hien thi anh nguoi dung lay tu FB
    @Override
    public void setProfileImage() {
        String url = presenter.getProfileImageUrl();
        Log.d("Debug", url.toString());
        Picasso.with(getActivity())
                .load(url)
                .placeholder(R.drawable.person_flat)
                .transform(new CircleTransform())
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(profileImage);
    }

    //Thiet lap hien thi ten nguoi dung
    @Override
    public void setUserName() {
        userName.setText(presenter.getUserName());
    }

    @Override
    public void attachListView() {
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayString);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                // Display the selected item text on TextView
                Toast.makeText(getActivity(),"Your favorite : " + selectedItem, Toast.LENGTH_LONG);
                switch (position){
                    case 0:
                    {
                        Toast.makeText(getContext(), "Click", Toast.LENGTH_LONG);
                        break;
                    }
                    case 1:
                    {
                        break;
                    }
                    case 3:
                    {
                        presenter.logout();

                        ((MainActivity)getActivity()).startActivity(new Intent(getActivity(),LoginActivity.class));
                        ((MainActivity)getActivity()).finish();
                        break;
                    }
                    default:
                        break;
                }
            }
        });
    }

    View.OnClickListener listener1 = new View.OnClickListener() {
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putInt(MyConstants.HOUR, timeHour);
            bundle.putInt(MyConstants.MINUTE, timeMinute);

            PickupTimeDialogFragment fragment = new PickupTimeDialogFragment(new MyHandler());
            fragment.setArguments(bundle);
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(fragment, MyConstants.TIME_PICKER);
            transaction.commit();
        }
    };

    class MyHandler extends Handler {
        @Override
        public void handleMessage (Message msg){
            Bundle bundle = msg.getData();
            timeHour = bundle.getInt(MyConstants.HOUR);
            timeMinute = bundle.getInt(MyConstants.MINUTE);
            time.setText(timeHour + ":" + timeMinute);
            Toast.makeText(getContext(), time.getText(), Toast.LENGTH_SHORT).show();

            createAlarm();


        }
    }

    private void createAlarm()
    {
        if(switchButton.isChecked())
        {
            final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
            final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);

            boolean enable=false;
            switch (nowDay)
            {
                case 1:
                    {
                        enable=chkSunday.isChecked();
                        break;
                     }
                case 2: {
                    enable = chkMonday.isChecked(); break;
                }
                case 3: {enable=chkTuesday.isChecked();
                break;}
                case 4: {
                    enable = chkWednesday.isChecked();
                break;}
                case 5: {enable=chkThursday.isChecked();
                break;}
                case 6: {
                    enable = chkFriday.isChecked();
                break;}
                case 7: {
                    enable = chkSaturday.isChecked();
                break;}
            }


            if (enable)
            {
                if ((timeHour > nowHour) |((timeHour == nowHour) && (timeMinute > nowMinute))) {


                    setAlarm();
                }
            }


        }
    }
    private void setAlarm(){




        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);
        calendar.set(Calendar.SECOND, 00);




        Intent myIntent = new Intent(getContext(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, myIntent, 0);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);



    }


    @Override
    public void onStop() {
        super.onStop();



        SharedPreferences.Editor editor =getActivity().getSharedPreferences("pref",MODE_PRIVATE).edit();
        editor.putInt("goal",totalLevel);
        editor.commit();




    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences myPref=getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        int goal=myPref.getInt("goal",1);
        Toast.makeText(getContext(), String.valueOf(goal), Toast.LENGTH_SHORT).show();

        spnCategory.setSelection(goal-1);
    }


}
