package com.pavelekozhevnikov.lesson2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private static final String TAG = "MainActivity";

    private PublishSubject observable1;
    private PublishSubject observable2;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = this.findViewById(R.id.textView);
        editText = this.findViewById(R.id.editText);

        /*
        Нужно добиться, чтобы при вводе символов в EditText эта последовательность символов отображалась в TextView.
        Для этой задачи следует использовать метод EditText - addTextChangedListener с объектом TextWatcher.
         */
        PublishSubject<String> observable = PublishSubject.create();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                observable.onNext(editable.toString());
            }
        });
        observable.subscribe(s -> textView.setText(s));


        /*
         *Создать EventBus, принимающий данные от двух Observable и передающий эти данные двум подписчикам. При этом сама «шина» должна уметь формировать свои собственные события.
         */

        //подписываем шину
        observable1 = PublishSubject.create();
        observable1.subscribe(EventBus.getInstance().toObserver());
        observable2 = PublishSubject.create();
        observable2.subscribe(EventBus.getInstance().toObserver());

        //подписчик шины 1
        EventBus.getInstance().toPublishSubject().subscribe(o -> Log.i(TAG,"Subscriber1, event of type: "+o.getClass()+", value = "+o.toString()));
        //подписчик шины 2
        EventBus.getInstance().toPublishSubject().subscribe(o -> Log.i(TAG,"Subscriber2, event of type: "+o.getClass()+", value = "+o.toString()));
    }

    public void Button1Click(View view) {
        observable1.onNext(new EventType1("Button1Click"));
    }

    public void Button2Click(View view) {
        observable2.onNext(new EventType1("Button2Click"));
    }
}
