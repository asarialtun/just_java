/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */
package com.credevapp.justjava;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import com.credevapp.justjava.R;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        boolean cream_topping = false;
        boolean choco_topping = false;
        CheckBox cream_checkbox = (CheckBox) findViewById(R.id.checkbox_cream);
        CheckBox choco_checkbox = (CheckBox) findViewById(R.id.checkbox_choco);
        if (cream_checkbox.isChecked())
          cream_topping = true;
        if (choco_checkbox.isChecked())
            choco_topping = true;
        int price = calculatePrice(choco_topping,cream_topping);


        EditText  text_name = (EditText) findViewById(R.id.text_name);
        String name = text_name.getText().toString();

        String priceMessage = createOrderSummary(price,cream_topping,choco_topping,name);
        //displayMessage(priceMessage);
        composeEmail("Coffee order",priceMessage);
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean choco, boolean cream) {
        int basePrice = 5;
        if(choco)
            basePrice = basePrice + 2;
        if (cream)
            basePrice = basePrice + 1;
        int price = quantity * basePrice;
        return price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given quantity value on the screen
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    private String createOrderSummary(int price, boolean cream_topping, boolean choco_topping, String name){
        String message = "Order Summary:\n\nName: "+ name +"\n"+"Cream Topping: " + cream_topping +"\nChocolate Topping:  "+choco_topping+"\nQuantity: " + quantity + "\n"+"Total: $" + price + "\nThank you!";
        return message;
    }

    /**
     * This method displays the given text on the screen
     */
    private void displayMessage(String message){
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    /**
     * This method increases the quantity
     */
    public void increment (View view){
        if(quantity >= 10) {
            Toast.makeText(this, "You cannot order more than 10 coffees",Toast.LENGTH_SHORT).show();

            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method decreases the quantity
     */
    public void decrement(View view) {
        if(quantity <= 1) {
            Context context = getApplicationContext();
            Toast error_toast = Toast.makeText(context, "You cannot order less than 1 coffee",Toast.LENGTH_SHORT);
            error_toast.show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    public void composeEmail(String subject, String orderText) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_TEXT, orderText);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}