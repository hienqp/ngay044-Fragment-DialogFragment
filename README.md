DIALOG FRAGMENT

- ngoài cách sử dụng AlertDialog để gọi 1 Dialog, ta cũng có thể cài đặt 1 Fragment là Dialog
- tương tự như AlertDialog thì khi Fragment làm Dialog cũng không cần thiết kế file layout, chỉ cần 1 file Java quản trị logic
___

DIALOG FRAGMENT

- để sử dụng Fragment làm Dialog thì trước tiên ta phải tạo 1 file Fragment __extends DialogFragment__
- đối với Fragment dạng Dialog ta sẽ override Method __onCreateDialog()__ thay vì onCreateView()
- __AskDeleteProductDialogFragment.java__
```java
package com.hienqp.dialogfragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class AskDeleteProductDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
```
- Method __onCreateDialog()__ sẽ trả về là 1 Dialog, ta tiến hành chỉnh sửa Method này như sau:
- ta sử dụng __AlertDialog.Builder__ để dựng 1 AlertDialog
```java
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
```
- sau đó ta thiết lập các thành phần của AlertDialog vừa build như sau
```java
        dialogBuilder.setTitle("Xác nhận")
                .setMessage("Bạn có muốn xóa sản phẩm này không")
                .setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                )
                .setNegativeButton(
                        "Không",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                );
```
- Method __onCreateDialog()__ cần trả về 1 Dialog, nhưng ta mới chỉ có 1 AlertDialog.Builder
- vì vậy từ AlertDialog.Builder ta gọi đến Method __create()__, __create()__ sẽ trả về 1 AlertDialog là subclass của Dialog
```java
        return dialogBuilder.create();
```
- __AskDeleteProductDialogFragment.java__ hoàn chỉnh
```java
package com.hienqp.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class AskDeleteProductDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("Xác nhận")
                .setMessage("Bạn có muốn xóa sản phẩm này không")
                .setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                )
                .setNegativeButton(
                        "Không",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                );

        return dialogBuilder.create();
    }
}
```
- thông thường khi tạo AlertDialog.Builder ta sẽ gọi method __show()__ ngay lập tức, sau đó mới return Dialog thông qua create(), rồi qua Activity gọi Fragment, sử dụng FragmentManager, FragmentTransaction, add fragment, commit
- nhưng vì ta đang __extends DialogFragment__ vì vậy ta có cách ngắn gọn hơn
	- ở DialogFragment ta chưa gọi show()
	- ở Activity ta khởi tạo object DialogFragment sau đó gọi show()

___

MAIN ACTIVITY

- ở MainActivity ta sẽ sử dụng 1 Button để bắt sự kiện khi click vào sẽ show DialogFragment
- __MainActivity.java__
```java
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
```
- khi run app ta sẽ có giao diện như sau

- khi click vào Button giả lập xóa sản phẩm
