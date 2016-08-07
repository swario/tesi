package com.example.cristian.everysale.Interfaces;

import com.example.cristian.everysale.BaseClasses.Province;
import com.example.cristian.everysale.BaseClasses.Region;

import java.util.ArrayList;

/**
 * Created by Cristian on 07/08/2016.
 */
public interface SpinnerSetup {

    public void setupRegions(ArrayList<Region> result);
    public void setupProvinces(ArrayList<Province> result);
    public void setupMunicipalities(ArrayList<String> result);
}
