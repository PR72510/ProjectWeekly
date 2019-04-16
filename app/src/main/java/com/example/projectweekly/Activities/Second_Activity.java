package com.example.projectweekly.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.projectweekly.Adapter.CusAdapter;
import com.example.projectweekly.BroadcastReciever.NetworkStateReciever;
import com.example.projectweekly.DataManager.DataManger;
import com.example.projectweekly.DetailFragment;
import com.example.projectweekly.Interfaces.CreateUserListener;
import com.example.projectweekly.Interfaces.FragmentListener;
import com.example.projectweekly.Interfaces.MenuListener;
import com.example.projectweekly.Interfaces.NetworkStateChangeListener;
import com.example.projectweekly.Interfaces.RetrofitResponseListener;
import com.example.projectweekly.Model.CreateUserResponseModel;
import com.example.projectweekly.Model.Datum;
import com.example.projectweekly.Model.NewUser;
import com.example.projectweekly.Model.UpdateUserModel;
import com.example.projectweekly.MyDialogFragment;
import com.example.projectweekly.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.projectweekly.Activities.MainActivity.MY_PREFS;

public class Second_Activity extends AppCompatActivity implements RetrofitResponseListener, CreateUserListener, NetworkStateChangeListener, FragmentListener, MenuListener {

    NetworkStateReciever networkStateReciever;
    DataManger dataManger = DataManger.getInstance();
    private static final String TAG = "Second_Activity";
    RecyclerView recyclerView;
    CusAdapter adapter;
    LinearLayoutManager layoutManager;
    private boolean isScrolling = true;
    List<Datum> list;
    static int page = 1;
    FloatingActionButton fab;
    MyDialogFragment myDialogFragment;
    Boolean isConnected;
    CoordinatorLayout layout;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_);
        myDialogFragment = new MyDialogFragment();
        layout = findViewById(R.id.container1);
        networkStateReciever = new NetworkStateReciever();
        networkStateReciever.setOnNetworkStateChanged(this);

        myDialogFragment.setInstance(this);
        dataManger.setInstance(this);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogFragment.show(getSupportFragmentManager(), "Dialog");
            }
        });

        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        fragmentManager = getSupportFragmentManager();
        list = new ArrayList<>();

        dataManger.getList(page);
        adapter = new CusAdapter(list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(recyclerViewPagination);
        adapter.setOnItemClickListener(this);
        adapter.setOnMenuClickListener(this);
    }

    private RecyclerView.OnScrollListener recyclerViewPagination = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.d(TAG, "onScrollStateChanged: ");
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {           // user is scrolling using touch, and their finger is still on the screen
                isScrolling = true;
                Log.d(TAG, "onScrollStateChanged: " + isScrolling);
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            Log.d(TAG, "onScrolled: ");
            super.onScrolled(recyclerView, dx, dy);
            int totalItems = layoutManager.getItemCount();
            int visibleItems = layoutManager.getChildCount();
            int scrolledOutItems = layoutManager.findFirstVisibleItemPosition();
            if (isScrolling && visibleItems + scrolledOutItems == totalItems) {
                isScrolling = false;
                if (page < 4) {
                    page++;
                    dataManger.getList(page);
                }
                Log.d(TAG, "onScrolled: " + page);

            }
        }
    };

    @Override
    public void responseListener(List<Datum> list) {
        if (list != null) {
            adapter.setUserList(list);
        }
    }

    @Override
    public void createUserListener(CreateUserResponseModel user) {
        Toast.makeText(this, "User Created at :" + user.getCreatedAt(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteUserListener(int code) {
        Toast.makeText(this, "User Deleted" + code, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUserListener(UpdateUserModel updateUser) {
        Toast.makeText(this, "User Updated at " + updateUser.getUpdatedAt(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void createUser(String name, String job, int which) {
        NewUser user = new NewUser(name, job);
        if(which == 2){
            dataManger.updateUser(user);
        }
        else{
            dataManger.createUser(user);
        }
    }

    @Override
    public void isNetworkConnected(boolean network_state) {
        isConnected = network_state;
        showSnackBar(isConnected);
    }

    private void showSnackBar(Boolean isConnected) {
        Snackbar snackbar;
        if (isConnected) {
            snackbar = Snackbar.make(layout, "Connected to Internet", Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(Color.argb(255, 0, 150, 100));

        } else {
            snackbar = Snackbar.make(layout, "Cannot connect to Internet", Snackbar.LENGTH_INDEFINITE);
            snackbar.getView().setBackgroundColor(Color.argb(255, 178, 34, 34));
        }
        snackbar.show();
    }


    @Override
    public void onItemClickListener(Datum datum) {
        Log.d(TAG, "onItemClickListener: " + datum.getFirstName());
        Bundle bundle = new Bundle();
        bundle.putString("fName", datum.getFirstName());
        bundle.putString("lName", datum.getLastName());
        bundle.putString("avatar", datum.getAvatar());
        bundle.putInt("ID", datum.getId());

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container1, detailFragment);
        fragmentTransaction.addToBackStack("Detail");
        fragmentTransaction.commit();
    }

    @Override
    public void menuOptionListener(Datum datum, int sendBy) {
        if (sendBy == 1) {
            // Update request
        } else if (sendBy == 2) {
            dataManger.deleteUser(datum.getId());                                                      // Delete Request
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
                sharedPreferences.edit().clear().apply();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReciever, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkStateReciever);
    }


}