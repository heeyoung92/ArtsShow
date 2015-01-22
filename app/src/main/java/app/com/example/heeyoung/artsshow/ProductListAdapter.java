package app.com.example.heeyoung.artsshow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import app.com.example.heeyoung.artsshow.model.Product;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProductListAdapter extends ArrayAdapter<Product>
{
    int mCheck = 0; //(임시)좋아요 체크 여부 -> DB저장 필요

    public ProductListAdapter(Context context, int resource)
    {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final Context context = parent.getContext();
        ViewHolder viewholder;
        if ( convertView != null ) {
            viewholder = (ViewHolder)convertView.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_item, parent, false);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
        }

        final Product product = getItem(position);
        final ViewHolder holder = viewholder;

        Glide.with(context).load(product.brand.brand_image).into(holder.m_artist_img);
        holder.m_artist_name.setText(product.brand.brand_name);
        holder.m_artist_na.setText(product.brand.brand_country);
        holder.m_artist_inf.setText(product.brand.brand_info);
        holder.m_time.setText(product.updated_at);
        Glide.with(context).load(product.images[0].url).into(holder.m_arts_img);
        holder.m_arts_text.setText(product.prd_title);
        holder.m_like.setText(String.valueOf(product.prd_num_likes));

        // like 버튼 클릭 리스너
        holder.m_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mCheck == 0 ) {
                    // 클릭 시 해당 아이템 좋아요 +1
                    product.prd_num_likes++;
                    holder.m_Btn.setBackgroundResource(R.drawable.click);
                    mCheck = 1;
                } else {
                    product.prd_num_likes--;
                    holder.m_Btn.setBackgroundResource(R.drawable.notclick);
                    mCheck = 0;
                }

                holder.m_like.setText(String.valueOf(product.prd_num_likes));

                // + LIKE 수 DB에 저장

                //Toast.makeText(context, "+1", Toast.LENGTH_SHORT).show();
            }
        });

        // 작품 사진 클릭 리스너
        holder.m_arts_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "작품 디테일 호출 : " + product.prd_id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailActivity.class)
                        .putExtra("product", product);
                context.startActivity(intent);
            }
        });

        // 프로필 사진 클릭 리스너
        holder.m_artist_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class)
                        .putExtra("brand", product.brand);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder
    {
        @InjectView(R.id.artistName) TextView m_artist_name;
        @InjectView(R.id.button_like) ImageButton m_Btn;
        @InjectView(R.id.artistView) ImageView m_artist_img;
        @InjectView(R.id.time) TextView m_time;
        @InjectView(R.id.artistNation) TextView m_artist_na;
        @InjectView(R.id.artistUniv) TextView m_artist_inf;
        @InjectView(R.id.arts_image) ImageView m_arts_img;
        @InjectView(R.id.like_num) TextView m_like;
        @InjectView(R.id.arts_name) TextView m_arts_text;

        public ViewHolder(View view)
        {
            ButterKnife.inject(this, view);
        }
    }
}
