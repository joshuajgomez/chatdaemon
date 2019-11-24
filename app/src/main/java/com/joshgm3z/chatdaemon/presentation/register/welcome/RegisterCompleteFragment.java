package com.joshgm3z.chatdaemon.presentation.register.welcome;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterCompleteFragment extends Fragment implements IProgressUpdateListener {

    private static final String ARG_USER_NAME = "ARG_USER_NAME";

    private String mUserName;

    @BindView(R.id.tv_hello)
    TextView mTvHello;

    @BindView(R.id.tv_progress)
    TextView mTvProgress;

    public RegisterCompleteFragment() {
        // Required empty public constructor
    }


    public static RegisterCompleteFragment newInstance(String name) {
        RegisterCompleteFragment fragment = new RegisterCompleteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserName = getArguments().getString(ARG_USER_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_welcome, container, false);
        ButterKnife.bind(this, view);
        mTvHello.setText("Hello " + mUserName);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onProgressUpdate(int progress) {
        Logger.log(Log.INFO, "progress = [" + progress + "]");
        mTvProgress.setText(progress + "%");
    }
}
