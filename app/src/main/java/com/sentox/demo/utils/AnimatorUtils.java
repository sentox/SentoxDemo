package com.sentox.demo.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：ValueAnimator动画工具类
 * 说明：
 * Created by Sentox
 * Created on 2016/12/26
 */
public class AnimatorUtils {

    /**
     * 取消动画
     */
    public static void cancelAnimator(Animator animator) {
        if ((animator != null) && (animator.isStarted() || animator.isRunning())) {
            animator.cancel();
        }
    }

    /**
     * X轴平移动画
     **/
    public static ObjectAnimator translationX(View view, int startX, int endX, long duration, long delayTime, Animator
            .AnimatorListener animatorListener) {
        ObjectAnimator anim = null;
        if (view != null) {
            anim = ObjectAnimator.ofFloat(view, "translationX", startX, endX);
            anim.setDuration(duration);
            anim.setStartDelay(delayTime);
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }
            anim.start();
        }
        return anim;
    }

    /**
     * Y轴平移动画
     **/
    public static ObjectAnimator translationY(View view, int startY, int endY, long duration, long delayTime, Animator
            .AnimatorListener animatorListener) {
        ObjectAnimator anim = null;
        if (view != null) {
            anim = ObjectAnimator.ofFloat(view, "translationY", startY, endY);
            anim.setDuration(duration);
            anim.setStartDelay(delayTime);
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }
            anim.start();
        }
        return anim;
    }

    /**
     * 平移到坐标点y
     **/
    public static ObjectAnimator translationToY(View view, long duration, long delayTime, Animator.AnimatorListener
            animatorListener, float... values) {
        ObjectAnimator anim = null;
        if (view != null) {
            anim = ObjectAnimator.ofFloat(view, View.Y, values);
            anim.setDuration(duration);
            anim.setStartDelay(delayTime);
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }
            anim.start();
        }
        return anim;
    }

    /**
     * 竖直平移淡出动画
     *
     * @param height:竖直平移高度
     **/
    public static List<ObjectAnimator> fadeMoveOut(View view, int height, long duration, long delayTime, Animator
            .AnimatorListener animatorListener) {
        List<ObjectAnimator> list = new ArrayList<ObjectAnimator>();
        ObjectAnimator anim = null;
        if (view != null) {
            anim = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
            anim.setDuration(duration);
            anim.setStartDelay(delayTime);
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }
            ObjectAnimator anim1 = translationY(view, 0, height, duration, delayTime, null);
            anim.start();
            list.add(anim);
            list.add(anim1);
        }
        return list;
    }

    /**
     * 竖直平移淡入动画
     *
     * @param height:竖直平移高度
     **/
    public static List<ObjectAnimator> fadeMoveIn(View view, int height, long duration, long delayTime, Animator
            .AnimatorListener animatorListener) {
        List<ObjectAnimator> list = new ArrayList<ObjectAnimator>();
        if (view != null) {
            ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
            anim.setDuration(duration);
            anim.setStartDelay(delayTime);
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }
            ObjectAnimator anim2 = translationY(view, height, 0, duration, delayTime, null);
            anim.start();
            list.add(anim);
            list.add(anim2);
        }
        return list;
    }

    /**
     * 淡入动画
     **/
    public static ObjectAnimator fadeIn(View view, long duration, long delayTime, Animator.AnimatorListener animatorListener) {
        ObjectAnimator anim = null;
        if (view != null) {
            anim = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
            anim.setDuration(duration);
            anim.setStartDelay(delayTime);
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }
            anim.start();
        }
        return anim;
    }

    /**
     * Y轴旋转动画
     **/
    public static ObjectAnimator rotateY(View view, long duration, long delayTime, int repeat, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator anim = null;
        if (view != null) {
            anim = ObjectAnimator.ofFloat(view, "rotationY", 0f, 360f);
            anim.setDuration(duration);
            anim.setStartDelay(delayTime);
            anim.setRepeatCount(repeat);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }
            anim.start();
        }
        return anim;
    }

    /**
     * 淡出动画
     **/
    public static ObjectAnimator fadeOut(View view, long duration, long delayTime, int repeat, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator anim = null;
        if (view != null) {
            anim = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
            anim.setDuration(duration);
            anim.setStartDelay(delayTime);
            anim.setRepeatCount(repeat);
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }
            anim.start();
        }
        return anim;
    }


    /**
     * 设置透明度动画
     **/
    public static ObjectAnimator fadeView(final View view, long duration, long delayTime, int repeat, Animator.AnimatorListener
            animatorListener, float... values) {
        ObjectAnimator anim = null;
        if (view != null) {
            anim = ObjectAnimator.ofFloat(view, "alpha", values);
            anim.setDuration(duration);
            anim.setRepeatCount(repeat);
            anim.setStartDelay(delayTime);
            anim.setInterpolator(new LinearInterpolator());
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }
            anim.start();
        }
        return anim;
    }

    /**
     * loading旋转
     **/
    public static ObjectAnimator rotateView(final View view, long duration, long delayTime, int repeat, Animator
            .AnimatorListener animatorListener, float... values) {
        ObjectAnimator anim = null;
        if (view != null) {
            anim = ObjectAnimator.ofFloat(view, "rotation", values);
            anim.setDuration(duration);
            anim.setRepeatCount(repeat);
            anim.setStartDelay(delayTime);
            anim.setInterpolator(new LinearInterpolator());
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }
            anim.start();
        }
        return anim;
    }

    /**
     * 缩放动画
     **/
    public static ValueAnimator zoomView(final View view, long duration, long delayTime, int repeat, Animator.AnimatorListener
            animatorListener, float... values) {
        ValueAnimator anim = null;
        if (view != null) {
            anim = ValueAnimator.ofFloat(values);
            anim.setDuration(duration);
            anim.setStartDelay(delayTime);
            anim.setRepeatCount(repeat);
            anim.setInterpolator(new LinearInterpolator());
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float scale = (float) animation.getAnimatedValue();
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                }
            });
            anim.start();
        }
        return anim;
    }

    /**
     * 垂直展开
     **/
    public static ValueAnimator expendHeight(final View view, long duration, long delayTime, Animator.AnimatorListener
            animatorListener, int... values) {
        ValueAnimator anim = null;
        if (view != null) {
            anim = ValueAnimator.ofInt(values);
            anim.setDuration(duration);
            anim.setStartDelay(delayTime);
            anim.setInterpolator(new LinearInterpolator());
            final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int h = (int) animation.getAnimatedValue();
                    layoutParams.height = h;
                    view.setLayoutParams(layoutParams);
                    view.requestLayout();
                }
            });
            anim.start();
        }
        return anim;
    }

    /**
     * 按钮动画：抖动
     **/
    public static ValueAnimator addBtnAnimShake(final View view, int delay, Animator.AnimatorListener animatorListener) {
        return AnimatorUtils.zoomView(view, 1600, delay, ValueAnimator.INFINITE, animatorListener, 1, 1, 1, 1, 1, 1, 1.05f,
                0.98f, 1.02f, 1);
    }

    /**
     * 按钮动画：晃动
     **/
    public static ValueAnimator addBtnAnimRock(final View view, int delay, Animator.AnimatorListener animatorListener) {
        return AnimatorUtils.rotateView(view, 1600, delay, ValueAnimator.INFINITE, animatorListener, 0, 0, 0, 0, 0, 0, -2, 1,
                -0.5f, 0);
    }

    /**
     * 按钮动画：呼吸
     **/
    public static ValueAnimator addBtnAnimBreathe(final View view, int delay, Animator.AnimatorListener animatorListener) {
        ValueAnimator anim = null;
        if (view != null) {
            anim = ValueAnimator.ofFloat(1, 1.05f, 1, 1, 1);
            anim.setDuration(2500);
            anim.setStartDelay(delay);
            anim.setRepeatCount(ValueAnimator.INFINITE);
            anim.setInterpolator(new LinearInterpolator());
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float scale = (float) animation.getAnimatedValue();
                    view.setScaleX(scale);
                    view.setScaleY(scale);
//                    view.setAlpha(scale - 0.2f);
                }
            });
            anim.start();
        }
        return anim;
    }


    /**
     * 按钮动画：横向
     **/
    public static ValueAnimator addBtnAnimCrosswiseShake(final View view, int delay, Animator.AnimatorListener animatorListener) {
        ValueAnimator anim = null;
        if (view != null) {
            final float oldX = view.getX();
            anim = ValueAnimator.ofFloat(0, -oldX / 5, oldX * 1 / 5, -oldX / 10, oldX / 5, oldX / 10, 0, 0, 0);
            anim.setDuration(1500);
            anim.setStartDelay(delay);
            anim.setRepeatCount(ValueAnimator.INFINITE);
            anim.setInterpolator(new LinearInterpolator());
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }

            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float trans = (float) animation.getAnimatedValue();
                    view.setX(oldX + trans);
                }
            });
            anim.start();
        }
        return anim;
    }

    /**
     * 单次缩放动画
     **/
    public static ValueAnimator addSingleBtnAnimBreathe(final View view, int durTime, int delay, int repeat, Animator
            .AnimatorListener animatorListener) {
        ValueAnimator anim = null;
        if (view != null) {
            anim = ValueAnimator.ofFloat(1, 0.9f, 1, 1, 1);
            anim.setDuration(durTime);
            anim.setStartDelay(delay);
            anim.setRepeatCount(repeat);
            anim.setInterpolator(new LinearInterpolator());
            if (animatorListener != null) {
                anim.addListener(animatorListener);
            }
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float scale = (float) animation.getAnimatedValue();
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                    view.setAlpha(scale * 4.6f - 3.6f);
                }
            });
            anim.start();
        }
        return anim;
    }

    /**
     * 呼吸动画
     *
     * @param view        目标对象
     * @param duration    动画时长
     * @param delay       延时
     * @param repeatCount 重播次数
     * @param listener    监听器
     * @return ValueAnimator
     */
    public static ValueAnimator applyBreathAnim(final View view, int duration, int delay, int repeatCount, Animator
            .AnimatorListener listener) {
        ValueAnimator anim = null;
        if (view != null) {
            anim = ValueAnimator.ofFloat(1, 1.05f, 1, 1, 1);
            anim.setDuration(duration);
            anim.setStartDelay(delay);
            anim.setRepeatCount(repeatCount);
            anim.setInterpolator(new LinearInterpolator());
            if (listener != null) {
                anim.addListener(listener);
            }
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float scale = (float) animation.getAnimatedValue();
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                }
            });
            anim.start();
        }
        return anim;
    }

    /**
     * 扩散小时动画
     *
     * @param view        目标对象
     * @param duration    动画时长
     * @param delay       延时
     * @param repeatCount 重播次数
     * @param listener    监听器
     * @return ValueAnimator
     */
    public static List<ValueAnimator> scaleDisappearAnim(final View view, int duration, int delay, int repeatCount, Animator
            .AnimatorListener listener) {
        List<ValueAnimator> list = new ArrayList<ValueAnimator>();
        if (view != null) {
            ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
            anim.setDuration(duration);
            anim.setRepeatCount(repeatCount);
            anim.setStartDelay(delay);
            if (listener != null) {
                anim.addListener(listener);
            }

            ValueAnimator anim2 = ValueAnimator.ofFloat(1, 1.4f);
            anim2.setDuration(duration);
            anim2.setStartDelay(delay);
            anim2.setRepeatCount(repeatCount);
            anim2.setInterpolator(new LinearInterpolator());
            anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float scale = (float) valueAnimator.getAnimatedValue();
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                }
            });
            anim.start();
            anim2.start();
            list.add(anim);
            list.add(anim2);
        }
        return list;
    }

    /**
     * 横向抖动动画
     *
     * @param view        目标对象
     * @param duration    动画时长
     * @param delay       延时
     * @param repeatCount 重播次数
     * @param listener    监听器
     * @return ValueAnimator
     */
    public static ValueAnimator applyHorizontalShakeAnim(final View view, int duration, int delay, int repeatCount, Animator
            .AnimatorListener listener) {
        ValueAnimator anim = null;
        if (view != null) {
            final float oldX = view.getX();
            anim = ValueAnimator.ofFloat(0, -oldX / 5, oldX * 1 / 5, -oldX / 10, oldX / 5, oldX / 10, 0, 0, 0);
            anim.setDuration(duration);
            anim.setStartDelay(delay);
            anim.setRepeatCount(repeatCount);
            anim.setInterpolator(new LinearInterpolator());
            if (listener != null) {
                anim.addListener(listener);
            }

            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float trans = (float) animation.getAnimatedValue();
                    view.setX(oldX + trans);
                }
            });
            anim.start();
        }
        return anim;
    }

}
