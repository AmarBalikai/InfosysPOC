package com.infosyspoc.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.infosyspoc.R;
import com.infosyspoc.adapter.CountryAdapter;
import com.infosyspoc.databinding.FragmentMainBinding;
import com.infosyspoc.model.ModelInformation;
import com.infosyspoc.room.CountryTable;
import com.infosyspoc.viewmodel.CountryDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    FragmentMainBinding binding;
    private CountryDetailViewModel mainViewModel;
    private CountryAdapter countryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_main, container, false);
        View root = binding.getRoot();
        swipeRefreshLayout=root.findViewById(R.id.swipeToRefresh);
        LinearLayoutManager horizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        binding.rvCountryList.setLayoutManager(horizontalLayout);
        binding.rvCountryList.setHasFixedSize(true);
        binding.rvCountryList.setItemAnimator(new DefaultItemAnimator());

        mainViewModel = new ViewModelProvider(this).get(CountryDetailViewModel.class);
        countryAdapter = new CountryAdapter();
        binding.rvCountryList.setAdapter(countryAdapter);

        getAllCountryInformation();

        //swipeRefreshLayout.
        return root;
    }

    private void getAllCountryInformation() {

        mainViewModel.getAllPosts().observe(getActivity(), new Observer<List<CountryTable>>() {
            @Override
            public void onChanged(@Nullable List<CountryTable> modelInformationList) {
                countryAdapter.setCountryResponseList((ArrayList<CountryTable>) modelInformationList);
            }
        });
    }
}
