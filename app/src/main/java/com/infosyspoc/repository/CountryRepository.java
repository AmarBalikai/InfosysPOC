package com.infosyspoc.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.infosyspoc.model.CountryResponse;
import com.infosyspoc.network.APIInterface;
import com.infosyspoc.network.RetrofitService;
import com.infosyspoc.room.CountryRoomDBRepository;
import com.infosyspoc.room.CountryTable;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryRepository
{
    private static CountryRepository countryRepository;
    ArrayList<CountryTable> webserviceResponseList = new ArrayList<>();
    private MutableLiveData<ArrayList<CountryTable>> newsData = new MutableLiveData<>();
    Application application;
    public  CountryRepository(Application application){
        this.application = application;
        mAPIInterface = RetrofitService.cteateService(APIInterface.class);
        if (countryRepository == null){
            countryRepository = new CountryRepository();
        }
    }
    public static CountryRepository getInstance(){
        if (countryRepository == null){
            countryRepository = new CountryRepository();
        }
        return countryRepository;
    }

    private APIInterface mAPIInterface;

    public CountryRepository(){
        mAPIInterface = RetrofitService.cteateService(APIInterface.class);
    }

    public MutableLiveData<ArrayList<CountryTable>> getCountryInformation(){

        mAPIInterface.getCountryInformation().enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call,
                                   Response<CountryResponse> response) {
                if (response.isSuccessful()){
                    //newsData.setValue(response.body());


                    webserviceResponseList=response.body().getRows();
                    CountryRoomDBRepository countryRoomDBRepository = new CountryRoomDBRepository(application);
                    countryRoomDBRepository.insertPosts(webserviceResponseList);
                    newsData.setValue(webserviceResponseList);
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }
}

