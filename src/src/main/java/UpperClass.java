

public class UpperClass {

    public String upperField;

    //Needs to be static otherwise Gson does not know how to deserialize (or manualy telling how to instantiate)
    public static class NestedClass {
        public String nestedField;

        public NestedClass() {

        }
    }
}
