package com.joshgm3z.chatdaemon.presentation.register.signup;

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
import com.joshgm3z.chatdaemon.presentation.register.login.IRegisterFragmentListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.et_user_name)
    EditText mEtUsername;

    @BindView(R.id.et_password1)
    EditText mEtPassword1;

    @BindView(R.id.et_password2)
    EditText mEtPassword2;

    private IRegisterFragmentListener mRegisterNameListener;

    public SignupFragment() {
        // Required empty public constructor
    }

    public static SignupFragment newInstance() {
        Bundle args = new Bundle();
        SignupFragment fragment = new SignupFragment();
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

    @OnClick(R.id.btn_signup)
    public void onClick(View view) {
        Logger.entryLog();
        String username = mEtUsername.getText().toString().trim();
        String password1 = mEtPassword1.getText().toString().trim();
        String password2 = mEtPassword2.getText().toString().trim();
        if (username.isEmpty()) {
            mEtUsername.setError("Please enter an username");
        } else if (password1.isEmpty()) {
            mEtPassword1.setError("Please enter a password");
        } else if (password2.isEmpty()) {
            mEtPassword2.setError("Please enter password again");
        } else if (!password1.equals(password2)){
            mEtPassword2.setError("Passwords do not match");
        }
        if (!username.isEmpty() && !password1.isEmpty() && !password2.isEmpty()
                && password1.equals(password2)) {
            mRegisterNameListener.onSignupClicked(username, password1);
        }
        Logger.exitLog();
    }

    public void showError(String message) {
        mEtUsername.requestFocus();
        mEtUsername.setError(message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
