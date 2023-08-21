package com.example.e_commerce_app.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.e_commerce_app.R;
import com.example.e_commerce_app.adapters.CartAdapter;
import com.example.e_commerce_app.databinding.ActivityCartBinding;
import com.example.e_commerce_app.model.Product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;
    CartAdapter adapter;
    ArrayList<Product> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
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



        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, Checkout_Activity.class));
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