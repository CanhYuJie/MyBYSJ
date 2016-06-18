package cn.lyj.mybysj.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Administrator on 2016/4/8.
 */
public class Utils {
    public static String getPackageName(Context context){
        return context.getPackageName();
    }

    public static <T> ArrayList<T> array2List(T[] array) {
        List<T> list = Arrays.asList(array);
        ArrayList newList = new ArrayList(list);
        return newList;
    }

    public static void showToast(Activity activity, String message, int lengthLong) {
        Toast.makeText(activity,message,lengthLong).show();
    }

    /**
     * 像素转dp
     * @param context
     * @param px
     * @return
     */
    public static int px2dp(Context context,int px){
        int density = (int) context.getResources().getDisplayMetrics().density;
        return px/density;
    }

    /**
     * dp转像素
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context,int dp){
        int density = (int) context.getResources().getDisplayMetrics().density;
        return dp*density;
    }


    public static void saveImg2Sd(Activity mActivity,String mAvatarType,String mUserName,Bitmap bitmap){
        File file = FileUtils.getAvatarPath(mActivity,mAvatarType, mUserName + ".jpg");
        if(!file.getParentFile().exists()){
            Toast.makeText(mActivity, "照片保存失败,保存的路径不存在", Toast.LENGTH_LONG).show();
            return ;
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("main", "头像保存失败");
        }
    }


}
