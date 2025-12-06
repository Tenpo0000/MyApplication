package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Integer> imagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<Integer> imagens = Arrays.asList(
                R.drawable.bob_esponja_filme,
                R.drawable.bob_esponja_filme,
                R.drawable.bob_esponja_filme,
                R.drawable.bob_esponja_filme,
                R.drawable.bob_esponja_filme,
                R.drawable.bob_esponja_filme,
                R.drawable.bob_esponja_filme
        );
        RecyclerView recycler = findViewById(R.id.recyclerFilmes);

        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recycler);

        FilmeDestaque_Adapter adapter = new FilmeDestaque_Adapter(imagens);
        recycler.setAdapter(adapter);
    }
}