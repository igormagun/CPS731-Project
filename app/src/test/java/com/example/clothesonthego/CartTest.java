package com.example.clothesonthego;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

/**
 * A series of unit tests for cart-related use cases, such as:
 * - Add product to cart
 * - Remove product from cart
 * - Modify product quantity in the cart
 *
 */
public class CartTest {
    @Mock
    CartActivity cartActivity;

    @Mock (name = "controller")
    DBController dbController;

    @Mock (name = "mAuth")
    FirebaseAuth auth;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    Cart cart;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(FirebaseAuth.getInstance()).thenReturn(null);
        Mockito.when(auth.getUid()).thenReturn("test");
        cart = new Cart(cartActivity);
    }

    @Test
    public void addToCart() {
        Map<String, Object> testCart = new HashMap<>();
        testCart.put("12345", 2);
        AtomicReference<Map<String, Object>> testCartReference = new AtomicReference<>(testCart);

        ArrayList<Product> productList = new ArrayList<>();
        Product testProduct = new Product("12345", "test", "test",
                "test", 123.56);
        productList.add(testProduct);

        cart.setupCart(testCartReference, productList, new ArrayList<>());

        Map<String, Long> resultCart = cart.getProducts();
        assertEquals(resultCart.size(), 1);
        assertEquals(resultCart.get("12345"), Long.valueOf(2));
    }
}
