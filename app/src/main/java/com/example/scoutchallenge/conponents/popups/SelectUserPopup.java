package com.example.scoutchallenge.conponents.popups;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.scoutchallenge.R;
import com.example.scoutchallenge.backend.BackendProxy;
import com.example.scoutchallenge.conponents.CircularImageView;
import com.example.scoutchallenge.conponents.HeadComponents;
import com.example.scoutchallenge.conponents.MDrawableEditText;
import com.example.scoutchallenge.conponents.MImageComponent;
import com.example.scoutchallenge.conponents.MTextView;
import com.example.scoutchallenge.helpers.JsonHelper;
import com.example.scoutchallenge.helpers.RecyclerItemClickListener;
import com.example.scoutchallenge.helpers.StringHelper;
import com.example.scoutchallenge.helpers.Tools;
import com.example.scoutchallenge.interfaces.DidOnFinishWork;
import com.example.scoutchallenge.interfaces.DidOnTap;
import com.example.scoutchallenge.models.MemberModule;
import com.example.scoutchallenge.network.MImageLoader;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONObject;

public class SelectUserPopup extends HeadComponents {
    protected LinearLayout mContainer;
    protected LinearLayout mHeadContainer;

    protected MImageComponent mAddBtn;
    protected MImageComponent mScanBtn;
    public MDrawableEditText mSearchEditText;

    protected MImageComponent mExitIcon;
    protected CodeScannerView mScannerView;


    protected RecyclerView mUserList;
    protected ListAdapter mAdapter;
    protected LinearLayoutManager mMyManager;

    protected JSONArray mUserArray;
    CodeScanner mCodeScanner;
    public DidOnFinishWork mDelegate;


    public SelectUserPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    public void init(Context ctx, AttributeSet attrs) {
        super.init(ctx, attrs);

        mContainer = new LinearLayout(ctx);
        mContainer.setOrientation(LinearLayout.VERTICAL);
        addView(mContainer);

        mHeadContainer = new LinearLayout(ctx);
        mHeadContainer.setOrientation(LinearLayout.HORIZONTAL);
        mContainer.addView(mHeadContainer);

        mMyManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mAdapter = new ListAdapter();


        mAddBtn = new MImageComponent(ctx);
        mAddBtn.setImageResource(getImage("plus_icon"));
        mHeadContainer.addView(mAddBtn);
        mAddBtn.setOnTapListener(new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                if (mDelegate != null) {
                    mDelegate.onFinishWork(getSelectedUsers());
                }
            }
        });

        mScanBtn = new MImageComponent(ctx);
        mScanBtn.setImageResource(getImage("qr_code"));
        mScanBtn.getImage().setColorFilter(getColor(R.color.hint));
        mHeadContainer.addView(mScanBtn);
        mScanBtn.setOnTapListener(new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                if (mCodeScanner != null) {
                    toggleTypeSelection(true);
                    mCodeScanner.startPreview();
                }
            }
        });

        mSearchEditText = new MDrawableEditText(ctx);
        mHeadContainer.addView(mSearchEditText);
        mSearchEditText.setPlaceHolder(getString(R.string.search));
        fixHintGarvity();
        mSearchEditText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString();
                if (StringHelper.isNullOrEmpty(text)) {
                    setDataArray(mUserArray);
                } else {
                    setDataArray(getSpesificUser(text));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mExitIcon = new MImageComponent(ctx);
        mContainer.addView(mExitIcon);
        mExitIcon.setImageResource(getImage("false_icon"));
        mExitIcon.setBackground(getDrawable(GradientDrawable.OVAL, R.color.white, dpToPx(250), -1, -1));
        mExitIcon.setOnTapListener(new DidOnTap() {
            @Override
            public void onTap(HeadComponents view) {
                toggleTypeSelection(false);
                if (mCodeScanner != null) {
                    mCodeScanner.releaseResources();
                }
            }
        });

        mScannerView = new CodeScannerView(ctx);
        mContainer.addView(mScannerView);
        mCodeScanner = new CodeScanner(getContext(), mScannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(() -> {
                    if (mCodeScanner != null) {
                        toggleTypeSelection(false);
                        mCodeScanner.releaseResources();
                    }
                    Toast.makeText(getContext(), result.getText(), Toast.LENGTH_SHORT).show();
                    modifyUsercheckUing(result.getText());
                });
            }
        });

        mScannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });


        mUserList = new RecyclerView(ctx);
        mUserList.setAdapter(mAdapter);
        mUserList.setLayoutManager(mMyManager);
        mUserList.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), mUserList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        mContainer.addView(mUserList);

        toggleTypeSelection(false);
        layoutViews();
    }


    private void layoutViews() {
        int margin = dpToPx(16);
        int editTextHeight = dpToPx(50);
        int hintHeight = dpToPx(25);
        int iconSize = dpToPx(50);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = margin;
        params.bottomMargin = margin;
        mContainer.setLayoutParams(params);

        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.setMargins(margin, margin / 2, margin, 0);
        mHeadContainer.setLayoutParams(linearParams);

        linearParams = new LinearLayout.LayoutParams(iconSize, iconSize);
        linearParams.setMargins(margin, margin / 2, margin, margin);
        linearParams.gravity = Gravity.END;
        mExitIcon.setLayoutParams(linearParams);

        linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.gravity = Gravity.CENTER;
        linearParams.setMargins(margin, 0, margin, margin);
        mScannerView.setLayoutParams(linearParams);

        linearParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.setMargins(margin, 0, margin, margin);
        linearParams.weight = 4;
        mSearchEditText.setLayoutParams(linearParams);


        linearParams = new LinearLayout.LayoutParams(0, editTextHeight);
        linearParams.weight = 1;
        linearParams.topMargin = hintHeight + margin / 2;
        linearParams.setMarginStart(margin);
        mAddBtn.setLayoutParams(linearParams);


        linearParams = new LinearLayout.LayoutParams(0, editTextHeight);
        linearParams.weight = 1;
        linearParams.topMargin = hintHeight + margin / 2;
        linearParams.setMarginStart(margin);
        mScanBtn.setLayoutParams(linearParams);


        linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams.gravity = Gravity.CENTER;
        linearParams.setMargins(margin, 0, margin, margin);
        mUserList.setLayoutParams(linearParams);


    }

    public void setUserArray(JSONArray array) {
        if (array == null) {
            return;
        }
        mUserArray = JsonHelper.cloneArray(array);
        setDataArray(mUserArray);
    }

    public void setDataArray(JSONArray array) {
        if (array == null) {
            return;
        }


        mAdapter.mDataSource = array;
        runOnUiThread(() -> {
            mAdapter.notifyDataSetChanged();
        });
    }

    private void modifyUsercheckUing(String text) {
        if (mUserArray != null) {
            for (int i = 0; i < mUserArray.length(); i++) {
                JSONObject user = mUserArray.optJSONObject(i);
                if (user != null) {
                    MemberModule model = new MemberModule();
                    model.setData(user);
                    if (model.getmSerialNumber().equalsIgnoreCase(text)) {
                        if (user.optBoolean("isChecked")) {
                            Tools.showSimplePopup("هذا العنصر مضاف مسبقا");
                        } else {
                            JsonHelper.put(user, "isChecked", true);
                            mAdapter.notifyDataSetChanged();
                            return;
                        }
                    }
                }
            }
            Tools.showSimplePopup("هذا العنصر مضاف مسبقا او لا تشمله صلاحياتك");

        }
    }

    public void toggleTypeSelection(boolean makeScan) {
        if (makeScan) {
            mUserList.setVisibility(GONE);
            mScannerView.setVisibility(VISIBLE);
            mHeadContainer.setVisibility(GONE);
            mExitIcon.setVisibility(VISIBLE);
        } else {
            mUserList.setVisibility(VISIBLE);
            mScannerView.setVisibility(GONE);
            mHeadContainer.setVisibility(VISIBLE);
            mExitIcon.setVisibility(GONE);

        }
    }

    private JSONArray getSpesificUser(String text) {
        return BackendProxy.getInstance().mUserManager.getRelatedNameUser(text, mUserArray);
    }


    private void fixHintGarvity() {
        MTextView hint = mSearchEditText.getHintTextView();
        if (hint != null) {
            LayoutParams params = (LayoutParams) hint.getLayoutParams();
            params.gravity = Gravity.END;
            hint.setLayoutParams(params);
        }
    }

    private JSONArray getSelectedUsers() {
        JSONArray selectedUser = new JSONArray();
        for (int i = 0; i < mAdapter.mDataSource.length(); i++) {
            JSONObject user = mAdapter.mDataSource.optJSONObject(i);
            if (user.optBoolean("isChecked")) {
                selectedUser.put(user.optString("_id"));
            }
        }

        return selectedUser;
    }


    public class ListAdapter extends RecyclerView.Adapter<SelectUserPopup.ListAdapter.CellViewHolder> {
//
//        protected static final int HORIZONTAL_CELL = 0;
//        protected static final int VERTICAL_CELL = 1;


        public JSONArray mDataSource;

        @Override
        public int getItemViewType(int position) {
//            JSONObject mData = mDataSource.optJSONObject(position);
//            String sectionId = mData.optString("id");
//            switch (sectionId) {
//                case NEWS:
//                    return VERTICAL_CELL;
//                case TRENDING_STICKERS:
//
//                default:
//                    break;
//            }

            //just for avoid error , getItemViewType not used for now
            return 1;
        }

        @NonNull
        @Override
        public SelectUserPopup.ListAdapter.CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            SelectUserPopup.ListAdapter.CellViewHolder viewHolder = null;
            switch (viewType) {
//                case VERTICAL_CELL:
//                    VerticalSectionViewCell viewCell = new VerticalSectionViewCell(parent.getContext());
//                    viewHolder = new CellViewHolder(viewCell);
////                    viewHolder.mCell.setBackgroundColor(Color.RED);
//                    break;
//                case HORIZONTAL_CELL:
//                    SectionViewCell horizontalCell = new SectionViewCell(parent.getContext());
//                    viewHolder = new CellViewHolder(horizontalCell);
//                    break;

            }
            UserSelectionCell challengeCell = new UserSelectionCell(getContext());
            viewHolder = new SelectUserPopup.ListAdapter.CellViewHolder(challengeCell);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SelectUserPopup.ListAdapter.CellViewHolder holder, int position) {
            if (mDataSource != null) {
                JSONObject object = mDataSource.optJSONObject(position);
                holder.mCell.setData(object);
            }
        }

        @Override
        public int getItemCount() {
            if (mDataSource == null) {
                return 0;
            }
            return mDataSource.length();
        }


        public class CellViewHolder extends RecyclerView.ViewHolder {
            protected UserSelectionCell mCell;

            public CellViewHolder(View itemView) {
                super(itemView);
                mCell = (UserSelectionCell) itemView;
            }
        }
    }


    public class UserSelectionCell extends HeadComponents {

        protected CircularImageView mImage;
        protected MTextView mNameLabel;
        protected MImageComponent mCheckIcon;
        protected boolean isChecked = false;

        protected MemberModule userModule = new MemberModule();

        public UserSelectionCell(@NonNull Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public UserSelectionCell(@NonNull Context context) {
            super(context);
        }

        @Override
        public void init(Context ctx, AttributeSet attrs) {
            super.init(ctx, attrs);

            mImage = new CircularImageView(ctx);
            mImage.setImageResource(getImage("onsor"));
            addView(mImage);

            mNameLabel = new MTextView(ctx);
            mNameLabel.setText("ديب شموط");
            mNameLabel.setTextSize(18);
            mNameLabel.getLabel().setGravity(Gravity.CENTER);
            mNameLabel.setTextColor(getColor(R.color.hint));
            addView(mNameLabel);

            mCheckIcon = new MImageComponent(ctx);
            mCheckIcon.getImage().setColorFilter(getColor(R.color.headColor));
            mCheckIcon.setOnTapListener(new DidOnTap() {
                @Override
                public void onTap(HeadComponents view) {
                    handleChecking(true);
                }
            });
            addView(mCheckIcon);

            GradientDrawable gradientDrawable = getDrawable(GradientDrawable.RECTANGLE, R.color.white, dpToPx(10), -1, -1);
            gradientDrawable.setStroke(3, getColor(R.color.secondColor));
            setBackground(gradientDrawable);
            setElevation(2);

            layoutViews();
        }

        public void layoutViews() {
            int cellSize = dpToPx(150);
            int iconSize = dpToPx(80);
            int margin = dpToPx(16);
            int twoDp = dpToPx(2);

            LayoutParams params = new LayoutParams(iconSize, iconSize);
            params.topMargin = margin / 2;
            params.setMarginEnd(margin);
            params.gravity = Gravity.END;
            params.bottomMargin = margin;
            mImage.setLayoutParams(params);


            params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_VERTICAL | Gravity.END;
            params.setMarginEnd(iconSize + margin * 2);
            params.setMarginStart(iconSize / 2 + twoDp);
            mNameLabel.setPadding(twoDp, twoDp, twoDp, twoDp);
            mNameLabel.setLayoutParams(params);


            params = new LayoutParams(iconSize / 2, iconSize / 2);
            params.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
            params.setMarginStart(margin / 2);
            mCheckIcon.setLayoutParams(params);


            params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = margin;
            params.setMarginStart(margin);
            params.setMarginEnd(margin);
            setLayoutParams(params);
        }

        @Override
        public void setData(JSONObject data) {
            super.setData(data);
            if (data != null) {
                userModule.setData(data);
                mNameLabel.setText(data.optString("Name"));
//            String avat = data.optString("file").split("\\\\")[1];
//            MImageLoader.loadWithGlide(D.ASSET_URL + "/" + avat, 0, mImage.getImage());

//            MImageLoader.loadWithGlide(avat, 0, mImage.getImage());
                mNameLabel.setText(data.optString("Name"));
                MImageLoader.loadWithGlide(userModule.getImageUrl(), 0, mImage.getImage());
                handleChecking(false);
            }
        }

        public void handleChecking(boolean isToToggle) {
            isChecked = mData.optBoolean("isChecked");
            String rcs = "";
            if (isChecked && isToToggle) {
                rcs = "unchecked_icon";
            } else {
                if (isToToggle) {
                    rcs = "checked_icon";
                } else {
                    if (isChecked) {
                        rcs = "checked_icon";
                    } else {
                        rcs = "unchecked_icon";
                    }

                }

            }
            mCheckIcon.setImageResource(getImage(rcs));

            if (isToToggle) {
                isChecked = !isChecked;
                JsonHelper.put(mData, "isChecked", isChecked);
            }
        }

    }


}
