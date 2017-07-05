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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import testrecyclerview.fxs.com.testrecyclerview.ItemDivider;
import testrecyclerview.fxs.com.testrecyclerview.R;
import testrecyclerview.fxs.com.testrecyclerview.listening.RecycleViewItmeClickListener;

public class ClickItmeRecycleViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private List<String> list;

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cilck_itme_recycle);
        initView();
        initData();
        init();
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new MyAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //增加分割线
        recyclerView.addItemDecoration(new ItemDivider(this,LinearLayoutManager.VERTICAL));


        adapter.setOnItmeClickListener(new RecycleViewItmeClickListener() {
            @Override
            public void OnItmeClickListener(View view, int position) {
                Toast.makeText(getApplicationContext(),"你点击了："+list.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void initData() {
        list = new ArrayList<String>();
        for(int i = 0;i < 50;i ++){
            list.add("条目"+i);
        }
    }

    private void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
    }




    /**
     * 实例化适配器
     */
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        private List<String> data;
        private RecycleViewItmeClickListener recycleViewItmeClickListener;

        public MyAdapter(List<String> data){
            this.data = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(ClickItmeRecycleViewActivity.this).inflate(R.layout.recycler_view_itme, parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.text.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        //为itme设置点击事件
        public void setOnItmeClickListener(RecycleViewItmeClickListener recycleViewItmeClickListener){
            this.recycleViewItmeClickListener = recycleViewItmeClickListener;
        }


        class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView text;

            public MyViewHolder(final View itemView) {
                super(itemView);

                text = (TextView)itemView.findViewById(R.id.text);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (recycleViewItmeClickListener != null) {
                            recycleViewItmeClickListener.OnItmeClickListener(itemView, getPosition());
                        }
                    }
                });
            }
        }
    }
}
