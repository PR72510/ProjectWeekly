package com.example.projectweekly.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projectweekly.Activities.Second_Activity;
import com.example.projectweekly.Interfaces.CreateUserListener;
import com.example.projectweekly.Interfaces.FragmentListener;
import com.example.projectweekly.Interfaces.MenuListener;
import com.example.projectweekly.Model.Datum;
import com.example.projectweekly.MyDialogFragment;
import com.example.projectweekly.R;

import java.util.List;


public class CusAdapter extends RecyclerView.Adapter<CusAdapter.MyViewHolder> {
    public static final int UPDATE = 1;
    public static final int DELETE = 2;
    MyDialogFragment myDialogFragment;
    private List<Datum> userList;
    Context mContext;
    FragmentListener mListener;
    MenuListener menuListener;
    int position;
    FragmentManager fragmentManager;

    public CusAdapter(List<Datum> userList, Context context) {
        this.userList = userList;
        mContext = context;
        myDialogFragment = new MyDialogFragment();
        myDialogFragment.setInstance((CreateUserListener) context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    public void setUserList(List<Datum> userList) {
        this.userList.addAll(userList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        Datum datum = userList.get(i);
        myViewHolder.tv_Id.setText(String.valueOf(datum.getId()));
        myViewHolder.tv_FirstName.setText(datum.getFirstName());
        myViewHolder.tv_LastName.setText(datum.getLastName());

        Glide.with(mContext)
                .load(datum.getAvatar())
                .into(myViewHolder.imageView);

        myViewHolder.viewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(mContext, myViewHolder.viewOptions);
                popup.inflate(R.menu.option_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                myDialogFragment.show(((Second_Activity) mContext).getSupportFragmentManager(), "Dialog");
                                myDialogFragment.setRequest(2);
                                break;
                            case R.id.menu2:
//                                Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                                if (menuListener != null) {
                                    position = myViewHolder.getAdapterPosition();
                                }
                                if (position != RecyclerView.NO_POSITION) {
                                    Datum data = userList.get(position);
                                    menuListener.menuOptionListener(data, DELETE);
                                }
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setOnItemClickListener(FragmentListener listener) {
        mListener = listener;
    }

    public void setOnMenuClickListener(MenuListener listener) {
        menuListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageButton viewOptions;
        ImageView imageView;
        TextView tv_FirstName, tv_LastName, tv_Id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_view);
            tv_Id = itemView.findViewById(R.id.tv_Id);
            tv_FirstName = itemView.findViewById(R.id.tv_fName);
            tv_LastName = itemView.findViewById(R.id.tv_lName);
            viewOptions = itemView.findViewById(R.id.btnImg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        position = getAdapterPosition();
                    }
                    if (position != RecyclerView.NO_POSITION) {
                        Datum data = userList.get(position);
                        mListener.onItemClickListener(data);
                    }
                }
            });
        }
    }
}