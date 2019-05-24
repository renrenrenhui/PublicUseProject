package com.example.xiaoniu.publicuseproject.dial;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


import com.example.xiaoniu.publicuseproject.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;


public class Utils {

    public static final int OVERLAY_PERMISSION_REQ_CODE = 1000;
    public static final int SETTINGS_REQUEST_CODE = 1001;


    /**
     * 打开悬浮窗设置页
     * 部分第三方ROM无法直接跳转可使用{@link #openAppSettings(Context)}跳到应用详情页
     *
     * @param context
     * @return true if it's open successful.
     */
    public static void openOpsSettings(Activity context) {
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            context.startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            openAppSettings(context);
        }
    }

    /**
     * 打开应用详情页
     *
     * @param context
     * @return true if it's open success.
     */
    public static boolean openAppSettings(Activity activity) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getApplicationContext().getPackageName(), null);
            intent.setData(uri);
            activity.startActivityForResult(intent, SETTINGS_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Dialog getStandardDialog(Context context, View view) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(view);
        setLayoutParams(context, dialog);
        dialog.show();
        return dialog;
    }

    public static void setLayoutParams(Context context, Dialog dialog) {

        if (dialog != null) {
            Window dialogWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = context.getResources().getDimensionPixelSize(R.dimen.dialog_width);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;
            lp.dimAmount = (float) 0.3;
            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialogWindow.setAttributes(lp);
        }
    }

    public static void deleteFile(File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
