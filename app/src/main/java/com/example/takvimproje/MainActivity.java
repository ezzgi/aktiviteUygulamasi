package com.example.takvimproje;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static veritabanı veritabanim;
    ListView list_view;

    int images[]= {R.drawable.ic_note};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       list_view=findViewById(R.id.list_view);

       veritabanim= Room.databaseBuilder(getApplicationContext(),veritabanı.class,"notdb").allowMainThreadQueries().build();
       Button btn_notekle=findViewById(R.id.btnnotekle);

       btn_notekle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(getApplicationContext(),notekle.class);
               startActivity(intent);
           }
       });

        List<Not> notlar= MainActivity.veritabanim.dao().getNot();
        ArrayList arrayList=new ArrayList();

        ArrayList n=new ArrayList();
        ArrayList s=new ArrayList();
        ArrayList t=new ArrayList();



        String veriler="";

        for (Not nt:notlar){
            int id=nt.getId();
            String nnotsaat=nt.getNotsaat();
            String nnottarih=nt.getNottarih();
            String nnot=nt.getNot();

            veriler=veriler+nnot+nnotsaat+nnottarih;
            arrayList.add(veriler);
            veriler="";





            n.add(nnot);
            s.add(nnotsaat);
            t.add(nnottarih);
        }

        MyAdapter adapter=new MyAdapter(this, n,s,t,images);
        list_view.setAdapter(adapter);


        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);

      list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


              AlertDialog.Builder diyalogOlusturucu = new AlertDialog.Builder(MainActivity.this);


              long veriid=arrayAdapter.getItemId(position);
              String listeid=String.valueOf(veriid);


              diyalogOlusturucu.setMessage("Not Silinsin mi?")
                      .setCancelable(false)
                      .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                              veriSil(listeid);
                          }
                      }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                              dialogInterface.dismiss();
                          }
                      });
              diyalogOlusturucu.create().show();
          }
      });

    }
    private void veriSil(String listeid){


        List<Not> notlarsil=MainActivity.veritabanim.dao().getNot();
        ArrayList arrayListSil=new ArrayList();

        int veriler;
        for (Not silliste : notlarsil){
            int id = silliste.getId();
            veriler=id;
           arrayListSil.add(veriler);
           veriler=0;
        }
             String Sid=listeid;
        int id=Integer.valueOf(Sid);
        int eleman=(int) arrayListSil.get(id);

        Not not=new Not();
        not.setId(eleman);
        MainActivity.veritabanim.dao().notSil(not);

        Toast.makeText(getApplicationContext(),"Not Silindi.",Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }
        class MyAdapter extends ArrayAdapter<String >{
        Context context;
        ArrayList rNot;
        ArrayList rSaat;
        ArrayList rTarih;
        int rImg[];
        MyAdapter(Context c,ArrayList not, ArrayList saat, ArrayList tarih,int imgs[]){

            super(c, R.layout.custom_view, R.id.txtnot, not);
            this.context=c;
            this.rNot= not;
            this.rSaat=saat;
            this.rTarih=tarih;
            this.rImg=images;
        }
        @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

            LayoutInflater layoutInflater=(LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.custom_view, parent, false);
            ImageView images=row.findViewById(R.id.resim);
            TextView myNot=row.findViewById(R.id.txtnot);
            TextView mySaat=row.findViewById(R.id.txtsaat);
            TextView myTarih=row.findViewById(R.id.txttarih);


            images.setImageResource(rImg[0]);
            myNot.setText((CharSequence) rNot.get(position));
            mySaat.setText((CharSequence) rSaat.get(position));
            myTarih.setText((CharSequence) rTarih.get(position));

            return row;
        }


        }
    }