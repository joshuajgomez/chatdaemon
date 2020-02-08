package com.joshgm3z.chatdaemon.presentation.register.loading;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingFragment extends Fragment {

    private static final String ARG_LOADING_MESSAGE = "ARG_LOADING_MESSAGE";

    private String mLoadingMessage;

    @BindView(R.id.tv_loading_message)
    TextView mTvLoadingMessage;

    public LoadingFragment() {
        // Required empty public constructor
    }

    public static LoadingFragment newInstance(String loadingMessage) {
        LoadingFragment fragment = new LoadingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOADING_MESSAGE, loadingMessage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLoadingMessage = getArguments().getString(ARG_LOADING_MESSAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        ButterKnife.bind(this, view);
        initUI();
        return view;
    }

    private void initUI() {
        if (mLoadingMessage != null) {
            mTvLoadingMessage.setText(mLoadingMessage);
        } else {
            Logger.log(Log.WARN, "mLoadingMessage is null");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
