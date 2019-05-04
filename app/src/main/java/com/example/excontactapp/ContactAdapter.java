package com.example.excontactapp;

import android.content.Context;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.viewHolder> {
    private List<Contact> mContacts;
    private Context mContext;
    private int layoutRes;
    OnClickNodeListener onClickListener;
    SendData sendData;


    public ContactAdapter(Context context, int res, List<Contact> contactList, OnClickNodeListener onClickListener, SendData sendData) {
        this.mContacts = contactList;
        this.mContext = context;
        this.layoutRes = res;
        this.onClickListener = onClickListener;
        this.sendData = sendData;
    }

    public void updateList(List<Contact> list) {
        this.mContacts.clear();
        this.mContacts.addAll(list);
        this.notifyDataSetChanged();

    }


    public void removeItem(int position) {
        mContacts.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(layoutRes, viewGroup, false);
        return new viewHolder(view, onClickListener, sendData);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        viewHolder.tvName.setText(mContacts.get(i).getmFullname());
        viewHolder.tvPhoneNum.setText(mContacts.get(i).getmMobile());
        viewHolder.tvEmail.setText(mContacts.get(i).getmEmail());
    }


    @Override
    public int getItemCount() {
        return mContacts.size();
    }


    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvPhoneNum;
        TextView tvEmail;
        OnClickNodeListener onClickNodeListener;
        SendData onLongClick;
        ImageButton Call;


        public viewHolder(@NonNull View itemView, final OnClickNodeListener onClickNodeListener, final SendData onLongClick) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_fullname);
            tvPhoneNum = itemView.findViewById(R.id.tv_phone);
            tvEmail = itemView.findViewById(R.id.tv_email);


            Call = itemView.findViewById(R.id.iv_call);

            this.onClickNodeListener = onClickNodeListener;
            this.onLongClick = onLongClick;
            Call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickNodeListener.onItemRecyclerClicked(getAdapterPosition(), 2);
                }
            });

            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickNodeListener.onItemRecyclerClicked(getAdapterPosition(), 1);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();
                    onLongClick.sendData(pos);


                    return false;
                }
            });


        }
    }


    //On click View Interface
    public interface OnClickNodeListener {
        void onItemRecyclerClicked(int postion, int actions);
    }

    public interface SendData {
        void sendData(int pos);
    }

}
