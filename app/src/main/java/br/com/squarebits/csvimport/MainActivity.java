package br.com.squarebits.csvimport;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FileOutputStream fileOutputStream;
    private final static int REQUEST_PERMISSIONS_CODE = 128;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_PHONE_STATE},
                    REQUEST_PERMISSIONS_CODE);

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readCsv();


//        rawToFile();
//
//        try {
//            FileReader file = new FileReader(mFile);
//            BufferedReader buffer = new BufferedReader(file);
//            String line = "";
//            List<String> collums = new ArrayList<>();
//            List<Object> data = new ArrayList<>();
//
//            while ((line = buffer.readLine()) != null) {
//                StringBuilder sb = new StringBuilder("");
//                String[] str = line.split(",");
//                try {
//                    Object object = new Object();
//                    object.setCol1(str[0]);
//                    object.setCol2(str[1]);
//                    object.setCol3(str[2]);
//                    object.setCol4(str[3]);
//                    object.setCol5(str[4]);
//                    object.setCol6(str[5]);
//                    data.add(object);
//                }catch (Exception e){
//
//                }
//            }
//
//        }catch (Exception e){
//            Log.e("e",e.getMessage());
//        }
    }


//
List<String[]> questionList;
    public final List<String[]> readCsv() {
        questionList = new ArrayList<String[]>();

        try {
            File tFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ndata.csv");

            InputStream csvStream = new FileInputStream(tFile);
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);
            CSVReader csvReader = new CSVReader(csvStreamReader);
            String[] line;

            // throw away the header
            csvReader.readNext();

            while ((line = csvReader.readNext()) != null) {
                questionList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionList;
    }


    File mFile;
    public void rawToFile(){
        mFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "data.csv");
        try {
            InputStream inputStream = new FileInputStream(mFile);

                    //getResources().openRawResource(R.raw.relatorio_inscricoes);
            fileOutputStream = new FileOutputStream(mFile);

            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                fileOutputStream.write(buf, 0, len);
            }

            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e1) {
            Log.e("e",e1.getMessage());

        }
    }


    public void Append(View v) {

        try {

            String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
            String fileName = "ndata.csv";
            String filePath = baseDir + File.separator + fileName;
            File f = new File(filePath );
            CSVWriter writer;


            if(f.exists() && !f.isDirectory()){
                FileWriter mFileWriter = new FileWriter(filePath , true);
                writer = new CSVWriter(mFileWriter);
            }
            else {
                writer = new CSVWriter(new FileWriter(filePath));
            }


            writer.writeNext(questionList.get(0));
            writer.writeNext(questionList.get(1));
            writer.writeNext(questionList.get(2));
            writer.writeNext(questionList.get(3));

            writer.close();


        } catch (Exception e) {
            Log.e("e",e.getMessage());

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_CODE:
                for (int i = 0; i < permissions.length; i++) {

                    if (permissions[i].equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "Favor habilitar a permissão para usar o aplicativo!", Toast.LENGTH_LONG).show();
                        return;
                    } else if (permissions[i].equalsIgnoreCase(Manifest.permission.READ_EXTERNAL_STORAGE)
                            && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "Favor habilitar a permissão para usar o aplicativo!", Toast.LENGTH_LONG).show();
                        return;
                    } else if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)
                            && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "Favor habilitar a permissão para usar o aplicativo!", Toast.LENGTH_LONG).show();
                        return;
                    } else if (permissions[i].equalsIgnoreCase(Manifest.permission.READ_PHONE_STATE)
                            && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "Favor habilitar a permissão para usar o aplicativo!", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
