package com.example.currencyconverterpro;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    Context context;
    private List<MyModel> list;

    Button Numbers[];

    private double[] currencies; // Changed from double[] to Double[] to handle null values

    int selectedPosition = -1;
    double convert_value = 0.0;
    public MyAdapter(List<MyModel> list, Context context){
        this.list = list;
        this.context = context;
        this.currencies = new double[list.size()]; // Initialize the currencies array

    }

    public static class ViewHolder {
        TextView amount;
        ImageView myImage;
        TextView countryName;
        LinearLayout layout;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.mylayout, parent, false);
            holder.amount = (TextView) view.findViewById(R.id.amount);
            holder.myImage =  (ImageView) view.findViewById(R.id.myImage);
            holder.countryName = (TextView) view.findViewById(R.id.countryName);
            holder.layout = (LinearLayout) view.findViewById(R.id.linear_layout);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        MyModel item = list.get(position);
        holder.countryName.setText(item.getC_name());
        holder.myImage.setImageResource(item.getC_img());
        holder.amount.setText(String.valueOf(currencies[position]));
        // Update the amount TextView with the new value

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
                setNumericButtonListeners(holder.amount, holder.countryName.getText().toString());
            }
        });
        if (position == selectedPosition) {
            holder.layout.setBackgroundColor(Color.parseColor("#f6fdfd"));
            holder.countryName.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.amount.setTextColor(Color.BLACK);
        } else {
            holder.layout.setBackgroundColor(Color.parseColor("#000000"));
            // Default color for unselected items
            holder.countryName.setTextColor(ContextCompat.getColor(context, R.color.white)); // Default text color for unselected items
            holder.amount.setTextColor(Color.WHITE);
        }

//
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectedPosition = position;
//
////                Toast.makeText(context, "You have selected " + holder.countryName.getText().toString(), Toast.LENGTH_SHORT).show();
//
//                notifyDataSetChanged(); // Refresh the list view to update the selection
//                setNumericButtonListeners(holder.amount, holder.countryName.getText().toString());
//
//            }
//        });
//        holder.amount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectedPosition = position;
//                notifyDataSetChanged();
//                Toast.makeText(context, "You have selected " + holder.countryName.getText().toString(), Toast.LENGTH_SHORT).show();
//
//                for (int i = 0; i < parent.getChildCount(); i++) {
//                    View child = parent.getChildAt(i);
//                    if (i == position) {
//                        // Selected item
//                        child.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    } else {
//                        // Unselected items
//                        child.setBackgroundColor(Color.parseColor("#000000"));
//                    }
//                }
//
//                // Handle other UI changes or actions as needed
//                Toast.makeText(context, "You have selected " + holder.countryName.getText().toString(), Toast.LENGTH_SHORT).show();
//                setNumericButtonListeners(holder.amount, holder.countryName.getText().toString());
//                //                notifyDataSetChanged(); // Refresh the list view to update the selection
////                setNumericButtonListeners(holder.amount, holder.countryName.getText().toString());
//
//            }
//        });


        return view;
    }

    private void setNumericButtonListeners(TextView amountTextView, String selectedCountry) {
        Button btn_cancel = ((MainActivity) context).findViewById(R.id.btn_cancel);
        Button btn_equal = ((MainActivity) context).findViewById(R.id.btn_equal);
        Button btn_back = ((MainActivity) context).findViewById(R.id.btn_back);
        Button[] Numbers = new Button[10];

        for (int i = 0; i <= 9; i++) {
            int buttonId = ((MainActivity) context).getResources().getIdentifier("button" + i, "id", context.getPackageName());
            Numbers[i] = ((MainActivity) context).findViewById(buttonId);
            int finalI = i;
            Numbers[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (amountTextView.getText().toString().equals("0.0")) {
                        amountTextView.setText(String.valueOf(finalI));
                    } else {
                        amountTextView.append(String.valueOf(finalI));
                    }
                }
            });
        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountTextView.setText("0.0");
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = amountTextView.getText().toString();
                if (!text.isEmpty() && !text.equals("0")) {
                    int posToDel = text.length() - 1;
                    String updatedText = text.substring(0, posToDel);
                    amountTextView.setText(updatedText);
                }
            }
        });

        btn_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convert_value = Double.parseDouble(amountTextView.getText().toString());
                new FetchExchangeRateTask(selectedCountry).execute("https://v6.exchangerate-api.com/v6/264afdc48aafd6f3663a09e1/latest/" + selectedCountry);
            }
        });
    }

    private void updateCurrencies(double[] newCurrencies) {
        DecimalFormat df = new DecimalFormat("#.###");
        for (int i = 0; i < currencies.length; i++) {
            if (i != selectedPosition) { // Update only if it's not the selected item
                currencies[i] = Double.parseDouble(df.format(newCurrencies[i]));
            }
            else {
                currencies[i] = convert_value;
            }
        }
        notifyDataSetChanged();
    }

    private class FetchExchangeRateTask extends AsyncTask<String, Void, String> {
        JsonObject conversionRates;
        String selectedCountry;

        FetchExchangeRateTask(String selectedCountry) {
            this.selectedCountry = selectedCountry;
        }

        @Override
        protected String doInBackground(String... urls) {
            String url_str = urls[0];
            try {
                URL url = new URL(url_str);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream) connection.getContent()));
                JsonObject jsonobj = root.getAsJsonObject();
                conversionRates = jsonobj.getAsJsonObject("conversion_rates");

                return jsonobj.get("result").getAsString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                double pkr = convert_value * conversionRates.get("PKR").getAsDouble();
                double afg = convert_value * conversionRates.get("AFN").getAsDouble();
                double usd = convert_value * conversionRates.get("USD").getAsDouble();
                double eur = convert_value * conversionRates.get("EUR").getAsDouble();
                double inr = convert_value * conversionRates.get("INR").getAsDouble();
                double irr = convert_value * conversionRates.get("IRR").getAsDouble();
                double jpy = convert_value * conversionRates.get("JPY").getAsDouble();
                double cny = convert_value * conversionRates.get("CNY").getAsDouble();

                double[] newCurrencies = new double[]{pkr, afg, usd, eur, inr, irr, jpy, cny};
                updateCurrencies(newCurrencies);
            } else {
                Toast.makeText(context, "Failed to fetch exchange rate", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void filteredList(List<MyModel> list){
        this.list = list;
        notifyDataSetChanged();
    }
}

