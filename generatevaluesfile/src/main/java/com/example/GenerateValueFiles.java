package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * @author zhy
 * @date 15/5/3.
 */
public class GenerateValueFiles {

    private final int baseW;
    private final int baseH;

    private final String dirStr = "generatevaluesfile/res";

    private final static String WTemplate = "<dimen name=\"x{0}\">{1}px</dimen>\n";
    private final static String HTemplate = "<dimen name=\"y{0}\">{1}px</dimen>\n";

    /**
     * {0}-HEIGHT
     */
    private final static String VALUE_TEMPLATE = "values-{0}x{1}";

    private static final String SUPPORT_DIMESION =
            "720,1280;" +
            "720,1208;" +
            "800,1280;" +
            "1080,1794;" +
            "1080,1812;" +
            "1080,1920;" +
            "1080,2160;" +// mate 10 pro
            "1440,2416;" +
            "1440,2560;";// mate 10

    private String supportStr = SUPPORT_DIMESION;

    GenerateValueFiles(int baseX, int baseY, String supportStr) {
        this.baseW = baseX;
        this.baseH = baseY;

        if (!this.supportStr.contains(baseX + "," + baseY)) {
            this.supportStr += baseX + "," + baseY + ";";
        }

        this.supportStr += validateInput(supportStr);

        System.out.println("supportStr=" + supportStr);

        File dir = new File(dirStr);
        if (!dir.exists()) {
            dir.mkdir();
        }
        System.out.println(dir.getAbsoluteFile());

    }

    /**
     * @param supportStr w,h_...w,h;
     * @return
     */
    private String validateInput(String supportStr) {
        StringBuffer sb = new StringBuffer();
        String[] vals = supportStr.split("_");
        int w;
        int h;
        String[] wh;
        for (String val : vals) {
            try {
                if (val == null || val.trim().length() == 0) {
                    continue;
                }

                wh = val.split(",");
                w = Integer.parseInt(wh[0]);
                h = Integer.parseInt(wh[1]);
            } catch (Exception e) {
                System.out.println("skip invalidate params : w,h = " + val);
                continue;
            }
            sb.append(w + "," + h + ";");
        }

        return sb.toString();
    }

    private void generate() {
        String[] vals = supportStr.split(";");
        for (String val : vals) {
            String[] wh = val.split(",");
            generateXmlFile(Integer.parseInt(wh[0]), Integer.parseInt(wh[1]));
        }

    }

    private boolean limitX(int x) {
        return x == 3
                || x == 5
                || x == 7
                || x == 13
                || x == 15
                || x == 17
                || x == 19
                || x == 25
                || x == 27
                || x == 31
                || x == 43
                || x == 50
                || x == 53
                || x == 54
                || x == 55
                || x == 60
                || x == 63
                || x == 64
                || x == 67
                || x == 80
                || x == 82
                || x == 88
                || x == 90
                || x == 94
                || x == 100
                || x == 108
                || x == 110
                || x == 121
                || x == 130
                || x == 140
                || x == 148
                || x == 150
                || x == 165
                || x == 154
                || x == 170
                || x == 174
                || x == 180
                || x == 200
                || x == 223
                || x == 230
                || x == 240
                || x == 242
                || x == 270
                || x == 280
                || x == 290
                || x == 300
                || x == 320
                || x == 354
                || x == 364
                || x == 390
                || x == 400
                || x == 434
                || x == 439
                || x == 440;
    }

    private boolean limitY(int y) {
        return y == 1
                || y == 3
                || y == 5
                || y == 7
                || y == 11
                || y == 13
                || y == 15
                || y == 19
                || y == 21
                || y == 23
                || y == 25
                || y == 33
                || y == 35
                || y == 37
                || y == 44
                || y == 45
                || y == 49
                || y == 50
                || y == 54
                || y == 55
                || y == 60
                || y == 64
                || y == 70
                || y == 75
                || y == 80
                || y == 84
                || y == 85
                || y == 90
                || y == 94
                || y == 95
                || y == 100
                || y == 110
                || y == 120
                || y == 124
                || y == 130
                || y == 135
                || y == 140
                || y == 148
                || y == 150
                || y == 154
                || y == 155
                || y == 180
                || y == 190
                || y == 200
                || y == 220
                || y == 230
                || y == 245
                || y == 280
                || y == 300
                || y == 336
                || y == 450
                || y == 500;
    }


    private void generateXmlFile(int w, int h) {

        StringBuffer sbForWidth = new StringBuffer();
        sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sbForWidth.append("<resources>\n");
        float cellw = w * 1.0f / baseW;
        System.out.println("width : " + w + "," + baseW + "," + cellw);
        for (int i = 1; i < baseW; i++) {
            if (i <= 40 && i % 2 == 0) {
                sbForWidth.append(WTemplate.replace("{0}", i + "").replace("{1}", change(cellw * i) + ""));
            }
            if (limitX(i)) {
                sbForWidth.append(WTemplate.replace("{0}", i + "").replace("{1}", change(cellw * i) + ""));
            }
        }
        sbForWidth.append(WTemplate.replace("{0}", baseW + "").replace("{1}", w + ""));
        sbForWidth.append("</resources>");

        StringBuffer sbForHeight = new StringBuffer();
        sbForHeight.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sbForHeight.append("<resources>\n");
        float cellh = h * 1.0f / baseH;
        System.out.println("height : " + h + "," + baseH + "," + cellh);
        for (int i = 1; i < baseH; i++) {
            if (i <= 40 && i % 2 == 0) {
                sbForHeight.append(HTemplate.replace("{0}", i + "").replace("{1}", change(cellh * i) + ""));
            }
            if (limitY(i)) {
                sbForHeight.append(HTemplate.replace("{0}", i + "").replace("{1}", change(cellh * i) + ""));
            }
        }
        sbForHeight.append(HTemplate.replace("{0}", baseH + "").replace("{1}", h + ""));
        sbForHeight.append("</resources>");

        File fileDir = new File(dirStr + File.separator + VALUE_TEMPLATE.replace("{0}", h + "")
                .replace("{1}", w + ""));
        fileDir.mkdir();

        File layxFile = new File(fileDir.getAbsolutePath(), "lay_x.xml");
        File layyFile = new File(fileDir.getAbsolutePath(), "lay_y.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(layxFile));
            pw.print(sbForWidth.toString());
            pw.close();
            pw = new PrintWriter(new FileOutputStream(layyFile));
            pw.print(sbForHeight.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static float change(float a) {
        int temp = (int) (a * 100);
        return temp / 100f;
    }


    /*** 基准 640 x 480*/
    public static void main(String[] args) {
        System.out.println("args.length=" + args.length);
        int baseW = 480;
        int baseH = 640;
        String addition = "";
        try {
            if (args.length >= 3) {
                baseW = Integer.parseInt(args[0]);
                baseH = Integer.parseInt(args[1]);
                addition = args[2];
            } else if (args.length >= 2) {
                baseW = Integer.parseInt(args[0]);
                baseH = Integer.parseInt(args[1]);
            } else if (args.length >= 1) {
                addition = args[0];
            }
        } catch (NumberFormatException e) {

            System.err
                    .println("right input params : java -jar xxx.jar width height w,h_w,h_..._w,h;");
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println("addition=" + addition + "\taddition.length()=" + addition.length());
        new GenerateValueFiles(baseW, baseH, addition).generate();
    }

}