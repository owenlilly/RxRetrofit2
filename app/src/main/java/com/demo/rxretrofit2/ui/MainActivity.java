package com.demo.rxretrofit2.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.rxretrofit2.R;
import com.demo.rxretrofit2.http.RxHttpServiceFactory;
import com.demo.rxretrofit2.http.model.Country;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView) RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CountryRecyclerAdapter());

        RxHttpServiceFactory.travelBriefing()
                            .getCountries()
                            .retry(2)
                            .observeOn(AndroidSchedulers.mainThread())
                            .flatMap(Observable::from)
                            .subscribe(country -> {
                                ((CountryRecyclerAdapter)recyclerView.getAdapter()).appendCountry(country);
                            }, Throwable::printStackTrace);
    }

    static class CountryRecyclerAdapter extends RecyclerView.Adapter<CountryRecyclerAdapter.CountryViewHolder> {

        private final List<Country> countries = new ArrayList<>();

        @Override
        public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CountryViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(CountryViewHolder holder, int position) {
            final Country country = countries.get(position);

            holder.txtCountry.setText(country.getName());
            holder.itemView.setOnClickListener(view ->
                    Toast.makeText(view.getContext(), country.getName(), Toast.LENGTH_SHORT).show());
        }

        @Override
        public int getItemCount() {
            return countries.size();
        }

        public void appendCountry(Country country){
            countries.add(country);
            notifyItemInserted(countries.size());
        }

        class CountryViewHolder extends RecyclerView.ViewHolder {

            public CountryViewHolder(ViewGroup parent) {
                this(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_country, parent, false));
            }

            private CountryViewHolder(View itemView){
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @Bind(R.id.txtCountry) TextView txtCountry;
        }
    }
}
