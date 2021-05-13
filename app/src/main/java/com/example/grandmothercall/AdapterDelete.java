package com.example.grandmothercall;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grandmothercall.bdDAO.Abonent;

import java.util.ArrayList;
import java.util.List;

public class AdapterDelete extends RecyclerView.Adapter<AdapterDelete.myHolder> {

    boolean changeBackground = false;
    private List<Abonent> myList = new ArrayList<>();

    public void setDeleteAbonent(deleteAbonent deleteAbonent) {
        mDeleteAbonent = deleteAbonent;
    }

    public deleteAbonent mDeleteAbonent;

    public void setMyList(List<Abonent> myList) {
        this.myList = myList;
        notifyDataSetChanged();
    }

    public List<Abonent> getMyList() {
        return myList;
    }

    interface deleteAbonent{
        void onClickDelete(int pos);
    }



    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new myHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
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
        holder.ImageViewCall.setImageResource(R.drawable.ic_delete_black_24dp);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {

        ImageView ImageView;
        TextView name;
        TextView phoneNumber;
        ConstraintLayout layout;
        ImageView ImageViewCall;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            ImageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.textView);
            phoneNumber = itemView.findViewById(R.id.textView2);
            layout = itemView.findViewById(R.id.backGround);
            ImageViewCall = itemView.findViewById(R.id.imageViewCall);
            ImageViewCall.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(mDeleteAbonent != null){
                        mDeleteAbonent.onClickDelete(getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }
}
