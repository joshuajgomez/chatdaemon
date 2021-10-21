package com.joshgm3z.chatdaemon.presentation.register.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.et_user_name)
    EditText mEtUsername;

    @BindView(R.id.et_password)
    EditText mEtPassword;

    private IRegisterFragmentListener mRegisterPhoneListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        Logger.entryLog();
        LoginFragment fragment = new LoginFragment();
        Logger.exitLog();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.entryLog();
        Logger.exitLog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Logger.entryLog();
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        Logger.exitLog();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.entryLog();
        if (getActivity() instanceof IRegisterFragmentListener) {
            mRegisterPhoneListener = (IRegisterFragmentListener) getActivity();
        } else {
            Logger.log(Log.ERROR, "listener not available");
        }
        Logger.exitLog();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.entryLog();
        Logger.exitLog();
    }

    @OnClick({R.id.btn_login, R.id.btn_new_user})
    public void onClick(View view) {
        Logger.entryLog();
        if (view.getId() == R.id.btn_login) {

            String username = mEtUsername.getText().toString().trim();
            String password = mEtPassword.getText().toString().trim();
            Logger.log("username " + username);
            if (username.isEmpty()) {
                mEtUsername.setError("Please enter an username");
            } else if (password.isEmpty()) {
                mEtPassword.setError("Please enter password");
            }
            if (!username.isEmpty() && !password.isEmpty()) {
                mRegisterPhoneListener.onLoginClicked(username, password);
            }
        } else if (view.getId() == R.id.btn_new_user) {
            mRegisterPhoneListener.onNewUserClick();
        }
        Logger.exitLog();
    }

    public void showError(String message) {
        mEtUsername.requestFocus();
        mEtUsername.setError(message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
