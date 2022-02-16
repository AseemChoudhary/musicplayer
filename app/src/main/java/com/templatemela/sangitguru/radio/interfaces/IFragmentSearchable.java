package com.templatemela.sangitguru.radio.interfaces;

import com.templatemela.sangitguru.radio.station.StationsFilter;

public interface IFragmentSearchable {
    void Search(StationsFilter.SearchStyle searchStyle, String query);
}
