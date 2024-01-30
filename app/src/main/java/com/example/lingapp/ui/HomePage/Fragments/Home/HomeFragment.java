package com.example.lingapp.ui.HomePage.Fragments.Home;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lingapp.R;
import com.example.lingapp.utils.HomeFragmentModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private Context context;
    private Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        if (getContext() != null) {
            context = getContext();
        }
        if (getActivity() != null) {
            activity = getActivity();
        }

        ArrayList<HomeFragmentModel> models = new ArrayList<>();
        String template = activity.getString(R.string.short_paragraph);

        // add content here
        HomeFragmentModel dyk = new HomeFragmentModel(R.drawable.did_you_know, "Super Seeds:\n\n" +
                "   - \uD83C\uDF30 Did you know seeds like chia and flaxseed are tiny but mighty? They're packed with good stuff for your brain and body!\n" +
                "   -\uD83C\uDF3B Sunflower seeds are tiny powerhouses of nutrition! They provide vitamin E, which is excellent for your skin.\n" +
                "\n" +
                "Breakfast Brain Boost:\n\n" +
                "   - \uD83C\uDF73 Breakfast is like a superhero cape for your brain! It helps you think, learn, and play better throughout the day.\n" +
                "   -\uD83E\uDD63 Oatmeal is a superhero breakfast. It keeps you full, gives you energy, and helps you focus on all your adventures!\n" +
                "\n" +
                "Picky Eater Power:\n\n" +
                "    - \uD83E\uDD14 Did you know that trying new foods is like unlocking special levels in a game? You might discover a new favorite!\n", "Did you know?");

        HomeFragmentModel ff = new HomeFragmentModel(R.drawable.fun_fact, "Did You Know?\n\n" +
                "   - \uD83E\uDD66 Broccoli is like a superhero for your body! It's packed with vitamins that help keep your bones strong and your skin healthy.\n" +
                "   - \uD83C\uDF53 Strawberries are not only delicious but also packed with vitamin C, which helps keep your skin healthy and glowing.\n" +
                "\n" +
                "Mealtime Magic:\n\n" +
                "   - \uD83C\uDF08 Eating a rainbow of fruits and veggies gives your body a mix of powerful nutrients. It's like a superhero team for your health!\n" +
                "\n" +
                "Healthy Habits Rock:\n\n" +
                "   - \uD83E\uDDE0 Your brain loves healthy fats found in foods like avocados and nuts. It's like brain fuel for thinking and learning!\n", "Fun Facts!");

        HomeFragmentModel fwf = new HomeFragmentModel(R.drawable.fun_with_food, "Mealtime Magic:\n\n" +
                "   - ✨ Make mealtime fun by creating colorful and creative plates. Try arranging veggies in smiley faces or making fruit kebabs!\n" +
                "\n" +
                "Food Art Adventures:\n\n" +
                "   - \uD83C\uDFA8 Turn your plate into a canvas! Use veggies, fruits, and healthy ingredients to create edible art. It's almost too pretty to eat!\n" +
                "\n\n" +
                "Fruit Fiesta:\n\n" +
                "   - \uD83C\uDF47\uD83C\uDF4D Mix and match fruits to create your own fruit fiesta. The more colors, the better!\n" +
                "\n\n" +
                "Veggie Tales:\n\n" +
                "    - \uD83E\uDD6C Turn veggies into characters. Create stories with your veggie pals and enjoy eating them together!\n" +
                "\n\n" +
                "Balancing Act:\n\n" +
                "    - ⚖️ Think of your plate like a seesaw. Balance it with a mix of fruits, veggies, proteins, and whole grains for a healthy ride!\n", "Fun with Foods!");

        HomeFragmentModel hb = new HomeFragmentModel(R.drawable.healthy_habits, "Hydration Station:\n\n" +
                "   - \uD83D\uDCA7 Water is your body's best friend. It keeps you cool, helps you think, and makes your skin happy. Don't forget to sip!\n" +
                "\n\n" +
                "Veggie Victory:\n\n" +
                "   - \uD83C\uDF3D Veggies are like little power-ups for your body. The more colors you eat, the stronger you become!\n" +
                "\n\n" +
                "Snack Attack:\n\n" +
                "    - \uD83E\uDD55 Keep healthy snacks within reach for a snack attack that's good for you. Carrot sticks, anyone?\n", "Health Habit!");

        HomeFragmentModel bst = new HomeFragmentModel(R.drawable.bite, "Mix and Match:\n\n" +
                "    - \uD83C\uDF4F Experiment with combining different foods. You might discover tasty combos like apple slices with a touch of peanut butter!\n" +
                "\n\n" +
                "Sneaky Veggies:\n\n" +
                "    - \uD83C\uDF5D Sneak veggies into pasta sauces or smoothies. It's a secret mission to make meals healthier and more delicious!\n" +
                "\n\n" +
                "Fruit Popsicle Fun:\n\n" +
                "    - \uD83C\uDF53 Freeze your favorite fruits in popsicle molds. It's a cool way to enjoy a sweet treat without added sugars.\n" +
                "\n\n" +
                "Berry Blast Smoothies:\n\n" +
                "    - \uD83C\uDF47\uD83C\uDF4C Create a berry blast smoothie with yogurt. It's a tasty way to get a dose of vitamins and calcium!\n", "Bite-Sized Tips!");

        models.add(dyk);
        models.add(ff);
        models.add(fwf);
        models.add(hb);
        models.add(bst);


        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getActivity(), context, models);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }
}