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
import android.widget.Toast;

import app.com.example.heeyoung.artsshow.model.Product;

public class ProductListAdapter extends ArrayAdapter<Product>
{
    public ProductListAdapter(Context context, int resource)
    {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final Context context = parent.getContext();
        ViewHolder holder = new ViewHolder();

        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_item, parent, false);

//            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.m_artist_img = (ImageView)convertView.findViewById(R.id.artistView);
            holder.m_artist_name = (TextView)convertView.findViewById(R.id.artistName);
            holder.m_artist_na = (TextView)convertView.findViewById(R.id.artistNation);
            holder.m_artist_inf = (TextView)convertView.findViewById(R.id.artistUniv);
//            holder.m_artist_na = (TextView)convertView.findViewById(R.id.artistUniv);
            holder.m_time = (TextView)convertView.findViewById(R.id.time);
            holder.m_arts_img = (ImageView)convertView.findViewById(R.id.arts_image);
            holder.m_arts_text = (TextView)convertView.findViewById(R.id.arts_name);
            holder.m_like = (TextView)convertView.findViewById(R.id.like_num);
            holder.m_Btn = (Button)convertView.findViewById(R.id.button_like);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        final Product product = getItem(position);
        holder.m_artist_name.setText(product.brand.brand_name);
        holder.m_artist_na.setText(product.brand.brand_country);
        holder.m_artist_inf.setText(product.brand.brand_info);
        holder.m_time.setText(product.updated_at);
        holder.m_like.setText(String.valueOf(product.prd_num_likes));
        holder.m_arts_text.setText(product.prd_title);
//        holder.m_artist_img = 이미지로드
//        holder.m_arts_img = 이미지로드

        // 버튼을 터치 했을 때 이벤트 발생
        final TextView finalLike = holder.m_like;
        holder.m_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 터치 시 해당 아이템 좋아요 +1
                product.prd_num_likes++;
                finalLike.setText(String.valueOf(product.prd_num_likes));
                //Toast.makeText(context, "+1", Toast.LENGTH_SHORT).show();
            }
        });

        // 작품이미지 터치시 이벤트 발생
        holder.m_arts_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "작품 디테일 호출 : " + product.prd_id, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, product.prd_id);
                context.startActivity(intent);
            }
        });

        //작가 프로필 사진 클릭리스너
        holder.m_artist_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                context.startActivity(intent);
            }
        });

        // 리스트 아이템을 길게 터치 했을 떄 이벤트 발생
        /*
        convertView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 터치 시 해당 아이템 이름 출력
                Toast.makeText(context, "리스트 롱 클릭 : " + product, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        */

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
