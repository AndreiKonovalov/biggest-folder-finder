import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderSizeCalculator extends RecursiveTask<Long> {
    //private final File folder;
    private Node node;

    public FolderSizeCalculator(Node node) {
        //this.folder = folder;
        this.node = node;
    }

    @Override
    protected Long compute() {
        File folder = node.getFolder();//убрать для домашки!!!!
        if (folder.isFile()) {
            long length = folder.length();
            node.setSize(length);
            return length;
        }
        long sum = 0;
        List<FolderSizeCalculator> subTasks = new LinkedList<>();
        File[] files = folder.listFiles();
        for (File file : files) {
            Node child = new Node(file);//убрать для домашки
            FolderSizeCalculator task = new FolderSizeCalculator(child);//заменить child на file
            task.fork(); // запустим асинхронно
            subTasks.add(task);
            node.addChild(child);//убрать для домашки
        }

        for (FolderSizeCalculator task : subTasks) {
            sum += task.join(); // дождёмся выполнения задачи и прибавим результат
        }
        node.setSize(sum);//убрать для домашки
        return sum;
    }

}



