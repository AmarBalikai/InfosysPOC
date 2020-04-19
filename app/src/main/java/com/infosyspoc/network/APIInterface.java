package com.infosyspoc.network;

import com.infosyspoc.model.CountryResponse;
import com.infosyspoc.utils.Constatets;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET(Constatets.COUNTRY_URL)
    Call<CountryResponse> getCountryInformation();
}
