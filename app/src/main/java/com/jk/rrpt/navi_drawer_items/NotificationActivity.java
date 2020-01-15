package com.jk.rrpt.navi_drawer_items;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jk.rrpt.API.APICall;
import com.jk.rrpt.MODEL.AllNoti;
import com.jk.rrpt.MODEL.Noti;
import com.jk.rrpt.R;

import java.util.ArrayList;

public class NotificationActivity extends Fragment {

    private static final String TAG = "notification";

    private RecyclerView rv_noti;
    private ArrayList<Noti> list;
    private SwipeRefreshLayout refresh_noti;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





        list = new ArrayList<Noti>();
        rv_noti = getActivity().findViewById(R.id.rv_noti);
        rv_noti.setHasFixedSize(true);
        rv_noti.setLayoutManager(new LinearLayoutManager(getActivity()));

        new GetNoti().execute();

        refresh_noti = (SwipeRefreshLayout) getActivity().findViewById(R.id.refresh_nofi);

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

    public class GetNoti extends AsyncTask<Void, Void, AllNoti> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.show();
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

            dialog.dismiss();
            if (allNoti != null) {
                list.clear();
                list.addAll(allNoti.getData());
                MyAdaptor1 adaptor = new MyAdaptor1(list);
                rv_noti.setAdapter(adaptor);
                adaptor.notifyDataSetChanged();
            }
            super.onPostExecute(allNoti);
        }

    }


    public class MyAdaptor1 extends RecyclerView.Adapter<MyAdaptor1.BlogViewHolder> {

        public MyAdaptor1(ArrayList<Noti> list) {
            this.list = list;
        }

        ArrayList<Noti> list;

        @NonNull
        @Override
        public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.noti_blog_card, parent, false);
            return new BlogViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {

            Noti noti = list.get(position);
            holder.show_noti.setText(noti.getNoti_name());

        }

        @Override
        public int getItemCount() {
            return list.size();
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
