package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Recyadapter recyadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetdataService service = RetrofitInstance.getRetrofitInstance().create(GetdataService.class);

        //Starting from
       /* Call<List<Pokemon>> call = service.getPokemons();
        call.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                System.out.println(response.body());
                generateData(response.body());
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something wrong",Toast.LENGTH_SHORT);
            }
        });*/
       //Starting from json object

        Call<Pokemonpojo> call = service.getPokemonsObj();
        call.enqueue(new Callback<Pokemonpojo>() {
            @Override
            public void onResponse(Call<Pokemonpojo> call, Response<Pokemonpojo> response) {
                ArrayList<Pokemon> pokarray = new ArrayList<>();
                Pokemonpojo pokojo = response.body();

                try
                {
                    pokarray = new ArrayList<>(pokojo.getPokemon());
                    generateData(pokarray);

                }catch (NullPointerException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Pokemonpojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something wrong",Toast.LENGTH_SHORT);
            }
        });
    }

    public void generateData(ArrayList<Pokemon> pokes  /*pokemonList*/){
        /*ArrayList<Pokemon> pokes = (ArrayList<Pokemon>) pokemonList;*/
        recyadapter = new Recyadapter(pokes,getApplicationContext());
        @SuppressLint("WrongConstant") LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycle_poke);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(recyadapter);
    }
}
