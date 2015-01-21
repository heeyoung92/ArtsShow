package app.com.example.heeyoung.artsshow.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable
{
    public String prd_id;
    public String prd_brand_id;
    public String prd_title;
    public int prd_price;
    public int prd_status;
    public int prd_num_likes;
    public String prd_desc;
    public String updated_at;
    public Image[] images;
    public Brand brand;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prd_id);
        dest.writeString(prd_brand_id);
        dest.writeString(prd_title);
        dest.writeInt(prd_price);
        dest.writeInt(prd_status);
        dest.writeInt(prd_num_likes);
        dest.writeString(prd_desc);
        dest.writeString(updated_at);
        dest.writeTypedArray(images, flags);
        dest.writeParcelable(brand, flags);
    }

    private Product(Parcel in) {
        prd_id = in.readString();
        prd_brand_id = in.readString();
        prd_title = in.readString();
        prd_price = in.readInt();
        prd_status = in.readInt();
        prd_num_likes = in.readInt();
        prd_desc = in.readString();
        updated_at = in.readString();
        images = in.createTypedArray(Image.CREATOR);
        brand = in.readParcelable(Brand.class.getClassLoader());
    }

    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
