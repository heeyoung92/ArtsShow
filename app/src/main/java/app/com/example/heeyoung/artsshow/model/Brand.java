package app.com.example.heeyoung.artsshow.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Brand implements Parcelable
{
    public int brand_id;
    public String brand_name;
    public String brand_country;
    public String brand_info;
    public String brand_image;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(brand_id);
        dest.writeString(brand_name);
        dest.writeString(brand_country);
        dest.writeString(brand_info);
        dest.writeString(brand_image);
    }

    private Brand(Parcel in) {
        brand_id = in.readInt();
        brand_name = in.readString();
        brand_country = in.readString();
        brand_info = in.readString();
        brand_image = in.readString();
    }

    public static final Parcelable.Creator<Brand> CREATOR
            = new Parcelable.Creator<Brand>() {
        public Brand createFromParcel(Parcel in) {
            return new Brand(in);
        }

        public Brand[] newArray(int size) {
            return new Brand[size];
        }
    };
}
