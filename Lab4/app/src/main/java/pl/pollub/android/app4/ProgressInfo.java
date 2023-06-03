package pl.pollub.android.app4;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProgressInfo implements Parcelable{
    public final static int DOWNLOAD_START = 0;
    public final static int DOWNLOAD_END = 1;
    public final static int DOWNLOAD_ERROR = 2;
    private int downloadBytes;
    private int filesize;
    private int downloadStatus;
    private String fileType;

    public ProgressInfo() {
        this.downloadBytes = 0;
        this.filesize = 0;
        this.downloadStatus = 0;
    }

    public ProgressInfo(int downloadBytes, int filesize, int downloadStatus) {
        this.downloadBytes = downloadBytes;
        this.filesize = filesize;
        this.downloadStatus = downloadStatus;
    }

    protected ProgressInfo(Parcel in) {
        downloadBytes = in.readInt();
        filesize = in.readInt();
        downloadStatus = in.readInt();
    }

    public static final Creator<ProgressInfo> CREATOR = new Creator<ProgressInfo>() {
        @Override
        public ProgressInfo createFromParcel(Parcel in) {
            return new ProgressInfo(in);
        }

        @Override
        public ProgressInfo[] newArray(int size) {
            return new ProgressInfo[size];
        }
    };

    public int getDownloadBytes() {
        return downloadBytes;
    }

    public void setDownloadBytes(int downloadBytes) {
        this.downloadBytes = downloadBytes;
    }

    public int getFilesize() {
        return filesize;
    }

    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }

    public int getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(downloadBytes);
        parcel.writeInt(filesize);
        parcel.writeInt(downloadStatus);
    }
    public int progressValue() {
        return (int)(((double)this.downloadBytes/(double)this.filesize)*100);
    }
    public boolean isDownloadComplete(){
        return this.downloadBytes == this.filesize;
    }
    public void increaseDownloadBytes(int download){
        this.downloadBytes += download;
    }
}
