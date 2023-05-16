package pl.pollub.android.app2_2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "phones")
public class Phone {
    public Phone(@NonNull String manufacturer, @NonNull String model, @NonNull String androidVersion, @NonNull String webSite) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.webSite = webSite;
    }
    @Ignore
    public Phone(long id, @NonNull String manufacturer, @NonNull String model, @NonNull String androidVersion, @NonNull String webSite) {
        Id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.webSite = webSite;
    }

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long Id;
    @ColumnInfo(name = "manufacturer")
    @NonNull
    private String manufacturer;
    @ColumnInfo(name = "model")
    @NonNull
    private String model;
    @ColumnInfo(name = "android_version")
    @NonNull
    private String androidVersion;
    @ColumnInfo(name = "web_site")
    @NonNull
    private String webSite;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    @NonNull
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(@NonNull String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @NonNull
    public String getModel() {
        return model;
    }

    public void setModel(@NonNull String model) {
        this.model = model;
    }

    @NonNull
    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(@NonNull String androidVersion) {
        this.androidVersion = androidVersion;
    }

    @NonNull
    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(@NonNull String webSite) {
        this.webSite = webSite;
    }
}
