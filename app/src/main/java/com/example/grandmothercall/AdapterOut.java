package com.example.grandmothercall;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grandmothercall.bdDAO.Abonent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdapterOut extends RecyclerView.Adapter<AdapterOut.ViewHolder> {

    boolean changeBackground = false;
    private onClickList mClickList;
    private onClickImage mOnClickImage;
    private List<Abonent> myList = new ArrayList<>();

    interface onClickImage {
        void onClick(int pos);
    }
     void someChange(String res, int pos){
        myList.get(pos).setImage(res);
        notifyDataSetChanged();

    }

    public void setOnClickImage(onClickImage onClickImage) {
        mOnClickImage = onClickImage;
    }

    public void setClickList(onClickList clickList) {
        mClickList = clickList;
    }

    public List<Abonent> getMyList() {
        return myList;
    }

    public void setMyList(List<Abonent> myList1) {
        this.myList = myList1;
        notifyDataSetChanged();

    }

    interface onClickList{
        void onClick(Intent intent);
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(String.valueOf(myList.get(position).getName()));
        if(changeBackground){
            holder.layout.setBackgroundResource(R.drawable.bg2);
            changeBackground = false;
        } else {
            holder.layout.setBackgroundResource(R.drawable.bg1);
            changeBackground = true;
        }
        holder.ImageView.setImageResource(R.drawable.manphoto);
        try {

            holder.ImageView.setImageURI(Uri.parse(myList.get(position).getImage()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.phoneNumber.setText(myList.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ImageView;
        TextView name;
        TextView phoneNumber;
        ConstraintLayout layout;
        ImageView ImageViewCall;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            ImageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView);
            phoneNumber = itemView.findViewById(R.id.textView2);
            layout = itemView.findViewById(R.id.backGround);
            ImageViewCall = itemView.findViewById(R.id.imageViewCall);

            ImageViewCall.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(phoneNumber != null && mClickList != null){

                        Intent call = new Intent(Intent.ACTION_CALL);
                        call.setData(Uri.parse("tel:" + myList.get(getAdapterPosition()).getPhone()));
                        mClickList.onClick(call);
                    }

                }
            });
            ImageView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                  mOnClickImage.onClick(getAdapterPosition());
                    Collections.sort(myList, new ComparatorName());
                  return true;
                }
            });
        }
    }
   public class ComparatorName implements Comparator<Abonent> {
        @Override
        public int compare(Abonent o1, Abonent o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
