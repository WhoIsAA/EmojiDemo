package com.example.emojidemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.vdurmont.emoji.EmojiParser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconTextView;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;


public class MainActivity extends AppCompatActivity implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {

    @BindView(R.id.tv_main_emoji)
    EmojiconTextView tvEmoji;

    @BindView(R.id.tv_main_unicode)
    TextView tvUnicode;

    @BindView(R.id.tv_main_unicode_dec)
    TextView tvUnicodeDec;

    @BindView(R.id.tv_main_unicode_hex)
    TextView tvUnicodeHex;

    @BindView(R.id.tv_main_aliase)
    TextView tvAliase;

    @BindView(R.id.tv_main_emoji_filter)
    TextView tvEmojiFilter;

    @BindView(R.id.fl_main_bottom_emoji)
    FrameLayout flBottomEmoji;

    @BindView(R.id.et_main_bottom)
    EmojiconEditText etBottom;

    private Context mContext;
    private FragmentManager mFragmentManager;
    private EmojiconsFragment mEmojiconsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        mFragmentManager = getSupportFragmentManager();
        if(mEmojiconsFragment == null) {
            mEmojiconsFragment = EmojiconsFragment.newInstance(false);
        }
    }

    /**
     * 显示表情选择框
     */
    private void showEmojiSelector() {
        flBottomEmoji.setVisibility(View.VISIBLE);
        if(mEmojiconsFragment.isAdded()) {
            mFragmentManager.beginTransaction().show(mEmojiconsFragment).commit();
        } else {
            mFragmentManager.beginTransaction().add(R.id.fl_main_bottom_emoji, mEmojiconsFragment).commit();
        }
    }

    /**
     * 隐藏表情选择框
     */
    private void hideEmojiSelector() {
        flBottomEmoji.setVisibility(View.GONE);
        if(mEmojiconsFragment.isAdded()) {
            mFragmentManager.beginTransaction().hide(mEmojiconsFragment).commit();
        }
    }

    @OnClick(R.id.et_main_bottom)
    public void onEditTextClick(View v) {
        //软键盘弹出时，隐藏表情选择框
        if(KeyBoardUtils.isKeyboardShown(mContext, etBottom)) {
            hideEmojiSelector();
        }
    }

    @OnClick(R.id.iv_main_emoji)
    public void emoji() {
        //显示或隐藏Emoji表情选择框
        KeyBoardUtils.closeKeybord(mContext, etBottom);

        if(!flBottomEmoji.isShown()) {
            showEmojiSelector();
        } else {
            hideEmojiSelector();
        }
    }

    @OnClick(R.id.btn_main_unicode)
    public void unicode() {
        //将Emoji表情转换成各种可见字符串
        KeyBoardUtils.closeKeybord(mContext, etBottom);

        String content = etBottom.getText().toString().trim();
        tvEmoji.setText(content);
        tvUnicode.setText(EmojiParser.parseToUnicode(content));
        tvUnicodeDec.setText(EmojiParser.parseToHtmlDecimal(content));
        tvUnicodeHex.setText(EmojiParser.parseToHtmlHexadecimal(content));
        tvAliase.setText(EmojiParser.parseToAliases(content));
        tvEmojiFilter.setText(EmojiParser.removeAllEmojis(content));
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        //删除EditText中的表情
        EmojiconsFragment.backspace(etBottom);
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        //选中表情，在EditText中显示
        EmojiconsFragment.input(etBottom, emojicon);
    }
}
