package com.joshgm3z.chatdaemon.presentation.register;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.joshgm3z.chatdaemon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements IRegisterView, View.OnClickListener {

    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;

    @BindView(R.id.et_name)
    EditText mEtName;

    @BindView(R.id.bt_sign_up)
    Button mButton;

    private IRegisterPresenter mRegisterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterPresenter = new RegisterPresenter(this);

        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = mEtName.getText().toString();
        String phoneNumber = mEtPhoneNumber.getText().toString();
        if (name != null && phoneNumber != null) {
            name = name.trim();
            phoneNumber = phoneNumber.trim();
        }
        mRegisterPresenter.onAddUserClick(name, phoneNumber);
    }
}
