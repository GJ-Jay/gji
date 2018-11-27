package com.bwie.gejuan.frag;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.gejuan.R;
import com.bwie.gejuan.bean.Bean;
import com.bwie.gejuan.imageloader.ImageLoaderUtils;
import com.bwie.gejuan.sql.Dao;
import com.bwie.gejuan.util.NetState;
import com.bwie.gejuan.util.NetUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class Frag01 extends Fragment {

    private PullToRefreshListView plv;
    //网络路径
    String urlString = "http://www.xieast.com/api/news/news.php?page=";
    //创建大集合
    ArrayList<Bean.DataBean> list = new ArrayList<>();
    int page=1;
    private ImageLoader instance;
    private MyAdapter myAdapter;
    private Dao dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag01,container,false);
        //初始化组件
        plv = view.findViewById(R.id.plv);

        //获取实例
        instance = ImageLoader.getInstance();
        //创建对象
        dao = new Dao(getContext());

        //设置可以上下拉加载模式
        plv.setMode(PullToRefreshBase.Mode.BOTH);
        //设置适配器
        myAdapter = new MyAdapter();
        //添加适配器
        plv.setAdapter(myAdapter);
        //设置请求路径
        initData(page);
        //设置PullToRefresh刷新监听
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                //下拉刷新
                list.clear();
                page=1;
                initData(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                //上拉加载
                page++;
                initData(page);
            }
        });

        return view;
    }

    private void initData(int page) {
        String s = urlString + page;
        if(NetState.isConn(this)){
            //有网进行网络请求
            new MAsyncTask().execute(s);
        }else{
            //无网络时，从数据库读数据
            Cursor cursor = dao.query("jsontable", null, null,
                    null, null,null, null);
            ArrayList<Bean.DataBean> dataList = new ArrayList<>();
            //判断有数据
            if(cursor.moveToFirst()){
                do {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String thumbnail_pic_s = cursor.getString
                            (cursor.getColumnIndex("thumbnail_pic_s"));
                    String thumbnail_pic_s02 = cursor.getString
                            (cursor.getColumnIndex("thumbnail_pic_s02"));
                    String thumbnail_pic_s03 = cursor.getString
                            (cursor.getColumnIndex("thumbnail_pic_s03"));
                    Bean.DataBean dataBean = new Bean.DataBean(title,thumbnail_pic_s,
                            thumbnail_pic_s02,thumbnail_pic_s03);
                    //添加到集合中
                    dataList.add(dataBean);
                }while (cursor.moveToNext());
            }
            //添加到大集合中
            list.addAll(dataList);
        }
    }

    private class MAsyncTask extends AsyncTask<String,Void,String> {

        private long insert;

        //子线程
        @Override
        protected String doInBackground(String... strings) {
            //进行网络请求数据
            String jsonString = NetUtils.getJsonString(strings[0]);
            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //解析json
            Gson gson = new Gson();
            Bean bean = gson.fromJson(s, Bean.class);
            List<Bean.DataBean> data = bean.getData();

            //删除数据库
            dao.delete("jsontable",null,null);
            //添加到大集合中
            list.addAll(data);
            //添加数据库
            /*private String title;
            private String thumbnail_pic_s;
            private String thumbnail_pic_s02;
            private String thumbnail_pic_s03;*/
            for (int i = 0; i <list.size() ; i++) {
                insert = 0;
                Bean.DataBean dataBean = list.get(i);
                ContentValues values = new ContentValues();
                values.put("thumbnail_pic_s",dataBean.getThumbnail_pic_s());
                values.put("thumbnail_pic_s02",dataBean.getThumbnail_pic_s02());
                values.put("thumbnail_pic_s03",dataBean.getThumbnail_pic_s03());
                values.put("title",dataBean.getTitle());
                insert = dao.insert("jsontable", null, values);
            }
            Toast.makeText(getContext(),"添加成功"+insert,Toast.LENGTH_SHORT);

            //刷新适配器
            myAdapter.notifyDataSetChanged();
            //上下拉刷新结束
            plv.onRefreshComplete();
        }
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return position%2;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int itemViewType = getItemViewType(position);
            //判断条目样式
            switch (itemViewType){
                case 0:
                    ViewHolder0 vh0 = null;
                    //优化
                    if(convertView==null){
                        convertView= View.inflate(getContext(),R.layout.plv_item0,null);
                        vh0 = new ViewHolder0();
                        vh0.tv = convertView.findViewById(R.id.tv);
                        vh0.iv = convertView.findViewById(R.id.iv);
                        convertView.setTag(vh0);
                    }else{
                        vh0 = (ViewHolder0) convertView.getTag();
                    }
                    vh0.tv.setText(list.get(position).getTitle());
                    DisplayImageOptions options = ImageLoaderUtils.getDisplayImageOptions();
                    instance.displayImage(list.get(position).getThumbnail_pic_s(),vh0.iv,options);
                    break;

                case 1:
                    ViewHolder1 vh1 = null;
                    //优化
                    if(convertView==null){
                        convertView= View.inflate(getContext(),R.layout.plv_item1,null);
                        vh1 = new ViewHolder1();
                        vh1.tv1 = convertView.findViewById(R.id.tv1);
                        vh1.iv1 = convertView.findViewById(R.id.iv1);
                        vh1.iv2 = convertView.findViewById(R.id.iv2);
                        vh1.iv3 = convertView.findViewById(R.id.iv3);
                        convertView.setTag(vh1);
                    }else{
                        vh1 = (ViewHolder1) convertView.getTag();
                    }
                    vh1.tv1.setText(list.get(position).getTitle());
                    DisplayImageOptions options1 = ImageLoaderUtils.getDisplayImageOptions();
                    instance.displayImage(list.get(position).getThumbnail_pic_s(),vh1.iv1,options1);
                    instance.displayImage(list.get(position).getThumbnail_pic_s02(),vh1.iv2,options1);
                    instance.displayImage(list.get(position).getThumbnail_pic_s03(),vh1.iv3,options1);
                    break;
            }
            return convertView;
        }
    }

    class ViewHolder0{
        TextView tv;
        ImageView iv;
    }

    class ViewHolder1{
        TextView tv1;
        ImageView iv1;
        ImageView iv2;
        ImageView iv3;
    }
}
