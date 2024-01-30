package com.example.lingapp.ui.HomePage.Fragments.EducationalResources;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lingapp.R;
import com.example.lingapp.utils.EducationalResourcesModel;

import java.util.ArrayList;

public class EducationalResourcesFragment extends Fragment {
    private final TextView title;
    private SearchView searchView;
    private EducationalResourcesAdapter adapter;

    private Toolbar toolbar;

    public EducationalResourcesFragment(Toolbar toolbar, TextView title) {
        this.toolbar = toolbar;
        this.title = title;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_educational_resourcement, container, false);


        SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = toolbar.getMenu().findItem(R.id.search);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ArrayList<EducationalResourcesModel> models = new ArrayList<>();

        // add new resources here
        EducationalResourcesModel tomato = new EducationalResourcesModel("Tomato", R.drawable.tomato_sample, "Nutritional Highlights:\n\n" +
                "    - Rich in vitamins A, C, and K\n" +
                "    - Excellent source of antioxidants, including lycopene\n" +
                "    - Contains potassium and folate\n" +
                "    - Low in calories\n" +
                "\n" +
                "Healthy Food Options:\n\n" +
                "    Caprese Salad with a Twist:\n" +
                "        - Combine tomatoes, fresh mozzarella, basil, and a drizzle of balsamic glaze. Opt for whole grains on the side for added fiber.\n" +
                "    Homemade Tomato Salsa:\n" +
                "        - Create a salsa with diced tomatoes, onions, cilantro, and lime juice. Use it as a topping for grilled proteins or whole-grain chips.\n" +
                "    Tomato and Lentil Soup:\n" +
                "        - Make a hearty soup with tomatoes, lentils, and vegetables. Enjoy with a side of whole-grain bread for a balanced meal.\n" +
                "\n" +
                "Nutrition Tips:\n\n" +
                "    - Healthy Eating: Opt for whole, minimally processed foods. Pair tomatoes with a variety of colorful vegetables and lean proteins for balanced meals.\n" +
                "    - Portion Control: Use smaller plates and bowls to control portions. A serving of tomatoes can complement a larger meal or serve as a snack.\n" +
                "    - Food Labels: Choose canned or packaged tomatoes with no added sugars or excessive sodium. Check labels for freshness.\n");

        EducationalResourcesModel potato = new EducationalResourcesModel("Potato", R.drawable.potato, "Nutritional Highlights:\n\n" +
                "    - Good source of potassium\n" +
                "    - High in vitamin C and vitamin B6\n" +
                "    - Contains fiber, especially in the skin\n" +
                "    - Provides complex carbohydrates for sustained energy\n" +
                "\n" +
                "Healthy Food Options:\n\n" +
                "    Herb-Roasted Potatoes:\n" +
                "        - Roast potatoes with herbs and olive oil for a flavorful and nutritious side dish.\n" +
                "    Sweet Potato and Chickpea Buddha Bowl:\n" +
                "        - Create a bowl with roasted sweet potatoes, chickpeas, mixed greens, and a tahini dressing.\n" +
                "    Baked Potato with Greek Yogurt:\n" +
                "        - Top a baked potato with Greek yogurt, chives, and a sprinkle of black pepper.\n" +
                "\n" +
                "Nutrition Tips:\n\n" +
                "    - Healthy Eating: Include a variety of colored potatoes to benefit from different nutrients. Pair with vegetables and lean proteins.\n" +
                "    - Portion Control: Limit added fats when preparing potatoes. Enjoy them as part of a balanced plate with protein and veggies.\n" +
                "    - Food Labels: Choose whole potatoes over processed forms. Frozen fries may contain added preservatives; check labels for additives.\n");

        EducationalResourcesModel carrots = new EducationalResourcesModel("Carrots", R.drawable.carrots, "Nutritional Highlights:\n\n" +
                "High in beta-carotene (precursor to vitamin A)\n" +
                "    - Good source of fiber\n" +
                "    - Contains vitamins C and K\n" +
                "    - Provides natural sweetness\n" +
                "\n" +
                "Healthy Food Options:\n\n" +
                "    Carrot and Apple Slaw:\n" +
                "        - Shred carrots and apples, mix with a light dressing, and serve as a refreshing slaw.\n" +
                "    Roasted Carrot Hummus:\n" +
                "        - Blend roasted carrots into hummus for a nutrient-rich dip.\n" +
                "    Carrot-Ginger Smoothie:\n" +
                "        - Combine carrots with ginger, banana, and almond milk for a vibrant and healthy smoothie.\n" +
                "\n" +
                "Nutrition Tips:\n\n" +
                "    - Healthy Eating: Incorporate carrots into snacks or meals for added crunch and natural sweetness.\n" +
                "    - Portion Control: Enjoy carrots in moderation within a well-balanced diet.\n" +
                "    - Food Labels: Choose fresh or whole carrots. When purchasing packaged carrot products, check for minimal additives and preservatives.");


        EducationalResourcesModel broccoli = new EducationalResourcesModel("Broccoli", R.drawable.brocolli, "Nutritional Highlights:\n\n" +
                "    - High in vitamin C, vitamin K, and folate\n" +
                "    - Rich in fiber and antioxidants (including sulforaphane)\n" +
                "    - Contains minerals such as potassium and manganese\n" +
                "\n" +
                "Healthy Food Options:\n\n" +
                "   Broccoli and Chickpea Stir-Fry:\n" +
                "       - Stir-fry broccoli with chickpeas, colorful bell peppers, and your favorite sauce.\n" +
                "   Quinoa Broccoli Bites:\n" +
                "       - Make quinoa and broccoli patties for a protein-packed and veggie-filled snack.\n" +
                "   Broccoli and Cheese Stuffed Chicken Breast:\n" +
                "       - Fill chicken breasts with broccoli and cheese for a delicious and nutritious main course.\n" +
                "\n" +
                "Nutrition Tips:\n\n" +
                "   - Healthy Eating: Steam or lightly sauté broccoli to retain maximum nutrients. Combine with lean proteins and whole grains.\n" +
                "   - Portion Control: Use broccoli as a side dish or incorporate it into recipes to increase nutrient density.\n" +
                "   - Food Labels: Opt for fresh or frozen broccoli. When buying packaged products, check for added salt or unnecessary preservatives.\n");


        EducationalResourcesModel corn = new EducationalResourcesModel("Corns", R.drawable.corn, "Nutritional Highlights:\n\n" +
                "   - Good source of fiber\n" +
                "   - Contains vitamins B and C\n" +
                "   - Provides essential minerals like magnesium and phosphorus\n" +
                "   - Contains antioxidants, including lutein\n" +
                "\n" +
                "Healthy Food Options:\n\n" +
                "   Grilled Corn and Avocado Salad:\n" +
                "       - Combine grilled corn, avocado, cherry tomatoes, and cilantro for a refreshing salad.\n" +
                "   Vegetarian Corn Tacos:\n" +
                "       - Fill corn tortillas with roasted corn, black beans, and salsa for a meatless taco option.\n" +
                "   Corn and Zucchini Fritters:\n" +
                "       - Make fritters with corn, zucchini, and whole-grain flour for a wholesome snack.\n" +
                "\n" +
                "Nutrition Tips:\n\n" +
                "   - Healthy Eating: Enjoy corn as part of a balanced meal with a variety of vegetables, proteins, and healthy fats.\n" +
                "   - Portion Control: Use corn as a flavorful addition to dishes rather than the main focus.\n" +
                "   - Food Labels: Choose fresh, frozen, or canned corn with no added sugars or excessive salt.\n");

        EducationalResourcesModel lettuce = new EducationalResourcesModel("Lettuce", R.drawable.lettuce, "Lettuce:\n" +
                "\n" +
                "Nutritional Highlights:\n\n" +
                "   - Low in calories\n" +
                "   - Good source of vitamins A and K\n" +
                "   - Provides folate and iron\n" +
                "   - Contains water for hydration\n" +
                "\n" +
                "Healthy Food Options:\n\n" +
                "   Salmon Avocado Lettuce Wraps:\n" +
                "       - Wrap grilled salmon, avocado, and your favorite veggies in lettuce leaves.\n" +
                "   Chickpea and Quinoa Lettuce Cups:\n" +
                "       - Fill lettuce cups with a mixture of chickpeas, quinoa, and Mediterranean-inspired toppings.\n" +
                "   Caesar Salad with Grilled Chicken:\n" +
                "       - Make a classic Caesar salad with grilled chicken for a satisfying and light meal.\n" +
                "\n" +
                "Nutrition Tips:\n\n" +
                "   - Healthy Eating: Choose darker lettuce varieties for increased nutrient content. Include a variety of colorful vegetables in salads.\n" +
                "   - Portion Control: Use lettuce as a base for salads or wraps to control overall caloric intake.\n" +
                "   - Food Labels: Fresh lettuce typically has no labels, but if purchasing packaged salads, check for freshness and minimal processing.\n");

        models.add(tomato);
        models.add(potato);
        models.add(carrots);
        models.add(broccoli);
        models.add(corn);
        models.add(lettuce);

        adapter = new EducationalResourcesAdapter(models, getContext(), getActivity());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setOnSearchClickListener(v -> title.setVisibility(View.GONE));
            searchView.setOnCloseListener(() -> {
                title.setVisibility(View.VISIBLE);
                return false;
            });
            searchView.setQueryHint("Search here...");
            searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Toast like print
                    if(!searchView.isIconified()) {
                        searchView.setIconified(true);
                    }
                    if (searchItem != null) {
                        searchItem.collapseActionView();
                    }
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String s) {
                    ArrayList<EducationalResourcesModel> tempModel = new ArrayList<>();
                    for (EducationalResourcesModel model: models) {
                        if (model.getName().toLowerCase().contains(s.toLowerCase())) {
                            tempModel.add(model);
                        }
                    }
                    recyclerView.removeAllViews();
                    adapter = new EducationalResourcesAdapter(tempModel, getContext(), getActivity());
                    recyclerView.setAdapter(adapter);
                    return false;
                }
            });
        }
        return view;
    }
}