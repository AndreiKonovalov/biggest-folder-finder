import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SizeCalculator {
    private static final char[] sizeMultiplier = {'B', 'K', 'M', 'G', 'T'};
    private static HashMap<Character, Integer> char2multiplier = getMultipliers();

    private static long getFolderSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }
        long sum = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            sum += getFolderSize(file);
        }
        return sum;
    }

    public static String getHumanReadableSize(long size) {
        int unit = 1024;
        if (size < unit) return size + " B";
        int exp = (int) (Math.log(size) / Math.log(unit));
        String pre = String.valueOf(("KMGT").charAt(exp - 1));
        return String.format("%.1f %sb", size / Math.pow(unit, exp), pre);
        //TODO: Второй вариант
//        for (int i = 0; i < sizeMultiplier.length; i++){
//            double value = size / Math.pow(1024, i);
//            if (value < 1024){
//                return Math.round(value) + "" + sizeMultiplier[i] + (i > 0 ? "b" : "");
//            }
//        }
//        return "Very big!";
    }

    public static long getSizeFromHumanReadable(String size) {
        long result = 0;
        Matcher matcher = Pattern.compile("(\\d+)(\\w+)").matcher(size);
        if (matcher.matches()) {
            switch (matcher.group(2)) {
                case "B", "b" -> result = Long.parseLong(matcher.group(1));
                case "Kb", "K" -> result = 1024 * Long.parseLong(matcher.group(1));
                case "Mb", "M" -> result = (long) (Math.pow(1024, 2) * Long.parseLong(matcher.group(1)));
                case "Gb", "G" -> result = (long) (Math.pow(1024, 3) * Long.parseLong(matcher.group(1)));
                case "Tb", "T" -> result = (long) (Math.pow(1024, 4) * Long.parseLong(matcher.group(1)));
            }
        }
        return result;
        //TODO: Второй вариант
//
//        char sizeFactor = size
//                .replaceAll("[0-9\\s+]+", "")
//                .charAt(0);
//        int multiplier = char2multiplier.get(sizeFactor);
//        long length = multiplier * Long.parseLong(size.replaceAll("[^0-9]",""));
//        return length;
    }

    private static HashMap<Character, Integer> getMultipliers() {
        HashMap<Character, Integer> char2multiplier = new HashMap<>();
        for (int i = 0; i < sizeMultiplier.length; i++) {
            char2multiplier.put(sizeMultiplier[i], (int) Math.pow(1024, i));
        }
        return char2multiplier;
    }
}
