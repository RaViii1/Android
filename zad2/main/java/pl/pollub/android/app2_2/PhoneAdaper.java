package pl.pollub.android.app2_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneAdaper extends RecyclerView.Adapter<PhoneViewHolder> {
    private LayoutInflater inflater;
    private List<Phone> phoneList;

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public PhoneAdaper(LayoutInflater inflater) {
        this.inflater = inflater;
        this.phoneList = null;
    }

    public void setOnPhoneClickListener(OnPhoneClickListener onPhoneClickListener) {
        this.onPhoneClickListener = onPhoneClickListener;
    }

    private OnPhoneClickListener onPhoneClickListener;

    interface OnPhoneClickListener{
        void onPhoneClick(Phone phone);
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = this.inflater.inflate(R.layout.phone_info, parent, false);
        PhoneViewHolder holder = new PhoneViewHolder(rootView);
        holder.itemView.setOnClickListener(view -> {
            if(this.phoneList != null && this.onPhoneClickListener != null) {
                Phone phone = this.phoneList.get(holder.getAdapterPosition());
                this.onPhoneClickListener.onPhoneClick(phone);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        Phone phone = this.phoneList.get(position);
        holder.getManufacturer().setText(phone.getManufacturer());
        holder.getModel().setText(phone.getModel());
    }

    @Override
    public int getItemCount() {
        return this.phoneList != null ? this.phoneList.size() : 0;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
        notifyDataSetChanged();
    }
}
