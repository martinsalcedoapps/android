package common_tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Objects;

import py.martinsalcedo.mypersonalinformation.Info_1_PersonalActivity;
import py.martinsalcedo.mypersonalinformation.Info_2_FamilyActivity;
import py.martinsalcedo.mypersonalinformation.Info_3_BusinessActivity;
import py.martinsalcedo.mypersonalinformation.Info_4_StudentActivity;
import py.martinsalcedo.mypersonalinformation.Info_5_SchoolActivity;
import py.martinsalcedo.mypersonalinformation.Info_6_BankActivity;
import py.martinsalcedo.mypersonalinformation.R;

public class PackageTools {
    // --Commented out by Inspection (03/11/18 08:06 AM):private AdView mAdView;
    private FirebaseAnalytics mFirebaseAnalytics;

    public void setObjectValues(Activity curActivity,
                                ArrayList<Integer> ridValueList,
                                ArrayList<String> sValueList,
                                ArrayList<Integer> ridCheckedList,
                                ArrayList<String> sCheckedList) {

        SharedPreferences sharedPref = curActivity.getSharedPreferences("MyPersonalInformation", Context.MODE_PRIVATE);

        for (int idx = 0; idx < ridValueList.size(); idx++) {
            EditText pEditText = curActivity.findViewById(ridValueList.get(idx));
            String sharedValue = sharedPref.getString(sValueList.get(idx), "");
            pEditText.setText(sharedValue);
            CheckBox pCheckBox = curActivity.findViewById(ridCheckedList.get(idx));
            pCheckBox.setChecked(sharedPref.getBoolean(sCheckedList.get(idx), true));
        }
    }

    public void saveObjectValues(Activity curActivity,
                                 ArrayList<Integer> ridValueList,
                                 ArrayList<String> sValueList,
                                 ArrayList<Integer> ridCheckedList,
                                 ArrayList<String> sCheckedList
    ) {
        SharedPreferences sharedPref = curActivity.getSharedPreferences("MyPersonalInformation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        for (int idx = 0; idx < ridValueList.size(); idx++) {
            EditText pEditText = curActivity.findViewById(ridValueList.get(idx));
            editor.putString(sValueList.get(idx), pEditText.getText().toString());
            CheckBox pCheckBox = curActivity.findViewById(ridCheckedList.get(idx));
            editor.putBoolean(sCheckedList.get(idx), pCheckBox.isChecked());
            Log.v("save key", sValueList.get(idx));
            Log.v("save value", pEditText.getText().toString());
            Log.v("save checked", pCheckBox.getText().toString());

        }
        editor.apply();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(curActivity);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Save");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Save");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "ButtonAction");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void shareString(Activity curActivity, String sectionName) {
        ArrayList<String> mFieldList;
        ArrayList<String> cFieldList;
        StringBuilder stringShare = new StringBuilder();
        //SharedPreferences sharedPreferences = curActivity.getSharedPreferences("MyPersonalInformation", Context.MODE_PRIVATE);

        mFieldList = getMandatoryDataList(curActivity, sectionName);
        cFieldList = getCompletedDataList(curActivity, sectionName);

        for (int idx = 0; idx < mFieldList.size(); idx++) {
            Log.v("Mandatory field ", mFieldList.get(idx));
            stringShare.append(mFieldList.get(idx));
            stringShare.append("\n");
        }
        for (int idx = 0; idx < cFieldList.size(); idx++) {
            Log.v("Completed field ", cFieldList.get(idx));
            stringShare.append(cFieldList.get(idx));
            stringShare.append("\n");
        }

        if (stringShare.length() > 0) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, stringShare.toString());
            sendIntent.setType("text/plain");
            curActivity.startActivity(sendIntent);
        } else {
            Toast.makeText(curActivity, R.string.tNothingToShare, Toast.LENGTH_LONG).show();

        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(curActivity);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Share");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Share");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "ButtonAction");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

    }

    public ArrayList<String> getSectionValueList(String sectionName) {
        ArrayList<String> sValueList = new ArrayList<>();

        sValueList.clear();
        if (Objects.equals(sectionName, "Personal")) {
            sValueList.add("Personal.FirstName");
            sValueList.add("Personal.LastName");
            sValueList.add("Personal.Birthday");
            sValueList.add("Personal.ID");
            sValueList.add("Personal.Address");
            sValueList.add("Personal.Mobile");
            sValueList.add("Personal.Mail");
            sValueList.add("Personal.Skype");
            sValueList.add("Personal.Facebook");
        }
        if (Objects.equals(sectionName, "Family")) {
            sValueList.add("Family.WifeHusband");
            sValueList.add("Family.Mobile");
        }
        if (Objects.equals(sectionName, "Business")) {
            sValueList.add("Business.FullName");
            sValueList.add("Business.Company");
            sValueList.add("Business.Area");
            sValueList.add("Business.JobPosition");
            sValueList.add("Business.Mail");
            sValueList.add("Business.Phone");
            sValueList.add("Business.Extension");
            sValueList.add("Business.Mobile");
        }
        if (Objects.equals(sectionName, "School")) {
            sValueList.add("School.Name");
            sValueList.add("School.Degree");
            sValueList.add("School.Section");
            sValueList.add("School.TeacherName");
            sValueList.add("School.Schedule");
        }
        if (Objects.equals(sectionName, "Student")) {
            sValueList.add("Student.SchoolName");
            sValueList.add("Student.Id");
            sValueList.add("Student.Career");
            sValueList.add("Student.Cycle");
        }
        if (Objects.equals(sectionName, "Bank")) {
            sValueList.add("Bank.Account1");
            sValueList.add("Bank.Account2");
            sValueList.add("Bank.Account3");
            sValueList.add("Bank.Account4");
            sValueList.add("Bank.Account5");
        }
        return sValueList;
    }

    public ArrayList<String> getSectionCheckedList(String sectionName) {
        ArrayList<String> sCheckedList = new ArrayList<>();

        sCheckedList.clear();
        ArrayList sValueList = getSectionValueList(sectionName);
        for (int idx = 0; idx < sValueList.size(); idx++) {
            String value = (String) sValueList.get(idx);
            value += "Checked";
            sCheckedList.add(value);
        }
        return sCheckedList;
    }

    private ArrayList<String> getMandatoryDataList(Activity curActivity, String sectionName) {
        ArrayList<String> sDataList = new ArrayList<>();

        Log.v("Mandatory SectionName", sectionName);

        SharedPreferences sharedPref = curActivity.getSharedPreferences("MyPersonalInformation", Context.MODE_PRIVATE);
        if (Objects.equals(sectionName, "Personal")) {
            sDataList.add(sharedPref.getString("Personal.FirstName", "") + " " + sharedPref.getString("Personal.LastName", ""));
        }
        if (Objects.equals(sectionName, "Family")) {
            sDataList.add(sharedPref.getString("Family.WifeHusband", ""));
        }
        if (Objects.equals(sectionName, "Business")) {
            sDataList.add(sharedPref.getString("Business.FullName", ""));
        }
        if (Objects.equals(sectionName, "Student")) {
            sDataList.add(sharedPref.getString("Student.SchoolName", ""));
        }
        if (Objects.equals(sectionName, "School")) {
            sDataList.add(sharedPref.getString("School.Name", ""));
        }
//        if (sectionName == "Bank") {
//            No Mandatory Fields Selected
//        }
        return sDataList;
    }

    private ArrayList<String> getCompletedDataList(Activity curActivity, String sectionName) {
        ArrayList<String> sDataList = new ArrayList<>();

        Log.v("Completed SectionName", sectionName);

        int fromIdx = 1;
        if (Objects.equals(sectionName, "Personal")) {
            fromIdx = 2;
        }
        if (Objects.equals(sectionName, "Bank")) {
            fromIdx = 0;
        }
        SharedPreferences sharedPref = curActivity.getSharedPreferences("MyPersonalInformation", Context.MODE_PRIVATE);
        ArrayList<String> sValueList = getSectionValueList(sectionName);
        ArrayList<String> sCheckedList = getSectionCheckedList(sectionName);
        for (int idx = fromIdx; idx < sValueList.size(); idx++) {
            String value = sharedPref.getString(sValueList.get(idx), "");
            Boolean checked = sharedPref.getBoolean(sCheckedList.get(idx), false);
            Log.v("key", sValueList.get(idx));
            Log.v("value", value);
            Log.v("checked", String.valueOf(checked));
            if (checked && !value.isEmpty()) {
                sDataList.add(value);
            }
        }
        return sDataList;
    }

    private int getMainCardImage(String sectionName) {
        int imageId = 0;

        switch (sectionName) {
            case "Personal":
                imageId = R.drawable.ic_personal;
                break;
            case "Student":
                imageId = R.drawable.ic_student;
                break;
            case "Family":
                imageId = R.drawable.ic_family;
                break;
            case "Business":
                imageId = R.drawable.ic_business;
                break;
            case "School":
                imageId = R.drawable.ic_school;
                break;
            case "Bank":
                imageId = R.drawable.ic_bank;
                break;
        }
        return imageId;
    }

    public void createMainContentCards(final Activity curActivity) {
        final ArrayList<String> sectionList = new ArrayList<>();
        final ArrayList<String> titleList = new ArrayList<>();
        final ArrayList classList = new ArrayList<>();
        CardView currentCard;
        ImageView cardImage;
        LinearLayout cardSpacing;
        final int CENTER_VERTICAL = 16;

        final LinearLayout mainContentCards = curActivity.findViewById(R.id.mainContentCards);

        mainContentCards.removeAllViews();

        sectionList.add("Personal");
        sectionList.add("Family");
        sectionList.add("Business");
        sectionList.add("Student");
        sectionList.add("School");
        sectionList.add("Bank");

        titleList.add(curActivity.getResources().getString(R.string.tPersonal));
        titleList.add(curActivity.getResources().getString(R.string.tFamily));
        titleList.add(curActivity.getResources().getString(R.string.tBusiness));
        titleList.add(curActivity.getResources().getString(R.string.tStudent));
        titleList.add(curActivity.getResources().getString(R.string.tSchool));
        titleList.add(curActivity.getResources().getString(R.string.tBank));

        classList.clear();
        classList.add(Info_1_PersonalActivity.class);
        classList.add(Info_2_FamilyActivity.class);
        classList.add(Info_3_BusinessActivity.class);
        classList.add(Info_4_StudentActivity.class);
        classList.add(Info_5_SchoolActivity.class);
        classList.add(Info_6_BankActivity.class);

        for (int sidx = 0; sidx < sectionList.size(); sidx++) {
            //currentCard = new LinearLayout(curActivity, null, 0, R.style.mainContentCard);

            //########## TARJETA ACTUAL
            currentCard = new CardView(curActivity, null, R.style.mainContentCard);
            currentCard.setRadius(10);
            currentCard.setElevation(30);
            currentCard.setMinimumHeight(200);

            //########## CONTENIDO DE LA TARJETA
            LinearLayout mainCCardLayout = new LinearLayout(curActivity, null, 0, R.style.mainContentCardLayout);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            mainCCardLayout.setLayoutParams(lparams);
            mainCCardLayout.setOrientation(LinearLayout.HORIZONTAL);
            mainCCardLayout.setPadding(10, 10, 10, 10);
            //########## LAYOUT IMAGEN DE LA TARJETA
            LinearLayout mainCCardImageLayout = new LinearLayout(curActivity, null, 0, R.style.mainContentCardImageLayout);
            LinearLayout.LayoutParams lparamsImageLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
//            mainCCardImageLayout.setGravity(CENTER_VERTICAL);
            mainCCardImageLayout.setLayoutParams(lparamsImageLayout);
            mainCCardImageLayout.setOrientation(LinearLayout.VERTICAL);
            //########## IMAGEN DE LA TARJETA
            cardImage = new ImageView(curActivity, null, 0, R.style.mainContentCardImage);
            cardImage.setImageResource(getMainCardImage(sectionList.get(sidx)));
            LinearLayout.LayoutParams lparamsImage = new LinearLayout.LayoutParams(100, 100);
            cardImage.setLayoutParams(lparamsImage);
            mainCCardImageLayout.addView(cardImage);
            mainCCardLayout.addView(mainCCardImageLayout);
            //########## LAYOUT DE INFORMACION
            LinearLayout mainCCardInfoLayout = new LinearLayout(curActivity, null, 0, R.style.mainContentCardInfoLayout);
            LinearLayout.LayoutParams lparamsInfoLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mainCCardInfoLayout.setLayoutParams(lparamsInfoLayout);
            mainCCardLayout.addView(mainCCardInfoLayout);
            //########## LAYOUT DE TEXTOS
            LinearLayout mainCCardTextLayout = new LinearLayout(curActivity, null, 0, R.style.mainContentCardTextLayout);
            LinearLayout.LayoutParams lparamsTextLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f);
            mainCCardTextLayout.setOrientation(LinearLayout.VERTICAL);
            mainCCardTextLayout.setLayoutParams(lparamsTextLayout);
            mainCCardInfoLayout.addView(mainCCardTextLayout);
            //########## TEXTOS
            TextView tvTitle = new TextView(curActivity, null, 0, R.style.mainContentCardTitle);
            tvTitle.setText(titleList.get(sidx));
            mainCCardTextLayout.addView(tvTitle);
            //MANDATORY FIELDS
            ArrayList mFieldList = getMandatoryDataList(curActivity, sectionList.get(sidx));
            for (int idx = 0; idx < mFieldList.size(); idx++) {
                TextView tvField;
                tvField = new TextView(curActivity, null, 0, R.style.mainContentCardText);
                tvField.setText(mFieldList.get(idx).toString());
                mainCCardTextLayout.addView(tvField);
            }
            //COMPLETED FIELDS
            ArrayList cFieldList = getCompletedDataList(curActivity, sectionList.get(sidx));
            for (int idx = 0; idx < cFieldList.size(); idx++) {
                TextView tvField;
                tvField = new TextView(curActivity, null, 0, R.style.mainContentCardText);
                tvField.setText(cFieldList.get(idx).toString());
                mainCCardTextLayout.addView(tvField);
            }
            //########## DIVISORIA
            LinearLayout mainCCardSeparator = new LinearLayout(curActivity);
            LinearLayout.LayoutParams lparamsSeparator = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
            mainCCardSeparator.setLayoutParams(lparamsSeparator);
            mainCCardSeparator.setBackgroundResource(R.color.colorPrimary);
            mainCCardInfoLayout.addView(mainCCardSeparator);
            //########## LAYOUT TEXTO MODIFICAR
            LinearLayout mainCCardModifyLayout = new LinearLayout(curActivity, null, 0, R.style.mainContentCardModifyLayout);
            LinearLayout.LayoutParams lparamsModifyLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lparamsModifyLayout.gravity = Gravity.END;
            mainCCardModifyLayout.setLayoutParams(lparamsModifyLayout);

            TextView tvModify = new TextView(curActivity, null, 0, R.style.LinkModify);
            tvModify.setText(curActivity.getResources().getString(R.string.bModify));

            final int finalSidx = sidx;
            tvModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    intent = new Intent(curActivity, (Class<?>) classList.get(finalSidx));
                    curActivity.startActivity(intent);
                }
            });
            mainCCardModifyLayout.addView(tvModify);
            mainCCardInfoLayout.addView(mainCCardModifyLayout);
            //##########
            //Se agrega cada tarjeta
            currentCard.addView(mainCCardLayout);
            mainContentCards.addView(currentCard);
            //Se agrega un espacio en blanco entre cada tarjeta
            cardSpacing = new LinearLayout(curActivity, null, 0, R.style.mainContentCardSpace);
            LinearLayout.LayoutParams lparamsSpace = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
            cardSpacing.setLayoutParams(lparamsSpace);
            mainContentCards.addView(cardSpacing);

            currentCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareString(curActivity, sectionList.get(finalSidx));
                }
            });
        }
    }
}
