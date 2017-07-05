package testrecyclerview.fxs.com.testrecyclerview.gridding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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

public class BaseGridActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private List<String> list;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_grid);
        initView();
        initData();
        init();

    }


    private void initData() {
        list = new ArrayList<String>();
        for (int i = 0;i < 500;i ++){
            list.add(""+i);
        }
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

        myAdapter = new MyAdapter(list);
        //设置布局管理器
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        recyclerView.setAdapter(myAdapter);
        //增加分割线
        //画2次线条，一次画横线一次画竖向就可以完成网格的绘制
        recyclerView.addItemDecoration(new ItemDivider(this,LinearLayoutManager.HORIZONTAL));
        recyclerView.addItemDecoration(new ItemDivider(this,LinearLayoutManager.VERTICAL));

        myAdapter.setOnItmeClickListener(new RecycleViewItmeClickListener() {
            @Override
            public void OnItmeClickListener(View view, int position) {
                Toast.makeText(getApplicationContext(),list.get(position),Toast.LENGTH_LONG).show();
            }
        });
    }


    private void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
    }



    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        List<String> data;
        RecycleViewItmeClickListener recycleViewItmeClickListener;

        public MyAdapter(List<String> data){
            this.data = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(BaseGridActivity.this).inflate(R.layout.recycler_view_itme, parent,false));
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

        public void setOnItmeClickListener(RecycleViewItmeClickListener recycleViewItmeClickListener) {
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
                        recycleViewItmeClickListener.OnItmeClickListener(itemView,getPosition());
                    }
                });

            }
        }
    }

}
