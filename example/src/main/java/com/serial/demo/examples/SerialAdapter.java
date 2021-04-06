package com.serial.demo.examples;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serial.demo.R;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author zhaosengshan
 */
public class SerialAdapter extends RecyclerView.Adapter<SerialAdapter.ViewHolder> {

    private List<SerialBean> data;

    public SerialAdapter(List<SerialBean> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        SerialBean bean = data.get(position);
        if (bean.getDriver() == null) {
            holder.text1.setText("<no driver>");
        } else if (bean.getDriver().getPorts().size() == 1) {
            holder.text1.setText(bean.getDriver().getClass().getSimpleName().replace("SerialDriver", ""));
        } else {
            holder.text1.setText(bean.getDriver().getClass().getSimpleName().replace("SerialDriver", "") + ", Port " + bean.getPort());
        }
        holder.text2.setText(String.format(Locale.US, "Vendor %04X, Product %04X", bean.getDevice().getVendorId(), bean.getDevice().getProductId()));


    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        }
        return 0;
    }

    private OnWifiClickListener clickListener;

    public void setClickListener(OnWifiClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnWifiClickListener {
        void onClick(SerialBean wifiBean);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView text1, text2;

        public ViewHolder(View view) {
            super(view);
            text1 = view.findViewById(R.id.text1);
            text2 = view.findViewById(R.id.text2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onClick(data.get(getLayoutPosition()));
            }
        }
    }
}