package com.example.sasha.finalsoftware.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.*;
import android.view.View;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.cs4531.finalsoftware.R;
import com.example.sasha.finalsoftware.model.Period;
import com.example.sasha.finalsoftware.model.Name;
import com.example.sasha.finalsoftware.model.util.CensusSearcher;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private CensusSearcher censusSearcher;
    List<Name> listNames;
    private Integer yearToggle;
    private Integer percentToggle;
    private Integer genderToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        censusSearcher = new CensusSearcher(this, this);
        yearToggle = 0;
        percentToggle = 0;
        genderToggle = 0;

        Button searchButton = findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        Button searchSortByPercent = findViewById(R.id.searchSortByPercent);
        searchSortByPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                percentSortAndUpdateGUI();
            }
        });

        Button searchSortByYear = findViewById(R.id.searchSortByYear);
        searchSortByYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yearSortAndUpdateGUI();
            }
        });

        Button searchSortByGender = findViewById(R.id.searchSortByGender);
        searchSortByGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               genderSortAndUpdateGUI();
            }
        });



    }



    protected void search() {
        EditText nameBox = findViewById(R.id.searchName);
        EditText minYearBox = findViewById(R.id.searchDate);
        EditText maxYearBox = findViewById(R.id.searchDate2);
        TextView searchError = findViewById(R.id.searchError);
        TextView nameDisc = findViewById(R.id.searchResultName);

        String name = nameBox.getText().toString();
        int minYear = Integer.parseInt(minYearBox.getText().toString());
        int maxYear = Integer.parseInt(maxYearBox.getText().toString());

        Period period = new Period();
        period.setPeriodTimeFrame(minYear, maxYear);

        String minDate = minYearBox.getText().toString();
        String maxDate = maxYearBox.getText().toString();

        String n1 = nameBox.getText().toString();

        if (!minDate.equals("") && !maxDate.equals("") && !n1.equals("")) {
            int mDate = Integer.parseInt(minDate);
            int maDate = Integer.parseInt(maxDate);
            if (mDate < 1880 || maDate > 2008 || mDate > maDate) {    //Makes sure data is in range
               searchError.setText("Check your dates");
            } else {
                nameDisc.setText(name);
                period.setPeriodTimeFrame(Integer.parseInt(minDate), Integer.parseInt(maxDate));

                listClear();
                censusSearcher.nameList.clear();
                censusSearcher.searchForName(name, period);
            }
        }
        else {
           searchError.setText("The two name and date fields can not be blank");
        }
    }

    public void percentSortAndUpdateGUI(){
        TextView namesList = findViewById(R.id.searchNameList);
        namesList.setMovementMethod(new ScrollingMovementMethod());
        TextView searchError = findViewById(R.id.searchError);

        List<Name> percentListName;

        listClear();

        if (listNames != null) {
            percentListName = listNames;
            if(percentToggle == 0){
                percentListName.sort(Name.getPopularityComparator());
                percentToggle = 1;
            }
            else if (percentToggle == 1){
                percentListName.sort(Name.getPopularityComparator().reversed());
                percentToggle = 0;
            }

            int list1Size = percentListName.size();
            for (int n = 0; n < list1Size; n++) {
                namesList.append(percentListName.get(n).toString());
            }
        }
        else{
            searchError.setText("You must get data from the server before you can sort!");
        }
    }

    public void genderSortAndUpdateGUI(){
        TextView namesList = findViewById(R.id.searchNameList);
        namesList.setMovementMethod(new ScrollingMovementMethod());
        TextView searchError = findViewById(R.id.searchError);

        List<Name> genderListName;

        listClear();

        if (listNames != null) {
            genderListName = listNames;
            if(genderToggle == 0){
                genderListName.sort(Name.getSexComparator());
                genderToggle = 1;
            }
            else if (genderToggle == 1){
                genderListName.sort(Name.getSexComparator().reversed());
                genderToggle = 0;
            }

            int list1Size = genderListName.size();
            for (int n = 0; n < list1Size; n++) {
                namesList.append(genderListName.get(n).toString());
            }
        }
        else{
            searchError.setText("You must get data from the server before you can sort!");
        }
    }

    public void yearSortAndUpdateGUI(){
        TextView namesList = findViewById(R.id.searchNameList);
        namesList.setMovementMethod(new ScrollingMovementMethod());
        TextView searchError = findViewById(R.id.searchError);

        List<Name> yearListName;

        listClear();

        if (listNames != null) {
            yearListName = listNames;
            if(yearToggle == 0){
                yearListName.sort(Name.getYearComparator());
                yearToggle = 1;
            }
            else if (yearToggle == 1){
                yearListName.sort(Name.getYearComparator().reversed());
                yearToggle = 0;
            }

            int list1Size = yearListName.size();
            for (int n = 0; n < list1Size; n++) {
                namesList.append(yearListName.get(n).toString());
            }
        }
        else{
            searchError.setText("You must get data from the server before you can sort!");
        }
    }


    public void updateGUI(){
        TextView namesList = findViewById(R.id.searchNameList);
        namesList.setMovementMethod(new ScrollingMovementMethod());

        listNames = censusSearcher.nameList;
        int listSize = listNames.size();
        for (int n = 0; n<listSize; n++) {
            namesList.append(listNames.get(n).toString());
        }

    }

    public void listClear(){
        TextView nameList1 = findViewById(R.id.searchNameList);
        nameList1.setText("");
    }

}
