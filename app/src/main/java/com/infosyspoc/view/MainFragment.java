package com.infosyspoc.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.infosyspoc.R;
import com.infosyspoc.adapter.CountryAdapter;
import com.infosyspoc.application.App;
import com.infosyspoc.databinding.FragmentMainBinding;
import com.infosyspoc.interfaces.RetrofitCallback;
import com.infosyspoc.model.ModelInformation;
import com.infosyspoc.room.CountryTable;
import com.infosyspoc.viewmodel.CountryDetailViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.infosyspoc.utils.Constatets.COUNTRY_INFORMATION;
import static com.infosyspoc.utils.Constatets.COUNTRY_NAME;
import static com.infosyspoc.utils.Constatets.SOMETHING_WENT_WRONG_PLEASE_TRY_AGAIN;

public class MainFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    FragmentMainBinding binding;
    private CountryDetailViewModel mainViewModel;
    private CountryAdapter countryAdapter;
    private SharedPreferences mSharePreference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_main, container, false);
        View root = binding.getRoot();
        swipeRefreshLayout = root.findViewById(R.id.swipeToRefresh);
        LinearLayoutManager horizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rvCountryList.setLayoutManager(horizontalLayout);
        binding.rvCountryList.setHasFixedSize(true);
        binding.rvCountryList.setItemAnimator(new DefaultItemAnimator());

        mainViewModel = new ViewModelProvider(this).get(CountryDetailViewModel.class);
        countryAdapter = new CountryAdapter();
        binding.rvCountryList.setAdapter(countryAdapter);

        mSharePreference = getActivity().getSharedPreferences(COUNTRY_INFORMATION, Context.MODE_PRIVATE);

        getAllCountryInformation();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewModel.getDataFromServer(App.getInstance());
            }
        });
        return root;
    }

    private void getAllCountryInformation() {

        mainViewModel.getAllPosts().observe(getActivity(), new Observer<List<CountryTable>>() {
            @Override
            public void onChanged(@Nullable List<CountryTable> modelInformationList) {
                swipeRefreshLayout.setRefreshing(false);
                ((MainActivity)getActivity()).getSupportActionBar().setTitle(mSharePreference.getString(COUNTRY_NAME,""));

                /*if(!modelInformationList.get(0).getStatus())
                {
                    Toast.makeText(getActivity(),SOMETHING_WENT_WRONG_PLEASE_TRY_AGAIN,Toast.LENGTH_LONG).show();
                }*/

                countryAdapter.setCountryResponseList((ArrayList<CountryTable>) modelInformationList);
            }
        });
    }
}
