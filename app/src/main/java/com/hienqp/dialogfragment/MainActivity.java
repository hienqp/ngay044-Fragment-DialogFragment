package com.hienqp.dialogfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ConfirmDeleteData{
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

    @Override
    public void deleteCommand(boolean delete) {
        if (delete) {
            Toast.makeText(this, "ACTIVITY: XÓA SẢN PHẨM", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "ACTIVITY: KHÔNG XÓA", Toast.LENGTH_SHORT).show();
        } 
    }
}