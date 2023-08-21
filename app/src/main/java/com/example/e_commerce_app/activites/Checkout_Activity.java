package com.example.e_commerce_app.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.e_commerce_app.R;
import com.example.e_commerce_app.adapters.CartAdapter;
import com.example.e_commerce_app.databinding.ActivityCheckoutBinding;
import com.example.e_commerce_app.model.Product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.Map;

public class Checkout_Activity extends AppCompatActivity {

    ActivityCheckoutBinding binding;

    CartAdapter adapter;
    ArrayList<Product> products;

    double totalPrice = 0;
    final int tax = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        products = new ArrayList<>();

        Cart cart = TinyCartHelper.getCart();

        for (Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()){

            Product product = (Product) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);

            products.add(product);
        }


//        products.add(new Product("Product 1", "", "123", 45, 45, 45, 1));
//        products.add(new Product("Product 2", "", "123", 45, 45, 45, 1));
//        products.add(new Product("Product 3", "", "123", 45, 45, 45, 1));

        adapter = new CartAdapter(this, products, new CartAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                binding.subtotal.setText(String.format("PKR %.2f", cart.getTotalPrice()));
            }
        });

        LinearLayoutManager layoutManager = (new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.cartList.setLayoutManager(layoutManager);
        binding.cartList.addItemDecoration(itemDecoration);
        binding.cartList.setAdapter(adapter);


        binding.subtotal.setText(String.format("PKR %.2f", cart.getTotalPrice()));

        totalPrice = (cart.getTotalPrice().doubleValue() * tax / 100) + cart.getTotalPrice().doubleValue();
        binding.total.setText("BDT "+ totalPrice);


        binding.checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Checkout_Activity.this, "We don't have payment method", Toast.LENGTH_SHORT).show();

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}