import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Allan Wang on 2016-11-30.
 * <p>
 * Some tester code using small images
 */
public class Comp202A4Tester {

    private final String filename;
    private final TYPE type;
    private Image image;
    private static final String prefix = "C202A4TAW/"; //just so I don't overwrite any of your own files

    public static void main(String[] args) throws Exception {
        //The methods below return true for a valid response, and false otherwise
        //To help debug, no extra exceptions are caught; if something crashes, it may be worth looking into
        if (!testPixel()) return;
        if (!testIO()) return;
        File testFolder = new File(prefix);
        testFolder.mkdirs();
        if (!testBasic()) return;
        if (!testMultiComment()) return;
        if (!testNoComment()) return;
        if (!testFlipH()) return;
        if (!testFlipV()) return;
        if (!testInvalidCropping()) return;
        print("All tests passed");
        if (!delete(testFolder)) print("Could not delete test dir %s\nPlease delete it yourself", prefix);
    }

    private static boolean testPixel() {
        int[][] values = {
                {-1, 2, 2},
                {3, 3, 999},
                {-1},
                {333}
        };
        for (int[] params : values) {
            try {
                if (params.length == 1) new Pixel(params[0]);
                else new Pixel(params[0], params[1], params[2]);
                print("Pixel test failed; make sure you throw IllegalArgumentExceptions for invalid pixel values");
                return false;
            } catch (IllegalArgumentException e) {
                //this is expected
            }
        }
        try {
            new Pixel(0, 255, 255);
            new Pixel(0);
            new Pixel(255);
        } catch (IllegalArgumentException e) {
            print("Pixel test failed; 0 and 255 are valid values for pixels");
            return false;
        }
        return true;
    }

    private static boolean testIO() {
        try {
            new Comp202A4Tester("tio", TYPE.INVALID).read(); //unless you really have a file with this name, it should crash
            print("Do not catch IOException for read and write in ImageFileUtilities; use throws IOException in the header");
            return false;
        } catch (IOException io) {
            return true;
        }
    }

    private static boolean testBasic() throws IOException {
        Image snapshot = new Image("# basic test", 222, createPixels(FORMAT.NORMAL, 3, 4));
        Comp202A4Tester test = new Comp202A4Tester("basic", TYPE.PGM)
                .setImage(snapshot)
                .create();
        boolean compare = test.read().compare(snapshot);
        if (!compare) print("Read write failed; make sure you keep the same format as the file you are reading");
        return compare;
    }

    private static boolean testMultiComment() throws IOException {
        Image snapshot = new Image("# asdf\n# fdsa\n# 9is0d**s", 222, createPixels(FORMAT.NORMAL, 3, 4));
        return testBase("multi", TYPE.PGM, snapshot, snapshot, "Multiline failed; make sure you read every line that starts with a '#'");
    }

    private static boolean testNoComment() throws IOException {
        Image snapshot = new Image("", 222, createPixels(FORMAT.NORMAL, 3, 4));
        return testBase("none", TYPE.PGM, snapshot, snapshot, "No comment failed; it is possible for an image to have no comment/metadata");
    }

    private static boolean testFlipH() throws IOException {
        Image snapshot = new Image("# horizontal flip", 222, createPixels(FORMAT.FLIPH, 3, 8));
        Image test = new Image("# horizontal flip", 222, createPixels(FORMAT.NORMAL, 3, 8));
        test.flip(true);
        return testBase("flipH", TYPE.PGM, snapshot, test, "Horizontal flip failed");
    }

    private static boolean testFlipV() throws IOException {
        Image snapshot = new Image("# vertical flip", 222, createPixels(FORMAT.FLIPV, 3, 8));
        Image test = new Image("# vertical flip", 222, createPixels(FORMAT.NORMAL, 3, 8));
        test.flip(false);
        return testBase("flipV", TYPE.PGM, snapshot, test, "Vertical flip failed");
    }

    private static boolean testBase(String fileName, TYPE type, Image snapshot, Image image, String fail) throws IOException {
        Comp202A4Tester test = new Comp202A4Tester(fileName, type)
                .setImage(image)
                .create();
        boolean compare = test.read().compare(snapshot);
        if (!compare) print(fail);
        return compare;
    }

    private static boolean testInvalidCropping() {
        Image test = new Image("# crop test", 222, createPixels(FORMAT.NORMAL, 3, 8));
        int[][] values = {
                {-1, 2, 3, 3},
                {3, -1, 2, 2},
                {3, 3, 2, 2}, //this one is arguable, but unless you considered having an end less than start it won't work
                {3, 3, 3, 3},
                {3, 99, 1, 3},
                {1, 1, 7, 7}
        };
        for (int[] params : values) {
            try {
                test.crop(params[0], params[1], params[2], params[3]);
                print("Invalid crops failed; make sure you throw IllegalArgumentExceptions with the arguments are not valid for the crop method");
                print("\t(In my opinion, startX cannot equal endX, so the test should fail on this case as well)");
                return false;
            } catch (IllegalArgumentException e) {
                //This is expected
            }
        }
        return true;
    }

    private Comp202A4Tester(String f, TYPE t) {
        filename = f;
        type = t;
    }

    private static Pixel[][] createPixels(FORMAT format, int height, int width) {
        Pixel[][] pixels = new Pixel[height][width];
        int c = 1;
        switch (format) {
            case NORMAL:
                for (int i = 0; i < height; i++)
                    for (int j = 0; j < width; j++) {
                        pixels[i][j] = new Pixel(c % 255);
                        c++;
                    }
                break;
            case FLIPH:
                for (int i = 0; i < height; i++)
                    for (int j = width - 1; j >= 0; j--) {
                        pixels[i][j] = new Pixel(c % 255);
                        c++;
                    }
                break;
            case FLIPV:
                for (int i = height - 1; i >= 0; i--)
                    for (int j = 0; j < width; j++) {
                        pixels[i][j] = new Pixel(c % 255);
                        c++;
                    }
                break;
            case RANDOM:
                Random rnd = new Random();
                for (int i = 0; i < height; i++)
                    for (int j = 0; j < width; j++) {
                        pixels[i][j] = new Pixel(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
                    }
                break;
        }
        return pixels;
    }

    private enum FORMAT {
        NORMAL, FLIPH, FLIPV, RANDOM;
    }

    private enum TYPE {
        PNM(".pnm"), PGM(".pgm"), INVALID(".bad");
        String type;

        TYPE(String s) {
            type = s;
        }
    }

    private Comp202A4Tester setImage(String metadata, int maxRange, Pixel[][] data) {
        image = new Image(metadata, maxRange, data); //I won't be testing maxRange
        return this;
    }

    private Comp202A4Tester setImage(Image image) {
        this.image = image;
        return this;
    }

    private boolean compare(Image image) {
        if (this.image.getHeight() != image.getHeight()) {
            print("\tExpected height: %d, Returned height: %d", image.getHeight(), this.image.getHeight());
            return false;
        }
        if (this.image.getWidth() != image.getWidth()) {
            print("\tExpected width: %d, Returned width: %d", image.getWidth(), this.image.getWidth());
            return false;
        }
        if (this.image.getMaxRange() != image.getMaxRange()) {
            print("\tExpected maxRange: %d, Returned maxRange: %d", image.getMaxRange(), this.image.getMaxRange());
            return false;
        }
        if (!this.image.getMetadata().trim().equals(image.getMetadata().trim())) {
            print("\tExpected metadata: %s, Returned metadata: %s", image.getMetadata(), this.image.getMetadata());
            return false;
        }
        for (int i = 0; i < image.getHeight(); i++)
            for (int j = 0; j < image.getWidth(); j++) {
                Pixel mine = this.image.getPixel(i, j);
                Pixel theirs = image.getPixel(i, j);
                if (mine.getRed() != theirs.getRed() ||
                        mine.getGreen() != theirs.getGreen() ||
                        mine.getBlue() != theirs.getBlue()) {
                    print("\tPixel colour mismatch");
                    return false;
                }
            }
        return true;
    }

    private Comp202A4Tester create() throws IOException {
        if (image == null) {
            print("No image specified");
            return this;
        }
        if (type == TYPE.PNM) ImageFileUtilities.writePnm(image, filename());
        else ImageFileUtilities.writePgm(image, filename());
        return this;
    }

    private Comp202A4Tester read() throws IOException {
        image = ImageFileUtilities.read(filename());
        return this;
    }

    private String filename() {
        return prefix + filename + type.type;
    }

    private static void print(String s, Object... o) {
        System.out.println(String.format(Locale.CANADA, s, o));
    }

    private static boolean delete(File file) {

        File[] flist = null;

        if (file == null) {
            return false;
        }

        if (file.isFile()) {
            return file.delete();
        }

        if (!file.isDirectory()) {
            return false;
        }

        flist = file.listFiles();
        if (flist != null && flist.length > 0) {
            for (File f : flist) {
                if (!delete(f)) {
                    return false;
                }
            }
        }

        return file.delete();
    }
}
