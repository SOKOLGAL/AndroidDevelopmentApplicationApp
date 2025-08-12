package com.example.androiddevelopmentapplicationapp

object STUB {

    private val categories = listOf(
        Category(
            id = 0,
            title = "Бургеры",
            description = "Рецепты всех популярных видов бургеров",
            imageUrl = "burger.png"
        ),
        Category(
            id = 1,
            title = "Десерты",
            description = "Самые вкусные рецепты десертов специально для вас",
            imageUrl = "dessert.png"
        ),
        Category(
            id = 2,
            title = "Пицца",
            description = "Пицца на любой вкус и цвет. Лучшая подборка для тебя",
            imageUrl = "pizza.png"
        ),
        Category(
            id = 3,
            title = "Рыба",
            description = "Печеная, жареная, сушеная, любая рыба на твой вкус",
            imageUrl = "fish.png"
        ),
        Category(
            id = 4,
            title = "Супы",
            description = "От классики до экзотики: мир в одной тарелке",
            imageUrl = "soup.png"
        ),
        Category(
            id = 5,
            title = "Салаты",
            description = "Хрустящий калейдоскоп под соусом вдохновения",
            imageUrl = "salad.png"
        )
    )

    fun getCategories(): List<Category> = categories

    private val burgerRecipes = listOf(
        Recipe(
            id = 0,
            title = "Классический гамбургер",
            ingredients = listOf(),
            method = listOf(),
            description = "Сочный говяжий котлет, свежие овощи, соус",
            imageUrl = "burger_hamburger.png",
                  ),
        Recipe(
            id = 1,
            title = "Чизбургер",
            ingredients = listOf(),
            method = listOf(),
            description = "Бургер с сыром чеддер и карамелизированным луком",
            imageUrl = "burger_cheeseburger.png"
        ),
        Recipe(
            id = 2,
            title = "Бургер с грибами и сыром",
            ingredients = listOf(),
            method = listOf(),
            description = "",
            imageUrl = "burger_mushrooms.png"
        ),
        Recipe(
            id = 3,
            title = "Бургер с курицей и авокадо",
            ingredients = listOf(),
            method = listOf(),
            description = "",
            imageUrl = "burger_avocado.png"
        ),
        Recipe(
            id = 4,
            title = "Бургер с рыбой",
            ingredients = listOf(),
            method = listOf(),
            description = "",
            imageUrl = "burger_fish.png"
        ),
        Recipe(
            id = 5,
            title = "Бургер с беконом",
            ingredients = listOf(),
            method = listOf(),
            description = "",
            imageUrl = "burger_bacon.png"
        ),
        Recipe(
            id = 6,
            title = "Веганский бургер",
            ingredients = listOf(),
            method = listOf(),
            description = "",
            imageUrl = "burger_vegan.png"
        ),
        Recipe(
            id = 7,
            title = "Острый бургер",
            ingredients = listOf(),
            method = listOf(),
            description = "",
            imageUrl = "burger_chili.png"
        )
    )

    fun getRecipesByCategoryId(categoryId: Int): List<Recipe> {
        return if (categoryId == 0) {
            burgerRecipes
        } else {
            emptyList()
        }
    }
}