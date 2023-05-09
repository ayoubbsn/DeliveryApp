package com.example.deliveryapplication

import androidx.lifecycle.ViewModel
import com.example.deliveryapplication.model.entity.MenuItem
import com.example.deliveryapplication.model.entity.Restaurant

class MainActivityViewModel : ViewModel() {
    var restaurantList = mutableListOf<Restaurant>(
        Restaurant(
            "Burger Barn",
            """
                    Experience the exotic flavors of Turkey with our authentic dishes made with fresh
                    and aromatic ingredients. Enjoy a delightful culinary journey at our
                    Turkish restaurant.
                """.trimIndent(),
            R.drawable.burger,
            250,
            4.5,
            34
        ),
        Restaurant(
            "Burger Barn",
            """
                    Experience the exotic flavors of Turkey with our authentic dishes made with fresh
                    and aromatic ingredients. Enjoy a delightful culinary journey at our
                    Turkish restaurant.
                """.trimIndent(),
            R.drawable.burger,
            250,
            4.5,
            34
        ),
        Restaurant(
            "Burger Barn",
            """
                    Experience the exotic flavors of Turkey with our authentic dishes made with fresh
                    and aromatic ingredients. Enjoy a delightful culinary journey at our
                    Turkish restaurant.
                """.trimIndent(),
            R.drawable.burger,
            250,
            4.5,
            34
        ),
        Restaurant(
            "Burger Barn",
            """
                    Experience the exotic flavors of Turkey with our authentic dishes made with fresh
                    and aromatic ingredients. Enjoy a delightful culinary journey at our
                    Turkish restaurant.
                """.trimIndent(),
            R.drawable.burger,
            250,
            4.5,
            34
        )
    )
    var menuItems = mutableListOf<MenuItem>(
        MenuItem(1,"Classic Burger", "Beef patty with lettuce, tomato, onion, ketchup, mustard, and mayo", 500),
        MenuItem(2,"Bacon Cheeseburger", "Beef patty with bacon, cheddar cheese, lettuce, tomato, onion, ketchup, and mayo", 700),
        MenuItem(3,"Mushroom Swiss Burger", "Beef patty with Swiss cheese, sautéed mushrooms, lettuce, tomato, and mayo", 800),
        MenuItem(4,"BBQ Burger", "Beef patty with cheddar cheese, bacon, BBQ sauce, lettuce, tomato, onion, and mayo", 900),
        MenuItem(5,"Jalapeno Burger", "Beef patty with pepper jack cheese, jalapenos, lettuce, tomato, onion, and mayo", 750),
        MenuItem(6,"Veggie Burger", "Vegetable patty with lettuce, tomato, onion, ketchup, and mayo", 550),
        MenuItem(7,"Turkey Burger", "Turkey patty with lettuce, tomato, onion, and mayo", 600),
        MenuItem(8,"Hawaiian Burger", "Beef patty with pineapple, ham, lettuce, tomato, onion, and mayo", 850),
        MenuItem(9,"Chili Burger", "Beef patty with chili con carne, cheddar cheese, lettuce, tomato, and onion", 950),
        MenuItem(10,"Buffalo Chicken Burger", "Grilled chicken breast with buffalo sauce, blue cheese dressing, lettuce, tomato, and onion", 800)
    )
}