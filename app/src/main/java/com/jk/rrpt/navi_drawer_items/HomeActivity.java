package com.jk.rrpt.navi_drawer_items;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jk.rrpt.API.APICall;
import com.jk.rrpt.API.APIRes;
import com.jk.rrpt.MODEL.AllPdf;
import com.jk.rrpt.MODEL.Pdf;
import com.jk.rrpt.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeActivity extends Fragment {

    private static final String TAG = "homeactivity";

    private ArrayList<Pdf> list;
    private RecyclerView rv_home;

    SharedPreferences preferences;

    private String email;
    private SwipeRefreshLayout refresh_home;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = getActivity().getSharedPreferences("login_data", Context.MODE_PRIVATE);

        email = preferences.getString("email", "not found");

        list = new ArrayList<Pdf>();
        rv_home = (RecyclerView) getActivity().findViewById(R.id.rv_home);
        rv_home.setHasFixedSize(true);
        rv_home.setLayoutManager(new LinearLayoutManager(getActivity()));

        new GetPdf().execute();

        refresh_home=(SwipeRefreshLayout)getActivity().findViewById(R.id.refresh_home);

        refresh_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new GetPdf().execute();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh_home.setRefreshing(false);
                      }
                },500);
            }
        });


    }


    public class GetPdf extends AsyncTask<Void, Void, AllPdf> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading..");
            dialog.show();
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

            dialog.dismiss();
            if (allPdf != null) {
                list.clear();
                list.addAll(allPdf.getData());
                //  adapter.notifyDataSetChanged();

                MyAdaptor adaptor = new MyAdaptor(list);
                rv_home.setAdapter(adaptor);
                adaptor.notifyDataSetChanged();

            }
            super.onPostExecute(allPdf);
        }

    }


    public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.CustomViewHolder> {

        private ArrayList<Pdf> data;

        public MyAdaptor(ArrayList<Pdf> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.pdf_blog_card, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

            final Pdf pdf = data.get(position);

            String path = pdf.getBook_image_url().toString().trim();
            final String id = pdf.getBook_id().toString().trim();


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
                    private ProgressDialog dialog;


                    @Override
                    protected void onPreExecute() {

//                        Toast.makeText(getActivity(), "clicked.." + id +" "+email, Toast.LENGTH_SHORT).show();

                        dialog = new ProgressDialog(getActivity());
                        dialog.setMessage("Please Wait...!");
                        dialog.show();
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
                        dialog.dismiss();

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


        @Override
        public int getItemCount() {
            return data.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            TextView show_name;
            ImageView show_image;
            RelativeLayout rl;
            Button btn_add;

            public CustomViewHolder(View itemView) {

                super(itemView);
                rl = (RelativeLayout) itemView.findViewById(R.id.rl);
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
