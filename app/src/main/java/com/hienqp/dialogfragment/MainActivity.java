package com.hienqp.dialogfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnDeleteProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDeleteProduct = findViewById(R.id.button_delete_product);

        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskDeleteProductDialogFragment dialogFragment = new AskDeleteProductDialogFragment();
                dialogFragment.show(getFragmentManager(), "dialogFragment");
            }
        });
    }
}