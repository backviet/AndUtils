package com.zegome.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by QuanLT on 8/1/16.
 */
public final class Utils {
    // ===========================================================
    // Constants
    // ===========================================================
    public static final String STRING_EMPTY = "";

    public static final int NONE = -1;

    public static final String COPY_RIGHT_C_LETTER = "©";

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================
    public static boolean isEmulator() {
        final String buildManufacturer = Build.MANUFACTURER;
        final String buildModel = Build.MODEL;
        return isGenymotionEmulator(buildManufacturer) || containsEmulatorHints(buildModel);
    }

    public static boolean isGenymotionEmulator(String buildManufacturer) {
        return buildManufacturer != null &&
                (buildManufacturer.contains("Genymotion") || buildManufacturer.equals("unknown"));
    }

    public static boolean containsEmulatorHints(String buildModel) {
        return buildModel.startsWith("sdk")
                || "google_sdk".equals(buildModel)
                || buildModel.contains("Emulator")
                || buildModel.contains("Android SDK");
    }

    public static String getStringFileFromAssets(@NonNull final Context context, @NonNull final String fileDir) {
        String json = null;
        try {
            final InputStream is = context.getAssets().open(fileDir);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String getDateStrFromUTC(@NonNull final Context context, final String UTCStr) {
        try {
            if (Utils.isStringEmptyorNull(UTCStr)) {
                return UTCStr;
            }
            final SimpleDateFormat mISO8601Local;
            final String format;
            if (UTCStr.contains("Z")) {
                format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
                mISO8601Local = new SimpleDateFormat(format);
                mISO8601Local.setTimeZone(TimeZone.getTimeZone("UTC"));
            } else {
                format = "yyyy-MM-dd'T'HH:mm:ss";
                mISO8601Local = new SimpleDateFormat(format);
            }

            final Date date = mISO8601Local.parse(UTCStr);
            final DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context.getApplicationContext());
            final String ret = dateFormat.format(date);
            return ret;
        } catch (ParseException e) {
            e.printStackTrace();
            return UTCStr;
        }
    }

    public static Date getDateFromUTC(@NonNull final Context context, final String UTCStr) {
        try {
            if (Utils.isStringEmptyorNull(UTCStr)) {
                return null;
            }
            final SimpleDateFormat mISO8601Local;
            final String format;
            if (UTCStr.contains("Z")) {
                format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
                mISO8601Local = new SimpleDateFormat(format);
                mISO8601Local.setTimeZone(TimeZone.getTimeZone("UTC"));
            } else {
                format = "yyyy-MM-dd'T'HH:mm:ss";
                mISO8601Local = new SimpleDateFormat(format);
            }

            final Date date = mISO8601Local.parse(UTCStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Dialog getDialog(final Context context) {
        return getDialog(context, NONE);
    }

    public static Dialog getDialog(final Context context, final int layout) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            if (NONE != layout) {
                dialog.setContentView(layout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public static final boolean isPackageInstalled(@NonNull Context context, String packagename) {
        try {
            final PackageManager pm = context.getPackageManager();
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static final Intent getLaunchIntentForPackage(@NonNull final Context context, final String packageName) {
        try {
            final Intent intentToResolve = new Intent(Intent.ACTION_MAIN);
            intentToResolve.addCategory(Intent.CATEGORY_INFO);
            intentToResolve.setPackage(packageName);

            final PackageManager pm = context.getPackageManager();
            List<ResolveInfo> ris = pm.queryIntentActivities(intentToResolve, 0);

            // Otherwise, try to find a main launcher activity.
            if (ris == null || ris.size() <= 0) {
                // reuse the intent instance
                intentToResolve.removeCategory(Intent.CATEGORY_INFO);
                intentToResolve.addCategory(Intent.CATEGORY_LAUNCHER);
                intentToResolve.setPackage(packageName);
                ris = pm.queryIntentActivities(intentToResolve, 0);
            }
            if (ris == null || ris.size() <= 0) {
                return null;
            }
            final Intent intent = new Intent(intentToResolve);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClassName(ris.get(0).activityInfo.packageName,
                    ris.get(0).activityInfo.name);

            return intent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static final int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static final String getCopyRight(final String company) {
        final int currYear = getCurrentYear();
        final String ret = COPY_RIGHT_C_LETTER + " " + currYear + " - " + company;
        return ret;
    }

    public static int getListViewHeightBasedOnChildren(@NonNull final ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        try {
            if (listAdapter != null) {
                int totalHeight = 0;
                int size = listAdapter.getCount();
                for (int i = 0; i < size; i++) {
                    View listItem = listAdapter.getView(i, null, listView);
                    if (listItem == null) {
                        continue;
                    }
                    listItem.measure(0, 0);
                    totalHeight += listItem.getMeasuredHeight();
                }
                totalHeight = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

                return totalHeight;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static final boolean isNetworkActive(@NonNull final Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static final void isNetworkActive(@NonNull final Activity ac, final INetworkCheckCallback callback) {
        if (callback == null) {
            return;
        }
        ac.runOnUiThread(new Runnable() {
            public void run() {
                ConnectivityManager connectivityManager = (ConnectivityManager) ac.getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

                callback.onCompleted(activeNetworkInfo!=null && activeNetworkInfo.isConnected());
            }
        });

    }

    public static final String checkNetworkType(final Context context) {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo =
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean wifiConnected = wifiInfo.getState() == NetworkInfo.State.CONNECTED;
        if (wifiConnected) {
            return "wifi";
        }
        final NetworkInfo mobileInfo =
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean mobileConnected = mobileInfo.getState() == NetworkInfo.State.CONNECTED;
        if (mobileConnected) return "3G/4G";
        return "disconnected";
    }

    private static final long getRamFree(final Context context) {
        ActivityManager.MemoryInfo memoryInfo = getAvailableMemory(context);
        return memoryInfo.availMem / 1048576;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private static final long getRamTotal(final Context context) {
        ActivityManager.MemoryInfo memoryInfo = getAvailableMemory(context);
        return memoryInfo.totalMem / 1048576;
    }

    // Get a MemoryInfo object for the device's current memory status.
    private static ActivityManager.MemoryInfo getAvailableMemory(final Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    public static final long getMemmorySD() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable = (long)stat.getBlockSize() *(long)stat.getBlockCount();
        long megAvailable = bytesAvailable / 1048576;

        return megAvailable;
    }

    public static final long getDiskInternalTotal(final Context context) {
        StatFs stat = new StatFs(context.getFilesDir().getPath());
        long bytesAvailable = (long)stat.getBlockSize() *(long)stat.getBlockCount();
        long megAvailable = bytesAvailable / 1048576;

        return megAvailable;
    }

    public static final long getDiskInternalFree(final Context context) {
        StatFs stat = new StatFs(context.getFilesDir().getPath());
        long bytesAvailable = (long)stat.getBlockSize() *(long)stat.getAvailableBlocks();
        long megAvailable = bytesAvailable / 1048576;

        return megAvailable;
    }
    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    @TargetApi(Build.VERSION_CODES.M)
    public static final boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isNull(final Object object) {
        return null == object;
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public static boolean isStringEmptyorNull(final String str) {
        return str == null || str.equals("");
    }

    public static String ensureStringNotNull(final String str) {
        return str == null ? "" : str;
    }


    public static void showToast(@NonNull final Context context, @NonNull final String message) {
        if (isMainThread()) {
            Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
        } else if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static boolean gotoWebsite(final Context context, String url) {
        try {
            url = url.trim();
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;

            final Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static final void callShareAction(@NonNull final Activity activity, @NonNull final String appName, @NonNull final String shareMessage, @NonNull final String chooserStr) {
        if (activity == null) {
            return;
        }
        try {
            final Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            activity.startActivity(Intent.createChooser(shareIntent, chooserStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void callShareActionForResult(@NonNull final Activity activity, @NonNull final String appName, @NonNull final String shareMessage, @NonNull final String chooserStr, final int requestCode) {
        if (activity == null) {
            return;
        }
        try {
            final Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//        activity.startActivity(Intent.createChooser(shareIntent, chooserStr));
            activity.startActivityForResult(Intent.createChooser(shareIntent, chooserStr), requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] split(String source, char delimiter) {
        if (source == null) return null;
        source = source.trim();
        if (source.equals("")) return new String[] {""};

        if (source.indexOf(delimiter, 0) < 0) return new String[] {source};

        String tmp;
        Vector<String> v = new Vector<String>();
        int startIndex = 0;
        int idx = source.indexOf(delimiter, startIndex);
        if (idx < 0) {
            return null;
        }
        while (idx >= 0) {
            tmp = source.substring(startIndex, idx).trim();
            v.addElement(tmp);
            startIndex = idx + 1;
            idx = source.indexOf(delimiter, startIndex);
            tmp = null;
        }
        if (v.size() == 0) {
            return null;
        }
        tmp = source.substring(source.lastIndexOf(delimiter) + 1, source.length());
        v.addElement(tmp);
        tmp = null;
        String[] r = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            r[i] = v.elementAt(i).toString();
        }
        return r;
    }

    public static void startInstalledAppDetailsActivity(@NonNull final Context context) {
        if (context == null) {
            return;
        }
        try {
            final Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String stringNoEnter(final String str) {
        return str.replaceAll("[\r\n]", "");
    }

    public static int dpAsPixels(@NonNull final Context context, final int sizeInDp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpAsPixels(sizeInDp, scale);
    }

    public static int dpAsPixels(final float sizeInDp, final float density) {
        int dpAsPixels = (int) (sizeInDp * density + 0.5f);
        return dpAsPixels;
    }

    public static final void openAppPackage(final Activity activity, final String appPackage) {
        final Intent intent = getLaunchIntentForPackage(activity, appPackage);
        if (intent != null) {
            activity.startActivity(intent);
            return;
        }
        gotoPlayStore(activity, appPackage);
    }

    public static final void gotoPlayStore(@NonNull final Activity activity, String appPackage) {
        if (Utils.isStringEmptyorNull(appPackage)) {
            return;
        }

        try {
            final String link = "market://details?id=" + appPackage;
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                    .parse(link)));

        } catch (android.content.ActivityNotFoundException anfe) {
            try {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse("http://play.google.com/store/apps/details?id=" + appPackage)));
            } catch (Exception e) {
            }
        }
    }

    public static final void callSendViaOtherApp(@NonNull final Context context, final String content, final String subject) {
        try {
            final Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, content);
            if (!isStringEmptyorNull(subject)) {
                sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            }
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void callSendMail(@NonNull final Context context,final String to, final String appName, final String subject, final String message) {
        if (context == null) {
            return;
        }
        try {
            final Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{ensureStringNotNull(to)});
            email.putExtra(Intent.EXTRA_SUBJECT, ensureStringNotNull(subject));
            email.putExtra(Intent.EXTRA_TEXT, ensureStringNotNull(message));
            email.setType("message/rfc822");
            context.startActivity(Intent.createChooser(email, "Choose an Email client :"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
    public interface INetworkCheckCallback {
        void onCompleted(boolean available);
    }

}
