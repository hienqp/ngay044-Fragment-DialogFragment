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
                                Toast.makeText(getActivity(), "XÓA", Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                .setNegativeButton(
                        "Không",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "KHÔNG", Toast.LENGTH_SHORT).show();
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
<img src="https://github.com/hienqp/ngay044-Fragment-DialogFragment/blob/main/Screenshot_20220924_224903.png">
- khi click vào Button giả lập xóa sản phẩm
<img src="https://github.com/hienqp/ngay044-Fragment-DialogFragment/blob/main/Screenshot_20220924_224919.png">

___

SỬ DỤNG INTERFACE TẠO CALLBACK TƯƠNG TÁC GIỮA DIALOG FRAGMENT VÀ ACTIVITY

- sau khi DialogFragment được gọi, user click vào tùy chọn __"Có"__ hoặc __"Không"__ 1 thông báo Toast sẽ hiển thị __CÓ__ hoặc __KHÔNG__, thì đây chỉ là kết quả của logic trên DialogFragment, hoàn toàn chưa có tác động nào đến Activity khi user click vào tùy chọn trên DialogFragment
- giả sử ta đang tương tác 1 với ListView của Activity, hộp thoại DialogFragment được gọi, vậy làm sao để tác động ở DialogFragment nó cũng ảnh hưởng đến ListView của Activity
- ta sẽ sử dụng INTERFACE để truyền data (callback) 1 giá trị nào đó từ DialogFragment cho Activity
- khai báo 1 __Interface ConfirmDeleteData__
```java
package com.hienqp.dialogfragment;

public interface ConfirmDeleteData {
    public void deleteCommand(boolean delete);
}
```
- interface ConfirmDeleteData chứa 1 method trừu tượng __deleteCommand()__
- method __deleteCommand(boolean delete)__ sẽ nhận vào 1 giá trị boolean xác nhận XÓA hay KHÔNG

___

SỬ DỤNG INTERFACE ConfirmDeleteData

- ở DialogFragment ta khai báo interface ConfirmDeleteData
```java
    ConfirmDeleteData confirmDeleteData;
```
- trong __onAttach()__ hoặc do ta đang trong DialogFragment, ta sẽ gán interface đã khai báo cho __getActivity()__ để lấy Activity mà DialogFragment đang liên kết, để biết data sẽ trả về cho Activity nào
```java
        confirmDeleteData = (ConfirmDeleteData) getActivity();
```
- ở hai sự kiện click vào Button Có hoặc Không trên DialogFragment, ta sẽ truyền giá trị __true__ hoặc __false__ tương ứng vào method __deleteCommand()__ của interface đã khai báo, vì ta đã __getActivity()__ cho biến interface này nên Activity đã implement Interface này sẽ nhận được giá trị tương ứng khi override method của interface đã implement
```java
        dialogBuilder.setTitle("Xác nhận")
                .setMessage("Bạn có muốn xóa sản phẩm này không")
                .setPositiveButton(
                        "Có",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                confirmDeleteData.deleteCommand(true);
                            }
                        }
                )
                .setNegativeButton(
                        "Không",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                confirmDeleteData.deleteCommand(false);
                            }
                        }
                );
```
- ở MainActivity ta __implements ConfirmDeleteData__ và override method __deleteCommand()__ của interface ConfirmDeleteData
- method override này sẽ truyền vào giá trị tương ứng với sự kiện click vào button bên DialogFragment
- lúc này tùy vào giá trị tham số truyền vào mà ta xử lý logic code tương ứng
- __MainActivity.java__
```java
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
```
- như vậy, khi user click vào button __CÓ__ hoặc __KHÔNG__ bên DialogFragment thì sẽ trả về giá trị tương ứng cho Activity để Toast thông báo nằm bên Activity, tức là tác động tức thì trên giao diện của Activity từ DialogFragment
<img src="https://github.com/hienqp/ngay044-Fragment-DialogFragment/blob/main/Screenshot_20220925_232249.png">
