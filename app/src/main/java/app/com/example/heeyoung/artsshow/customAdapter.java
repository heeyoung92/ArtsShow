package app.com.example.heeyoung.artsshow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by heeyoung on 2015-01-19.
 */

class ListItem {
    ListItem(int marts_ID, int martist_image, String martist_name, String martist_nation, String martist_info,
             String mtime, int marts_image,int mlike_num,String marts_name){

        arts_ID = marts_ID; // 작품 아이디(고유값)

        artist_image = martist_image;
        artist_name = martist_name;
        artist_nation = martist_nation;
        artist_info = martist_info;
        time = mtime;

        arts_image = marts_image;
        like_num = mlike_num;
        prd_title = marts_name;
    }

    int arts_ID;
    int artist_image;
    String artist_name;
    String artist_nation;
    String artist_info;
    String time;
    int arts_image;
    int like_num;
    String prd_title;
}

public class customAdapter extends ArrayAdapter<ListItem> {

    private ArrayList<ListItem> m_List;


    public customAdapter(Context context, int textViewResourceId,
                         ArrayList<ListItem> m_List ){
        super(context, textViewResourceId, m_List);
        this.m_List = m_List;
    }
    @Override
    public int getCount() {
        return m_List.size();
    }
    // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
    @Override
    public ListItem getItem(int position) {
        return m_List.get(position);
    }

    // 아이템 position의 ID 값 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    String  artsID;
    // 출력 될 아이템 관리
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        ImageButton Artist_img = null;
        TextView Artist_text = null;
        TextView Time = null;
        TextView Artist_na = null;
        TextView Artist_inf = null;
        ImageView Arts_img = null;
        TextView Like = null;
        TextView Arts_text =null;
        Button btn = null;

        CustomHolder  holder  = null;

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_item, parent, false);

            Artist_img = (ImageButton)convertView.findViewById(R.id.artistView);
            Artist_text = (TextView) convertView.findViewById(R.id.artistName);
            Time = (TextView) convertView.findViewById(R.id.time);
            Artist_na = (TextView)convertView.findViewById(R.id.artistNation);
            Artist_inf = (TextView)convertView.findViewById(R.id.artistUniv);
            Arts_img = (ImageView)convertView.findViewById(R.id.arts_image);
            Arts_text = (TextView)convertView.findViewById(R.id.arts_name);
            Like = (TextView)convertView.findViewById(R.id.like_num);

            btn = (Button) convertView.findViewById(R.id.button_like);

            // 홀더 생성 및 Tag로 등록
            holder=new
            CustomHolder();

            holder.m_TextView= Artist_text;
            holder.m_Btn= btn;
      //      holder.m_artist_img =artist_img;
            holder.m_artist_na =Artist_na;
            holder.m_artist_inf=Artist_inf;
      //      holder.m_arts_img = Arts_img;
            holder.m_time = Time;
            holder.m_arts_text = Arts_text;
            holder.m_like = Like;

            convertView.setTag(holder);
        }else{
            holder = (CustomHolder) convertView.getTag();
            Artist_text = holder.m_TextView;
            btn = holder.m_Btn;

    //        artist_img =holder.m_artist_img;
            Artist_na = holder.m_artist_na;
            Artist_inf = holder.m_artist_inf;
    //        arts_img=holder.m_arts_img;
            Time = holder.m_time;
            Arts_text = holder.m_arts_text;
            Like = holder.m_like;
        }

        // 현재 position의 작가이름 추가
        Artist_text.setText(m_List.get(position).artist_name);
        Artist_na.setText(m_List.get(position).artist_nation);
        Artist_inf.setText(m_List.get(position).artist_info);
        Time.setText(m_List.get(position).time);
        Like.setText(String.valueOf(m_List.get(position).like_num));
        Arts_text.setText(m_List.get(position).prd_title);

        // 버튼을 터치 했을 때 이벤트 발생
        final TextView finalLike = Like;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override

                public void onClick(View v) {
                    // 터치 시 해당 아이템 좋아요 +1
                    m_List.get(pos).like_num++;
                    finalLike.setText(String.valueOf(m_List.get(pos).like_num));

    //                Toast.makeText(context, "+1", Toast.LENGTH_SHORT).show();
                }
        });

            // 작품이미지 터치시 이벤트 발생
      Arts_img.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Toast.makeText(context, "작품 디테일 호출 : " + m_List.get(pos).arts_ID, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, m_List.get(pos).arts_ID);
                context.startActivity(intent);
            }
        });


        //작가 프로필 사진 클릭리스너
        Artist_img.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
 //                       .putExtra(Intent.EXTRA_TEXT, m_List.get(pos).artist_ID);
                context.startActivity(intent);
            }
        });



/*
        // 리스트 아이템을 길게 터치 했을 떄 이벤트 발생
        convertView.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    Toast.makeText(context, "리스트 롱 클릭 : " + m_List.get(pos), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });*/
        return convertView;
    }

// 스크롤 시 데이터가 변경 되는 것과 findViewById()를 사용을 줄여 속도 향상
    private class CustomHolder {
        TextView    m_TextView;
        Button      m_Btn;

        ImageView m_artist_img;
        TextView m_time;
        TextView m_artist_na;
        TextView m_artist_inf;
        ImageView m_arts_img;
        TextView m_like;
        TextView m_arts_text;
    }

//    public void add_artsId(){return }
    // 외부에서 아이템 추가 요청 시 사용
    public void add(ListItem _data) {m_List.add(_data);}

    // 외부에서 아이템 삭제 요청 시 사용
  //  public void remove(int _position) {
   //     m_List.remove(_position);
   // }
}
