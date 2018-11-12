package com.example.younes.hiddenfounderscodingchallenge.ui.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.younes.hiddenfounderscodingchallenge.R;
import com.example.younes.hiddenfounderscodingchallenge.ui.utils.AppConstants;
import com.example.younes.hiddenfounderscodingchallenge.ui.utils.SaveSharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by younes on 11/9/2018.
 */

public class SettingsFragment extends Fragment {


    @BindView(R.id.linear_view_button)
    ImageView linearViewButton;
    @BindView(R.id.gridx2_view_button)
    ImageView grid2xViewButton;
    @BindView(R.id.gridx3_view_button)
    ImageView gridx3ViewButton;

    @OnClick(R.id.linear_view_button)
    public void OnLinearViewClick(View view) {
        changeTint(AppConstants.VERTICAL_LINEAR_VIEW);
        SaveSharedPreference.setViewMode(getContext(),AppConstants.VERTICAL_LINEAR_VIEW);
    }
    @OnClick(R.id.gridx2_view_button)
    public void OnGrid2xViewClick(View view) {
        changeTint(AppConstants.GRID_TWO_ELEMENT_ROW_VIEW);
        SaveSharedPreference.setViewMode(getContext(),AppConstants.GRID_TWO_ELEMENT_ROW_VIEW);
    }
    @OnClick(R.id.gridx3_view_button)
    public void OnGrid3xViewClick(View view) {
        changeTint(AppConstants.GRID_THREE_ELEMENT_ROW_VIEW);
        SaveSharedPreference.setViewMode(getContext(),AppConstants.GRID_THREE_ELEMENT_ROW_VIEW);

    }

    public SettingsFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this,rootView);
        changeTint(SaveSharedPreference.getViewMode(getContext()));

        return rootView;
    }
    private void removeTints(){
        int lightBlackResource = getContext().getResources().getColor(R.color.lightblack);
        linearViewButton.setColorFilter(lightBlackResource);
        grid2xViewButton.setColorFilter(lightBlackResource);
        gridx3ViewButton.setColorFilter(lightBlackResource);
    }
    private void changeTint(int viewTypeSelected){
        removeTints();
        switch (viewTypeSelected){
            case AppConstants.VERTICAL_LINEAR_VIEW:
                linearViewButton.setColorFilter(getContext().getResources().getColor(R.color.colorAccent));
                break;
            case AppConstants.GRID_TWO_ELEMENT_ROW_VIEW:
                grid2xViewButton.setColorFilter(getContext().getResources().getColor(R.color.colorAccent));
                break;
            case AppConstants.GRID_THREE_ELEMENT_ROW_VIEW:
                gridx3ViewButton.setColorFilter(getContext().getResources().getColor(R.color.colorAccent));
                break;
        }
    }

}
