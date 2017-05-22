package pl.edu.agh.bo.bees.parser;

import pl.edu.agh.bo.bees.input.*;

import java.io.*;

public class Parser {

    File filename;
    FileInputStream fileInputStream;
    BufferedReader br;
    String buffer;

    public Parser(File filename) {
        this.filename = filename;
    }

    public void parse(Input input) {
        openFile();

        try {
            // stockProviders amount
            buffer = br.readLine();
            while(buffer.charAt(0) == '#') {
                buffer = br.readLine();
            }
            int stockProvidersAmount = Integer.valueOf(buffer);
            input.setStockProvidersAmount(stockProvidersAmount);

            // factories amount
            buffer = br.readLine();
            while(buffer.charAt(0) == '#') {
                buffer = br.readLine();
            }
            int factoriesAmount = Integer.valueOf(buffer);
            input.setFactoriesAmount(factoriesAmount);

            // stockProvider position and size
            for (int i = 0; i < stockProvidersAmount; i++) {
                buffer = br.readLine();
                while(buffer.charAt(0) == '#') {
                    buffer = br.readLine();
                }
                String[] buf = buffer.split(" ");
                Position position = new Position(Integer.valueOf(buf[0]), Integer.valueOf(buf[1]));
                StockProvider sp = new StockProvider(Integer.valueOf(buf[2]), position);
                input.addStockProvider(i, sp);
            }

            // factories position and demands
            for(int i = 0; i < factoriesAmount; i++) {
                buffer = br.readLine();
                while(buffer.charAt(0) == '#') {
                    buffer = br.readLine();
                }
                String[] buf = buffer.split(" ");
                Position position = new Position(Integer.valueOf(buf[0]), Integer.valueOf(buf[1]));
                Factory factory = new Factory(position);
                input.addFactory(i, factory);
                for(int j = 2; j < buf.length; j += 2) {
                    Demand demand = new Demand(factory, buf[j], Integer.valueOf(buf[j+1]));
                    input.addDemand(demand);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        closeFile();
    }

    private void closeFile() {
        try {
            this.br.close();
            this.fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openFile() {
        try {
            this.fileInputStream = new FileInputStream(filename);
            this.br = new BufferedReader(new InputStreamReader(fileInputStream));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
