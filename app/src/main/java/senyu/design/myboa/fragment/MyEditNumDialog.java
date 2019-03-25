package senyu.design.myboa.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import es.dmoral.toasty.Toasty;
import senyu.design.myboa.R;

public class MyEditNumDialog extends DialogFragment {
    private static final String TAG = "SENYU";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.edit_dialog,null);
        builder.setView(view)
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = view.findViewById(R.id.edit_content);
                        Toasty.success(getActivity(),editText.getText().toString()).show();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("amount",editText.getText());
                        resultIntent.putExtra("position",getArguments().getInt("position"));
                        getTargetFragment().onActivityResult(1,1,resultIntent);
                    }
                });
        return builder.create();
    }
}
