package com.joshgm3z.chatdaemon.presentation.register.name;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.utils.Logger;
import com.joshgm3z.chatdaemon.presentation.register.phoneNumber.IRegisterFragmentListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterNameFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.et_name)
    EditText mEtName;

    @BindView(R.id.bt_sign_up)
    Button mBtSignup;

    private IRegisterFragmentListener mRegisterNameListener;

    public RegisterNameFragment() {
        // Required empty public constructor
    }

    public static RegisterNameFragment newInstance() {
        Bundle args = new Bundle();
        RegisterNameFragment fragment = new RegisterNameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_name, container, false);
        ButterKnife.bind(this, view);
        mBtSignup.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.entryLog();
        if (getActivity() instanceof IRegisterFragmentListener) {
            mRegisterNameListener = (IRegisterFragmentListener) getActivity();
        } else {
            Logger.log(Log.ERROR, "listener not available");
        }
        Logger.exitLog();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        Logger.entryLog();
        String name = mEtName.getText().toString();
        if (name != null && !name.trim().isEmpty()) {
            name = name.trim();
            mRegisterNameListener.onNameEntered(name);
        }
        Logger.exitLog();
    }
}
