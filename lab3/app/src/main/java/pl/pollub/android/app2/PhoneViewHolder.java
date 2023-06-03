package pl.pollub.android.app2;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhoneViewHolder extends RecyclerView.ViewHolder {
    private TextView manufacturer;
    private TextView model;

    public PhoneViewHolder(@NonNull View itemView) {
        super(itemView);
        this.manufacturer = itemView.findViewById(R.id.manufacturer_tv);
        this.model = itemView.findViewById(R.id.model_tv);
    }

    public TextView getManufacturer() {
        return manufacturer;
    }

    public TextView getModel() {
        return model;
    }
}
