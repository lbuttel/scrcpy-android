package org.las2mile.scrcpy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ResolutionDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Activity mainActivity = getActivity();
		AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
		LayoutInflater inflater = LayoutInflater.from(mainActivity);
		View view = inflater.inflate(R.layout.fragment_resolution, null);
		final EditText et_height = view.findViewById(R.id.fragment_resolution_et_y);
		final EditText et_width = view.findViewById(R.id.fragment_resolution_et_x);
		builder.setView(view);
		builder.setTitle(R.string.fragment_resolution_tv_title)
				.setPositiveButton(R.string.fragment_resolution_btn_add, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int width = Integer.parseInt(et_width.getText().toString());
						int height = Integer.parseInt(et_height.getText().toString());
						saveResolution(height, width);
						((MainActivity)getActivity()).onResolutionSaved();
					}
				})
				.setNegativeButton(R.string.fragment_resolution_btn_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		return builder.create();
	}

	private String saveResolution(int height, int width) {

		String resolution;
		try {
			FileOutputStream fileOutputStream = getActivity().openFileOutput(getString(R.string.config_file_name), Context.MODE_APPEND);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
			resolution = height + "x" + width;
			outputStreamWriter.write(resolution);
			outputStreamWriter.append("\n");
			outputStreamWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return resolution;
	}
}
