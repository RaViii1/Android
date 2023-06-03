package pl.pollub.android.app2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneViewHolder> {
    private LayoutInflater inflater;
    private List<Phone> phoneList;

    public PhoneAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
        this.phoneList = null;
    }

    private OnPhoneClickListener onPhoneClickListener;

    interface OnPhoneClickListener{
        void onPhonceClick(Phone phone);
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = this.inflater.inflate(R.layout.phone_info, parent, false);
        PhoneViewHolder holder = new PhoneViewHolder(rootView);
        if(this.phoneList != null && this.onPhoneClickListener != null) {
            holder.itemView.setOnClickListener(view -> {
                    int i = holder.getAdapterPosition();
                    Phone phone = this.phoneList.get(i);
                    this.onPhoneClickListener.onPhonceClick(phone);
            });
        }
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

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setOnPhoneClickListener(OnPhoneClickListener onPhoneClickListener) {
        this.onPhoneClickListener = onPhoneClickListener;
    }
}
