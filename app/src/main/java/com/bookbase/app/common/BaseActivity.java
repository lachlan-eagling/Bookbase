package com.bookbase.app.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    BasePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = attachPresenter();
        //ButterKnife.bind(this);
    }

    /**
     * @return Presenter attached to activity.
     * This must be implemented by every activity extending from {@link BasePresenter}
     * */
    protected abstract BasePresenter attachPresenter();
}
