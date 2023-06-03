package pl.pollub.android.app2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import kotlin.text.UStringsKt;

@Entity(tableName = "phones")
public class Phone {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long Id;

    @ColumnInfo(name = "manufacturer")
    @NonNull
    private String manufacturer;

    @ColumnInfo(name = "model")
    @NonNull
    private String model;

    public void setId(long id) {
        Id = id;
    }

    public void setManufacturer(@NonNull String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setModel(@NonNull String model) {
        this.model = model;
    }

    public void setAndroidVersion(@NonNull String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public void setWebsite(@NonNull String website) {
        this.website = website;
    }

    @ColumnInfo(name = "android_version")
    @NonNull
    private String androidVersion;

    @ColumnInfo(name = "website")
    @NonNull
    private String website;

    public long getId() {
        return Id;
    }

    @NonNull
    public String getAndroidVersion() {
        return androidVersion;
    }

    @NonNull
    public String getWebsite() {
        return website;
    }

    public Phone(@NonNull String manufacturer, @NonNull String model, @NonNull String androidVersion, @NonNull String website) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.website = website;
    }

    @Ignore
    public Phone(long id, @NonNull String manufacturer, @NonNull String model, @NonNull String androidVersion, @NonNull String website) {
        Id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.website = website;
    }

    @NonNull
    public String getManufacturer() {
        return manufacturer;
    }

    @NonNull
    public String getModel() {
        return model;
    }
}
