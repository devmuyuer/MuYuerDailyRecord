package yao.muyuer.skinlibrary.utils;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import yao.muyuer.skinlibrary.loader.SkinManager;

/**
 * Created by MuYuer
 * Date:2016/7/9
 * Time:13:56
 */
public class SkinResourcesUtils {

    public static int getColor(int resId) {
        return SkinManager.getInstance().getColor(resId);
    }

    public static int getNightColor(String resName) {
        return SkinManager.getInstance().getNightColor(resName);
    }

    public static Drawable getDrawable(int resId) {
        return SkinManager.getInstance().getDrawable(resId);
    }

    public static Drawable getNightDrawable(String resName) {
        return SkinManager.getInstance().getNightDrawable(resName);
    }

    /**
     * get drawable from specific directory
     *
     * @param resId res id
     * @param dir   res directory
     * @return drawable
     */
    public static Drawable getDrawable(int resId, String dir) {
        return SkinManager.getInstance().getDrawable(resId, dir);
    }

    public static ColorStateList getColorStateList(int resId) {
        return SkinManager.getInstance().getColorStateList(resId);
    }

    public static int getColorPrimaryDark() {
        if (!isNightMode()) {
            Resources resources = SkinManager.getInstance().getResources();
            if (resources != null) {
                int identify = resources.getIdentifier(
                        "colorPrimaryDark",
                        "color",
                        SkinManager.getInstance().getCurSkinPackageName());
                if (identify > 0)
                    return resources.getColor(identify);
            }
        } else {
            return SkinManager.getInstance().getNightColor("colorPrimaryDark");
        }
        return -1;
    }

    public static boolean isNightMode() {
        return SkinManager.getInstance().isNightMode();
    }
}
