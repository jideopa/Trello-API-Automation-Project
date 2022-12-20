package trelloutils;

import com.github.javafaker.Faker;

public class DataGenerator {

    public static DataGenerator dataGenerator = null;
    public Faker faker = new Faker();

    private DataGenerator(){

    }

    public  static DataGenerator getInstance (){
        if (dataGenerator==null){
            dataGenerator= new DataGenerator();

        }
        return dataGenerator;
    }
}
