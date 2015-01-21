package app.com.example.heeyoung.artsshow.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable
{
    public int image_id;
    public int prd_id;
    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image_id);
        dest.writeInt(prd_id);
        dest.writeString(url);
    }

    private Image(Parcel in) {
        image_id = in.readInt();
        prd_id = in.readInt();
        url = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR
            = new Parcelable.Creator<Image>() {
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
