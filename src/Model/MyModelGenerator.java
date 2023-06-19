package Model;

public class MyModelGenerator {
    static MyModel model;
    public static MyModel generateMyModel()
    {
        if(model == null)
        {
            model=new MyModel();
        }
        return model;
    }
}
