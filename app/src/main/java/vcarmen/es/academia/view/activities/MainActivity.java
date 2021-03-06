package vcarmen.es.academia.view.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import vcarmen.es.academia.R;
import vcarmen.es.academia.view.fragments.FragmentAlumno;
import vcarmen.es.academia.view.fragments.FragmentAsignatura;
import vcarmen.es.academia.view.fragments.FragmentMatricula;
import vcarmen.es.academia.view.adapters.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        switch (getIntent().getIntExtra("soy", 0)) {
            case 1:
                if (tabLayout.getTabAt(1) != null)
                    tabLayout.getTabAt(1).select();
                break;
            case 2:
                if (tabLayout.getTabAt(2) != null)
                    tabLayout.getTabAt(2).select();
                break;
        }

    }

    private void setupViewPager (ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentAlumno(), "Alumno");
        adapter.addFragment(new FragmentAsignatura(), "Asignatura");
        adapter.addFragment(new FragmentMatricula(), "Matricula");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2); // 3 ViewPagger a la vez
    }

}