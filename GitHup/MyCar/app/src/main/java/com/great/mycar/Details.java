package com.great.mycar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.great.mycar.adapter.ProductCategoryAdapter;
import com.great.mycar.adapter.fulldetails;
import com.great.mycar.fragments.FragmentDetails;

import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity {

    String id;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setId();
        page_view();
    }

    public void setId() {
        id=getIntent().getStringExtra("id");
    }

    ArrayList<Integer>color=new ArrayList<>();
    void page_view() {
        color.add(R.drawable.bg);color.add(R.color.blue);
        color.add(R.color.white);color.add(R.color.black);
        color.add(R.color.orange);color.add(R.color.yellow);
        color.add(R.color.green);color.add(R.color.gray);
        color.add(R.color.green);color.add(R.color.gray);
        viewPager = findViewById(R.id.pager);
        FirebaseDatabase.getInstance().getReference().child("CarColor")
                .child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final viewpager adapter = new viewpager(getSupportFragmentManager());
                for(DataSnapshot data:snapshot.getChildren()){
                    Integer val=Integer.parseInt(data.getKey()),val2=R.layout.fullstack;
                    if(val==2||val==5)
                        val2=R.layout.fullstackblack;
                    adapter.addFragment(new FragmentDetails(color.get(val)
                            ,data.getValue().toString(),val2,id),color.get(val));
                }
                viewPager.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
class viewpager extends FragmentStatePagerAdapter {
    private final List<Fragment> mList = new ArrayList<>();
    private final List<Integer> mTitleList = new ArrayList<>();

    public viewpager(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }
    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }
    @Override
    public int getCount() {
        return mList.size();
    }
    public void addFragment(Fragment fragment, Integer color) {
        mList.add(fragment);
        mTitleList.add(color);
    }
}
