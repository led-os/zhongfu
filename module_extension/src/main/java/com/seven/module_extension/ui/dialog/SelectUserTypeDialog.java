package com.seven.module_extension.ui.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.seven.lib_common.base.sheet.IBaseSheet;
import com.seven.lib_common.listener.OnClickListener;
import com.seven.lib_common.widget.PickerView;
import com.seven.module_extension.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxxxxxH on 2019/4/24.
 */
public class SelectUserTypeDialog extends IBaseSheet implements PickerView.onSelectListener {

    private TextView cancel;
    private TextView confirm;
    private PickerView pickerView;
    private List<String> userType;
    private Window window;
    private String selectStr = "";

    public SelectUserTypeDialog(Activity activity, int theme, com.seven.lib_common.listener.OnClickListener listener) {
        super(activity, theme, listener);
    }

    @Override
    public int getRootLayoutId() {
        isTouch = true;
        return R.layout.me_dialog_selectusertype;
    }

    @Override
    public void onInitRootView(Bundle savedInstanceState) {
        initView();

        initData();
    }

    @Override
    public void initView() {

        cancel = getView(cancel, R.id.me_choice_cancel);
        confirm = getView(confirm, R.id.me_choice_confirm);
        pickerView = getView(pickerView, R.id.me_select_pv);
        pickerView.setIsLoop(false);
        pickerView.setOnSelectListener(this);
    }

    @Override
    public void initData() {
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        userType = new ArrayList<>();
        userType.add(0, "普通用户");
        userType.add(1, "VIP");
        userType.add(2, "矿主");
        userType.add(3, "场主");
        userType.add(4, "城主");
        pickerView.setData(userType);
        pickerView.setSelected(0);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.me_choice_cancel) {

        }
        if (view.getId() == R.id.me_choice_confirm) {
            listener.onClick(confirm, selectStr);
        }
        dismiss();
    }

    @Override
    public void showDialog(int x, int y) {
        window = getWindow(); //得到对话框
        window.setWindowAnimations(com.seven.lib_common.R.style.dialogWindowAnim); //设置窗口弹出动画
        //window.setBackgroundDrawableResource(R.color.vifrification); //设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        //根据x，y坐标设置窗口需要显示的位置
        wl.x = x; //x小于0左移，大于0右移
        wl.y = y; //y小于0上移，大于0下移
        //wl.alpha = 0.6f; //设置透明度
        wl.gravity = Gravity.BOTTOM; //设置重力
        wl.width = WindowManager.LayoutParams.MATCH_PARENT;
        //wl.height = ScreenUtils.getScreenHeight(activity) / 2;
        window.setAttributes(wl);
        show();
    }

    @Override
    public void onSelect(View v, String text) {
        selectStr = text;
    }
}
