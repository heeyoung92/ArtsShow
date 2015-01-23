package app.com.example.heeyoung.artsshow.data;

import android.provider.BaseColumns;

/**
 * Created by heeyoung on 2015-01-23.
 */
public class ArtsContract {

    /* Inner class that defines the table contents of the product table */
    public static final class ProductEntry implements BaseColumns {

        public static final String TABLE_NAME = "product";
        public static final String COLUMN_PRD_KEY = "product_id";
        public static final String COLUMN_BRAND_KEY = "brand_id";
        public static final String COLUMN_PRD_TITLE = "product_tile";
        public static final String  COLUMN_PRD_PRICE = "product_price";
        public static final String  COLUMN_PRD_STATUS= "product_status";
        public static final String  COLUMN_PRD_LIKENUM= "product_likenum";
        public static final String  COLUMN_PRD_DESC= "product_desc";
        public static final String COLUMN_DATETEXT = "date";

    }

    public static final class BrandEntry implements BaseColumns {

        public static final String TABLE_NAME = "brand";
        public static final String COLUMN_BRAND_KEY = "brand_id";
        public static final String COLUMN_BRAND_TITLE = "brand_tile";
        public static final String  COLUMN_BRAND_COUNTRY = "brand_country";
        public static final String  COLUMN_BRAND_INFO = "brand_info";

        public static final String  COLUMN_BRAND_IMAGE = "brand_image";

    }
    public static final class ImageEntry implements BaseColumns{

        public static final String TABLE_NAME = "Image";

        public static final String COLUMN_IMAGE_KEY = "image_id";
        public static final String COLUMN_BRAND_KEY = "brand_id";

        public static final String  COLUMN_IMAGE_URL = "image_url";

    }
}