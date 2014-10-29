package mysword.test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by hyao on 8/8/2014.
 */
public class ScheduleTest {

    public static void test1() throws IOException {
        FileUtils.writeStringToFile(new File("E:\\Temp Folder\\Test1.txt"), DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS") + "\n", true);
    }

    public static void test2() throws IOException {
        FileUtils.writeStringToFile(new File("E:\\Temp Folder\\Test2.txt"), DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS") + "\n", true);
    }

    public void test3() throws IOException {
        FileUtils.writeStringToFile(new File("E:\\Temp Folder\\Test3.txt"), DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS") + "\n", true);
    }
}
