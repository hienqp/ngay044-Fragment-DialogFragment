package com.hienqp.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class AskDeleteProductDialogFragment extends DialogFragment {

    ConfirmDeleteData confirmDeleteData;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        confirmDeleteData = (ConfirmDeleteData) getActivity();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
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

        return dialogBuilder.create();
    }
}
