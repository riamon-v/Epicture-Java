package com.example.riamon_v.java_epicture_2017;

import android.content.Intent;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.riamon_v.java_epicture_2017.AddActuality.AddActivity;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.ImgurModel.AllObjects;
import com.example.riamon_v.java_epicture_2017.Api.Imgur.Services.GetImagesService;
import com.example.riamon_v.java_epicture_2017.DatabaseManagment.DatabaseHandler;
import com.example.riamon_v.java_epicture_2017.DatabaseManagment.User;
import com.example.riamon_v.java_epicture_2017.ListManagment.AdapterCard;
import com.example.riamon_v.java_epicture_2017.ListManagment.CardClass;
import com.example.riamon_v.java_epicture_2017.SignLoginHandling.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FloatingActionButton addButton;
    public static CoordinatorLayout container;
    public static User user;
    private static MenuItem searchItem;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        container = findViewById(R.id.main_content);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        addButton = findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSectionsPagerAdapter.fragment.add(getString(mViewPager.getCurrentItem() == 0 ? R.string.title_imgur : R.string.title_flickr));
                finish();
            }
        });
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        user = DatabaseHandler.getInstance(getApplicationContext()).getUserDao().getUserById(
                                            getIntent().getIntExtra("idUser", 0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        searchItem = menu.findItem(R.id.action_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            /*case R.id.action_settings:
                break ;
            */case R.id.action_disconnect:
                disconnect();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void disconnect() {
        user.setUserDiconnect();
        DatabaseHandler.getInstance(this).getUserDao().updateUser(user);
        Intent discoIntent = new Intent(this, LoginActivity.class);
        startActivity(discoIntent);
        MainActivity.this.finish();
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private RecyclerView recyclerView;
        private AdapterCard adapter;
        final List<CardClass> imgImgur = new ArrayList<>();
        final List<CardClass> fakeFlickr = new ArrayList<>();
        final List<CardClass> favImgImgur = new ArrayList<>();
        Map<Integer, CardClass> tmpList = new HashMap<Integer, CardClass>();
        List<CardClass> adapterList = new ArrayList<>();
        private boolean isClickFav = false;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
       // private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            for (int i = 0; i < 6; i++) {
                CardClass c;
                if ((i % 2) == 0) {
                    c = new CardClass("Morgan le Bg", "Vraiment un bg démesuré", "", "", "true");
                    c.setIdResources(R.drawable.cat);
                }
                else {
                    c = new CardClass("Sylvain le jeunot", "Android Expert", "","", "true");
                    c.setIdResources(R.drawable.dog);
                }
                fakeFlickr.add(c);
            }

            recyclerView = rootView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            //for crate home button

            imageServiceInstance(true);
            SystemClock.sleep(300);
            imageServiceInstance(false);

            return rootView;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            switch (id) {
                case R.id.action_favorite:
                    item.setIcon(!isClickFav ? R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_white_24dp);
                    showFavorite();
                    break;
            }

            final SearchView searchView =
                    (SearchView) searchItem.getActionView();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {

                    for (int j = 0; j < imgImgur.size(); j++) {
                        if (imgImgur.get(j).getTitle().indexOf(query) == -1) {
                            for (int i = adapterList.size(); i > 0; i--) {
                                if (adapterList.get(i - 1).getId() == imgImgur.get(j).getId())
                                    adapter.removeItem(i - 1);
                            }
                        } else if (!adapterList.contains(imgImgur.get(j))) {
                            adapter.restoreItem(imgImgur.get(j), 0);
                        }
                    }
                    return false;
                }
            });

            searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem menuItem) {
                     return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    adapterList.clear();
                   /* for(Map.Entry<Integer, CardClass> entry : tmpList.entrySet()) {
                        adapter.restoreItem(entry.getValue(), entry.getKey());
                    }*/
                    adapterList.addAll(imgImgur);
                    adapter.notifyDataSetChanged();
                    return true;
                }
            });
            /*searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    /*for(Map.Entry<Integer, CardClass> entry : tmpList.entrySet()) {
                        adapter.restoreItem(entry.getValue(), entry.getKey());
                    }
                    tmpList.clear();
                    Log.d("CLOSE", "close");
                    return false;
                }
            });*/

            return super.onOptionsItemSelected(item);
        }

        public void showFavorite() {
            isClickFav = !isClickFav;
            int i = adapter.getItemCount();

            if (isClickFav) {
                while (i > 0) {
                    if (Objects.equals(adapterList.get(i - 1).fav, "false")) {
                        tmpList.put(i - 1, adapterList.get(i - 1));
                        adapter.removeItem(i - 1);
                    }
                    i--;
                }
            }
            else {
                for (Map.Entry<Integer, CardClass> entry : tmpList.entrySet()) {
                    if (adapterList.size() != 0)
                        adapter.restoreItem(entry.getValue(), entry.getKey());
                    else
                        adapterList.add(entry.getValue());
                }
                tmpList.clear();
            }
        }

        public void imageServiceInstance(final boolean favCheck/*, final boolean assertModif*/) {
            new GetImagesService(getContext(), user).Execute(new retrofit.Callback<AllObjects.ListImageResponse>() {
                @Override
                public void success(AllObjects.ListImageResponse imageResponse, Response response) {
                    try {
                        JSONObject obj = new JSONObject( new String(((TypedByteArray) response.getBody()).getBytes()));
                        JSONArray arr = obj.getJSONArray("data");

                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject a = arr.getJSONObject(i);
                            CardClass picture = new CardClass(a.getString("title"), a.getString("description"),
                                    a.getString("link"), a.getString("id"), a.getString("favorite"));
                            if (!favCheck) {
                                for (int j = 0; j < favImgImgur.size(); j++) {
                                    if (Objects.equals(picture.getId(), favImgImgur.get(j).getId()))
                                        picture.fav = "true";
                                }
                                imgImgur.add(picture);
                            }
                            else
                                favImgImgur.add(picture);
                        }
                        adapterList.clear();
                        adapterList.addAll(getArguments().getInt(ARG_SECTION_NUMBER) == 1 ? imgImgur : fakeFlickr);
                        adapter = new AdapterCard(adapterList, null, getContext());
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    //Assume we have no connection, since error is null
                    if (error == null) {
                    }
                }
            }, favCheck);
        }

        public void add(String title) {
            Intent intent = new Intent(getActivity(), AddActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("idUser", user.getId());

            startActivity(intent);
        }

        public RecyclerView getRecycler() {
            return recyclerView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public PlaceholderFragment fragment;

        public SectionsPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            fragment = PlaceholderFragment.newInstance(position + 1);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
