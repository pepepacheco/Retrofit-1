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
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        if (getIntent().getIntExtra("soy", 0) == 1)
            tabLayout.getTabAt(1).select();
    }

    private void setupViewPager (ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentAlumno(), "Alumno");
        adapter.addFragment(new FragmentAsignatura(), "Asignatura");
        adapter.addFragment(new FragmentMatricula(), "Matricula");
        viewPager.setAdapter(adapter);
    }

}