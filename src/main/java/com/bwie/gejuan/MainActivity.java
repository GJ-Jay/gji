package com.bwie.gejuan;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.bwie.gejuan.frag.Frag01;
import com.bwie.gejuan.frag.Frag02;
import com.bwie.gejuan.frag.Frag03;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private FrameLayout frag;
    private RadioGroup radioGroup;
    private ImageView img;
    private ListView lv;
    private FragmentManager fragmentManager;
    private Frag01 frag01;
    private Frag02 frag02;
    private Frag03 frag03;
    private ImageLoader instance;
    //图片路径
    String urlBitmap = "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg";
    //创建大集合
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化组件
        dl = findViewById(R.id.dl);
        frag = findViewById(R.id.frag);
        radioGroup = findViewById(R.id.radio);
        img = findViewById(R.id.img);
        lv = findViewById(R.id.lv);

        //获取实例
        instance = ImageLoader.getInstance();

        //侧拉
        instance.displayImage(urlBitmap,img);
        for (int i= 0;i<3;i++){
            list.add("drawerLayout"+(i+1));
        }
        //创建适配器
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(
                MainActivity.this,android.R.layout.simple_list_item_1,list
        );
        //添加适配器
        lv.setAdapter(myAdapter);
        //设置点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                switch (position){
                    case 0:
                        transaction1.show(frag01).hide(frag02).hide(frag03);
                        //按钮选中
                        radioGroup.check(radioGroup.getChildAt(0).getId());
                        //侧拉消失
                        dl.closeDrawers();
                        break;

                    case 1:
                        transaction1.show(frag02).hide(frag01).hide(frag03);
                        //按钮选中
                        radioGroup.check(radioGroup.getChildAt(1).getId());
                        //侧拉消失
                        dl.closeDrawers();
                        break;

                    case 2:
                        transaction1.show(frag03).hide(frag02).hide(frag01);
                        //按钮选中
                        radioGroup.check(radioGroup.getChildAt(2).getId());
                        //侧拉消失
                        dl.closeDrawers();
                        break;
                }
                //提交事务
                transaction1.commit();
            }
        });

        fragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //创建视图
        frag01 = new Frag01();
        frag02 = new Frag02();
        frag03 = new Frag03();
        //添加视图
        transaction.add(R.id.frag,frag01).show(frag01);
        transaction.add(R.id.frag,frag02).hide(frag02);
        transaction.add(R.id.frag,frag03).hide(frag03);
        //提交事务
        transaction.commit();
        //默认第一个页面选中
        radioGroup.check(radioGroup.getChildAt(0).getId());
        //设置点击按钮页面改变
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                switch (checkedId){
                    case R.id.radio1:
                        transaction1.show(frag01).hide(frag02).hide(frag03);
                        break;

                    case R.id.radio2:
                        transaction1.show(frag02).hide(frag01).hide(frag03);
                        break;

                    case R.id.radio3:
                        transaction1.show(frag03).hide(frag02).hide(frag01);
                        break;
                }
                //提交事务
                transaction1.commit();
            }
        });



    }
}
