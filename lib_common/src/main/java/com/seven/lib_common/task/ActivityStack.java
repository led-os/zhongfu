package com.seven.lib_common.task;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.transition.ChangeImageTransform;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.util.Iterator;
import java.util.Stack;


/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2017/10/26
 */
public class ActivityStack {

    private static ActivityStack instance = null;
    private static Stack<Activity> activityStack;// task
    private boolean isAnimationEnable = false;
    private int animationIn, animationOut;

    private ActivityStack() {
        activityStack = new Stack<Activity>();
    }

    public static ActivityStack getInstance() {
        if (instance == null) {
            synchronized (ActivityStack.class) {
                if (instance == null) {
                    instance = new ActivityStack();
                }
            }
        }
        return instance;
    }

    public void setAnimationEnable(boolean isEnable) {
        isAnimationEnable = isEnable;
    }

    public void setAnimation(int inAnim, int outAnim) {
        animationIn = inAnim;
        animationOut = outAnim;
    }

    /**
     * @param activity
     */
    public void push(Activity activity) {
        activityStack.push(activity);
    }

    /**
     * @return
     */
    public Activity pop() {
        if (activityStack.isEmpty())
            return null;
        return activityStack.pop();
    }

    /**
     * @return
     */
    public Activity peek() {
        if (activityStack.isEmpty())
            return null;
        return activityStack.peek();
    }

    /**
     * Used for remote login or exit activity
     */
    public void clearActivity() {
        while (!activityStack.isEmpty()) {
            Activity activity = activityStack.pop();
            /*if (activity instanceof LoginActivity) {
            } else {
                activity.finish();
            }*/
        }
    }

    /**
     * @param activity
     */
    public void remove(Activity activity) {
        if (activityStack.size() > 0 && activity == activityStack.peek())
            activityStack.pop();
        else
            activityStack.remove(activity);
    }

    /**
     * Is there a stack
     *
     * @param activity
     * @return
     */
    public boolean contains(Activity activity) {
        return activityStack.contains(activity);
    }

    /**
     * finish all Activity
     */
    public void finishAllActivity() {
        while (!activityStack.isEmpty()) {
            activityStack.pop().finish();
        }
    }

    /**
     * @param context
     */
    public void exitApp(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            //clear notification
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
        }
    }

    /**
     * activity jump with return result
     *
     * @param srcAct       source in activity
     * @param desActCls    target in activity
     * @param isFinishSelf is finish source activity
     * @param bundle       the parameters to pass
     * @param requestCode
     * @param flags        intent flags
     */
    public void startActivityForResult(Activity srcAct,
                                       Class<? extends Activity> desActCls, boolean isFinishSelf, Bundle bundle, int requestCode,
                                       int... flags) {
        if (null == srcAct || null == desActCls) {
            return;
        }
        Intent intent = new Intent(srcAct, desActCls);
        if (null != flags) {
            for (int flag : flags) {
                intent.addFlags(flag);
            }
        }
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        srcAct.startActivityForResult(intent, requestCode);
        if (isFinishSelf) {
            srcAct.finish();
        }
    }

    /**
     * activity jump with return result
     *
     * @param srcActCls    source in activity
     * @param desActCls    target in activity
     * @param isFinishSelf is finish source activity
     * @param bundle       the parameters to pass
     * @param requestCode
     * @param flags        intent flags
     */
    public void startActivityForResult(Class<? extends Activity> srcActCls,
                                       Class<? extends Activity> desActCls, boolean isFinishSelf, Bundle bundle, int requestCode,
                                       int... flags) {
        Activity srcAct = findActivityByClass(srcActCls);
        startActivityForResult(srcAct, desActCls, isFinishSelf, bundle, requestCode, flags);
    }

    /**
     * found activity
     *
     * @param actCls need found of activity
     * @return activity_amount_report
     */
    public Activity findActivityByClass(Class<? extends Activity> actCls) {
        Activity aActivity = null;
        Iterator<Activity> itr = activityStack.iterator();
        while (itr.hasNext()) {
            aActivity = itr.next();
            if (null != aActivity && aActivity.getClass().getName().equals(actCls.getName()) && !aActivity.isFinishing()) {
                break;
            }
            aActivity = null;
        }
        return aActivity;
    }

    /**
     * activity jump with return result
     *
     * @param desActCls    target in activity
     * @param isFinishSelf is finish source activity
     * @param bundle       the parameters to pass
     * @param requestCode
     * @param flags        intent flags
     */
    public void startActivityForResult(Class<? extends Activity> desActCls, boolean isFinishSelf, Bundle bundle, int requestCode,
                                       int... flags) {
        Activity srcAct = peek();
        if (null != srcAct) {
            startActivityForResult(srcAct, desActCls, isFinishSelf, bundle, requestCode, flags);
        }
    }

    /**
     * run a activity,the default initiator is activity stack vertex element
     *
     * @param desActCls      target in activity
     * @param isFinishSrcAct is finish source activity
     * @param flags          intent flags
     */
    public void startActivity(Class<? extends Activity> desActCls,
                              boolean isFinishSrcAct, int... flags) {
        int size = activityStack.size();
        if (1 <= size) {
            Activity srcAct = activityStack.get(size - 1);
            Intent intent = new Intent(srcAct, desActCls);
            if (null != flags) {
                for (int flag : flags) {
                    intent.addFlags(flag);
                }
            }
            srcAct.startActivity(intent);
            if (isAnimationEnable) {
                srcAct.overridePendingTransition(animationIn,
                        animationOut);
            }
            if (isFinishSrcAct) {
                srcAct.finish();
            }
        }

    }

    /**
     * start a activity,the default initiator is activity stack vertex element（protocol）
     *
     * @param desActCls      target in activity
     * @param isFinishSrcAct is finish source activity
     * @param bundle         the parameters to pass
     * @param flags          intent flags
     */
    public void startActivity(Class<? extends Activity> desActCls,
                              boolean isFinishSrcAct, Bundle bundle, int... flags) {
        int size = activityStack.size();
        if (1 <= size) {
            Activity srcAct = activityStack.get(size - 1);
            startActivity(srcAct, desActCls, isFinishSrcAct, bundle, flags);
        }

    }

    public void startActivityTransition(Class<? extends Activity> desActCls, View view, String transition,
                                        boolean isFinishSrcAct, Bundle bundle, int... flags) {
        int size = activityStack.size();
        if (1 <= size) {
            Activity srcAct = activityStack.get(size - 1);
            startActivityTransition(srcAct, desActCls, view, transition, isFinishSrcAct, bundle, flags);
        }
    }

    public void startActivityTransition(Activity srcAct, Class<? extends Activity> desActCls,
                                        View view, String transition, boolean isFinishSelf, Bundle bundle, int... flags) {
        Intent intent = new Intent(srcAct, desActCls);

        if (null != flags) {
            for (int flag : flags) {
                intent.addFlags(flag);
            }
        }
        if (null != bundle) {
            intent.putExtras(bundle);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && view != null) {
            ActivityOptions activityOptions =
                    ActivityOptions.makeSceneTransitionAnimation(srcAct, view, transition);
            srcAct.getWindow().setSharedElementEnterTransition(new ChangeImageTransform(srcAct, null));
            srcAct.startActivity(intent, activityOptions.toBundle());
        } else {
            srcAct.startActivity(intent);
        }

        if (isFinishSelf) {
            srcAct.finish();
        }
    }

    public void startActivityOptions(Class<? extends Activity> desActCls, boolean isFinishSrcAct, Bundle bundle, int... flags) {
        int size = activityStack.size();
        if (1 <= size) {
            Activity srcAct = activityStack.get(size - 1);
            startActivityOptions(srcAct, desActCls, isFinishSrcAct, bundle, flags);
        }
    }

    public void startActivityOptions(final Activity srcAct, Class<? extends Activity> desActCls, boolean isFinishSelf, Bundle bundle, int... flags) {
        Intent intent = new Intent(srcAct, desActCls);

        if (null != flags) {
            for (int flag : flags) {
                intent.addFlags(flag);
            }
        }
        if (null != bundle) {
            intent.putExtras(bundle);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(srcAct, false);
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(srcAct, pairs);
            srcAct.startActivity(intent, transitionActivityOptions.toBundle());
        } else {
            srcAct.startActivity(intent);
        }

        if (isFinishSelf) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    srcAct.finish();
                }
            }, 1000);
        }
    }

    /**
     * start a activity,manually specify the initiator instance
     *
     * @param srcAct       source in activity
     * @param desActCls    target in activity
     * @param isFinishSelf is finish source activity
     * @param bundle       the parameters to pass
     * @param flags        intent flags
     */
    public void startActivity(Activity srcAct,
                              Class<? extends Activity> desActCls, boolean isFinishSelf, Bundle bundle,
                              int... flags) {
        Intent intent = new Intent(srcAct, desActCls);
        if (null != flags) {
            for (int flag : flags) {
                intent.addFlags(flag);
            }
        }
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        srcAct.startActivity(intent);
        if (isFinishSelf) {
            srcAct.finish();
        }
    }

    /**
     * start a activity through the package name,
     * the default initiator is activity stack vertex element（protocol）
     *
     * @param packageName    target in activity package name
     * @param isFinishSrcAct is finish source activity
     * @param bundle         the parameters to pass
     * @param flags          intent flags
     */
    public void startPackageActivity(String packageName, boolean isFinishSrcAct, Bundle bundle, int... flags) {

        int size = activityStack.size();
        if (1 <= size) {
            Activity srcAct = activityStack.get(size - 1);
            startPackageActivity(srcAct, packageName, isFinishSrcAct, bundle, flags);
        }
    }

    /**
     * start a activity,manually specify the initiator instance
     *
     * @param srcAct       source in activity
     * @param desActCls    target in activity
     * @param isFinishSelf is finish source activity
     * @param flags        intent flags
     */
    public void startActivity(Activity srcAct,
                              Class<? extends Activity> desActCls, boolean isFinishSelf,
                              int... flags) {
        Intent intent = new Intent(srcAct, desActCls);
        if (null != flags) {
            for (int flag : flags) {
                intent.addFlags(flag);
            }
        }
        srcAct.startActivity(intent);

        if (isFinishSelf) {
            srcAct.finish();
        }
    }

    /**
     * start a activity,manually specify the initiator instance
     *
     * @param srcActCls      source in activity
     * @param desActCls      target in activity
     * @param isFinishSrcAct is finish source activity
     * @param flags          intent flags
     */
    public void startActivity(Class<? extends Activity> srcActCls,
                              Class<? extends Activity> desActCls, boolean isFinishSrcAct,
                              int... flags) {
        Activity srcAct = findActivityByClass(srcActCls);
        if (null != srcAct) {
            Intent intent = new Intent(srcAct, desActCls);
            if (null != flags) {
                for (int flag : flags) {
                    intent.addFlags(flag);
                }
            }
            srcAct.startActivity(intent);

            if (isFinishSrcAct) {
                srcAct.finish();
            }
        }

    }

    /**
     * start a activity through the package name,manually specify the initiator instance
     *
     * @param srcAct       source in activity
     * @param packageName  target in activity package name
     * @param isFinishSelf is finish source activity
     * @param bundle       the parameters to pass
     * @param flags        intent flags
     */
    public void startPackageActivity(Activity srcAct, String packageName, boolean isFinishSelf, Bundle bundle, int... flags) {

        if (TextUtils.isEmpty(packageName))
            return;

        Intent intent = new Intent();
        if (null != flags) {
            for (int flag : flags)
                intent.addFlags(flag);
        }

        if (null != bundle) {
            intent.putExtras(bundle);
        }
        intent.setClassName(srcAct, packageName);

        try {
            srcAct.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Logger.e(e, "");
        }

        if (isFinishSelf) {
            srcAct.finish();
        }
    }


    public boolean finishActivity(Class<? extends Activity> actCls) {
        Activity act = findActivityByClass(actCls);
        if (null != act && !act.isFinishing()) {
            act.finish();
            return true;
        }
        return false;
    }
}