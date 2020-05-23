package Game;

public class Random {
    public static float RandomFloat(){
        return (float) Math.random();
    }

    public static float RandomFloatRange(float min, float max){
        return (float) ((Math.random() * ((max-min)+1)) + min);
    }

    public static int RandomInt(){
        return (int) (Math.random() * 10);
    }

    public static int RandomIntRange(int min, int max){
        return (int) (Math.random() * ((max-min)+1) + min);
    }
}
