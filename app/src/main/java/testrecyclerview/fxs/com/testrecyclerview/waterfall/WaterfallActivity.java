package testrecyclerview.fxs.com.testrecyclerview.waterfall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import testrecyclerview.fxs.com.testrecyclerview.ItemDivider;
import testrecyclerview.fxs.com.testrecyclerview.R;
import testrecyclerview.fxs.com.testrecyclerview.listening.LoadCallback;
import testrecyclerview.fxs.com.testrecyclerview.model.InkmanData;
import testrecyclerview.fxs.com.testrecyclerview.model.Waterfall;

public class WaterfallActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private List<Waterfall> list;
    private MyAdapter adapter;

    private int imageAarry[] = {R.mipmap.i1,R.mipmap.i2,R.mipmap.i3,R.mipmap.i4,R.mipmap.i5,R.mipmap.i6,R.mipmap.i1,R.mipmap.i14,
            R.mipmap.i7,R.mipmap.i8,R.mipmap.i9,R.mipmap.i10,R.mipmap.i11,R.mipmap.i12,R.mipmap.i3,R.mipmap.i14,R.mipmap.i15,
            R.mipmap.i12,R.mipmap.i8,R.mipmap.i4,R.mipmap.i9,R.mipmap.i14,R.mipmap.i3,R.mipmap.i6,R.mipmap.i10,R.mipmap.i7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterfall);
        initData();
        initView();
    }

    private void initData() {
        list = new ArrayList<Waterfall>();
        for(int i = 0;i < imageAarry.length;i++){
            Waterfall data = new Waterfall();
            data.setImage(imageAarry[i]);
            data.setText("图一" + i);
            list.add(data);

        }
    }


    private void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        adapter = new MyAdapter(list,getApplicationContext());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }



    /**
     * RecycLerView适配器
     */
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        private List<Waterfall> list;
        private Context context;
        private LoadCallback callback;

        public MyAdapter(List<Waterfall> list,Context context){
            this.list = list;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(WaterfallActivity.this).inflate(R.layout.waterfall_itme,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.text.setText(list.get(position).getText());
            holder.image.setImageResource(list.get(position).getImage());


            Palette.generateAsync(disposeBigImage(list.get(position).getImage(),context),
                    new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            Palette.Swatch vibrant =
                                    palette.getVibrantSwatch();
                            if (vibrant != null) {

                                int rgb = vibrant.getRgb();
                                int titleTextColor = vibrant.getTitleTextColor();
                                int bodyTextColor = vibrant.getBodyTextColor();

                                holder.text.setTextColor(rgb);
                            }
                        }
                    });
        }



        @Override
        public int getItemCount() {
            return list.size();
        }

        public Bitmap disposeBigImage(int iamgeID, Context context){
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            //获取资源图片
            InputStream is = context.getResources().openRawResource(iamgeID);
            Bitmap bitmap = BitmapFactory.decodeStream(is,null,opt);
            return bitmap;
        }


        class MyViewHolder extends RecyclerView.ViewHolder{

            private ImageView image;
            private TextView text;

            public MyViewHolder(View itemView) {
                super(itemView);

                image = (ImageView)itemView.findViewById(R.id.image);
                text = (TextView)itemView.findViewById(R.id.text);
            }
        }
    }
}
