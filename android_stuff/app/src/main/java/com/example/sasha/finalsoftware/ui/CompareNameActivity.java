package com.example.sasha.finalsoftware.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.cs4531.finalsoftware.R;
import com.example.sasha.finalsoftware.model.Name;
import com.example.sasha.finalsoftware.model.Period;
import com.example.sasha.finalsoftware.model.util.CensusSearcher;

import java.util.List;

public class CompareNameActivity extends AppCompatActivity {

    private CensusSearcher name1Search;
    private CensusSearcher name2Search;
    private List<Name> listName1;
    private List<Name> listName2;
    private Integer yearToggle;
    private Integer percentToggle;
    private Integer genderToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        name1Search = new CensusSearcher(this, this, 1);
        name2Search = new CensusSearcher(this, this, 2);
        yearToggle = 0;
        percentToggle = 0;
        genderToggle = 0;

        Button compareBtn = findViewById(R.id.compareBtn);
        compareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView nameList1 = findViewById(R.id.compareNameList1);
                TextView nameList2 = findViewById(R.id.compareNameList2);
                TextView namePercentDiff = findViewById(R.id.compareNamePercentDiff);
                TextView name1disc = findViewById(R.id.compareResultName1disc);
                TextView name2disc = findViewById(R.id.compareResultName2disc);
                EditText mindate = findViewById(R.id.compareDate1);
                EditText maxdate = findViewById(R.id.compareDate2);
                EditText name1 = findViewById(R.id.compareName);
                EditText name2 = findViewById(R.id.compareName1);
                Period timeP = new Period();

                nameList1.setMovementMethod(new ScrollingMovementMethod());
                nameList2.setMovementMethod(new ScrollingMovementMethod());

                String minDate = mindate.getText().toString();
                String maxDate = maxdate.getText().toString();

                String n1 = name1.getText().toString();
                String n2 = name2.getText().toString();

                if (!minDate.equals("") && !maxDate.equals("") && !n1.equals("") && !n2.equals("")) {      //Makes sure there is no blank fields
                    int mDate = Integer.parseInt(minDate);
                    int maDate = Integer.parseInt(maxDate);
                    if (mDate < 1880 || maDate > 2008 || mDate > maDate) {    //Makes sure data is in range
                        namePercentDiff.setText("Check your dates");
                    } else {
                        name1disc.setText(n1);
                        name2disc.setText(n2);
                        timeP.setPeriodTimeFrame(Integer.parseInt(minDate), Integer.parseInt(maxDate));

                        listClear();
                        name1Search.nameList.clear();
                        name2Search.nameList.clear();
                        name1Search.searchForName(n1, timeP);
                        name2Search.searchForName(n2, timeP);
                    }
                }
                else {
                    namePercentDiff.setText("The two name and date fields can not be blank");
                }
            }
        });

        Button compareSortByPercent = findViewById(R.id.compareSortByPercent);
        compareSortByPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                percentSortAndUpdateGUI();
            }
    });

        Button compareSortByYear = findViewById(R.id.compareSortByYear);
        compareSortByYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yearSortAndUpdateGUI();
            }
        });

        Button compareSortByGender = findViewById(R.id.compareSortByGender);
        compareSortByGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genderSortAndUpdateGUI();
            }
        });
    };

    /**
     * Updates the contense of nameList1 and nameList2
     * @param dist Stands for what nameList is being searched
     */
    public void updateGUI(int dist){
        TextView nameList1 = findViewById(R.id.compareNameList1);
        TextView nameList2 = findViewById(R.id.compareNameList2);

    if (dist == 1) {
            listName1 = name1Search.nameList;
            listName1.sort(Name.getYearComparator());
            int list1Size = listName1.size();
            for (int n = 0; n < list1Size; n++) {
                nameList1.append(listName1.get(n).toString());
            }
    }
    else if (dist == 2) {
            listName2 = name2Search.nameList;
            listName2.sort(Name.getYearComparator());
            int list2Size = listName2.size();
            for (int n = 0; n < list2Size; n++) {
                nameList2.append(listName2.get(n).toString());
            }
    }
    }

    public void percentSortAndUpdateGUI(){
        TextView nameList1 = findViewById(R.id.compareNameList1);
        TextView nameList2 = findViewById(R.id.compareNameList2);
        TextView namePercentDiff = findViewById(R.id.compareNamePercentDiff);

        List<Name> percentListName1;
        List<Name> percentListName2;

        listClear();

        if (listName1 != null && listName2 != null) {
            percentListName1 = listName1;
            percentListName2 = listName2;
            if(percentToggle == 0){
                percentListName1.sort(Name.getPopularityComparator());
                percentListName2.sort(Name.getPopularityComparator());
                percentToggle = 1;
            }
            else if (percentToggle == 1){
                percentListName1.sort(Name.getPopularityComparator().reversed());
                percentListName2.sort(Name.getPopularityComparator().reversed());
                percentToggle = 0;
            }

            int list1Size = percentListName1.size();
            for (int n = 0; n < list1Size; n++) {
                nameList1.append(percentListName1.get(n).toString());
            }

            int list2Size = percentListName2.size();
            for (int n = 0; n < list2Size; n++) {
                nameList2.append(percentListName2.get(n).toString());
            }
        }
        else{
            namePercentDiff.setText("You must get data from the server before you can sort!");
        }
    }

    public void yearSortAndUpdateGUI(){
        TextView nameList1 = findViewById(R.id.compareNameList1);
        TextView nameList2 = findViewById(R.id.compareNameList2);
        TextView namePercentDiff = findViewById(R.id.compareNamePercentDiff);

        List<Name> yearListName1;
        List<Name> yearListName2;

        listClear();

        if (listName1 != null && listName2 != null) {
            yearListName1 = listName1;
            yearListName2 = listName2;
            if(yearToggle == 1){
                yearListName1.sort(Name.getYearComparator());
                yearListName2.sort(Name.getYearComparator());
                yearToggle = 0;
            }
            else if (yearToggle == 0){
                yearListName1.sort(Name.getYearComparator().reversed());
                yearListName2.sort(Name.getYearComparator().reversed());
                yearToggle = 1;
            }


            int list1Size = yearListName1.size();
            for (int n = 0; n < list1Size; n++) {
                nameList1.append(yearListName1.get(n).toString());
            }

            int list2Size = yearListName2.size();
            for (int n = 0; n < list2Size; n++) {
                nameList2.append(yearListName2.get(n).toString());
            }
        }
        else{
            namePercentDiff.setText("You must get data from the server before you can sort!");
        }
    }

    public void genderSortAndUpdateGUI(){
        TextView nameList1 = findViewById(R.id.compareNameList1);
        TextView nameList2 = findViewById(R.id.compareNameList2);
        TextView namePercentDiff = findViewById(R.id.compareNamePercentDiff);

        List<Name> genderListName1;
        List<Name> genderListName2;

        listClear();

        if (listName1 != null && listName2 != null) {
            genderListName1 = listName1;
            genderListName2 = listName2;
            if(genderToggle == 0){
                genderListName1.sort(Name.getSexComparator());
                genderListName2.sort(Name.getSexComparator());
                genderToggle = 1;
            }
            else if (genderToggle == 1){
                genderListName1.sort(Name.getSexComparator().reversed());
                genderListName2.sort(Name.getSexComparator().reversed());
                genderToggle = 0;
            }

            int list1Size = genderListName1.size();
            for (int n = 0; n < list1Size; n++) {
                nameList1.append(genderListName1.get(n).toString());
            }

            int list2Size = genderListName2.size();
            for (int n = 0; n < list2Size; n++) {
                nameList2.append(genderListName2.get(n).toString());
            }
        }
        else{
            namePercentDiff.setText("You must get data from the server before you can sort!");
        }
    }

    /**
     * Clears the data in nameList1 and nameList2
     */
    public void listClear(){
        TextView nameList1 = findViewById(R.id.compareNameList1);
        TextView nameList2 = findViewById(R.id.compareNameList2);

        nameList1.setText("");
        nameList2.setText("");
    }






}
