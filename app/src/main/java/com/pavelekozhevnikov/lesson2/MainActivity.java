package com.pavelekozhevnikov.lesson2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private static final String TAG = "MainActivity";

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = this.findViewById(R.id.textView);
        editText = this.findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //подписываем на bus
        EventBus.getInstance().toObservable().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG,"event of type: "+o.getClass()+" = "+o.toString());
            }
        });

        EventBus.getInstance().toObservable().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (o instanceof EventType1) {
                    Log.i(TAG,"event1: "+((EventType1) o).getS());
                }else if (o instanceof EventType2) {
                    Log.i(TAG,"event2: "+((EventType2) o).getS());
                }
            }
        });
    }

    public void Button1Click(View view) {
        EventBus.getInstance().send(new EventType1("Button1Click"));
    }

    public void Button2Click(View view) {
        EventBus.getInstance().send(new EventType2("Button2Click"));
    }
}
