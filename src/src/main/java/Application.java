import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;

public class Application {

    public static void main(String[] args) {

        System.out.println("--------PRIMITIVE EXAMPLES------------------");

        // Serialization
        Gson gson = new Gson();
        System.out.println(gson.toJson(1));          // ==> 1
        System.out.println(gson.toJson("abcd"));       // ==> "abcd"
        System.out.println(gson.toJson(10L));          // ==> 10
        int[] values = { 1 };
        System.out.println(gson.toJson(values));     // ==> [1]

        System.out.println("--------------------------");

        // Deserialization
        System.out.println(gson.fromJson("1", int.class));
        System.out.println(gson.fromJson("1", Integer.class));
        System.out.println(gson.fromJson("1", Long.class));
        System.out.println(gson.fromJson("false", Boolean.class));
        System.out.println(gson.fromJson("\"abc\"", String.class));
        System.out.println(gson.fromJson("[\"abc\"]", String[].class));

        System.out.println("--------------------------");
        System.out.println("---------OBJECT EXAMPLES-----------------");

        // Serialization
        BagOfPrimitives obj = new BagOfPrimitives();
        String json = gson.toJson(obj);
        System.out.println(json);

        System.out.println("--------------------------");

        //Nested classes
        UpperClass upperClass = new UpperClass();
        upperClass.upperField = "Hello";

        UpperClass.NestedClass nestedClass = new UpperClass.NestedClass();
        nestedClass.nestedField = "World!";

        Gson gsonNested = new GsonBuilder().setPrettyPrinting().create();
        String jsonUpperClass = gsonNested.toJson(upperClass);
        String jsonNestedClass = gsonNested.toJson(nestedClass);

        System.out.println("Serialized UpperClass:");
        System.out.println(jsonUpperClass);
        System.out.println("\nSerialized NestedClass:");
        System.out.println(jsonNestedClass);

        //Deserialize
        UpperClass deserializedUpper = gsonNested.fromJson(jsonUpperClass, UpperClass.class);
        //Here it will fail in case the nested class is not static
        UpperClass.NestedClass deserializedNested = gsonNested.fromJson(jsonNestedClass, UpperClass.NestedClass.class);

        System.out.println("\nDeserialized Upper Class: upperField = " + deserializedUpper.upperField);
        System.out.println("Deserialized Nested Class: nestedField = " + deserializedNested.nestedField);

        System.out.println("--------------------------");

        System.out.println("---------ARRAY EXAMPLES-----------------");

        int[] ints = {1, 2, 3, 4, 5};
        String[] strings = {"abc", "def", "ghi"};

        // Serialization
        System.out.println(gson.toJson(ints));
        System.out.println(gson.toJson(strings));

        // Deserialization
        System.out.println(Arrays.toString(gson.fromJson("[1,2,3,4,5]", int[].class)));


    }
}
