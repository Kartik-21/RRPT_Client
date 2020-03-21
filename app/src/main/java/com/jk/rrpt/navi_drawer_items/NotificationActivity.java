package com.jk.rrpt.navi_drawer_items;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.jk.rrpt.API.APICall;
import com.jk.rrpt.MODEL.AllNoti;
import com.jk.rrpt.MODEL.Noti;
import com.jk.rrpt.R;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class NotificationActivity extends Fragment {

    private static final String TAG = "notification";

    private MyAdaptor1 adaptor;

    private RecyclerView rv_noti;
    private ArrayList<Noti> list;
    private SwipeRefreshLayout refresh_noti;
    private ScrollView scrol_noti;
    private AdView mAdView;
    private SpinKitView spinkit;

    public NotificationActivity() {
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        spinkit = getActivity().findViewById(R.id.spin_kit);

        //ads banner
        mAdView = getActivity().findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //sdk for ads
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        list = new ArrayList<Noti>();
        rv_noti = getActivity().findViewById(R.id.rv_noti);
        rv_noti.setHasFixedSize(true);
        rv_noti.setLayoutManager(new LinearLayoutManager(getActivity()));

        // scrol_noti=(ScrollView)getActivity().findViewById(R.id.scroll_noti);
        //scrol_noti.fullScroll(View.FOCUS_DOWN);


        new GetNoti().execute();

        refresh_noti = getActivity().findViewById(R.id.refresh_nofi);

        refresh_noti.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new GetNoti().execute();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh_noti.setRefreshing(false);
                    }
                }, 500);
            }
        });


    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu2, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptor.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public class GetNoti extends AsyncTask<Void, Void, AllNoti> {

        //  ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
//            dialog = new ProgressDialog(getActivity());
//            dialog.setMessage("Loading...");
//            dialog.show();
            spinkit.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected AllNoti doInBackground(Void... voids) {

            APICall apiCall = new APICall();
            AllNoti result = apiCall.GetNotiData();
            return result;
        }


        @Override
        protected void onPostExecute(AllNoti allNoti) {

            spinkit.setVisibility(View.GONE);
            //  dialog.dismiss();
            if (allNoti != null) {
                list.clear();
                list.addAll(allNoti.getData());
                adaptor = new MyAdaptor1(list);
                rv_noti.setAdapter(adaptor);
                adaptor.notifyDataSetChanged();
            } else {

                list.clear();
                //    list.add(null);
                adaptor = new MyAdaptor1(null);
                rv_noti.setAdapter(adaptor);
                adaptor.notifyDataSetChanged();

            }
            super.onPostExecute(allNoti);
        }

    }


    private class MyAdaptor1 extends RecyclerView.Adapter<MyAdaptor1.BlogViewHolder> implements Filterable {

        ArrayList<Noti> list;
        private ArrayList<Noti> dataf;
        private Filter exfilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                ArrayList<Noti> filterlist = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {

                    filterlist.addAll(dataf);
                } else {
                    String filterpattern = constraint.toString().toLowerCase().trim();

                    for (Noti item : dataf) {

                        if (item.getNoti_name().contains(filterpattern)) {
                            filterlist.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filterlist;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                list.clear();
                list.addAll((ArrayList) results.values);
                notifyDataSetChanged();
            }
        };

        @NonNull
        @Override
        public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.noti_blog_card, parent, false);
            return new BlogViewHolder(view);

        }

        public MyAdaptor1(ArrayList<Noti> list) {
            this.list = list;
            dataf = new ArrayList<>(list);
        }


        @Override
        public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {

            if (list.get(position) != null) {
                Noti noti = list.get(position);

                holder.show_noti.setText(Html.fromHtml(noti.getNoti_name()));

            }
        }

        @Override
        public int getItemCount() {

            if (list != null && list.size() > 0) {
                //   Toast.makeText(getContext(),list.size(),Toast.LENGTH_SHORT).show();
                return list.size();
            } else {
                return 0;
            }
        }

        @Override
        public Filter getFilter() {
            return exfilter;
        }


        public class BlogViewHolder extends RecyclerView.ViewHolder {

            TextView show_noti;

            public BlogViewHolder(@NonNull View itemView) {
                super(itemView);
                show_noti = itemView.findViewById(R.id.show_noti);
            }
        }


    }

}
