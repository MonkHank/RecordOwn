package com.monk.modulefragment;

import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.luojilab.router.facade.annotation.RouteNode;
import com.monk.activity.base.BaseCompatActivity;
import com.monk.activity.base.OnFragmentInteractionListener;
import com.monk.commonutils.LogUtil;
import com.monk.modulefragment.fragment.FragmentA;
import com.monk.modulefragment.fragment.FragmentC;
import com.monk.modulefragment.fragment.FragmentChildA;
import com.monk.modulefragment.fragment.FragmentD;
import com.monk.modulefragment.fragment.FragmentOher;
import com.monk.modulefragment.otherfra.FragmentLocation;
import com.monk.modulefragment.otherfra.FragmentRetrofit2;
import com.monk.modulefragment.rxjava2.RxJava2Fragment;

import java.util.List;


@RouteNode(path = "/main", desc = "主页")
public class FragmentsActivity extends BaseCompatActivity<FragmentsActivity> implements
        OnFragmentInteractionListener, View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigation;
    private TextView tvFragmentMsg;
    private Button btTestAttach, btAdd;

    private FragmentManager fragmentManager;
    private Fragment fragmentA, rxJava2Fra, fragmentC, fragmentD,fragmentOher;
    private Fragment mCurrentFragment;

    private UriMatcher uriMatcher;
    private final int fragmentChildACode = 0;
    private final int fragmentOhterCode1 = 1;
    private final int fragmentOhterCode2 = 2;
    private int menuItemItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar(R.layout.activity_module_fragment_main);

        navigation = findViewById(R.id.navigation);
        tvFragmentMsg = findViewById(R.id.tvFragmentMsg);
        btTestAttach = findViewById(R.id.btTestAttach);
        btAdd = findViewById(R.id.btAdd);

        btTestAttach.setOnClickListener(this);
        btAdd.setOnClickListener(this);
        navigation.setOnNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            fragmentA = fragmentManager.findFragmentByTag(FragmentA.class.getName());
            rxJava2Fra = fragmentManager.findFragmentByTag(RxJava2Fragment.class.getName());
            fragmentC = fragmentManager.findFragmentByTag(FragmentC.class.getName());
            fragmentD = fragmentManager.findFragmentByTag(FragmentD.class.getName());
            fragmentOher = fragmentManager.findFragmentByTag(FragmentOher.class.getName());
            menuItemItemId = savedInstanceState.getInt("menuItemItemId");
            navigation.setSelectedItemId(menuItemItemId);
        } else {
            // TODO: 2019-05-29 如果menuItemItemId 和其它resId一样，这样就不行了
            navigation.setSelectedItemId(R.id.navigation_sample);
        }

        initUri();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItemItemId = menuItem.getItemId();
        if (menuItem.getItemId() == R.id.navigation_sample) {
            if (fragmentA == null) fragmentA = new FragmentA();
            addAndShowFragment(fragmentA);
            showOrHideFragmentButtons(true);
            return true;
        }
        showOrHideFragmentButtons(false);
        if (menuItem.getItemId() == R.id.navigation_home) {
            if (rxJava2Fra == null) rxJava2Fra =  RxJava2Fragment.Companion.newInstance("参数1","参数2");
            addAndShowFragment(rxJava2Fra);
            return true;
        }
        if (menuItem.getItemId() == R.id.navigation_jni) {
            if (fragmentC == null) fragmentC = new FragmentC();
            addAndShowFragment(fragmentC);
            return true;
        }
        if (menuItem.getItemId() == R.id.navigation_custom_view) {
            if (fragmentD == null) fragmentD = new FragmentD();
            addAndShowFragment(fragmentD);
            return true;
        }
        if (menuItem.getItemId() == R.id.navigation_other) {
            if (fragmentOher == null) fragmentOher = new FragmentOher();
            addAndShowFragment(fragmentOher);
            return true;
        }
        return false;
    }

    public void addAndShowFragment(Fragment fragment) {
        // FragmentTransaction表示一次完整的事务，commit() 之前的一系列连贯操作称之为一次事务。
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (mCurrentFragment != null) ft.hide(mCurrentFragment);

        if (!fragment.isAdded()) {
            ft.add(R.id.fragmentContainer, fragment, fragment.getClass().getName());
        } else {
            ft.show(fragment);
        }
        ft.commit();
        mCurrentFragment = fragment;
    }

    private void initUri() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(getPackageName(), FragmentA.class.getSimpleName(), fragmentChildACode);
        uriMatcher.addURI(getPackageName(), FragmentLocation.class.getSimpleName(), fragmentOhterCode1);
        uriMatcher.addURI(getPackageName(), FragmentRetrofit2.class.getSimpleName(), fragmentOhterCode2);
    }

    private void showOrHideFragmentButtons(boolean show){
        btTestAttach.setVisibility(show?View.VISIBLE:View.GONE);
        btAdd.setVisibility(show?View.VISIBLE:View.GONE);
        tvFragmentMsg.setVisibility(show?View.VISIBLE:View.GONE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        int match = uriMatcher.match(uri);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        tvFragmentMsg.setText("uri:"+uri.toString()+"\n match:"+match);
        if (match == fragmentChildACode){
            ft.replace(R.id.fragmentContainer,new FragmentChildA())
                    .addToBackStack(FragmentA.class.getSimpleName())
                    .commit();
            return;
        }
        if (match == fragmentOhterCode1){
            ft.replace(R.id.fragmentContainer,FragmentLocation.Companion.newInstance())
                    .addToBackStack(FragmentOher.class.getSimpleName())
                    .commit();
            return;
        }
        if (match == fragmentOhterCode2){
            ft.replace(R.id.fragmentContainer,new FragmentRetrofit2())
                    .addToBackStack(FragmentOher.class.getSimpleName())
                    .commit();
            return;
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (v.getId() == R.id.btTestAttach) {
            if (fragmentA.isAdded()) ft.detach(fragmentA).commit();
            else ft.attach(fragmentA).commit();
            tvFragmentMsg.setText("fragmentA.isAdded:"+fragmentA.isAdded()+"\n isDetached:"+fragmentA.isDetached());
            return;
        }
        if (v.getId() == R.id.btAdd) {
            ft.replace(R.id.fragmentContainer, fragmentA).commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("menuItemItemId", menuItemItemId);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment t : fragments) {
            LogUtil.w(tag, "fragment:" + t.getClass().getSimpleName());
        }
    }
}
