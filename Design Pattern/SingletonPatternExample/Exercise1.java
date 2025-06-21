class Logger{
    private static Logger instance;

    private Logger(){}

    public static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }

}

public class Exercise1 {
    public static void main(String[] args){
        Logger log = Logger.getInstance();
        Logger log2 = Logger.getInstance();

        System.out.println(log == log2);

    }
}