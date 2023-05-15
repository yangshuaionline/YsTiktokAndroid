package com.yangshuai.module.find.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yangshuai.module.find.R;

import java.util.List;

public class AutoPollAdapter extends RecyclerView.Adapter<AutoPollAdapter.BaseViewHolder> {

    private Context mContext;
    private List<String> mData;

    public AutoPollAdapter(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.home_autopoll_list_item, null);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.tvName.setText(mData.get(position % mData.size()).split(",")[0]);
        holder.tvHospitalName.setText(mData.get(position % mData.size()).split(",")[1]);
        holder.tvTime.setText(mData.get(position % mData.size()).split(",")[2]);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvHospitalName;
        TextView tvTime;

        public BaseViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvname);
            tvHospitalName = itemView.findViewById(R.id.tvospitalName);
            tvTime = itemView.findViewById(R.id.tvtime);
        }
    }
}
