package app.com.example.heeyoung.artsshow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by heeyoung on 2015-01-19.
 */

public class customAdapter extends BaseAdapter {

    private ArrayList<String> m_List;


    public customAdapter(){
        m_List = new ArrayList<String>();
    }
    @Override
    public int getCount() {
        return m_List.size();
    }
    // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    // 아이템 position의 ID 값 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 출력 될 아이템 관리
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        TextView text = null;
        Button btn = null;
        CustomHolder  holder  = null;

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_item, parent, false);

            text = (TextView) convertView.findViewById(R.id.artistName);
            btn = (Button) convertView.findViewById(R.id.button_like);

            // 홀더 생성 및 Tag로 등록
            holder=new

            CustomHolder();

            holder.m_TextView=text;
            holder.m_Btn=btn;
            convertView.setTag(holder);
        }else{
            holder = (CustomHolder) convertView.getTag();
            text = holder.m_TextView;
            btn = holder.m_Btn;
        }

        // TextView에 현재 position의 문자열 추가
        text.setText(m_List.get(position));

        // 버튼을 터치 했을 때 이벤트 발생
        btn.setOnClickListener(new OnClickListener() {
            @Override

                public void onClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    Toast.makeText(context, "+1", Toast.LENGTH_SHORT).show();
                }
        });

            // 리스트 아이템을 터치 했을 때 이벤트 발생
        convertView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    Toast.makeText(context, "작품 디테일 호출 : "+m_List.get(pos), Toast.LENGTH_SHORT).show();
                }
        });

        // 리스트 아이템을 길게 터치 했을 떄 이벤트 발생
        convertView.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    Toast.makeText(context, "리스트 롱 클릭 : " + m_List.get(pos), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        return convertView;
    }

// 스크롤 시 데이터가 변경 되는 것과 findViewById()를 사용을 줄여 속도 향상
    private class CustomHolder {
        TextView    m_TextView;
        Button      m_Btn;
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(String _msg) {
        m_List.add(_msg);
    }

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
        m_List.remove(_position);
    }
}
