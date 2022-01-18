import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        String[] arguments = {"-d", "c:\\npw", "-l", "30G"};
        ParametersBag bag = new ParametersBag(args);

        String folderPath = bag.getPath();
        long sizeLimit = bag.getLimit();
        File file = new File(folderPath);
        Node root = new Node(file, sizeLimit);//убрать для домашки

        long start = System.currentTimeMillis();

        FolderSizeCalculator calculator = new FolderSizeCalculator(root);//заменить на file
        ForkJoinPool pool = new ForkJoinPool();
        //long size = pool.invoke(calculator);
        pool.invoke(calculator);
        System.out.println(root);
        //System.out.println(getFolderSize(file));
        long duration = System.currentTimeMillis() - start;
        System.out.println(duration + " ms");
    }
}
