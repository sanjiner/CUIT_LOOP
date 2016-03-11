package cuit.emergency.app;

import cuit.emergency.R;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class EmergencyApplication extends Application {

	/** 请求数据的dialog */
	private static Dialog toast_dialog;
	private static TextView toast_text;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public static void showDialog(Context context, String message) {
		toast_dialog = new Dialog(context, R.style.DialogStyle);
		toast_dialog.setCancelable(false);
		toast_dialog.setContentView(R.layout.progress_dialog_custom);
		Window window = toast_dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();

		int screenW = context.getResources().getDisplayMetrics().widthPixels;
		lp.width = (int) (0.6 * screenW);
		toast_text = (TextView) toast_dialog.findViewById(R.id.dialogText);
		toast_text.setText(message);
		toast_dialog.show();
	}

	public static void dismissDialog() {
		if (toast_dialog != null && toast_dialog.isShowing())
			toast_dialog.dismiss();
	}
}
