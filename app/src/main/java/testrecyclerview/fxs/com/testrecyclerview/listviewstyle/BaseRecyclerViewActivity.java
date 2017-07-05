package testrecyclerview.fxs.com.testrecyclerview.listviewstyle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import testrecyclerview.fxs.com.testrecyclerview.R;

/**
 *     Android 5.0的v7版本支持包中引入了新的RecyclerView控件，
 * 正如官方文档所言，RecyclerView是ListView的豪华增强版。
 * 它主要包含以下几处新的特性，如ViewHolder，ItemDecorator，
 * LayoutManager，SmothScroller以及增加或删除item时item动画等。
 * 官方推荐我们采用RecyclerView来取代ListView<br><br>
 *
 *使用RecyclerView第一步android studio在gradle中添加compile 'com.android.support:recyclerview-v7:23.0.1'
 * eclipse需要添加RecyclerView的jar包
 */
public class BaseRecyclerViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private List<String> data;

    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_recycler_view);
        initView();
        initData();
        init();
    }


    private void initData() {
        data = new ArrayList<String>();

        for (int i = 0 ; i < 25; i++) {
            data.add("Item"+i);
        }
    }


    private void init() {
        //toolbar箭头点击back事件
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myAdapter = new MyAdapter(data);
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

    }


    private void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
    }



    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        List<String> data;

        public MyAdapter(List<String> data){
            this.data = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(BaseRecyclerViewActivity.this).inflate(R.layout.recycler_view_itme, parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.text.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView text;

            public MyViewHolder(View itemView) {
                super(itemView);
                text = (TextView)itemView.findViewById(R.id.text);
            }
        }
    }
}
