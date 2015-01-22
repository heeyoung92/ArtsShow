package app.com.example.heeyoung.artsshow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import app.com.example.heeyoung.artsshow.model.Product;

public class ProductListAdapter extends ArrayAdapter<Product>
{
    public ProductListAdapter(Context context, int resource)
    {
        super(context, resource);
    }
    int m_check = 0; //(임시)좋아요 체크 여부 -> DB저장 필요

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final Context context = parent.getContext();
        ViewHolder holder = null;

        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_item, parent, false);

            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.m_artist_img = (ImageView)convertView.findViewById(R.id.artistView);
            holder.m_artist_name = (TextView)convertView.findViewById(R.id.artistName);
            holder.m_artist_na = (TextView)convertView.findViewById(R.id.artistNation);
            holder.m_artist_inf = (TextView)convertView.findViewById(R.id.artistUniv);
            holder.m_time = (TextView)convertView.findViewById(R.id.time);
            holder.m_arts_img = (ImageView)convertView.findViewById(R.id.arts_image);
            holder.m_arts_text = (TextView)convertView.findViewById(R.id.arts_name);
            holder.m_like = (TextView)convertView.findViewById(R.id.like_num);
            holder.m_Btn = (Button)convertView.findViewById(R.id.button_like);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final Product product = getItem(position);

        Glide.with(context).load(product.brand.brand_image).into(holder.m_artist_img);
        holder.m_artist_name.setText(product.brand.brand_name);
        holder.m_artist_na.setText(product.brand.brand_country);
        holder.m_artist_inf.setText(product.brand.brand_info);
        holder.m_time.setText(product.updated_at);

        Glide.with(context).load(product.images[0].url).into(holder.m_arts_img);
        holder.m_arts_text.setText(product.prd_title);
        holder.m_like.setText(String.valueOf(product.prd_num_likes));

        // like 버튼 클릭 리스너
        final TextView finalLike = holder.m_like;
        final ViewHolder finalHolder = holder;
        holder.m_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if(m_check == 0) {
                    // 클릭 시 해당 아이템 좋아요 +1
                     product.prd_num_likes++;
                     finalHolder.m_Btn.setText("V"); // 이미지로 변경 필요
                     m_check = 1;
                  }else{
                    product.prd_num_likes--;
                    finalHolder.m_Btn.setText("-");
                    m_check = 0;
                  }

                finalLike.setText(String.valueOf(product.prd_num_likes));

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

    private static class ViewHolder
    {
        TextView m_artist_name;
        Button m_Btn;
        ImageView m_artist_img;
        TextView m_time;
        TextView m_artist_na;
        TextView m_artist_inf;
        ImageView m_arts_img;
        TextView m_like;
        TextView m_arts_text;
    }
}
