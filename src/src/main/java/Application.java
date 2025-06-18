import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

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

        System.out.println("---------COLLECTION EXAMPLES-----------------");

        Collection<Integer> collectionOfInts = Arrays.asList(1,2,3,4,5);

        // Serialization
        String collectionSerialization = gson.toJson(collectionOfInts);  // ==> [1,2,3,4,5]
        System.out.println("Collection Serialization: " + collectionSerialization);

        // Deserialization
        TypeToken<Collection<Integer>> collectionType = new TypeToken<Collection<Integer>>(){};
        System.out.println("Collection Type: " + collectionType);

        /*
        Gson can serialize collection of arbitrary objects but can not deserialize from it,
        because there is no way for the user to indicate the type of the resulting object.
         Instead, while deserializing, the Collection must be of a specific,
         generic type. This makes sense, and is rarely a problem when following good Java coding practices.
        */

        System.out.println("---------MAP EXAMPLES-----------------");

        Map<String, String> stringMap = new LinkedHashMap<>();
        stringMap.put("key", "value");
        stringMap.put(null, "null-entry");

        // Serialization
        // Print "null" (String) for null entries
        System.out.println("MAP Serialization: " + gson.toJson(stringMap));

        Map<Integer, Integer> intMap = new LinkedHashMap<>();
        intMap.put(2, 4);
        intMap.put(3, 6);

        // Serialization
        System.out.println("MAP Serialization after PUTs: " + gson.toJson(intMap));

        TypeToken<Map<String, String>> mapType = new TypeToken<Map<String, String>>(){};
        String jsonMap = "{\"key\": \"value\"}";

        // Deserialization
        // For older versions of gson need to use .getType()
        Map<String, String> mapDeserialization = gson.fromJson(jsonMap, mapType.getType());
        System.out.println("Map Deserialization: " + mapDeserialization);

        System.out.println("--------------------------");

        Gson gsonComplexKey = new GsonBuilder().enableComplexMapKeySerialization().create();
        Map<Person, Integer> complexMap = new LinkedHashMap<>();
        complexMap.put(new Person("Fabio", "Fucks"), 36);
        complexMap.put(new Person("Joao", "Silva"), 55);

        // Serialization; complex map is serialized as a JSON array containing key-value pairs (as JSON arrays)
        System.out.println("Json complex map: " + gsonComplexKey.toJson(complexMap));

        Map<String, String> stringMapComplex = new LinkedHashMap<>();
        stringMapComplex.put("key", "value");

        // Serialization; non-complex map is serialized as a regular JSON object
        System.out.println("Json non complex map: " + gson.toJson(stringMapComplex));


        System.out.println("----------GENERIC TYPES----------------");

        Generic<String> genericObj = new Generic<String>();

        Type genericType = new TypeToken<Generic<String>>() {}.getType();
        System.out.println("Serialize Generics: " + gson.toJson(genericObj, genericType));

        System.out.println("Deserialize Generics: " + gson.fromJson(json, genericType));

        System.out.println("------------------------------------------");

        System.out.println("----------COLLECTION TYPES----------------");

        Collection collectionObj = new ArrayList();
        collectionObj.add("hello");
        collectionObj.add(5);
        collectionObj.add(new Person("Fabio", "Fucks"));

        String jsonColection = gson.toJson(collectionObj);
        //Serialized
        System.out.println("Serialized Collection: " + jsonColection);

        //Deserialized
        System.out.println("Deserialized Collection: " + gson.fromJson(jsonColection, Collection.class));
    }
}
