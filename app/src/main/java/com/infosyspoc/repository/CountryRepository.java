package com.infosyspoc.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Display;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.infosyspoc.interfaces.RetrofitCallback;
import com.infosyspoc.model.CountryResponse;
import com.infosyspoc.network.APIInterface;
import com.infosyspoc.network.RetrofitService;
import com.infosyspoc.room.CountryRoomDBRepository;
import com.infosyspoc.room.CountryTable;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.infosyspoc.utils.Constatets.COUNTRY_INFORMATION;
import static com.infosyspoc.utils.Constatets.COUNTRY_NAME;

public class CountryRepository
{
    private static CountryRepository countryRepository;
    ArrayList<CountryTable> webserviceResponseList = new ArrayList<>();
    private MutableLiveData<ArrayList<CountryTable>> newsData = new MutableLiveData<>();
    Application application;
    RetrofitCallback retrofitCallback;
    public  CountryRepository(Application application, RetrofitCallback retrofitCallback){
        this.retrofitCallback=retrofitCallback;
        this.application = application;
        mAPIInterface = RetrofitService.cteateService(APIInterface.class);
        if (countryRepository == null){
            countryRepository = new CountryRepository();
        }
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
                    retrofitCallback.onSuccess();
                    SharedPreferences.Editor mSharePreference=application.getSharedPreferences(COUNTRY_INFORMATION, Context.MODE_PRIVATE).edit();
                    mSharePreference.putString(COUNTRY_NAME,response.body().getTitle());
                    mSharePreference.apply();
                    mSharePreference.commit();
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                newsData.setValue(null);
                retrofitCallback.onFailure(t.getMessage());

            }
        });
        return newsData;
    }
}

