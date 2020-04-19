package com.infosyspoc.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.infosyspoc.interfaces.RetrofitCallback;
import com.infosyspoc.model.CountryResponse;
import com.infosyspoc.repository.CountryRepository;
import com.infosyspoc.room.CountryRoomDBRepository;
import com.infosyspoc.room.CountryTable;
import com.infosyspoc.utils.Constatets;

import java.util.ArrayList;
import java.util.List;

import static com.infosyspoc.utils.ConnectivityReceiver.isConnected;
import static com.infosyspoc.utils.Constatets.SOMETHING_WENT_WRONG_PLEASE_TRY_AGAIN;


public class CountryDetailViewModel extends AndroidViewModel implements RetrofitCallback
{

  private CountryRoomDBRepository countryRoomDBRepository;
  private LiveData<List<CountryTable>> mAllPosts;
  private LiveData<CountryResponse> mAllPostsssssss;
  CountryRepository countryRepository ;
  private LiveData<ArrayList<CountryTable>>  retroObservable;
  Application application;
  public CountryDetailViewModel(Application application){
    super(application);
    this.application=application;
    countryRoomDBRepository = new CountryRoomDBRepository(application);
    countryRepository = new CountryRepository(application,this);

    getDataFromServer(application);

    mAllPosts = countryRoomDBRepository.getAllPosts();
  }

  public void getDataFromServer(Application application) {
    if(isConnected(application))
    {
      retroObservable = countryRepository.getCountryInformation();
    }
    else
    {
      Toast.makeText(application, Constatets.DEVICE_NOT_CONNECTED_TO_INTERNET,Toast.LENGTH_SHORT).show();
    }
  }

  public LiveData<List<CountryTable>> getAllPosts() {
    return mAllPosts;
  }
  @Override
  public void onSuccess() {

  }

  @Override
  public void onFailure(String error) {
    //mAllPosts.getValue().get(0).setStatus(false);
    //mAllPostsssssss.getValue().

  }

}