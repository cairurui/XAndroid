### 一个简单的演示

```
public class MyTextView extends View {

    Paint mPaint;
    String text;

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setColor(Color.RED);
        mPaint.setTextSize(100);
//        text = "这是文本信息";

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyTextView);
        text = a.getString(R.styleable.MyTextView_text);
        int color = a.getColor(R.styleable.MyTextView_textColor, Color.BLACK);
        mPaint.setColor(color);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Xlog.d("widthSize:" + widthSize + "  widthMode:" + getFriendlyModeName(widthMode)); //widthSize:1080  widthMode:AT_MOST
        Xlog.d("heightSize:" + heightSize + "  heightMode:" + getFriendlyModeName(heightMode));//heightSize:1704  heightMode:AT_MOST
    }

    private String getFriendlyModeName(int mode) {
        switch (mode) {
            case MeasureSpec.EXACTLY:
                return "EXACTLY";
            case MeasureSpec.AT_MOST:
                return "AT_MOST";
            case MeasureSpec.UNSPECIFIED:
                return "UNSPECIFIED";
        }
        return "UNKOWN";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE); // 绘制背景颜色
        /**
         * Draw the text, with origin at (x,y), using the specified paint. The origin is interpreted
         * based on the Align setting in the paint.
         *
         * @param text The text to be drawn
         * @param x The x-coordinate of the origin of the text being drawn
         * @param y The y-coordinate of the baseline of the text being drawn
         * @param paint The paint used for the text (e.g. color, size, style)
         */

        Rect bounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length() - 1, bounds);
        canvas.drawText(text, 0, 0 + bounds.height(), mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("xiaocai", "ACTION_DOWN DOWN DOWN DOWN   ==== ");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("xiaocai", "ACTION_MOVE MOVE MOVE MOVE MOVE MOVE ");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("xiaocai", "ACTION_UP UP UP UP UP UP ");
                break;
        }
        return true; // 需要返回 true 才能接受到后面的事件,要不然只能收到 ACTION_DOWN 的事件
//        return super.onTouchEvent(event);
    }
}

// 在 attrs.xml 文件中定义自定义属性
<declare-styleable name="MyTextView">
    <attr name="text" format="string"/>
    <attr name="textColor" format="color"/>
</declare-styleable>

// 在布局中使用
<com.charry.xandroid.ui.customview.MyTextView
    android:layout_width="wrap_content"
    android:layout_marginTop="20dp"
    app:text="添加自定义属性"
    app:textColor="@android:color/white"
    android:layout_height="wrap_content" />
```


![](img/mytextview.png)
