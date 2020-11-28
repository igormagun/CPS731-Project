package com.example.clothesonthego;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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
@RunWith(PowerMockRunner.class)
@PrepareForTest({DBController.class, FirebaseAuth.class, Cart.class})
public class CartTest {
    @Mock
    private CartActivity cartActivity;

    @Mock
    private DBController controller;

    @Mock
    private FirebaseAuth mAuth;

    private Cart cart;
    private Product testProduct;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Mock FirebaseAuth.getInstance() to return our mock instance
        PowerMockito.mockStatic(FirebaseAuth.class);
        PowerMockito.when(FirebaseAuth.getInstance()).thenReturn(mAuth);
        Mockito.when(mAuth.getUid()).thenReturn("test");

        // Mock new DBController() to return our mock controller
        PowerMockito.whenNew(DBController.class).withNoArguments().thenReturn(controller);

        // Create a Cart to test using our mock CartActivity
        cart = new Cart(cartActivity);

        // Populate the cart with a test item. Each test will check/modify this accordingly
        Map<String, Object> testCart = new HashMap<>();
        testCart.put("12345", 2L);
        AtomicReference<Map<String, Object>> testCartReference = new AtomicReference<>(testCart);

        // Place a test Product object in the expected ArrayList
        ArrayList<Product> productList = new ArrayList<>();
        testProduct = new Product("12345", "test", "test",
                "test", 123.56);
        productList.add(testProduct);

        cart.setupCart(testCartReference, productList, new ArrayList<>());
    }

    @Test
    public void addToCart() {
        Map<String, Long> resultCart = cart.getProducts();
        assertEquals(resultCart.size(), 1);
        assertEquals(resultCart.get(testProduct.getId()), Long.valueOf(2));

        ArrayList<Product> productDetails = cart.getProductDetails();
        assertEquals(productDetails.size(), 1);
        assertEquals(productDetails.get(0), testProduct);
    }

    @Test
    public void removeFromCart() {
        cart.removeFromCart(testProduct.getId());

        Map<String, Long> resultCart = cart.getProducts();
        assertEquals(resultCart.size(), 0);

        ArrayList<Product> productDetails = cart.getProductDetails();
        assertEquals(productDetails.size(), 0);
    }

    @Test
    public void modifyQuantity() {
        // First test adjusting to a non-zero quantity
        cart.modifyQuantity(testProduct.getId(), 3);

        Map<String, Long> resultCart = cart.getProducts();
        assertEquals(resultCart.size(), 1);
        assertEquals(resultCart.get(testProduct.getId()), Long.valueOf(3));

        ArrayList<Product> productDetails = cart.getProductDetails();
        assertEquals(productDetails.size(), 1);
        assertEquals(productDetails.get(0), testProduct);

        // Now test modifying to a zero quantity - this should delete the product
        cart.modifyQuantity(testProduct.getId(), 0);

        resultCart = cart.getProducts();
        assertEquals(resultCart.size(), 0);

        productDetails = cart.getProductDetails();
        assertEquals(productDetails.size(), 0);
    }
}
