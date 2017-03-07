package com.example.emojidemo;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by haoshun on 2017/3/7.
 * 软键盘工具类
 */
public class KeyBoardUtils {

    /**
     * 打卡软键盘
     *
     * @param context 上下文
     * @param mEditText 输入框
     */
    public static void openKeybord(Context context, EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param context 上下文
     * @param mEditText 输入框
     */
    public static void closeKeybord(Context context, EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 软键盘是否弹出
     * @param context 上下文
     * @param edittext 输入框
     * @return
     */
    public static boolean isKeyboardShown(Context context, View edittext) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0)) {
            imm.showSoftInput(edittext, 0);
            return true;
        } else {
            return false;
        }
    }
}
