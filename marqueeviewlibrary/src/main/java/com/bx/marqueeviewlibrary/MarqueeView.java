package com.bx.marqueeviewlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.List;

/**
 * Created by smallbai on 2018/4/12.
 * Function:
 */

public class MarqueeView extends ViewFlipper {
    /**
     * 滚动间隔时间
     */
    private int mvInterval;
    /**
     * 动画持续时间
     */
    private int mvAnimDuration;
    /**
     * 左侧文字大小
     */
    private int mvLeftTextSize;
    /**
     * 右侧文字大小
     */
    private int mvRightTextSize;
    /**
     * 左侧文字颜色
     */
    private int mvLeftTextColor;
    /**
     * 右侧文字颜色
     */
    private int mvRightTextColor;

    private Context context;
    /**
     * 当前消息位置
     */
    private int position;
    /**
     * 动画是否开始,防止
     */
    private boolean isAnimStarted;

    public MarqueeView(Context context) {
        this(context,null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MarqueeView);
        mvInterval = typedArray.getInteger(R.styleable.MarqueeView_mvInterval,mvInterval);
        mvAnimDuration = typedArray.getInteger(R.styleable.MarqueeView_mvAnimDuration,mvAnimDuration);
        mvLeftTextSize = (int) typedArray.getDimension(R.styleable.MarqueeView_mvLeftTextSize, mvLeftTextSize);
        mvRightTextSize = (int) typedArray.getDimension(R.styleable.MarqueeView_mvRightTextSize, mvRightTextSize);
        mvLeftTextColor = typedArray.getColor(R.styleable.MarqueeView_mvLeftTextColor, mvLeftTextColor);
        mvRightTextColor = typedArray.getColor(R.styleable.MarqueeView_mvRightTextColor, mvRightTextColor);
        typedArray.recycle();
        setFlipInterval(mvInterval);
    }

    /**
     *
     * @param notices 滚动的消息列表
     */
    public void startMarquee(final List<? extends NoticeBean> notices){
        if (notices!=null&&notices.size()>0){
            //开启滚动,耗时异步处理
            post(new Runnable() {
                @Override
                public void run() {
                    //避免重影
                    removeAllViews();
                    clearAnimation();
                    position = 0;
                    addView(createTextView(position,notices));
                    setInAndOutAnimation();
                    startFlipping();
                    if (getInAnimation()!=null){
                        getInAnimation().setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                if (isAnimStarted){
                                    animation.cancel();
                                }
                                isAnimStarted = true;
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                position = position+2;

                                if (notices.size()%2==0) {
                                    if (position >= notices.size()) {
                                        position = 0;
                                    }
                                }else {
                                    if (position > notices.size()) {
                                        position = 0;
                                    }
                                }


                                View view = createTextView(position,notices);
                                if (view.getParent() == null) {
                                    addView(view);
                                }
                                isAnimStarted = false;
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                    }
                }
            });
        }
    }

    /**
     *
     * @param pos 消息的当前位置
     * @param notices 消息列表
     * @return 创建textview
     */
    private View createTextView(int pos,List<? extends NoticeBean> notices){

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        for (int i = 0; i < 2; i++) {

            //判断是否符合添加要求,有可能只需要添加一个
            if (pos+i==notices.size()) {
                break;
            }

            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView leftTextView = new TextView(context);
            leftTextView.setText(notices.get(pos+i).getLeftString());
            leftTextView.setTextSize(mvLeftTextSize);
            leftTextView.setTextColor(mvLeftTextColor);
            leftTextView.setGravity(Gravity.CENTER_VERTICAL);

            TextView rightTextView = new TextView(context);
            rightTextView.setText(notices.get(pos+i).getRightString());
            rightTextView.setTextSize(mvRightTextSize);
            rightTextView.setTextColor(mvRightTextColor);
            rightTextView.setGravity(Gravity.CENTER_VERTICAL);
            rightTextView.setPadding(10,0,0,0);

            linearLayout.addView(leftTextView);
            linearLayout.addView(rightTextView);
            layout.addView(linearLayout);
        }


        return layout;
    }

    /**
     * 设置进入动画和离开动画
     */
    private void setInAndOutAnimation() {
        Animation inAnim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_bottom_into);
        inAnim.setDuration(mvAnimDuration);
        setInAnimation(inAnim);

        Animation outAnim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_top_out);
        outAnim.setDuration(mvAnimDuration);
        setOutAnimation(outAnim);
    }
}
