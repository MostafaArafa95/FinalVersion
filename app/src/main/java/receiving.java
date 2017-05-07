import com.example.mostafaarafa.finalversion.MainActivity;

/**
 * Created by Mostafa Arafa on 4/26/2017.
 */

public class receiving {
    MainActivity activity;
    public receiving(MainActivity _activity){
    activity=_activity;
    }
    public void takeMessage(String message){
        activity.receive(message);


    }
}
