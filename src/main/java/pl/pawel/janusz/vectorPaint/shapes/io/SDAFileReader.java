package pl.pawel.janusz.vectorPaint.shapes.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

//klasa do odczytu plików
public class SDAFileReader {

    private File file;

    public SDAFileReader(File file) {
        this.file = file;
    }

    public List<String> readFile() {
        BufferedReader br = null;
        List<String> list = new LinkedList<>();
        try {
            System.out.println(file.getAbsolutePath());
            java.io.FileReader fileReader = new java.io.FileReader(file); // czyta krokami
            br = new BufferedReader(fileReader);

            //czytanie paczkami danych
            //char[] buffer = new char[1024]
            //int isSuccess = fileReader.read(buffer);

            String nextLine = br.readLine();
            while (nextLine != null) { // lecimy po wszystkich linijkach
                System.out.println(nextLine);

                list.add(nextLine);
                nextLine = br.readLine();

            }

            System.out.println("Koniec pliku");
            br.close(); //zawsze zamykamy sesję otwierania pliku
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
