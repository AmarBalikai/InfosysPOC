package com.infosyspoc.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.infosyspoc.repository.CountryRepository;
import com.infosyspoc.room.CountryRoomDBRepository;
import com.infosyspoc.room.CountryTable;
import com.infosyspoc.utils.Constatets;

import java.util.ArrayList;
import java.util.List;

import static com.infosyspoc.utils.ConnectivityReceiver.isConnected;


public class CountryDetailViewModel extends AndroidViewModel
{

  private CountryRoomDBRepository countryRoomDBRepository;
  private LiveData<List<CountryTable>> mAllPosts;
  CountryRepository countryRepository ;
  private LiveData<ArrayList<CountryTable>>  retroObservable;
  public CountryDetailViewModel(Application application){
    super(application);
    countryRoomDBRepository = new CountryRoomDBRepository(application);
    countryRepository = new CountryRepository(application);
    if(isConnected(application))
    {
      retroObservable = countryRepository.getCountryInformation();
    }
    else
    {
      Toast.makeText(application, Constatets.DEVICE_NOT_CONNECTED_TO_INTERNET,Toast.LENGTH_SHORT).show();
    }

    mAllPosts = countryRoomDBRepository.getAllPosts();
  }

  public LiveData<List<CountryTable>> getAllPosts() {
    return mAllPosts;
  }

}