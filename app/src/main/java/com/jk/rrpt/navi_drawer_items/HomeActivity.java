package com.jk.rrpt.navi_drawer_items;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.jk.rrpt.API.APIRes;
import com.jk.rrpt.MODEL.AllPdf;
import com.jk.rrpt.MODEL.Pdf;
import com.jk.rrpt.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class HomeActivity extends Fragment {

    private static final String TAG = "homeactivity";

    private ArrayList<Pdf> list;
    private RecyclerView rv_home;

    private SharedPreferences preferences;
    private AdView mAdView;
    private MyAdaptor adaptor;

    private String email;
    private SwipeRefreshLayout refresh_home;
    private SpinKitView spinkit;

    public HomeActivity() {
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        spinkit = getActivity().findViewById(R.id.spin_kit);

        //ads banner
        mAdView = getActivity().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //sdk for ads
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        preferences = getActivity().getSharedPreferences("login_data", Context.MODE_PRIVATE);
        email = preferences.getString("email", "not found");

        list = new ArrayList<Pdf>();
        rv_home = getActivity().findViewById(R.id.rv_home);
        rv_home.setHasFixedSize(true);
        rv_home.setLayoutManager(new LinearLayoutManager(getActivity()));

        new GetPdf().execute();

        refresh_home = getActivity().findViewById(R.id.refresh_home);

        refresh_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new GetPdf().execute();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh_home.setRefreshing(false);
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

    public class GetPdf extends AsyncTask<Void, Void, AllPdf> {

        // private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
//            dialog = new ProgressDialog(getActivity());
//            dialog.setMessage("Loading..");
//            dialog.show();
            spinkit.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }


        @Override
        protected AllPdf doInBackground(Void... voids) {

            APICall api = new APICall();
            AllPdf result = api.GetPdfData();
            Log.d("home", String.valueOf(result));
            return result;
        }

        @Override
        protected void onPostExecute(AllPdf allPdf) {

            spinkit.setVisibility(View.GONE);
            // dialog.dismiss();
            if (allPdf != null) {
                list.clear();
                list.addAll(allPdf.getData());
                adaptor = new MyAdaptor(list);
                rv_home.setAdapter(adaptor);
                adaptor.notifyDataSetChanged();
            } else {
                list.clear();
                //list.add(null);
                adaptor = new MyAdaptor(null);
                rv_home.setAdapter(adaptor);
                adaptor.notifyDataSetChanged();
            }
            super.onPostExecute(allPdf);
        }

    }


    private class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.CustomViewHolder> implements Filterable {

        private ArrayList<Pdf> data;
        private ArrayList<Pdf> dataf;


        public MyAdaptor(ArrayList<Pdf> data) {
            this.data = data;
            dataf = new ArrayList<>(data);
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.pdf_blog_card, parent, false);

            return new CustomViewHolder(view);
        }

        private Filter exfilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                ArrayList<Pdf> filterlist = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {

                    filterlist.addAll(dataf);
                } else {
                    String filterpattern = constraint.toString().toLowerCase().trim();

                    for (Pdf item : dataf) {

                        if (item.getBook_title().contains(filterpattern)) {
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

                data.clear();
                data.addAll((ArrayList) results.values);
                notifyDataSetChanged();
            }
        };


        @Override
        public int getItemCount() {
            if (list != null && list.size() > 0) {
                return list.size();
            } else {
                return 0;
            }
            //  return data.size();
        }

        @Override
        public Filter getFilter() {
            return exfilter;
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

            if (data.get(position) != null) {
                final Pdf pdf = data.get(position);

                String path = pdf.getBook_image_url().trim();
                final String id = pdf.getBook_id().trim();


                if (path != "") {
                    holder.setImage(getContext(), path);

                } else {
                    Toast.makeText(getActivity(), "does't load url", Toast.LENGTH_SHORT).show();
                }

                holder.show_name.setText(pdf.getBook_title());

                holder.btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AddBook().execute();

                    }

                    class AddBook extends AsyncTask<Void, Void, String> {
                        // private ProgressDialog dialog;


                        @Override
                        protected void onPreExecute() {

//                        Toast.makeText(getActivity(), "clicked.." + id +" "+email, Toast.LENGTH_SHORT).show();


//                            dialog = new ProgressDialog(getActivity());
//                            dialog.setMessage("Please Wait...!");
//                            dialog.show();
                            spinkit.setVisibility(View.VISIBLE);
                            super.onPreExecute();
                        }


                        @Override
                        protected String doInBackground(Void... voids) {

                            APICall api = new APICall();
                            String result = api.AddBookUser(email, id);
                            return result;
                        }

                        @Override
                        protected void onPostExecute(String result) {

                            if (result != null) {
                                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Please try again..!", Toast.LENGTH_SHORT).show();
                            }
                            // dialog.dismiss();
                            spinkit.setVisibility(View.GONE);
                            super.onPostExecute(result);
                        }
                    }
                });

                holder.rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // Toast.makeText(getActivity(), "You Clicked " + APIRes.BASE_URL + pdf.getBook_pdf_url(), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setDataAndType(Uri.parse(APIRes.BASE_URL + pdf.getBook_pdf_url()), "application/pdf");
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                    }
                });
            }
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            TextView show_name;
            ImageView show_image;
            RelativeLayout rl;
            Button btn_add;

            public CustomViewHolder(View itemView) {

                super(itemView);
                rl = itemView.findViewById(R.id.rl);
                show_name = itemView.findViewById(R.id.show_name);
                show_image = itemView.findViewById(R.id.show_image);
                btn_add = itemView.findViewById(R.id.btn_add);
            }

            public void setImage(Context context, String image) {

                Picasso.get()
                        .load(APIRes.BASE_URL + image)
                        .fit()
                        .into(show_image);
            }

        }
    }
}

