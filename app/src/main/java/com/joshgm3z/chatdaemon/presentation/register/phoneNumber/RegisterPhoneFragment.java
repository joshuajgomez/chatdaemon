package com.joshgm3z.chatdaemon.presentation.register.phoneNumber;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.joshgm3z.chatdaemon.R;
import com.joshgm3z.chatdaemon.common.utils.ContactFetcher;
import com.joshgm3z.chatdaemon.common.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterPhoneFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;

    @BindView(R.id.ll_continue_button)
    LinearLayout mBtContinue;

    private IRegisterFragmentListener mRegisterPhoneListener;

    public RegisterPhoneFragment() {
        // Required empty public constructor
    }

    public static RegisterPhoneFragment newInstance() {
        Logger.entryLog();
        RegisterPhoneFragment fragment = new RegisterPhoneFragment();
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
        View view = inflater.inflate(R.layout.fragment_register_phone, container, false);
        ButterKnife.bind(this, view);
        mBtContinue.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        Logger.entryLog();
        String phoneNumber = mEtPhoneNumber.getText().toString();
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            phoneNumber = phoneNumber.trim();
            phoneNumber = ContactFetcher.formatPhoneNumber(phoneNumber);
            if (phoneNumber != null) {
                mRegisterPhoneListener.onPhoneNumberEntered(phoneNumber);
            }
        } else {
            Toast.makeText(getContext(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
        }
        Logger.exitLog();
    }
}
